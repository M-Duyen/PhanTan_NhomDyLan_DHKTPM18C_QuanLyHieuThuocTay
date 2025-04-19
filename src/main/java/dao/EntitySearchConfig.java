package dao;

import java.util.List;

public class EntitySearchConfig {
    private final Class<?> entityClass;
    private final List<String> searchFields;

    public EntitySearchConfig(Class<?> entityClass, List<String> searchFields) {
        this.entityClass = entityClass;
        this.searchFields = searchFields;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }

    public List<String> getSearchFields() {
        return searchFields;
    }
}