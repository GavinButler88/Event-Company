package com.example.app.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Model {

    private static Model instance = null;

    public static synchronized Model getInstance() throws DataAccessException {
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }

    private List<Event> events;
    private List<Location> locations;
    private EventTableGateway gateway;
    private LocationTableGateway locationGateway;

    private Model() throws DataAccessException {

        try {
            Connection conn = DBConnection.getInstance();
            this.gateway = new EventTableGateway(conn);
            this.locationGateway = new LocationTableGateway(conn);

            this.events = this.gateway.getEvents();
            this.locations = this.locationGateway.getLocations();
        } catch (ClassNotFoundException ex) {
            throw new DataAccessException("Exception initialising Model object" + ex.getMessage());
        } catch (SQLException ex) {
            throw new DataAccessException("Exception initialising Model object" + ex.getMessage());
        }
    }

    public List<Event> getEvents() {
        return this.events;
    }
    
    public List<Event> getEventsByLocationID(int locationID) {
        List<Event> list = new ArrayList<Event>();
        for (Event e : this.events) {
            if (e.getLocationID() == locationID) {
                list.add(e);
            }
        }
        return list;
    }

    //completes process of adding event to the database
    public void addEvent(Event e) throws DataAccessException {
        try {
            int id = this.gateway.insertEvent(
                    e.getTitle(), e.getDescription(), e.getStartDate(),
                    e.getTime(), e.getEndDate(), e.getMaxCapacity(), e.getPrice(), e.getLocationID());
            e.setEventID(id);
            this.events.add(e);
        } catch (SQLException ex) {
            throw new DataAccessException("Exception adding event" + ex.getMessage());
        }
    }

    //method to remove event
    public boolean removeEvent(Event p) throws DataAccessException {
        boolean removed = false;

        try {       //use gateway object to try remove event
            removed = this.gateway.deleteEvent(p.getEventID());
            if (removed) {
                removed = this.events.remove(p);
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Exception removing event" + ex.getMessage());
        }
        //boolean returns true if event is deleted from database and array list
        return removed;
    }

    public Event findEventById(int id) {
        //e is assigned to each event in the list
        Event e = null;
        int i = 0;
        //found checks to see the correct specified price
        boolean found = false;
        while (i < this.events.size() && !found) {
            e = this.events.get(i);
            //checks to see price entered is equal to what we are looking for
            if (e.getEventID() == id) {
                found = true;
            } else {
                i++;
            }
        }
        if (!found) {
            e = null;
        }
        return e;
    }

    //UPDATE EVENT
    boolean updateEvent(Event e) throws DataAccessException {
        boolean updated = false;

        try {
            //method calls on gateway update method which returns true/false value and is then returned
            updated = this.gateway.updateEvent(e);
        } catch (SQLException ex) {
            throw new DataAccessException("Exception updating event" + ex.getMessage());
        }

        return updated;

    }

    public boolean addLocation(Location l) throws DataAccessException {
        boolean result = false;
        try {
            int lid = this.locationGateway.insertLocation(
                    l.getNameOfLocation(), l.getAddress(), l.getMaxCapacity(), l.getLocationManagerName(), l.getLocationManagerAddress(), l.getLocationManagerNumber());
            if (lid != -1) {
                l.setLocationID(lid);
                this.locations.add(l);
                result = true;

            }
        } catch (SQLException ex) {
            throw new DataAccessException("Exception adding event" + ex.getMessage());
        }
        return result;
    }

    
    public boolean removeLocation(Location l) throws DataAccessException {
        boolean removed = false;

        try {
            removed = this.locationGateway.deleteLocation(l.getLocationID());
            if (removed) {
                removed = this.locations.remove(l);
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Exception removing location" + ex.getMessage());
        }

        return removed;
    }

    public List<Location> getLocation() {
        return this.locations;
    }

    public Location findLocationById(int LocationID) {
        Location l = null;
        int i = 0;
        boolean found = false;
        while (i < this.locations.size() && !found) {
            l = this.locations.get(i);
            if (l.getLocationID() == LocationID) {
                found = true;
            } else {
                i++;
            }
        }
        if (!found) {
            l = null;
        }
        return l;
    }

    boolean updateLocation(Location l) throws DataAccessException {
        boolean updated = false;

        try {
            updated = this.locationGateway.updateLocation(l);
        } catch (SQLException ex) {
            throw new DataAccessException("Exception updating location" + ex.getMessage());
        }

        return updated;
    }
    
    
}
