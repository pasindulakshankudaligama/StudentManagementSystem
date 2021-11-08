package dao;

import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<T, ID, TM> {
    boolean add(T t) throws SQLException, ClassNotFoundException;

    boolean delete(ID id) throws SQLException, ClassNotFoundException;

    boolean update(TM t) throws SQLException, ClassNotFoundException;

    ArrayList<T> getAll() throws SQLException, ClassNotFoundException;

    ObservableList<TM> getList() throws SQLException, ClassNotFoundException;
}
