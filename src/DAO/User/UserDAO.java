package DAO.User;

import Entity.User.User;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public class UserDao implements IUser {
    @Override
    public boolean UserExisted(String userName) {
        return !jdbc.QueryOneRow("SELECT userID FROM user WHERE userName = ?", userName).isEmpty();
    }

    @Override
    public User FindUserByUserName(String userName) {
        Map<String, Object> map = jdbc.QueryOneRow("SELECT userID,userName,passWord FROM user WHERE userName = ?", userName);
        if (map.isEmpty())
            return null;
        User u = new User();
        u.setUserID(((BigInteger) map.get("userID")).toString());
        u.setUserName((String) map.get("userName"));
        u.setPassWord((String) map.get("passWord"));
        return u;
    }

    @Override
    public boolean UserLogin(String userName, String passWord) {
        User u = FindUserByUserName(userName);
        return u != null && u.getPassWord().equals(passWord);
    }
}
