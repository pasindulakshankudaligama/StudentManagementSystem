package dao.custom;

import dao.CrudDAO;
import javafx.collections.ObservableList;
import model.Fee;
import model.StudentDetail;
import view.tm.FeeTM;
import view.tm.StudentDetailTM;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ManageFee extends CrudDAO<Fee,String, FeeTM> {

    boolean saveStudentDetails(ArrayList<StudentDetail> students) throws SQLException, ClassNotFoundException;

    ObservableList<StudentDetailTM> getStudentsFeeList() throws SQLException, ClassNotFoundException;

}
