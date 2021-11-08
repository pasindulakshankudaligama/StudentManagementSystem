package dao.custom;

import dao.CrudDAO;
import model.Class;
import view.tm.ClassTM;

import java.sql.SQLException;
import java.util.List;

public interface  ClassManage extends CrudDAO<Class,String, ClassTM> {

    List<String> getAllClassIds() throws SQLException, ClassNotFoundException;

    Class passClassDetails(String id) throws SQLException, ClassNotFoundException;
}
