<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="Utilities.*, Entities.*, java.util.ArrayList, java.sql.Date"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Hotel Search Page</title>
        <link rel="stylesheet" type="text/css" href="css/index.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    </head>
  <body>
    <%
    Object loggedInObject = session.getAttribute("isLoggedIn");
    boolean isLoggedIn = loggedInObject != null && (boolean)loggedInObject == true;
    ArrayList<HotelChain> hotelChains = (ArrayList<HotelChain>)request.getAttribute("hotelChains");
    ArrayList<RoomDisplay> rooms = (ArrayList<RoomDisplay>)request.getAttribute("rooms");
    %>
    <header>
        <h1>Hotel Search Page</h1>
        <div class="login-buttons">
            <% if(!isLoggedIn) {%>
            <a href="login"><button class="login">Login</button></a>
            <a href="signup"><button class="signup">Sign Up</button></a>
            <%} else {%>
            <a href="profile"><button class="signup">My Profile</button></a>
            <%}%>
        </div>
    </header>
        <div class="container">
            <div class="filters">
            <h2>Filters</h2>
            <form action="/" method="post"><div class="filter-list">

                <div class="filter-item">
                    <label for="check-in">Check-In</label>
                    <input type="date" id="check-in" name="check-in" value="<%=session.getAttribute("confirmedStartDate")%>">
                </div>

                <div class="filter-item">
                    <label for="check-out">Check-Out</label>
                    <input type="date" id="check-out" name="check-out" value="<%=session.getAttribute("confirmedEndDate")%>">
                </div>

                <%if(session.getAttribute("confirmedStartDate") == null || session.getAttribute("confirmedEndDate") == null) {%>
                <div class="filter-warning">
                    <p> Check-in and Check-out both need to be set to be able to book a room </p>
                </div>
                <%}%>

                <%
                int categoryId = 0;
                Object categoryObj = request.getAttribute("category");
                if(categoryObj != null) {
                    categoryId = (int)categoryObj;
                }
                %>
                <div class="filter-item">
                    <label for="category">Hotel Category</label>
                    <select id="category" name="category">
                        <option value="0" <%=((categoryId==0)?"selected":"")%>>All Categories</option>
                        <option value="1" <%=((categoryId==1)?"selected":"")%>>Resort</option>
                        <option value="2" <%=((categoryId==2)?"selected":"")%>>Motel</option>
                        <option value="3" <%=((categoryId==3)?"selected":"")%>>Inn</option>
                        <option value="4" <%=((categoryId==4)?"selected":"")%>>Luxary</option>
                    </select>
                </div>

                <%
                int roomSizeId = 0;
                Object capacityObj = request.getAttribute("capacity");
                if(capacityObj != null) {
                    roomSizeId = (int)capacityObj;
                }
                %>
                <div class="filter-item">
                    <label for="room-size">Room Capacity</label>
                    <select id="room-size" name="room-size">
                        <option value="0" <%=((roomSizeId==0)?"selected":"")%>>Any</option>
                        <option value="1" <%=((roomSizeId==1)?"selected":"")%>>Single</option>
                        <option value="2" <%=((roomSizeId==2)?"selected":"")%>>Double</option>
                        <option value="3" <%=((roomSizeId==3)?"selected":"")%>>Triple</option>
                        <option value="4" <%=((roomSizeId==4)?"selected":"")%>>Quadruple</option>
                        <option value="5" <%=((roomSizeId==5)?"selected":"")%>>Quintuple</option>
                    </select>
                </div>

                <%
                int chainId = 0;
                Object chainObj = request.getAttribute("chain");
                if(chainObj != null) {
                    chainId = (int)chainObj;
                }
                %>
                <div class="filter-item">
                    <label for="hotel-chain">Hotel Chain</label>
                    <select id="hotel-chain" name="hotel-chain">
                        <option value="0" <%=((chainId==0)?"selected":"")%>>Any</option>
                        <%if(hotelChains != null) {
                            for(int i = 0; i < hotelChains.size(); i++) {%>
                                <option value=<%=hotelChains.get(i).getChainId()%> <%=((chainId==(i+1))?"selected":"")%>><%=hotelChains.get(i).getChainName()%></option>
                            <%}
                        }%>
                    </select>
                </div>

                <div class="filter-item">
                    <label for="area">City / Area</label>
                    <input type="text" id="area" name="area" placeholder="Enter location..." value="${area}">
                </div>

                <h3>Price</h3>

                <div class="filter-item-price">
                    <div class="filter-item-price-group">
                    <label for="price-min">Min</label>
                    <input type="number" id="price-min" name="price-min" placeholder="Min price" min="0" value="${minPrice}">
                    </div>
                    <div class="filter-item-price-group">
                    <label for="price-max">Max</label>
                    <input type="number" id="price-max" name="price-max" placeholder="Max price" min="0" value="${maxPrice}">
                    </div>
                </div>

                <button type="submit" name="ApplyFilters" value ="submit">Apply Filters</button>
            </div></form>
        </div>
        <div class="results"><h2>Results</h2><ul>
            <%
            Date startDate = (Date)session.getAttribute("confirmedStartDate");
            Date endDate = (Date)session.getAttribute("confirmedEndDate");
            boolean allowedToBook = isLoggedIn && startDate != null && endDate != null && startDate.compareTo(endDate) == -1;

            for(int i = 0; i < rooms.size(); i++) {
                RoomDisplay room = rooms.get(i);
                int stars = (int)room.rating;
            %>

            <li><div class="item">
                <img src="https://via.placeholder.com/150x150" alt="Item Image">
                <div class="item-vertical">
                    <p class="chain-name"><%=room.chainName%></p>
                    <p class="price">$<%=String.format("%.2f",room.roomPrice)%> per night</p>
                    <p class="location"><%=room.address.getRegion()%>, <%=room.address.getCity()%></p>
                    <div>
                        <span class="fa fa-star <%if(stars >= 1){%>checked<%}%>"></span>
                        <span class="fa fa-star <%if(stars >= 2){%>checked<%}%>"></span>
                        <span class="fa fa-star <%if(stars >= 3){%>checked<%}%>"></span>
                        <span class="fa fa-star <%if(stars >= 4){%>checked<%}%>"></span>
                        <span class="fa fa-star <%if(stars >= 5){%>checked<%}%>"></span>
                    </div>
                </div>
                <div class="item-vertical-bar"></div>
                <div class="item-vertical" style="flex: 1;">
                    <p class="item-address"><%=room.address.getStreetNumber()%> <%=room.address.getStreetName()%>, <%=room.address.getCity()%>, <%=room.address.getRegion()%></p>
                    <p class="item-person">For <%=room.roomCapacity%> person<%=((room.roomCapacity > 1)?"s":"")%></p>
                    <div class="item-tag-list">
                        <%if(RoomTags.TV.checkIfTagIsPresent(room.roomTags)) {%><p class="item-tags">TV</p><%}%>
                        <%if(RoomTags.AirConditioning.checkIfTagIsPresent(room.roomTags)) {%><p class="item-tags">Air Conditioning</p><%}%>
                        <%if(RoomTags.Microwave.checkIfTagIsPresent(room.roomTags)) {%><p class="item-tags">Microwave</p><%}%>
                        <%if(RoomTags.Fridge.checkIfTagIsPresent(room.roomTags)) {%><p class="item-tags">Fridge</p><%}%>
                        <%if(RoomTags.SmokingAllowed.checkIfTagIsPresent(room.roomTags)) {%><p class="item-tags">Smoking Allowed</p><%}%>
                        <%if(RoomTags.PetsFriendly.checkIfTagIsPresent(room.roomTags)) {%><p class="item-tags">Pets Friendly</p><%}%>
                        <%if(RoomTags.SeaView.checkIfTagIsPresent(room.roomTags)) {%><p class="item-tags">Sea View</p><%}%>
                        <%if(RoomTags.MountainView.checkIfTagIsPresent(room.roomTags)) {%><p class="item-tags">Mountain View</p><%}%>
                    </div>
                </div>
                <div class="item-vertical-bar"></div>
                <form method="post" style="align-self: center;" id="tooltip-container">
                    <button class="book-btn" type="submit" name="BookButton" id="trigger" value=<%="book-"+room.roomId%>

                    <%if(!allowedToBook) {%>
                        disabled
                    <%}%>

                    >Book</button>
                </form>
            </div></li>

            <%}%>

        </ul></div></div>
  </body>
</html>