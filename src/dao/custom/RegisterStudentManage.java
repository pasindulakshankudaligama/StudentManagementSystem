package dao.custom;

import dao.CrudDAO;
import model.RegisterStudent;
import view.tm.RegisterStudentTM;

import java.sql.SQLException;
import java.util.List;

public interface RegisterStudentManage extends CrudDAO<RegisterStudent, String, RegisterStudentTM> {

    List<String> getRegisteredStudentIds() throws SQLException, ClassNotFoundException;

    List<RegisterStudent> searchRegisterStudent(String value) throws SQLException, ClassNotFoundException;

    String getRegisterId() throws SQLException, ClassNotFoundException;

    RegisterStudent passRegisteredStudentDetails(String id) throws SQLException, ClassNotFoundException;

}
