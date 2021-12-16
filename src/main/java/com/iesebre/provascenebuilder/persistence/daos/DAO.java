package com.iesebre.provascenebuilder.persistence.daos;


import com.iesebre.provascenebuilder.model.Person;
import com.iesebre.provascenebuilder.persistence.exceptions.DAOException;
import javafx.collections.ObservableList;

import java.util.List;

public interface DAO <T>{

    T get(Long id) throws DAOException;

    List<T> getAll() throws DAOException;

    void save(T obj) throws DAOException;

    void saveAll(List<T> data) throws DAOException;

    void clear() throws DAOException;

    //Tots els mètodes necessaris per interactuar en la BD

}
