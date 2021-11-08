package dao.custom;

import dao.CrudDAO;
import model.User;
import view.tm.UserTM;

import java.sql.SQLException;

public interface UserManage extends CrudDAO<User, String, UserTM> {
    String login(User u) throws SQLException, ClassNotFoundException;
}
