package com.example.app.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

//new comment
private static Connection sConnection;

    //here creates a connection and reacts appropriately whether a connection is established or not

    public static Connection getInstance() throws ClassNotFoundException, SQLException {
        String host, db, user, password;
        //my details to connect to the right database
        host = "daneel";
        db = "n00134587";
        user = "N00134587";
        password = "N00134587";

        if (sConnection == null || sConnection.isClosed()) {
            String url = "jdbc:mysql://" + host + "/" + db;
            Class.forName("com.mysql.jdbc.Driver");
            sConnection = DriverManager.getConnection(url, user, password);
        }

        return sConnection;
    }
}
