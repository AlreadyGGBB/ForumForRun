package DAO.User;


import DAO.jdbcUtil;
import Entity.User.User;

//用户类在数据库的操作
public interface IUser {
    public jdbcUtil jdbc = new jdbcUtil();
    //    根据用户名查找用户是否存在
//    返回一个布尔值
    public boolean UserExisted(String userName);

//    根据用户名返回一个具体用户用户
//    如果查找不到用户则返回一个空值
    public User FindUserByUserName(String userName);

//    校验用户登录的用户名密码是否正确
    public boolean UserLogin(String userName, String passWord);
}
