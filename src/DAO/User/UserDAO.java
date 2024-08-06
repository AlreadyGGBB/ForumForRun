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
    public boolean RegisterUser(String userName,String passWord) {
//         这里可以做密码的格式校验
        if (UserExisted(userName))
            return false;
         return jdbc.InsertOneRow("INSERT INTO user (userName,passWord) VALUES (?,?)",userName,passWord);
    }

    @Override
    public boolean UserLogin(String userName, String passWord) {
        User u = FindUserByUserName(userName);
        return u != null && u.getPassWord().equals(passWord);
    }
}
