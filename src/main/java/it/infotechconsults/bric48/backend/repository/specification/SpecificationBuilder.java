package it.infotechconsults.bric48.backend.repository.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.data.jpa.domain.Specification;

import it.infotechconsults.bric48.backend.rest.dto.FilterCriteriaDTO;
import it.infotechconsults.bric48.backend.rest.dto.FilterGroupDTO;
import it.infotechconsults.bric48.backend.rest.dto.FiltersDTO;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class SpecificationBuilder<T> {

    public CriteriaQuery<Long> buildCountCriteriaQuery(FiltersDTO filters, CriteriaBuilder builder, Class<T> clazz) {
        CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
        Root<T> root = countQuery.from(clazz);
        Predicate wherePredicate = generateWhereCondition(countQuery, builder, root, filters);
        return countQuery.select(builder.count(root)).where(wherePredicate);
    }

    public Specification<T> buildSpecification(FiltersDTO filters) {
        return (root, query, builder) -> {
            return generateWhereCondition(query, builder, root, filters);
        };
    }

    public Predicate generateWhereCondition(CriteriaQuery<?> query, CriteriaBuilder builder, Root<T> root,
            FiltersDTO filters) {
        List<Predicate> predicates = new ArrayList<>();

        // Apply simple filters
        if (filters.getCriterias() != null && !filters.getCriterias().isEmpty())
            for (FilterCriteriaDTO criteria : filters.getCriterias()) {
                predicates.add(createPredicate(criteria, root, builder, query));
            }

        // Apply grouped filters, including nested groups
        if (filters.getGroups() != null && !filters.getGroups().isEmpty())
            for (FilterGroupDTO group : filters.getGroups()) {
                Predicate predicate = buildGroupPredicate(group, root, builder, query);
                if (predicate != null)
                    predicates.add(predicate);
            }

        if (filters.getOperator() != null)
            switch (filters.getOperator()) {
                case AND -> {
                    return builder.and(predicates.toArray(new Predicate[0]));
                }
                case OR -> {
                    return builder.or(predicates.toArray(new Predicate[0]));
                }
                default -> {
                    return null;
                }
            }
        else
            return builder.and(predicates.toArray(new Predicate[0]));
    }

    // Helper method to create individual predicates based on FilterCriteria
    private Predicate createPredicate(FilterCriteriaDTO criteria, Root<T> root, CriteriaBuilder builder,
            CriteriaQuery<?> query) {
        Path<?> path = getPath(root, criteria.getField(), query);
        switch (criteria.getOperation()) {
            case IS_NULL:
                return builder.isNull(path);
            case EQUAL:
                return builder.equal(path, criteria.getValue());
            case EQUAL_IGNORECASE:
                return builder.equal(builder.lower(path.as(String.class)),
                        criteria.getValue().toString().toLowerCase());
            case GT:
                return builder.greaterThan(path.as(String.class), criteria.getValue().toString());
            case GTE:
                return builder.greaterThanOrEqualTo(path.as(String.class), criteria.getValue().toString());
            case LT:
                return builder.lessThan(path.as(String.class), criteria.getValue().toString());
            case LTE:
                return builder.lessThanOrEqualTo(path.as(String.class), criteria.getValue().toString());
            case LIKE:
                return builder.like(path.as(String.class), "%" + criteria.getValue() + "%");
            case ILIKE:
                return builder.like(builder.lower(path.as(String.class)),
                        "%" + criteria.getValue().toString().toLowerCase() + "%");
            default:
                throw new UnsupportedOperationException("Operation not supported: " + criteria.getOperation());
        }
    }

    // Helper method to handle nested paths, including collection joins
    private Path<?> getPath(Root<T> root, String fieldPath, CriteriaQuery<?> query) {
        String[] parts = fieldPath.split("\\.");
        Path<?> path = root;

        for (String part : parts) {
            if (isCollectionField(path, part)) {
                path = handleCollectionJoin(path, query, part);
            } else {
                path = path.get(part);
            }
        }
        return path;
    }

    // Recursive method to build predicate for a group, including nested groups
    private Predicate buildGroupPredicate(FilterGroupDTO group, Root<T> root, CriteriaBuilder builder,
            CriteriaQuery<?> query) {
        List<Predicate> groupPredicates = new ArrayList<>();

        // Add criteria in this group
        for (FilterCriteriaDTO criteria : group.getCriterias()) {
            groupPredicates.add(createPredicate(criteria, root, builder, query));
        }

        // Recursively add nested groups
        for (FilterGroupDTO nestedGroup : group.getGroups()) {
            Predicate predicate = buildGroupPredicate(nestedGroup, root, builder, query);
            if (predicate != null)
                groupPredicates.add(predicate);
        }

        // Combine predicates based on group operator
        switch (group.getOperator()) {
            case AND -> {
                return builder.and(groupPredicates.toArray(new Predicate[0]));
            }
            case OR -> {
                return builder.or(groupPredicates.toArray(new Predicate[0]));
            }
            default -> {
                return null;
            }
        }
    }

    // Check if the field represents a collection (OneToMany or ManyToMany
    // relationship)
    private boolean isCollectionField(Path<?> path, String field) {
        Class<?> fieldClass = Stream.of(path.getJavaType().getDeclaredFields()).filter(x -> x.getName().equals(field)).findFirst().get().getType();
        return List.class.isAssignableFrom(fieldClass) || Set.class.isAssignableFrom(fieldClass);
    }

    // Handle joining on collection fields and returning the joined path
    private Path<?> handleCollectionJoin(Path<?> root, CriteriaQuery<?> query, String collectionField) {
        Join<?, ?> join = null;
        if (root instanceof Root)
            join = ((Root<?>) root).join(collectionField, JoinType.LEFT);
        else if (root instanceof Root)
            join = ((Join<?, ?>) root).join(collectionField, JoinType.LEFT);

        if (join != null) {
            query.distinct(true); // Avoid duplicate results when joining collections
            return join;
        }

        return root;
    }

}
