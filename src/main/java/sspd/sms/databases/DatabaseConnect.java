package sspd.sms.databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnect {

    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/smsdb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    private static DatabaseConnect instance = null;
    private static Connection con = null;


    private DatabaseConnect() {


        try {

            Class.forName(DB_DRIVER);

            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            if(con != null) {

                System.out.println("Connected successfully");
            }
            else {
                System.out.println("Failed to connect to database");
            }


        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);


        }

    }

    public static DatabaseConnect getInstance() {

        if(instance == null) {

            instance = new DatabaseConnect();

        }

        return instance;


    }

    public  Connection getConnection(){

       return con;

    }



}
