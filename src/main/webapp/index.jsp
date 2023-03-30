<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
  <head>
    <title>Hotel Search Page</title>
    <link rel="stylesheet" href="index.css">
  </head>
  <body>
    <header>
      <div class="container">
        <h1>Hotel Search Page</h1>
        <form>
          <input type="text" placeholder="Search for items...">
          <button type="submit" class="search-button">Search</button>
        </form>
        <div class="login-buttons">
          <a href="login"><button class="login">Login</button></a>
          <a href="signup"><button class="signup">Sign Up</button></a>
          <a href="profile"><button class="signup">My Profile</button></a>
        </div>
      </div>

    </header>
    <main>
      <div class="container">
        <div class="filters">
          <h2>Filters</h2>
          <form>
            <label for="check-in">Check-In:</label>
            <input type="date" id="check-in" name="check-in">

            <label for="check-out">Check-Out:</label>
            <input type="date" id="check-out" name="check-out">
            <label for="category">Hotel Category</label>
            <select id="category" name="category">
              <option value="all">All Categories</option>
              <option value="electronics">Resort</option>
              <option value="furniture">Motel</option>
              <option value="cars">Inn</option>
              <option value="housing">Luxary</option>
            </select>

            <label for="room-type">Room Type:</label>
            <select id="room-type" name="room-type">
              <option value="">Any</option>
              <option value="single">Single</option>
              <option value="double">Double</option>
              <option value="suite">Suite</option>
            </select>

            <label for="room-capacity">Room Capacity:</label>
            <input type="number" id="room-capacity" name="room-capacity" min="1" max="10">

            <label for="area">Area:</label>
            <select id="area" name="area">
              <option value="">Any</option>
              <option value="downtown">Downtown</option>
              <option value="uptown">Uptown</option>
              <option value="midtown">Midtown</option>
            </select>

            <label for="hotel-chain">Hotel Chain:</label>
            <select id="hotel-chain" name="hotel-chain">
              <option value="">Any</option>
              <option value="hilton">Hilton</option>
              <option value="marriott">Marriott</option>
              <option value="ihg">IHG</option>
            </select>
            <label for="location">Location</label>
            <input type="text" id="location" name="location" placeholder="Enter location...">
            <div class="price-filter filter">
                <h3>Price</h3>
                <div class="form-group">
                  <label for="price-min">Min</label>
                  <input type="number" id="price-min" name="price-min" placeholder="Min price" min="0">
                </div>
                <div class="form-group">
                  <label for="price-max">Max</label>
                  <input type="number" id="price-max" name="price-max" placeholder="Max price" min="0">
                </div>
              </div>
            <button type="submit">Apply Filters</button>
          </form>
        </div>
        <div class="results">
          <h2>Results</h2>
          <ul>
            <li>
              <div class="item">
                <img src="https://via.placeholder.com/150x150" alt="Item Image">
                <h3>Hotel Name</h3>
                <p>Item Description</p>
                <p class="price">$25.00 per night</p>
                <p class="location">Toronto, ON</p>
              </div>
            </li>
            <li>
              <div class="item">
                <img src="https://via.placeholder.com/150x150" alt="Item Image">
                <h3>Hotel Name</h3>
                <p>Item Description</p>
                <p class="price">$100.00 per night</p>
                <p class="location">Mississauga, ON</p>
              </div>
            </li>
            <li>
              <div class="item">
                <img src="https://via.placeholder.com/150x150" alt="Item Image">
                <h3>Hotel Name</h3>
                <p>Item Description</p>
                <p class="price">$75.00 per night</p>
                <p class="location">Brampton, ON</p>
              </div>
            </li>
          </ul>
        </div>
      </div>
    </main>
  </body>
</html>