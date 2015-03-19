package com.example.app.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LocationTableGateway {

    private Connection mConnection;

    private static final String TABLE_NAME = "location";
    private static final String COLUMN_LOCATIONID = "LocationID";
    private static final String COLUMN_NAMEOFLOCATION = "NameOfLocation";
    private static final String COLUMN_ADDRESS = "Address";
    private static final String COLUMN_MAXCAPACITY = "MaxCapacity";
    private static final String COLUMN_LOCATIONMANAGERNAME = "LocationManagerName";
    private static final String COLUMN_LOCATIONMANAGERADDRESS = "LocationManagerAddress";
    private static final String COLUMN_LOCATIONMANAGERNUMBER = "LocationManagerNumber";

    public LocationTableGateway(Connection connection) {
        mConnection = connection;
    }

    public int insertLocation(String nol, String a, String max, String lmn, String lma, String lmno) throws SQLException {
        String query;                   // the SQL query to execute
        PreparedStatement stmt;         // the java.sql.PreparedStatement object used to execute the SQL query
        int numRowsAffected;
        int lid = -1;

        // the required SQL INSERT statement with place holders for the values to be inserted into the database
        query = "INSERT INTO " + TABLE_NAME + " ("
                + COLUMN_NAMEOFLOCATION + ", "
                + COLUMN_ADDRESS + ", "
                + COLUMN_MAXCAPACITY + ", "
                + COLUMN_LOCATIONMANAGERNAME + ", "
                + COLUMN_LOCATIONMANAGERADDRESS + ", "
                + COLUMN_LOCATIONMANAGERNUMBER
                + ") VALUES (?, ?, ?, ?, ?, ?)";

        // create a PreparedStatement object to execute the query and insert the values into the query
        stmt = mConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, nol);
        stmt.setString(2, a);
        stmt.setString(3, max);
        stmt.setString(4, lmn);
        stmt.setString(5, lma);
        stmt.setString(6, lmno);

        // execute the query and make sure that one and only one row was inserted into the database
        numRowsAffected = stmt.executeUpdate();
        if (numRowsAffected == 1) {
            // if one row was inserted, retrieve the id assigned to that row
            ResultSet keys = stmt.getGeneratedKeys();
            keys.next();

            lid = keys.getInt(1);
        }

        // return the id assigned to the row in the database
        return lid;
    }

    public boolean deleteLocation(int lid) throws SQLException {
        String query;                   // the SQL query to execute
        PreparedStatement stmt;         // the java.sql.PreparedStatement object used to execute the SQL query
        int numRowsAffected;

        // the required SQL DELETE statement with place holders for the id of the row to be remove from the database
        query = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_LOCATIONID + " = ?";

        // create a PreparedStatement object to execute the query and insert the id into the query
        stmt = mConnection.prepareStatement(query);
        stmt.setInt(1, lid);

        // execute the query
        numRowsAffected = stmt.executeUpdate();

        // return the true if one and only one row was deleted from the database
        return (numRowsAffected == 1);
    }

    public List<Location> getLocations() throws SQLException {
        String query;                   // the SQL query to execute
        Statement stmt;                 // the java.sql.Statement object used to execute the SQL query
        ResultSet rs;                   // the java.sql.ResultSet representing the result of SQL query
        List<Location> locations;         // the java.util.List containing the location objects created for each row
        // in the result of the query the id of a manager
        
        String nameOfLocation, address, locationManagerName, locationManagerAddress, locationManagerNumber;
        int locationId, maxCapacity;
        Location l;                   // a Manager object created from a row in the result of the query

        // execute an SQL SELECT statement to get a java.util.ResultSet representing
        // the results of the SELECT statement
        query = "SELECT * FROM " + TABLE_NAME;
        stmt = this.mConnection.createStatement();
        rs = stmt.executeQuery(query);
        
        // iterate through the result set, extracting the data from each row
        // and storing it in a Manager object, which is inserted into an initially
        // empty ArrayList
        locations = new ArrayList<Location>();
        while (rs.next()) {
            locationId = rs.getInt(COLUMN_LOCATIONID);
            nameOfLocation = rs.getString(COLUMN_NAMEOFLOCATION);
            address = rs.getString(COLUMN_ADDRESS);
            maxCapacity = rs.getInt(COLUMN_MAXCAPACITY);
            locationManagerName = rs.getString(COLUMN_LOCATIONMANAGERNAME);
            locationManagerAddress = rs.getString(COLUMN_LOCATIONMANAGERADDRESS);
            locationManagerNumber = rs.getString(COLUMN_LOCATIONMANAGERNUMBER);
            

            l = new Location(nameOfLocation, address, maxCapacity, locationManagerName, locationManagerAddress, locationManagerNumber);
            locations.add(l);
        }

        // return the list of Manager objects retrieved
        return locations;
    }
    
         boolean updateLocation(Location l) throws SQLException {
        String query;                   // the SQL query to execute
        PreparedStatement stmt;         // the java.sql.PreparedStatement object used to execute the SQL query
        int numRowsAffected;

        // the required SQL INSERT statement with place holders for the values to be inserted into the database
        query = "UPDATE " + TABLE_NAME + " SET " +
                COLUMN_NAMEOFLOCATION     + " = ?, " +
                COLUMN_ADDRESS     + " = ?, " +
                COLUMN_MAXCAPACITY    + " = ?, " +
                COLUMN_LOCATIONMANAGERNAME + " = ? " +
                COLUMN_LOCATIONMANAGERADDRESS + " = ? " +
                COLUMN_LOCATIONMANAGERNUMBER + " = ? " +
                " WHERE " + COLUMN_LOCATIONID + " = ?";

        // create a PreparedStatement object to execute the query and insert the new values into the query
        stmt = mConnection.prepareStatement(query);
        stmt.setString(1, l.getNameOfLocation());
        stmt.setString(2, l.getAddress());
        stmt.setInt(3, l.getMaxCapacity());
        stmt.setString(4, l.getLocationManagerName());
        stmt.setString(5, l.getLocationManagerAddress());
        stmt.setString(6, l.getLocationManagerNumber());
        stmt.setInt(7, l.getLocationId());

        // execute the query
        numRowsAffected = stmt.executeUpdate();

        // return the true if one and only one row was updated in the database
        return (numRowsAffected == 1);
    }

}


