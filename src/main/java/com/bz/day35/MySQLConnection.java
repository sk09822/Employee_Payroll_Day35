package com.bz.day35;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
    String url = "jdbc:mysql://localhost:3306/payroll_service";
    String username ="root";
    String password ="Sandip@123";

    public Connection getConnection(){
        Connection connection = null;
        try {
            connection =  DriverManager.getConnection(url,username,password);
            return connection;

        }catch (SQLException e ){
            e.printStackTrace();
        }
        return null;
    }

}

