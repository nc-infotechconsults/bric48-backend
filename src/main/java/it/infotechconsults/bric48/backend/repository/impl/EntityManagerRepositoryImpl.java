package it.infotechconsults.bric48.backend.repository.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import it.infotechconsults.bric48.backend.repository.EntityManagerRepository;
import it.infotechconsults.bric48.backend.repository.specification.SpecificationBuilder;
import it.infotechconsults.bric48.backend.rest.dto.FiltersDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Selection;

@Component
public class EntityManagerRepositoryImpl<E> implements EntityManagerRepository<E> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<E> search(FiltersDTO filters, Pageable pageable, Class<E> clazz) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Tuple> query = builder.createTupleQuery();
        Root<E> root = query.from(clazz);

        var specBuilder = new SpecificationBuilder<E>();
        var predicate = specBuilder.generateWhereCondition(query, builder, root, filters);
        query.where(predicate);

        Map<String, Join<?, ?>> joins = new HashMap<>();
        List<Selection<?>> selections = new ArrayList<>();
        if (filters.getFields() != null && !filters.getFields().isEmpty()) {
            for (String field : filters.getFields()) {
                selections.add(getPath(root, field, joins).alias(field)); // Get nested path if required
            }
            query.multiselect(selections);
        }

        // Apply sorting from Pageable
        applySorting(query, root, joins, builder, pageable);

        TypedQuery<Tuple> tQuery = entityManager.createQuery(query);

        if (pageable.isPaged())
            tQuery.setFirstResult((int) pageable.getOffset())
                    .setMaxResults(pageable.getPageSize());

        List<Tuple> results = tQuery.getResultList();

        CriteriaQuery<Long> countQuery = specBuilder.buildCountCriteriaQuery(filters, builder, clazz);
        long total = entityManager.createQuery(countQuery).getSingleResult();

        List<E> entities = mapTuplesToEntities(results, filters.getFields(), clazz);

        return new PageImpl<>(entities, pageable, total);
    }

    private List<E> mapTuplesToEntities(List<Tuple> tuples, List<String> fieldsToSelect, Class<E> clazz) {
        List<E> entities = new ArrayList<>();

        for (Tuple tuple : tuples) {
            E entity;
            try {
                // Instantiate the entity type T using reflection
                entity = clazz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException("Failed to instantiate entity of type: " + clazz.getName(), e);
            }

            // Map each field from Tuple to T (or DTO) properties
            for (String field : fieldsToSelect) {
                Object value = tuple.get(field);
                setNestedFieldValue(entity, field, value); // Helper to set nested field by name
            }

            entities.add(entity);
        }

        return entities;
    }

    // Utility method to set a nested field value using reflection, including
    // collections
    private void setNestedFieldValue(Object target, String fieldPath, Object value) {
        try {
            String[] parts = fieldPath.split("\\.");
            Object currentObject = target;

            for (int i = 0; i < parts.length; i++) {
                String fieldName = parts[i];
                Field field = ReflectionUtils.findField(currentObject.getClass(), fieldName);
                field.setAccessible(true);

                if (i == parts.length - 1) { // Last part: set the value
                    if (Set.class.isAssignableFrom(field.getType())) {
                        Set set = (Set<?>) field.get(currentObject);
                        if (set == null) {
                            set = new HashSet<>();
                            field.set(currentObject, set);
                        }
                        set.add(value);
                    } else if(List.class.isAssignableFrom(field.getType())) { 
                        List set = (List<?>) field.get(currentObject);
                        if (set == null) {
                            set = new ArrayList<>();
                            field.set(currentObject, set);
                        }
                        set.add(value);
                    } else {
                        field.set(currentObject, value);
                    }
                } else { // Intermediate part: navigate to the next nested object
                    Object nestedObject = field.get(currentObject);
                    if (nestedObject == null) {
                        nestedObject = field.getType().getDeclaredConstructor().newInstance();
                        field.set(currentObject, nestedObject);
                    }
                    currentObject = nestedObject;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to map field: " + fieldPath, e);
        }
    }

    // Utility to handle nested field paths in CriteriaQuery and joins for
    // collections
    private Path<?> getPath(Root<?> root, String fieldPath, Map<String, Join<?, ?>> joins) {
        String[] parts = fieldPath.split("\\.");
        Path<?> path = root;
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            if (path instanceof Root<?> && ((Root<?>) path).getModel().getAttribute(part).isCollection()) {
                final Root<?> rootPath = (Root<?>) path;
                Join<?, ?> join = joins.computeIfAbsent(part, k -> rootPath.join(part, JoinType.LEFT));
                path = join;
            } else {
                path = path.get(part);
            }
        }
        return path;
    }

    // New method to apply sorting from Pageable
    private void applySorting(CriteriaQuery<Tuple> query, Root<E> root, Map<String, Join<?, ?>> joins,
            CriteriaBuilder builder, Pageable pageable) {
        if (pageable.getSort().isSorted()) {
            List<Order> orders = new ArrayList<>();
            for (Sort.Order sortOrder : pageable.getSort()) {
                Path<?> path = getPath(root, sortOrder.getProperty(), joins); // Get nested path if required
                orders.add(sortOrder.isAscending() ? builder.asc(path) : builder.desc(path));
            }
            query.orderBy(orders);
        }
    }

}
