package com.example.app.model;

import java.sql.Date;
import java.sql.Time;

public class Event {

    private int eventID;
    private String title;
    private String description;
    private Date startDate;
    private Time time;
    private Date endDate;
    private int maxCapacity;
    private double price;
    private int locationID;

    public Event(int eventID, String t, String d, Date sd, Time tm, Date ed, int mc, double p, int lId) {
        this.eventID = eventID;
        this.title = t;
        this.description = d;
        this.startDate = sd;
        this.time = tm;
        this.endDate = ed;
        this.maxCapacity = mc;
        this.price = p;
        this.locationID = lId;
    }

    public Event(String t, String d, Date sd, Time tm, Date ed, int mc, double p, int lId) {
        this(-1, t, d, sd, tm, ed, mc, p, lId);
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapcaity) {
        this.maxCapacity = maxCapacity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    public int getLocationID() {
        return locationID;
    }
    
    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }
}
