package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.ManageFee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Fee;
import model.StudentDetail;
import view.tm.FeeTM;
import view.tm.StudentDetailTM;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FeeController implements ManageFee {
    @Override
    public boolean add(Fee fee) throws SQLException, ClassNotFoundException {
        Connection con = null;
        try {
            con.setAutoCommit(false);
            if (CrudUtil.executeUpdate("INSERT INTO Fee VALUES(?,?)",fee.getFeeId(),fee.getAmount())) {
                if (saveStudentDetails(fee.getStudents())) {
                    con.commit();
                    return true;
                } else {
                    con.rollback();
                    return false;
                }
            } else {
                con.rollback();
                return false;
            }


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean delete(String feeId) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM Fee WHERE Fee_ID='" + feeId + "'");

    }

    @Override
    public boolean update(FeeTM fee) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE Fee SET Fee_Amount=? WHERE Fee_ID=?");

    }

    @Override
    public ArrayList<Fee> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ObservableList<FeeTM> getList() throws SQLException, ClassNotFoundException {
        ObservableList<FeeTM> fees = FXCollections.observableArrayList();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Fee");
        while (rst.next()) {
            fees.add(new FeeTM(
                    rst.getString(1),
                    rst.getInt(2)
            ));
        }
        return fees;
    }
    @Override
    public boolean saveStudentDetails(ArrayList<StudentDetail> students) throws SQLException, ClassNotFoundException {
        for (StudentDetail temp : students
        ) {
            return CrudUtil.executeUpdate
                    ("INSERT INTO Student_Detail VALUES(?,?,?,?,?,?)", temp.getRegisterId(), temp.getClassId(), temp.getFeeId(), temp.getCollectDate(), temp.getCollectTime(), temp.getAmount());
        }
        return true;
    }
    @Override
    public ObservableList<StudentDetailTM> getStudentsFeeList() throws SQLException, ClassNotFoundException {
        ObservableList<StudentDetailTM> students = FXCollections.observableArrayList();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Student_Detail");
        while (rst.next()) {
            students.add(new StudentDetailTM(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getDouble(6)
            ));
        }
        return students;
    }
}
