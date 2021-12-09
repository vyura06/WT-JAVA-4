package com.example.hotel.repository;

import com.example.hotel.entity.Entity;
import com.example.hotel.exception.RepositoryException;
import com.example.hotel.specification.Specification;

import java.util.List;

public interface Repository< T extends Entity> {
    void add(T entity) throws RepositoryException;
    void remove(T entity) throws RepositoryException;
    void update(T entity) throws RepositoryException;

    List<T> query(Specification specification) throws RepositoryException;
}
