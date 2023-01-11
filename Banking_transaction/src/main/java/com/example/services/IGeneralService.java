package com.example.services;

import java.util.List;
import java.util.Optional;

public interface IGeneralService<T> {
    List<T> findAll();

    Optional<T> findById(Long id);

    T getById(Long id);

    T save(T t);

    void delete(T t);

    void deleteById(Long id);

    Boolean existsByIdEquals(long id);
}
