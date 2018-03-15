package com.Expedia;

import com.Expedia.Offers.Offer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 3/11/2018.
 */
public class SearchResults {
    private List<Offer> offersToDisplay;

    public SearchResults(List<Offer> offersToDisplay){
        this.offersToDisplay = offersToDisplay;
    }

}
