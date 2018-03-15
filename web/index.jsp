<%@ page import="java.util.ArrayList" %>
<%@ page import="com.Expedia.Offers.Offer" %>
<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 3/10/2018
  Time: 6:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

  <head>
    <title>Expedia Hotels Deals</title>
  </head>
  <body>

    <div>
      <div align="center" style="margin-top: 50px;">

        <form action="${pageContext.request.contextPath}/MainServlet" method="POST">
          Destination:  <input type="text" name="destination" size="20px"> <br>
          Min Start Date:  <input type="text" name="minStartDate" size="20px"> <br>
          Max Start Date:  <input type="text" name="maxStartDate" size="20px"> <br>
          Min rating:  <input type="text" name="minTotalRate" size="20px"> <br>
          Max rating:  <input type="text" name="maxTotalRate" size="20px"> <br>
          Hotel Name:  <input type="text" name="hotelName" size="20px"> <br>
          <br>
          <input type="submit" value="Search">
        </form>

      </div>
    </div>
    <div>
      <table>
          <thead>
          <tr>
              <td>Hotel Name</td>
              <td></td>
              <td></td>
              <td>Price</td>
              <td></td>
              <td></td>
              <td>Rank</td>
              <td></td>
          </tr>
          </thead>
        <tbody>
          <%
            ArrayList<Offer> offers = (ArrayList<Offer>)request.getSession().getAttribute("offers");
            if (offers != null){
                for (int i=0; i< offers.size(); i++){
                    String hotelName = offers.get(i).hotelInfo.hotelName;
                    double price = offers.get(i).hotelPricingInfo.totalPriceValue;
                    double rank = offers.get(i).hotelInfo.hotelStarRating;
          %>
          <tr>
              <td><%=hotelName%></td>
              <td></td>
              <td></td>
              <td><%=price%></td>
              <td></td>
              <td></td>
              <td><%=rank%></td>
              <td></td>

          </tr>
          <%
                  }
            }

          %>
        </tbody>
      </table>
    </div>
  </body>
</html>
