package com.example.validateTransaction;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.*;
import java.util.UUID;

public class tasks {
    @Autowired
    private DataStorageService dataStorageService;

    public static String getUniqId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();

    }
    public static transaction stringToObject(String jsonStr){
        Gson g = new Gson();

        return g.fromJson(jsonStr, transaction.class);
    }



    public static Connection establishConnection(String jdbcUrl, String username, String password) throws SQLException {
        // Load the MySQL JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return DriverManager.getConnection(jdbcUrl, username, password);
    }
    public static boolean isPinCorrect(Connection connection, int user_id, int pin) throws SQLException {

        String retrieveDataQuery = "SELECT pin FROM loginid WHERE user_id =?";


        PreparedStatement preparedStatement = connection.prepareStatement(retrieveDataQuery);
       preparedStatement.setInt(1,user_id);
        ResultSet res = preparedStatement.executeQuery();
        res.next();
        int r_pin = res.getInt("pin");
        System.out.println(r_pin);
        return r_pin==pin;

    }

    public static boolean isReciverExixt(Connection connection, int receiver) throws SQLException {
        String retrieveDataQuery = "SELECT user_id FROM wallet WHERE user_id = ?" ;
        PreparedStatement preparedStatement = connection.prepareStatement(retrieveDataQuery);
        preparedStatement.setInt(1,receiver);
        ResultSet res = preparedStatement.executeQuery();

        return res.next();

    }
    public static boolean isSufficientBalance(Connection connection, int senderid, double amount) throws SQLException, SQLException {

        String retrieveDataQuery = "SELECT * FROM wallet WHERE user_id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(retrieveDataQuery);
        preparedStatement.setInt(1,senderid);
        ResultSet res = preparedStatement.executeQuery();
        res.next();
        double balance = res.getDouble("balance");
         System.out.println(balance);

        return balance - amount > 0;

    }



    public static void  performTransaction(Connection connection, transaction json) throws SQLException {
        String trxid = getUniqId();
        String task1 = "UPDATE wallet SET balance = balance - ? WHERE user_id =  ?";
        PreparedStatement preparedStatement = connection.prepareStatement(task1);
        preparedStatement.setDouble(1,json.amount);
        preparedStatement.setInt(2,json.sender_id);
        preparedStatement.executeUpdate();

        String task2 = "UPDATE wallet SET transaction_ids =JSON_ARRAY_INSERT(transaction_ids, '$[0]', '"
                + trxid + "') WHERE user_id =  "
                + json.sender_id;
        PreparedStatement preparedStatement1 = connection.prepareStatement(task2);

        preparedStatement1.executeUpdate();


        String task3 = "UPDATE wallet SET transaction_ids =JSON_ARRAY_INSERT(transaction_ids, '$[0]', '"
                + trxid + "') WHERE user_id =  "
                + json.receiver_id;
        PreparedStatement preparedStatement2 = connection.prepareStatement(task3);
        preparedStatement2.executeUpdate();

        String task4= "UPDATE wallet SET balance = balance +" + json.amount + " WHERE user_id =  " + json.receiver_id;

        PreparedStatement preparedStatement3 = connection.prepareStatement(task4);
        preparedStatement3.executeUpdate();

        String task5 = "INSERT INTO transactions (sender_id,receiver_id,amount,transaction_id,request_id) VALUES (?,?,?,?,?)";
        PreparedStatement preparedStatement4 = connection.prepareStatement(task5);
        preparedStatement4.setInt(1, json.sender_id); // user_id
        preparedStatement4.setInt(2, json.receiver_id); // user_id
        preparedStatement4.setDouble(3, json.amount); // user_id
        preparedStatement4.setString(4, trxid); // user_id
        preparedStatement4.setString(5, json.request_id); // user_id// user_id
        System.out.println(trxid);

        preparedStatement4.executeUpdate();


    }



    public static void test(int sender,int receiver) throws SQLException{
        String jdbcUrl = "jdbc:mysql://localhost:3306/paymentApp";
        String username = "root";
        String password = "0000";
        try { Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection to the MySQL server
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

            // Create a statement
            Statement statement = connection.createStatement();

            // SQL query to retrieve data
            String retrieveDataQuery = "SELECT * FROM wallet WHERE user_id ='"+sender+"'";

            // Execute the query
            ResultSet resultSet = statement.executeQuery(retrieveDataQuery);

            // Loop through the result set and display data
            while (resultSet.next()) {
                String userId = resultSet.getString("user_id");

                double balance = resultSet.getDouble("balance");
                String transactionIds = resultSet.getString("transaction_ids");
                System.out.println("sender details ");
                System.out.println(userId);

                System.out.println("Balance: " + balance);
                System.out.println("Transaction IDs: " + transactionIds);
                System.out.println("---------------------------");
            }

            String retrieveDataQuery1 = "SELECT * FROM wallet WHERE user_id ='"+receiver+"'";

            // Execute the query
            ResultSet resultSet1 = statement.executeQuery(retrieveDataQuery1);

            // Loop through the result set and display data
            while (resultSet1.next()) {
                String userId = resultSet1.getString("user_id");

                double balance = resultSet1.getDouble("balance");
                String transactionIds = resultSet1.getString("transaction_ids");
                System.out.println("receiver details ");
                System.out.println(userId);

                System.out.println("Balance: " + balance);
                System.out.println("Transaction IDs: " + transactionIds);
                System.out.println("---------------------------");
            }







        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
        public  void executeTransation(transaction t) throws SQLException {
            String jdbcUrl = "jdbc:mysql://localhost:3306/paymentApp";
            String username = "root";
            String password = "0000";

            Connection connection= tasks.establishConnection(jdbcUrl,username,password);
            if(!tasks.isPinCorrect(connection,t.sender_id,t.pin)){

                dataStorageService.storeData(t.request_id,"wrong pin");



            }
            if(!tasks.isReciverExixt(connection,t.receiver_id)){
                dataStorageService.storeData(t.request_id,"receiver not exist");


            }
            if(!tasks.isSufficientBalance(connection,t.sender_id,t.amount)){
                dataStorageService.storeData(t.request_id,"low balance ");


            }

            tasks.performTransaction(connection,t);
            dataStorageService.storeData(t.request_id,"transaction successful");
















































    }

    public static void main(String[] args) throws SQLException {
        String jdbcUrl = "jdbc:mysql://localhost:3306/paymentApp";
        String username = "root";
        String password = "0000";
        transaction transaction = new transaction();
        transaction.amount=50;
        transaction.receiver_id=2;
        transaction.sender_id=1;
        transaction.request_id="dd";














       // test();
        Connection connection =establishConnection(jdbcUrl,username,password);
        performTransaction(connection,transaction);
        test(transaction.sender_id, transaction.receiver_id);







       // boolean ans = isSufficientBalance(connection,2,333);
       // System.out.println(ans);
    }



}
