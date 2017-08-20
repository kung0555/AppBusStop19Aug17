package com.example.toto.projertbutstop;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ben Kung on 20-Aug-17.
 */

public class TeatBusPart {
    TeatBusPart(ArrayList<String> busPast) {

    }
    public List<TeatBusPart> getBusPast() {
        return busPast;
    }

    public void setBusPast(List<TeatBusPart> busPast) {
        this.busPast = busPast;
    }

    private List<TeatBusPart> busPast = new ArrayList<>();
}
