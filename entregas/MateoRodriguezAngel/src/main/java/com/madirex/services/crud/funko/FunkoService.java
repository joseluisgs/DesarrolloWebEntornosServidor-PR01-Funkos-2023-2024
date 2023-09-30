package com.madirex.services.crud.funko;

import com.madirex.exceptions.FunkoException;
import com.madirex.models.Funko;
import com.madirex.services.crud.BaseCRUDService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface FunkoService extends BaseCRUDService<Funko, FunkoException>{

    List<Funko> findByName(String nombre) throws SQLException;

    void backup(String path, String fileName) throws SQLException, IOException;

}