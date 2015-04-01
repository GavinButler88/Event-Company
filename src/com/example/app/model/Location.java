//a
package com.example.app.model;

public class Location {
    
    private int locationID;
    private String nameOfLocation;
    private String address;
    private int maxCapacity;
    private String locationManagerName;
    private String locationManagerAddress;
    private String locationManagerNumber;
    
    public Location(int lid, String nol, String a, int max, String lmn, String lma, String lmno) {
        this.locationID = lid;
        this.nameOfLocation = nol;
        this.address = a;
        this.maxCapacity = max;
        this.locationManagerName = lmn;
        this.locationManagerAddress = lma;
        this.locationManagerNumber = lmno;
    }
    
    public Location(String nol, String a, int max, String lmn, String lma, String lmno) {
            this(-1, nol, a, max, lmn, lma, lmno);
    }

    public int getLocationID() {
        return locationID;
    }

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }
    

    public String getNameOfLocation() {
        return nameOfLocation;
    }

    public void setNameOfLocation(String nameOfLocation) {
        this.nameOfLocation = nameOfLocation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public String getLocationManagerName() {
        return locationManagerName;
    }

    public void setLocationManagerName(String locationManagerName) {
        this.locationManagerName = locationManagerName;
    }

    public String getLocationManagerAddress() {
        return locationManagerAddress;
    }

    public void setLocationManagerAddress(String locationManagerAddress) {
        this.locationManagerAddress = locationManagerAddress;
    }

    public String getLocationManagerNumber() {
        return locationManagerNumber;
    }

    public void setLocationManagerNumber(String locationManagerNumber) {
        this.locationManagerNumber = locationManagerNumber;
    }
}
