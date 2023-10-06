package com.example.logs;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import com.google.gson.Gson;

public class loadLogs {
      public static String hashMapToString(HashMap<String,Object> hashMap){
          Gson gson = new Gson();

          return gson.toJson(hashMap);
      }
    public static String[] convertStringToArray(String inputString) {
        // Remove square brackets and split the string by commas
        String[] splitString = inputString.substring(1, inputString.length() - 1).split(",");

        // Initialize an empty list to store the final array of strings
        List<String> resultArray = new ArrayList<>();

        // Iterate through the splitString and convert each element to a string
        for (String item : splitString) {
            item = item.trim();  // Remove leading/trailing whitespace
            if (item.matches("-?\\d+")) {
                // If the item is a number (integer), convert it to a string
                resultArray.add(item);
            } else {
                // If the item is not a number, remove double quotes (if present) and add it to the result array
                item = item.replaceAll("^\"|\"$", "");
                resultArray.add(item);
            }
        }

        // Convert the list to an array

        return resultArray.toArray(new String[0]);
    }


    public static HashMap<String,Object> lodLog(String userid) throws SQLException {




        String jdbcUrl = "jdbc:mysql://localhost:3306/paymentApp";
        String username = "root";
        String password = "0000";

        Connection connection = null;
        PreparedStatement walletStatement = null;
        PreparedStatement transactionStatement = null;
        ResultSet walletResultSet = null;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
             connection= DriverManager.getConnection(jdbcUrl, username, password);
            String retrieveDataQuery = "SELECT * FROM loginid WHERE email_id = ?";


            String walletQuery = "SELECT transaction_ids FROM wallet WHERE user_id=?";

            walletStatement = connection.prepareStatement(walletQuery);
            walletStatement.setString(1,userid);
            walletResultSet = walletStatement.executeQuery();

           if(! walletResultSet.next()){
               return null;
           }

            String transactionId = walletResultSet.getString("transaction_ids");

             System.out.println(transactionId);
             if(transactionId==null){

                 return null;
             }

            String[] resultArray = convertStringToArray(transactionId);
            HashMap<String, Object> resultMap = new HashMap<>();

           for (int i=0;i< resultArray.length-1;i++) {

                String transactionQuery = "SELECT * FROM transactions WHERE transaction_id = ?";
                transactionStatement = connection.prepareStatement(transactionQuery);
               transactionStatement.setString(1, resultArray[i]);
                System.out.println(transactionStatement);

                ResultSet transactionResultSet = transactionStatement.executeQuery();


             while ( transactionResultSet.next()) {
                 //  transactionResultSet.next();

                 String key = transactionResultSet.getString("transaction_id");
                 System.out.println(key);// Adjust the column index as needed

                 // Create a List to store values for this row
                 List<Object> values = new ArrayList<>();
                 for (int j = 1; j <= 6; j++) { // Assuming values are in columns 2 to 5
                     values.add(transactionResultSet.getObject(j)); // Adjust the column index as needed
                 }

                 // Add the key and values to the HashMap

                 resultMap.put(key, values);
             }
              //  System.out.println(resultMap.get("50fc3241-976c-48ab-b065-7cdd8fc3816a").toString());



                 //add in map object here











            }




            return resultMap;




          //  PreparedStatement preparedStatement = connection.prepareStatement(retrieveDataQuery);
          //  preparedStatement.setString(1,email);
          ////  System.out.println(preparedStatement);
         //   ResultSet res = preparedStatement.executeQuery();

            //System.out.println(res.getString("email_id"));



        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }



          return null;




    }

    public static void main(String[] args) throws SQLException {

        HashMap<String, Object> hashMap = lodLog("4");

        String hello = hashMapToString(hashMap);
        System.out.println(hello);





    }

}
