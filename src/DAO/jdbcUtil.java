package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class jdbcUtil {
    private Connection conn = null;

    private final String url = "jdbc:mysql://127.0.0.1:3306/iweb?useUnicode=true&characterEncoding=UTF-8";

    private final String user = "root";

    private final String password = "1415926";

    private void init() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url,user,password);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("DataBase Connection Failed:" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void close() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("DaraBase Connection Close Failed:" + e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    public Map<String,Object> QueryOneRow(String sql, Object... params) {
        init();
        try (PreparedStatement state = conn.prepareStatement(sql)) {
            setParams(state,params);
            ResultSet resultSet = state.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columCount = metaData.getColumnCount();
            Map<String,Object> map = new HashMap<>();

            while (resultSet.next()){
                for (int i = 1; i <= columCount; i++)
                    map.put(metaData.getColumnName(i), resultSet.getObject(i));
            }
            close();
            return map;

        } catch (SQLException e) {
            System.out.println("Query Exception: "+e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public boolean InsertOneRow(String sql,Object... params){
        init();
        try (PreparedStatement state = conn.prepareStatement(sql)){
            setParams(state,params);
            return state.execute();
        } catch (SQLException e) {
            System.out.println("Insert Exception: "+e.getMessage());
            throw new RuntimeException(e);
        }finally {
            close();
        }
    }

    private void setParams(PreparedStatement state,Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++){
            if (params[i].getClass() == String.class)
                state.setString(i+1,(String) params[i]);
            if (params[i].getClass() == Integer.class)
                state.setInt(i+1,(Integer) params[i]);
        }
    }
}
