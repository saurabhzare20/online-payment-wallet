package com.example.logs;

import java.sql.*;

public class getBalance {


    public static double getbalance(String user) throws ClassNotFoundException {

        String jdbcUrl = "jdbc:mysql://localhost:3306/paymentApp";
        String username = "root";
        String password = "0000";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
            String retrieveDataQuery = "SELECT balance FROM wallet WHERE user_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(retrieveDataQuery);
            preparedStatement.setString(1, user);
            System.out.println(preparedStatement);
            ResultSet res = preparedStatement.executeQuery();

           if( res.next()){
         return   res.getDouble("balance");}
           return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //System.out.println(res.getString("email_id"));



    }


    public static void main(String[] args) throws ClassNotFoundException {
        double a = getbalance("2");
        System.out.println(a);

    }

    }


