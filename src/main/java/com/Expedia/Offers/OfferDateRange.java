package com.Expedia.Offers;

import java.util.Calendar;

/**
 * Created by User on 3/9/2018.
 */
public class OfferDateRange {

    public int[] travelStartDate = new int[3];
    public int[] travelEndDate = new int[3];
    public int lengthOfStay;

    public String getStartDate(){
        return travelStartDate[0]+"-"+travelStartDate[1]+"-"+travelStartDate[2];
    }

    public Calendar getEndDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, travelEndDate[0]);
        calendar.set(Calendar.MONTH,travelEndDate[1]);
        calendar.set(Calendar.DAY_OF_MONTH,travelEndDate[2]);
        return calendar;
    }
}
