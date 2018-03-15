package com.Expedia;


/**
 * Created by User on 3/10/2018.
 *A class represents Search filters
 */
public class Search {

    private String destination;
    private String minStartDate;
    private String maxStartDate;
    private String minTotalRate;
    private String maxTotalRate;
    private String hotelName;

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getMinStartDate() {
        return minStartDate;
    }

    public void setMinStartDate(String minStartDate) {
        this.minStartDate = minStartDate;
    }

    public String getMaxStartDate() {
        return maxStartDate;
    }

    public void setMaxStartDate(String maxStartDate) {
        this.maxStartDate = maxStartDate;
    }


    public String getMinTotalRate() {
        return minTotalRate;
    }

    public void setMinTotalRate(String minTotalRate) {
        this.minTotalRate = minTotalRate;
    }

    public String getMaxTotalRate() {
        return maxTotalRate;
    }

    public void setMaxTotalRate(String maxTotalRate) {
        this.maxTotalRate = maxTotalRate;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }
}
