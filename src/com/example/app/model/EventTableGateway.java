package com.example.app.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class EventTableGateway {

    private static final String TABLE_NAME = "event";
    private static final String COLUMN_EVENTID = "EventID";
    private static final String COLUMN_TITLE = "Title";
    private static final String COLUMN_DESCRIPTION = "Description";
    private static final String COLUMN_STARTDATE = "StartDate";
    private static final String COLUMN_TIME = "Time";
    private static final String COLUMN_ENDDATE = "EndDate";
    private static final String COLUMN_MAXCAPACITY = "MaxCapacity";
    private static final String COLUMN_PRICE = "Price";

    private Connection mConnection;

    public EventTableGateway(Connection connection) {
        mConnection = connection;
    }

    public int insertEvent(String t, String d, Date sd, Time tm, Date ed, int mc, double p) throws SQLException {
        Event e = null;

        String query;       // the SQL query to execute
        PreparedStatement stmt;         // the java.sql.PreparedStatement object used to execute the SQL query
        int numRowsAffected;
        int id = 0;

        // the required SQL INSERT statement with place holders for the values to be inserted into the database
        query = "INSERT INTO " + TABLE_NAME + " ("
                + COLUMN_TITLE + ", "
                + COLUMN_DESCRIPTION + ", "
                + COLUMN_STARTDATE + ", "
                + COLUMN_TIME + ", "
                + COLUMN_ENDDATE + ", "
                + COLUMN_MAXCAPACITY + ", "
                + COLUMN_PRICE
                + ") VALUES (?, ?, ?, ?, ?, ?, ?)";

        // create a PreparedStatement object to execute the query and insert the values into the query
        stmt = mConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, t);
        stmt.setString(2, d);
        stmt.setDate(3, sd);
        stmt.setTime(4, tm);
        stmt.setDate(5, ed);
        stmt.setInt(6, mc);
        stmt.setDouble(7, p);

        //  execute the query and make sure that one and only one row was inserted into the database
        numRowsAffected = stmt.executeUpdate();
        if (numRowsAffected == 1) {
            // if one row was inserted, retrieve the id assigned to that row and create a Programmer object to return
            ResultSet keys = stmt.getGeneratedKeys();
            keys.next();

            id = keys.getInt(1);

            e = new Event(t, d, sd, tm, ed, mc, p);
        }

        // return the Programmer object created or null if there was a problem
        return id;
    }

    //get method to retrieve all events in the database

    public List<Event> getEvents() throws SQLException {
        String query;                   // the SQL query to execute
        Statement stmt;                 // the java.sql.Statement object used to execute the SQL query
        ResultSet rs;                   // the java.sql.ResultSet representing the result of SQL query
        List<Event> events;   // the java.util.List containing the Programmer objects created for each row
        // in the result of the query the id of a programmer

        String title, description;
        int eventID, maxCapacity;
        Date startDate, endDate;
        Time time;
        double price;

        Event e;                   // a Programmer object created from a row in the result of the query

        // execute an SQL SELECT statement to get a java.util.ResultSet representing
        // the results of the SELECT statement
        query = "SELECT * FROM " + TABLE_NAME;
        stmt = this.mConnection.createStatement();
        rs = stmt.executeQuery(query);

        // iterate through the result set, extracting the data from each row
        // and storing it in a Programmer object, which is inserted into an initially
        // empty ArrayList
        events = new ArrayList<Event>();
        while (rs.next()) {
            eventID = rs.getInt(COLUMN_EVENTID);
            title = rs.getString(COLUMN_TITLE);
            description = rs.getString(COLUMN_DESCRIPTION);
            startDate = rs.getDate(COLUMN_STARTDATE);
            time = rs.getTime(COLUMN_TIME);
            endDate = rs.getDate(COLUMN_ENDDATE);
            maxCapacity = rs.getInt(COLUMN_MAXCAPACITY);
            price = rs.getDouble(COLUMN_PRICE);

            e = new Event(eventID, title, description, startDate, time, endDate, maxCapacity, price);
            events.add(e);
        }

        // return the list of Programmer objects retrieved
        return events;
    }

    //UPDATE EVENT
    boolean updateEvent(Event e) throws SQLException {
        String query;
        PreparedStatement stmt;
        int numRowsAffected;
        //format of edit table including placeholders
        query = "UPDATE " + TABLE_NAME + " SET "
                + COLUMN_TITLE + " = ?, "
                + COLUMN_DESCRIPTION + " = ?, "
                + COLUMN_STARTDATE + " = ?, "
                + COLUMN_TIME + " = ?, "
                + COLUMN_ENDDATE + " = ?, "
                + COLUMN_MAXCAPACITY + " = ?, "
                + COLUMN_PRICE + " = ? "
                + " WHERE " + COLUMN_EVENTID + " = ?";
        //insert values
        stmt = mConnection.prepareStatement(query);
        stmt.setString(1, e.getTitle());
        stmt.setString(2, e.getDescription());
        stmt.setDate(3, e.getStartDate());
        stmt.setTime(4, e.getTime());
        stmt.setDate(5, e.getEndDate());
        stmt.setInt(6, e.getMaxCapacity());
        stmt.setDouble(7, e.getPrice());
        stmt.setInt(8, e.getEventID());

        numRowsAffected = stmt.executeUpdate();
        //boolean value of true/false is returned dependeing on how many rows are affected
        return (numRowsAffected == 1);
    }

    //code that checks if an event has been deleted from the database
    //boolean returns true or false whether something has been deleted

    public boolean deleteEvent(int eventID) throws SQLException {
        String query;
        PreparedStatement stmt;
        //variable which holds the value or rows affected(deleted)
        int numRowsAffected;

        query = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_EVENTID + " = ? ";

        stmt = mConnection.prepareStatement(query);
        stmt.setInt(1, eventID);

        numRowsAffected = stmt.executeUpdate();

        return (numRowsAffected == 1);

    }
}
