package com.Servlets.Test;

/**
 * Created by User on 3/11/2018.
 */

import com.Expedia.Data;
import com.Expedia.Offers.Offer;
import com.Expedia.Search;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchTest {

    List<Offer> offersToDisplay;
    Search searchFilter;

    public void getData() {
        try {
            URL expedia = new URL("https://offersvc.expedia.com/offers/v2/getOffers?scenario=deal-finder&page=foo&uid=foo&productType=Hotel");
            URLConnection yc = expedia.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            String input = in.readLine();
            Gson gson = new Gson();
            Data data = gson.fromJson(input, Data.class);
            //
            offersToDisplay = new ArrayList<Offer>();
            for (Offer offer : data.offers.Hotel) {
                offersToDisplay.add(offer);
            }

        } catch (MalformedURLException e) {
            System.out.println("Wrong URL format");
        } catch (IOException e) {
            System.out.println("Failed to open URL");
        }
    }

    private void getSearchResults() {

        if (searchFilter.getDestination().length() > 0) {
            offersToDisplay = offersToDisplay.stream().filter(x -> x.destination.shortName.equals(searchFilter.getDestination())).collect(Collectors.toList());
        }

        if (searchFilter.getMinStartDate().length() > 0) {
            offersToDisplay = offersToDisplay.stream().filter(x -> x.offerDateRange.getStartDate().equals(searchFilter.getMinStartDate())).collect(Collectors.toList());
        }

        if (searchFilter.getMaxStartDate().length() > 0) {
            offersToDisplay = offersToDisplay.stream().filter(x -> x.offerDateRange.getStartDate().equals(searchFilter.getMaxStartDate())).collect(Collectors.toList());
        }

        if (searchFilter.getMinTotalRate().length() > 0) {
            offersToDisplay = offersToDisplay.stream().filter(x -> x.hotelInfo.hotelStarRating >= Double.parseDouble(searchFilter.getMinTotalRate())).collect(Collectors.toList());
        }

        if (searchFilter.getMaxTotalRate().length() > 0) {
            offersToDisplay = offersToDisplay.stream().filter(x -> x.hotelInfo.hotelStarRating <= Double.parseDouble(searchFilter.getMaxTotalRate())).collect(Collectors.toList());
        }

        if (searchFilter.getHotelName().length() > 0) {
            offersToDisplay = offersToDisplay.stream().filter(x -> x.hotelInfo.hotelName.equals(searchFilter.getHotelName())).collect(Collectors.toList());
        }
    }

    @Before
    public void test1() {
        getData();
    }

    @Test
    public void test1Result() {

        searchFilter = new Search();
        searchFilter.setHotelName("");
        searchFilter.setDestination("");
        searchFilter.setMaxStartDate("");
        searchFilter.setMinStartDate("");
        searchFilter.setMinTotalRate("");
        searchFilter.setMaxTotalRate("");
        getSearchResults();
        Assert.assertEquals(offersToDisplay.size(), 6);
    }

    @Test
    public void test2Result() {
        searchFilter = new Search();
        searchFilter.setHotelName("");
        searchFilter.setDestination("Miami");
        searchFilter.setMaxStartDate("");
        searchFilter.setMinStartDate("");
        searchFilter.setMinTotalRate("");
        searchFilter.setMaxTotalRate("");
        getSearchResults();
        Assert.assertEquals(offersToDisplay.size(), 1);
    }

    @Test(expected = Exception.class)
    public void test3Result() {
        searchFilter = new Search();
        searchFilter.setHotelName("");
        searchFilter.setDestination("");
        searchFilter.setMaxStartDate("");
        searchFilter.setMinStartDate("2");
        searchFilter.setMinTotalRate("");
        searchFilter.setMaxTotalRate("");
        getSearchResults();
    }
}
