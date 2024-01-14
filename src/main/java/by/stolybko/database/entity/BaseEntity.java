package by.stolybko.database.entity;

import java.io.Serializable;

public interface BaseEntity<T extends Serializable> {

    void setUuid(T uuid);

    T getUuid();
}