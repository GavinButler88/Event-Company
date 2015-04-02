package com.example.app.model;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DemoApp {

    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);

        Model model = Model.getInstance();

        Event e;

        int opt = 11;
        do {
            //user interface menu
            System.out.println(" 1. Create new Event");
            System.out.println(" 2. Delete existing Event");
            System.out.println(" 3. Edit Events");
            System.out.println(" 4. View all Events");
            System.out.println(" 5. View single Event");
            System.out.println();
            System.out.println(" 6. View all Locations");
            System.out.println(" 7. Create new Location");
            System.out.println(" 8. Delete existing Location");
            System.out.println(" 9. Edit Locations");
            System.out.println(" 10. View single Location");
            System.out.println();
            System.out.println(" 11. Exit");
            System.out.println();
               //where the user will input their choice of method
            // methods contained elsewhere on page
            System.out.print("Enter option: ");
            String line = keyboard.nextLine();
            opt = Integer.parseInt(line);
            //cases which allow user to manipulate databese
            System.out.println("You chose option " + opt);
            //switch used to print message to user based on their selection
            switch (opt) { //
                case 1: {
                    System.out.println("Creating event");
                    Event p = readEvent(keyboard);
                    model.addEvent(p);

                    break;
                }
                case 2: {
                    //prompts user an option to delete 
                    System.out.println("Deleting event");
                    deleteEvent(model, keyboard);

                    break;
                }

                case 3: {
                    System.out.println("Editing event");
                    editEvent(keyboard, model);
                    break;
                }
                case 4: {
                    System.out.println("Viewing events");
                    viewEvents(model);
                    break;
                }
                
                 case 5: {
                    System.out.println("Viewing single event");
                    viewEvent(model, keyboard);
                    break;
                }
                 
                case 6: {
                    System.out.println("Viewing locations");
                    viewLocations(model);
                    break;
                }
                
                case 7: {
                        System.out.println("Creating location");
                        Location l = readLocation(keyboard);
                        model.addLocation(l);
                    }
                
                 case 8: {
                    //prompts user an option to delete 
                    System.out.println("Deleting location");
                    deleteLocation(model, keyboard);

                    break;
                }
                 
                  case 9: {
                        System.out.println("Editing locations");
                        editLocation(keyboard, model);
                        break;
                    }
                  
                  case 10: {
                    System.out.println("Viewing location");
                    viewLocation(model, keyboard);
                    break;
                }
            }
        } while (opt != 11);
        System.out.println("Goodbye");
    }

    //allows user to edit an event
    private static void editEvent(Scanner kb, Model m) {
        System.out.println("Enter the id of the event you want to edit: ");
        int id = Integer.parseInt(kb.nextLine());
        Event e;

        e = m.findEventById(id);
        //if price entered in not 0 then run other methods
        if (e != null) {
            editEventDetails(kb, e);
            if (m.updateEvent(e)) {
                System.out.println("Event updated");
            } else {
                System.out.println("Event not updated");
            }
        }
    }

    private static void editLocation(Scanner kb, Model m) {
        System.out.println("Enter the id of the location you want to edit: ");
        int id = Integer.parseInt(kb.nextLine());
        Location l;

        l = m.findLocationById(id);
        //if price entered in not 0 then run other methods
        if (l != null) {
            editLocationDetails(kb, l);
            if (m.updateLocation(l)) {
                System.out.println("Location updated");
            } else {
                System.out.println("Location not updated");
            }
        }
    }
    //this code allows me to read an event and store it in a database

    private static Event readEvent(Scanner keyb) {
        String title, description;
        int maxCapacity;
        Date startDate, endDate;
        Time time;
        double price;
        int locationID;
        String line;

        java.util.Date now = new java.util.Date();
        //formatting the date and time aspects which the user enters
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
        //user enters details of event into the prompts 
        title = getString(keyb, "Enter title: ");
        description = getString(keyb, "Enter description: ");

        line = getString(keyb, "enter start date (YYYY-MM-DD): ");
        try {
            startDate = new Date(dateFormatter.parse(line).getTime());
        } catch (ParseException ex) {
            startDate = new Date(now.getTime());
        }

        line = getString(keyb, "enter time (HH:MM:SS): ");
        try {
            time = new Time(timeFormatter.parse(line).getTime());
        } catch (ParseException ex) {
            time = new Time(now.getTime());
        }

        line = getString(keyb, "Enter end date (YYYY-MM-DD): ");
        try {
            endDate = new Date(dateFormatter.parse(line).getTime());
        } catch (ParseException ex) {
            endDate = new Date(now.getTime());
        }

        line = getString(keyb, "enter maximum capacity: ");
        maxCapacity = Integer.parseInt(line);
        line = getString(keyb, "enter price: ");
        price = Double.parseDouble(line);
        line = getString(keyb, "enter location id: ");
        locationID = Integer.parseInt(line);

        Event e = 
            new Event(title, description, startDate,
                        time, endDate, maxCapacity, price, locationID);

        return e;
    }

    //method to read user input as a string

    private static String getString(Scanner keyboard, String prompt) {
        System.out.print(prompt);
        return keyboard.nextLine();
    }

    private static void deleteEvent(Model model, Scanner keyboard) {
          System.out.println("Enter the id number of the product to delete:");
            int id = Integer.parseInt(keyboard.nextLine());
            Event e;

            e = model.findEventById(id);
            if (e != null) {
                if(model.removeEvent(e)) {
                    System.out.println("Product deleted");
                }
                else{
                    System.out.println("Event not deleted");
                }
            }
            else {
                System.out.println("Event not found");
            }
    }
    
    

    //EDIT EVENT
    private static void editEventDetails(Scanner keyb, Event e) {
        String title, description;
        int maxCapacity;
        Date startDate, endDate;
        Time time;
        double price;
        String line1, line2, line3, line4, line5;

        java.util.Date now = new java.util.Date();
        //formats type time and date
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
        //where user will enter details to edit
        title = getString(keyb, "Enter title [" + e.getTitle() + "]: ");
        description = getString(keyb, "Enter description [" + e.getDescription() + "]: ");
        line1 = getString(keyb, "enter start date (YYYY-MM-DD) [" + e.getStartDate() + "]: ");
        line2 = getString(keyb, "enter time (HH:MM:SS) [" + e.getTime() + "]: ");
        line3 = getString(keyb, "Enter end date (YYYY-MM-DD) [" + e.getEndDate() + "]: ");
        line4 = getString(keyb, "enter maximum capacity: ");
        line5 = getString(keyb, "enter price: ");
        //line6 = getString(keyb, "Enter location id [" + e.getLocationID() + "]: ");
        
        //if the details of the event have been atlered then it becomes the new value
        if (title.length() != 0) {
            e.setTitle(title);
        }

        if (description.length() != 0) {
            e.setDescription(description);
        }

        if (line1.length() != 0) {
            try {
                startDate = new Date(dateFormatter.parse(line1).getTime());
            } catch (ParseException ex) {
                startDate = new Date(now.getTime());
            }
            e.setStartDate(startDate);
        }

        if (line2.length() != 0) {
            try {
                time = new Time(timeFormatter.parse(line2).getTime());
            } catch (ParseException ex) {
                time = new Time(now.getTime());
            }
            e.setTime(time);
        }

        if (line3.length() != 0) {
            try {
                endDate = new Date(dateFormatter.parse(line3).getTime());
            } catch (ParseException ex) {
                endDate = new Date(now.getTime());
            }
            e.setEndDate(endDate);
        }

        if (line4.length() != 0) {
            maxCapacity = Integer.parseInt(line4);
            e.setMaxCapacity(maxCapacity);
        }

        if (line5.length() != 0) {
            price = Double.parseDouble(line5);
            e.setPrice(price);
        }
        
        //if (line6.length() != 0) {
          //  locationID = Integer.parseInt(line6);
          // e.setLocationID(locationID);
       // }
    }
    
    private static void viewEvent(Model model, Scanner keyboard) {
          System.out.println("Enter the id number of the event you want to view:");
            int id = Integer.parseInt(keyboard.nextLine());
            Event e;

            e = model.findEventById(id);
            System.out.println();
            if (e != null) {
                    Location l = model.findLocationById(e.getLocationID());
                    System.out.println("Title        : " + e.getTitle());
                    System.out.println("Description  : " + e.getDescription());
                    System.out.println("StartDate    : " + e.getStartDate());
                    System.out.println("Time         : " + e.getTime());
                    System.out.println("EndDate      : " + e.getEndDate());
                    System.out.println("MaxCapacity  : " + e.getMaxCapacity());
                    System.out.println("Price        : " + e.getPrice());
                    System.out.println("Location     : " + ((l != null) ? l.getNameOfLocation() : ""));
                    System.out.println();
                    
                }
                else{
                    System.out.println("Event not deleted");
                }
            }
            
    

    //VIEW EVENT
    //prints table of events to the user
    private static void viewEvents(Model model) {
        List<Event> events = model.getEvents();
        System.out.println();
        if (!events.isEmpty()) {
            System.out.printf("%5s %20s  %30s  %25s  %22s  %20s %20s %18s %20s\n", "ID", "Title", "Description", "Start Date", "Time", "End Date", "Max Capacity", "Price", "Locationid");
            for (Event ev : events) {
                Location l = model.findLocationById(ev.getLocationID());
                System.out.printf("%5d %20s  %30s  %25s  %22s  %20s %20d %18.2f %20s\n",
                        ev.getEventID(),
                        ev.getTitle(),
                        ev.getDescription(),
                        ev.getStartDate(),
                        ev.getTime(),
                        ev.getEndDate(),
                        ev.getMaxCapacity(),
                        ev.getPrice(),
                        (l != null) ? l.getNameOfLocation() : "");

            }
            
        } else {
           System.out.println("There are no events in your database");
        }
        System.out.println();
    }
    
    private static void displayEvents(List<Event> events, Model model) {
        System.out.printf("%5s %20s  %30s  %25s  %22s  %20s %20s %18s %20s\n", "ID", "Title", "Description", "Start Date", "Time", "End Date", "Max Capacity", "Price", "Locationid");
            for (Event ev : events) {
                Location l = model.findLocationById(ev.getLocationID());
                System.out.printf("%5d %20s  %30s  %25s  %22s  %20s %20d %18.2f %20s\n",
                        ev.getEventID(),
                        ev.getTitle(),
                        ev.getDescription(),
                        ev.getStartDate(),
                        ev.getTime(),
                        ev.getEndDate(),
                        ev.getMaxCapacity(),
                        ev.getPrice(),
                        (l != null) ? l.getNameOfLocation() : "");

            }
    }
    
     private static void viewLocations(Model model) {
        List<Location> locations = model.getLocation();
        System.out.println();
        if (!locations.isEmpty()) {
            //formats the table appropriately
            System.out.printf("%5s %10s %20s  %30s  %30s  %30s  %30s \n", "ID", "Name of Location", "Address", "Capacity", "Manager Name", "Manager Address", "ManagerNumber");
            for (Location l : locations) {
                System.out.printf("%5d %10s %20s  %30s  %30s  %30s  %30s \n",
                        l.getLocationID(),
                        l.getNameOfLocation(),
                        l.getAddress(),
                        l.getMaxCapacity(),
                        l.getLocationManagerName(),
                        l.getLocationManagerAddress(),
                        l.getLocationManagerNumber());

            }
        } else {
            System.out.println("There are no locations in your database");
        }
        System.out.println();
    }
    
     /*private static void viewLocation(Scanner keyboard, Model model) {
        int id = getLocation(keyboard, "Enter the id of the location to view:", -1);
        Location l;

        m = model.findManagerById(id);
        System.out.println();
        if (m != null) {
            System.out.println("Name      : " + m.getName());
            System.out.println("Office    : " + m.getOffice());
            System.out.println("Extension : " + m.getExtension());

            List<Programmer> programmerList = model.getProgrammersByManagerId(m.getId());
            System.out.println();
            if (programmerList.isEmpty()) {
                System.out.println("This manager manages no programmers");
            }
            else {
                System.out.println("This manager manages the following programmers:");
                System.out.println();
                displayProgrammers(programmerList, model);
            }
            System.out.println();
        }
        else {
            System.out.println("Manager not found");
        }
        System.out.println();
    }*/
     
     private static Location readLocation(Scanner keyb) {
        String nameOfLocation, address, locationManagerName, locationManagerAddress, locationManagerNumber;
        int maxCapacity,  locationID;
        String line;
        
        //user enters details of event into the prompts 
        nameOfLocation = getString(keyb, "Enter name of location: ");
        address = getString(keyb, "Enter address: ");
        locationManagerName = getString(keyb, "Enter manager name: ");
        locationManagerAddress = getString(keyb, "Enter manager address: ");
        locationManagerNumber = getString(keyb, "Enter manager number: ");
        line = getString(keyb, "enter maximum capacity: ");
        maxCapacity = Integer.parseInt(line);
        line = getString(keyb, "enter location id: ");
        locationID = Integer.parseInt(line);

        Location l = 
            new Location(locationID, nameOfLocation, address, maxCapacity,
                        locationManagerName, locationManagerAddress, locationManagerNumber);

        return l;
    }
     
     private static void viewLocation(Model model, Scanner keyboard) {
      System.out.println("Enter the id number of the location to view:");
        int id = Integer.parseInt(keyboard.nextLine());
            Location l;

            l = model.findLocationById(id);
            System.out.println();
            if (l != null) {
                System.out.println("Name            : " + l.getNameOfLocation());
                System.out.println("Address         : " + l.getAddress());
                System.out.println("Max Capacity    : " + l.getMaxCapacity());
                System.out.println("Manager Name    : " + l.getLocationManagerName());
                System.out.println("Manager Address : " + l.getLocationManagerAddress());
                System.out.println("Manager Number  : " + l.getLocationManagerNumber());
                System.out.println();
                
                List<Event> eventList = model.getEventsByLocationID(l.getLocationID());
                System.out.println();
                if (eventList.isEmpty()) {
                    System.out.println("This location has no events");
                    System.out.println();
                }
                else {
                    System.out.println("This location has the following events:");
                    System.out.println();
                    displayEvents(eventList, model);
                    System.out.println();
                }
            }
           
            else {
                System.out.println("Location not found");
            }
    }
     
     private static void editLocationDetails(Scanner keyb, Location l) {
        String nameOfLocation, address, locationManagerName, locationManagerAddress, locationManagerNumber;
        int maxCapacity;
        String line1;

        java.util.Date now = new java.util.Date();
        
        //where user will enter details to edit
        nameOfLocation = getString(keyb, "Enter name of location [" + l.getNameOfLocation() + "]: ");
        address = getString(keyb, "Enter address [" + l.getAddress() + "]: ");
        locationManagerName = getString(keyb, "enter name of manger [" + l.getLocationManagerName() + "]: ");
        locationManagerAddress = getString(keyb, "enter address of manager [" + l.getLocationManagerAddress() + "]: ");
        locationManagerNumber = getString(keyb, "Enter manager number [" + l.getLocationManagerNumber() + "]: ");
        line1 = getString(keyb, "enter maximum capacity: ");
        //line6 = getString(keyb, "Enter location id [" + e.getLocationID() + "]: ");
        
        //if the details of the event have been atlered then it becomes the new value
        if (nameOfLocation.length() != 0) {
            l.setNameOfLocation(nameOfLocation);
        }

        if (address.length() != 0) {
            l.setAddress(address);
        }

       if (line1.length() != 0) {
            maxCapacity = Integer.parseInt(line1);
            l.setMaxCapacity(maxCapacity);
        }

       if (locationManagerName.length() != 0) {
            l.setLocationManagerName(locationManagerName);
        }
       
       if (locationManagerAddress.length() != 0) {
            l.setLocationManagerAddress(locationManagerAddress);
        }
       
       if (locationManagerAddress.length() != 0) {
            l.setLocationManagerAddress(locationManagerAddress);
        }
     }
        
        
        //if (line6.length() != 0) {
          //  locationID = Integer.parseInt(line6);
          // e.setLocationID(locationID);
       // }
     
       
        private static void deleteLocation(Model model, Scanner keyboard) {
          System.out.println("Enter the id number of the location to delete:");
            int id = Integer.parseInt(keyboard.nextLine());
            Location l;

            l = model.findLocationById(id);
            if (l != null) {
                if(model.removeLocation(l)) {
                    System.out.println("Location deleted");
                }
                else{
                    System.out.println("Location not deleted");
                }
            }
            else {
                System.out.println("Location not found");
            }
    }
    
          private static int getInt(Scanner keyb, String prompt, int defaultValue) {
        int opt = defaultValue;
        boolean finished = false;

        do {
            try {
                System.out.print(prompt);
                String line = keyb.nextLine();
                if (line.length() > 0) {
                    opt = Integer.parseInt(line);
                }
                finished = true;
            }
            catch (NumberFormatException e) {
                System.out.println("Exception: " + e.getMessage());
            }
        }
        while (!finished);

        return opt;
    }



     
      

}
