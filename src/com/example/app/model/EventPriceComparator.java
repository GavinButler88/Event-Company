package com.example.app.model;

import java.util.Comparator;


public class EventPriceComparator implements Comparator<Event>{

    @Override
    public int compare(Event e1, Event e2) {
        return (int)(e1.getPrice() - e2.getPrice());
    }
    
}
