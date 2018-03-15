package com.Servlets;

import com.Expedia.Data;
import com.Expedia.Offers.Offer;
import com.Expedia.Search;
import com.Expedia.SearchResults;
import com.google.gson.Gson;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by User on 3/10/2018.
 */
@WebServlet(urlPatterns = "/MainServlet",
name = "MainServlet")
public class MainServlet extends HttpServlet {

    private Data data;
    private List<Offer> offersToDisplay;
    //SearchResults results;
    Search searchFilter;

    @Override
    public void init(){
        //Read data from JSON file on Servlet load
        getFromJson();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Refresh data at each search, same for search filters
        getFromJson();
        searchFilter = new Search();
        //Get search filters values
        searchFilter.setDestination(request.getParameter("destination"));
        searchFilter.setMinStartDate(request.getParameter("minStartDate"));
        searchFilter.setMaxStartDate(request.getParameter("maxStartDate"));
        searchFilter.setMinTotalRate(request.getParameter("minTotalRate"));
        searchFilter.setMaxTotalRate(request.getParameter("maxTotalRate"));
        searchFilter.setHotelName(request.getParameter("hotelName"));
        if (!validateSearch()){
            //If s
            System.out.println("Wrong filters format, please recheck");
            offersToDisplay.clear();
        }
        else{
            getSearchResults();

        }
        request.getSession().setAttribute("offers",offersToDisplay);
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        if (dispatcher != null){
            dispatcher.forward(request,response);
        }
    }

    private void getSearchResults() {

        if (searchFilter.getDestination().length()>0){
            offersToDisplay= offersToDisplay.stream().filter(x -> x.destination.shortName.equals(searchFilter.getDestination())).collect(Collectors.toList());
        }

        if (searchFilter.getMinStartDate().length()>0){
            offersToDisplay=offersToDisplay.stream().filter(x -> x.offerDateRange.getStartDate().equals(searchFilter.getMinStartDate())).collect(Collectors.toList());
        }

        if(searchFilter.getMaxStartDate().length()>0){
            offersToDisplay=offersToDisplay.stream().filter(x -> x.offerDateRange.getStartDate().equals(searchFilter.getMaxStartDate())).collect(Collectors.toList());
        }

        if (searchFilter.getMinTotalRate().length()>0){
            offersToDisplay=offersToDisplay.stream().filter(x -> x.hotelInfo.hotelStarRating >= Double.parseDouble(searchFilter.getMinTotalRate())).collect(Collectors.toList());
        }

        if (searchFilter.getMaxTotalRate().length()>0){
            offersToDisplay=offersToDisplay.stream().filter(x -> x.hotelInfo.hotelStarRating <= Double.parseDouble(searchFilter.getMaxTotalRate())).collect(Collectors.toList());
        }

        if (searchFilter.getHotelName().length()>0){
            offersToDisplay=offersToDisplay.stream().filter(x -> x.hotelInfo.hotelName.equals(searchFilter.getHotelName())).collect(Collectors.toList());
        }
    }

    private boolean validateSearch() {

         try {
            if (searchFilter.getMinStartDate().length()>0 && searchFilter.getMaxStartDate().length()>0 &&  ! new SimpleDateFormat("yyyy-mm-dd").parse(searchFilter.getMaxStartDate()).after( new SimpleDateFormat("yyyy-mm-dd").parse(searchFilter.getMinStartDate()))){
                return false;
            }
            if (searchFilter.getMaxStartDate().length()>0){
                new SimpleDateFormat("yyyy-mm-dd").parse(searchFilter.getMaxStartDate());
            }
            if (searchFilter.getMinStartDate().length()>0){
                new SimpleDateFormat("yyyy-mm-dd").parse(searchFilter.getMaxStartDate());
            }
        } catch (ParseException e) {
             System.out.println("Wrong Date format");
            return false;
        }

        if (searchFilter.getMinTotalRate().length()>0 && ! validateRate(searchFilter.getMinTotalRate())){
            return false;
        }

        if (searchFilter.getMaxTotalRate().length()>0 && ! validateRate(searchFilter.getMaxTotalRate())){
            return false;
        }

        if (searchFilter.getMinTotalRate().length()>0 && searchFilter.getMaxTotalRate().length()>0){
            if (Double.parseDouble(searchFilter.getMaxTotalRate()) - Double.parseDouble(searchFilter.getMinTotalRate()) <0){
                return false;
            }
        }
        return true;
    }

    private boolean validateRate(String minTotalRate) {
        try{
            Double.parseDouble(minTotalRate);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //results = new SearchResults(offersToDisplay);
        request.getSession().setAttribute("offers",offersToDisplay);
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        if (dispatcher != null){
            dispatcher.forward(request,response);
        }
    }

    private void getFromJson() {
        try {
            URL expedia = new URL("https://offersvc.expedia.com/offers/v2/getOffers?scenario=deal-finder&page=foo&uid=foo&productType=Hotel");
            URLConnection yc = expedia.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            String input = in.readLine();
            Gson gson = new Gson();
            data = gson.fromJson(input, Data.class);
            //
             offersToDisplay= new ArrayList<Offer>();
            for (Offer offer: data.offers.Hotel){
                offersToDisplay.add(offer);
            }
        } catch (MalformedURLException e) {
            System.out.println("Wrong URL format");
        } catch (IOException e) {
            System.out.println("Failed to open URL");
        }
    }
}
