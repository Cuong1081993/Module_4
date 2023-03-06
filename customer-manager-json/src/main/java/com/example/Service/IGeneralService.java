package com.example.Service;

import java.util.Optional;

public interface IGeneralService<T> {
    Iterable<T> finAll();

    Optional<T> findById(Long id);
    T save(T t);

    void    remove(Long id);

}