package DAO.UserDAO;

import Entity.User;

public interface UserDAO {
//    如果没有查询到则返回空值
    public User FindUserByName(String Username);
//    根据用户名查找用户是否存在
    public boolean isUserExisted(String Username);
//    判断用户输入用户名密码是否正确
    public boolean UserLogin(String userName,String passWord);
}
