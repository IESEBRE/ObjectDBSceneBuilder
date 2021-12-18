package com.iesebre.provascenebuilder.persistence.exceptions;

import java.util.HashMap;
import java.util.Map;

public class DAOException extends Exception{

    private Map<Integer, String> mis=new HashMap<Integer,String>();
    private Integer tipo;
    public DAOException(){
        mis.put(1,"Error al borrar a la base de dades!!");
        mis.put(2,"Error al tancar la base de dades!!");
        mis.put(3,"Error al guardar canvis a la base de dades!!");
        mis.put(4,"Error al llegir de la base de dades!!");
        mis.put(5,"");

    }
    public DAOException(Integer tipo) {
        this.tipo = tipo;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
