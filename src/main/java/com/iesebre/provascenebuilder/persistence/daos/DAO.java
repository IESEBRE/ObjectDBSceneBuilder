package com.iesebre.provascenebuilder.persistence.daos;

import com.iesebre.provascenebuilder.persistence.exceptions.DAOException;
import java.util.List;

public interface DAO <T>{

    T get(Long id) throws DAOException;

    List<T> getAll() throws DAOException;

    void save(T obj) throws DAOException;

    void saveAll(List<T> data) throws DAOException;

    void clear() throws DAOException;

    boolean remove(T object) throws DAOException;

    void close() throws DAOException;

    void saveAndCopy(T object)  throws DAOException;

    //Tots els mètodes necessaris per interactuar en la BD

}
