package it.infotechconsults.bric48.backend.repository.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
import jakarta.persistence.criteria.Selection;

public class SpecificationBuilder<T> {
    public Specification<T> buildSpecification(FiltersDTO filters) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Apply simple filters
            if(filters.getCriterias() != null && !filters.getCriterias().isEmpty())
                for (FilterCriteriaDTO criteria : filters.getCriterias()) {
                    predicates.add(createPredicate(criteria, root, builder, query));
                }

            // Apply grouped filters, including nested groups
            if(filters.getGroups() != null && !filters.getGroups().isEmpty())
                for (FilterGroupDTO group : filters.getGroups()) {
                    Predicate predicate = buildGroupPredicate(group, root, builder, query);
                    if(predicate != null)
                        predicates.add(predicate);
                }

            // Set fields to load in select clause (projection)
            if (filters.getFields() != null && !filters.getFields().isEmpty()) {
                query.multiselect(
                        filters.getFields().stream()
                                .map(field -> getPath(root, field, query))
                                .toArray(Selection[]::new));
            }

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
        };
    }

    // Helper method to create individual predicates based on FilterCriteria
    private Predicate createPredicate(FilterCriteriaDTO criteria, Root<T> root, CriteriaBuilder builder,
            CriteriaQuery<?> query) {
        Path<?> path = getPath(root, criteria.getField(), query);
        switch (criteria.getOperation()) {
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
            if (isCollectionField(root, part)) {
                path = handleCollectionJoin(root, query, part);
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
            if(predicate != null)
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
    private boolean isCollectionField(Root<?> path, String field) {
        Class<?> fieldClass = path.getModel().getAttribute(field).getJavaType();
        return List.class.isAssignableFrom(fieldClass) || Set.class.isAssignableFrom(fieldClass);
    }

    // Handle joining on collection fields and returning the joined path
    private Path<?> handleCollectionJoin(Root<T> root, CriteriaQuery<?> query, String collectionField) {
        Join<?, ?> join = root.join(collectionField, JoinType.LEFT);
        query.distinct(true); // Avoid duplicate results when joining collections
        return join;
    }

}
