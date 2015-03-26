package com.example.app.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Model {

    private static Model instance = null;

    public static synchronized Model getInstance() {
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }

    private List<Event> events;
    private List<Location> locations;
    private EventTableGateway gateway;
    private LocationTableGateway locationGateway;

    private Model() {

        try {
            Connection conn = DBConnection.getInstance();
            this.gateway = new EventTableGateway(conn);
            this.locationGateway = new LocationTableGateway(conn);

            this.events = this.gateway.getEvents();
            this.locations = this.locationGateway.getLocations();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Event> getEvents() {
        return this.events;
    }

    //completes process of adding event to the database
    public void addEvent(Event e) {
        try {
            int id = this.gateway.insertEvent(
                    e.getTitle(), e.getDescription(), e.getStartDate(),
                    e.getTime(), e.getEndDate(), e.getMaxCapacity(), e.getPrice(), e.getLocationID());
            e.setEventID(id);
            this.events.add(e);
        } catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //method to remove event
    public boolean removeEvent(Event p) {
        boolean removed = false;

        try {       //use gateway object to try remove event
            removed = this.gateway.deleteEvent(p.getEventID());
            if (removed) {
                removed = this.events.remove(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
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
    boolean updateEvent(Event e) {
        boolean updated = false;

        try {
            //method calls on gateway update method which returns true/false value and is then returned
            updated = this.gateway.updateEvent(e);
        } catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }

        return updated;

    }


public boolean addLocation(Location l) {
        boolean result = false;
        try {
            int lid = this.locationGateway.insertLocation(l.getNameOfLocation(), l.getAddress(), l.getMaxCapacity(), l.getLocationManagerName(), l.getLocationManagerAddress(), l.getLocationManagerNumber());
            if (lid != -1) {
                l.setLocationId(lid);
                this.locations.add(l);
                result = true;
            

}
        }
        catch (SQLException ex) {
            Logger.getLogger(Model.class  

.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }


public boolean removeLocation(Location l) {
        boolean removed = false;

        try {
            removed = this.locationGateway.deleteLocation(l.getLocationId());
            if (removed) {
                removed = this.locations.remove(l);
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }

        return removed;
    }

 public List<Location> getLocation() {
        return this.locations;
    }
 
 Location findLocationById(int Locationid) {
        Location l = null;
        int i = 0;
        boolean found = false;
        while (i < this.locations.size() && !found) {
            l = this.locations.get(i);
            if (l.getLocationId()== Locationid) {
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
 
  boolean updateLocation(Location l) {
        boolean updated = false;

        try {
            updated = this.locationGateway.updateLocation(l);
        }
        catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }

        return updated;
    }
}

