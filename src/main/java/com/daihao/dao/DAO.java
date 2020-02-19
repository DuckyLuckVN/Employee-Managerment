package com.daihao.dao;

import java.util.List;

public interface DAO<E>
{
    List<E> getAll();
    E findById();
    E insert(E e);
    E update(E e, Object ... param);
    boolean delete(Object e);
}
