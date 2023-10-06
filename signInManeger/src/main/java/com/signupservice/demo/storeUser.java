package com.signupservice.demo;

import java.sql.*;
import java.util.Map;

public class storeUser {

    public static ResultSet establishConnection(String jdbcUrl, String username, String password, String quary) throws SQLException {


        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
            //String retrieveDataQuery = "SELECT * FROM wallet"; // Replace with your table name
            PreparedStatement preparedStatement = connection.prepareStatement(quary);
            ResultSet res = preparedStatement.executeQuery();

            return res;


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static boolean cheakUsernameValide(String Username) throws SQLException {
        try {
            String jdbcUrl = "jdbc:mysql://localhost:3306/paymentApp";
            String username = "root";
            String password = "0000";
            String quarry = "SELECT * FROM loginid WHERE user_id =? ";
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

            PreparedStatement preparedStatement = connection.prepareStatement(quarry);
            preparedStatement.setString(1, username);
            ResultSet res = preparedStatement.executeQuery();


            return !res.next();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


        return false;
    }

    public static boolean signup(String email, String password) throws SQLException {

        String jdbcUrl = "jdbc:mysql://localhost:3306/paymentApp";
        String username = "root";
        String pass = "0000";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(jdbcUrl, username, pass);
            String retrieveDataQuery = "SELECT email_id,password FROM loginid WHERE email_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(retrieveDataQuery);
            preparedStatement.setString(1, email);
            System.out.println(preparedStatement);
            ResultSet res = preparedStatement.executeQuery();
            if (res.next()) {
                //res.next();
                String emaill = res.getString("email_id");
                String passwordd = res.getString("password");
                System.out.println(res.getString("password"));
                System.out.println(passwordd);
                System.out.println(res.getString("email_id"));
                System.out.println(emaill);
                return emaill.equals(email) && passwordd.equals(password);
            }


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        return false;

    }


    public static int setUsername(String email) throws SQLException {

        String jdbcUrl = "jdbc:mysql://localhost:3306/paymentApp";
        String username = "root";
        String pass = "0000";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(jdbcUrl, username, pass);
            String retrieveDataQuery = "SELECT user_id FROM loginid WHERE email_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(retrieveDataQuery);
            preparedStatement.setString(1, email);
            System.out.println(preparedStatement);
            ResultSet res = preparedStatement.executeQuery();
            res.next();
            //res.next();
            int user = res.getInt("user_id");

            return user;


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        return 0;

    }


    public static boolean cheakEmailValide(String email) throws SQLException {


        String jdbcUrl = "jdbc:mysql://localhost:3306/paymentApp";
        String username = "root";
        String password = "0000";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
            String retrieveDataQuery = "SELECT * FROM loginid WHERE email_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(retrieveDataQuery);
            preparedStatement.setString(1, email);
            System.out.println(preparedStatement);
            ResultSet res = preparedStatement.executeQuery();
            if (res.next() == true) {
                return false;
                // res.next();
            }
            //System.out.println(res.getString("email_id"));
            return true;


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        //


        return false;
    }


    public static String insertData(User user) throws SQLException {
        String jdbcUrl = "jdbc:mysql://localhost:3306/paymentApp";
        String username = "root";
        String password = "0000";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
            String retrieveDataQuery = "INSERT INTO loginid (email_id,password,user_id,pin) VALUES (?, ?, ?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(retrieveDataQuery);
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getUsername());
            preparedStatement.setInt(4, user.getPin());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();

            String retrieveDataQuery1 = "INSERT INTO wallet (user_id,balance,transaction_ids) VALUES (?, ?, NULL)";
            PreparedStatement preparedStatement1 = connection.prepareStatement(retrieveDataQuery1);
            preparedStatement1.setString(1, user.getUsername());
            preparedStatement1.setDouble(2, 50000);
            preparedStatement1.executeUpdate();


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        return "registerd sussfully!!";
    }


}













}
