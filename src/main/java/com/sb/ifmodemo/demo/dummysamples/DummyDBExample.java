package com.sb.ifmodemo.demo.dummysamples;

import java.sql.*;
import java.util.Random;

public class DummyDBExample {

    public static void main(String[] args) throws SQLException {
        String url = "jdbc:postgresql://localhost/ifmo_test";
        String user = "test";
        String password = "test";

        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println("Connected to the PostgreSQL server successfully.");

        int r = new Random().nextInt(10000);

        String query1 = "insert into musers (ind, name) values (" + r + ",'person" + r + "');";
        conn.createStatement().execute(query1);
        String query2 = "select * from musers;";
        ResultSet rs = conn.createStatement().executeQuery(query2);
        while (rs.next()) {
            int ind = rs.getInt("ind");
            String name = rs.getString("name");
            System.out.println(name + " - " + ind);
        }

    }
}
