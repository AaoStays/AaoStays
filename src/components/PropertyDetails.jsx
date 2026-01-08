import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import "../PropertyDetails.css";

function PropertyDetails() {
  const { id } = useParams();
  const [property, setProperty] = useState(null);
  const [checkIn, setCheckIn] = useState("");
  const [checkOut, setCheckOut] = useState("");
  const [guests, setGuests] = useState(1);

  useEffect(() => {
    axios
      .get(`http://localhost:8080/api/v1/properties/${id}`)
      .then((res) => setProperty(res.data.data))
      .catch((err) => console.error("Error fetching property:", err));
  }, [id]);

  if (!property) {
    return (
      <div className="loading-container">
        <div className="loading-text">Loading...</div>
      </div>
    );
  }

  const calculateNights = () => {
    if (!checkIn || !checkOut) return 0;
    const start = new Date(checkIn);
    const end = new Date(checkOut);
    const nights = Math.ceil((end - start) / (1000 * 60 * 60 * 24));
    return nights > 0 ? nights : 0;
  };

  const nights = calculateNights();
  const totalPrice = nights * (property.pricePerNight || 0);

  return (
    <div className="property-page">
      {/* Header */}
      <div className="property-header">
        <h1>{property.propertyName || "Property Name"}</h1>
        <div className="property-header-info">
          <span>
            ⭐ {property.ratingAverage || 4.38}
          </span>
          <span>·</span>
          <span>{property.totalReviews || 16} reviews</span>
          <span>·</span>
          <span className="property-location">
            {property.city || "City"}, {property.state || "State"}
          </span>
        </div>
      </div>

      {/* Image Grid */}
      <div className="image-section">
        <img
          className="primary-image"
          src={property.primaryImage || "https://via.placeholder.com/800x600"}
          alt="Primary view"
        />
        <div className="side-images">
          {property.images && property.images.length > 0 ? (
            property.images.slice(0, 2).map((img, idx) => (
              <div key={img.imageId || idx} className="side-image-container">
                <img src={img.imageUrl} alt={`View ${idx + 1}`} />
                {idx === 1 && (
                  <button className="show-images">Show all photos</button>
                )}
              </div>
            ))
          ) : (
            <>
              <div className="side-image-container">
                <img src="https://via.placeholder.com/400x300" alt="View 1" />
              </div>
              <div className="side-image-container">
                <img src="https://via.placeholder.com/400x300" alt="View 2" />
                <button className="show-images">Show all photos</button>
              </div>
            </>
          )}
        </div>
      </div>

      {/* Main Content */}
      <div className="property-body">
        {/* Left Side */}
        <div className="property-details">
          {/* Property Type Info */}
          <div className="property-type-section">
            <h2>
              {property.propertyType || "Villa"} in {property.city || "City"}
            </h2>
            <div className="property-specs">
              {property.maxGuests || 4} guests · {property.bedrooms || 2}{" "}
              bedrooms · {property.beds || 2} beds · {property.bathrooms || 1}{" "}
              bathrooms
            </div>
          </div>

          {/* Description */}
          <div className="description-section">
            <p>
              {property.description ||
                "Experience luxury and comfort in this beautiful property."}
            </p>
          </div>

          {/* Amenities */}
          <div className="amenities-section">
            <h3>What this place offers</h3>
            <div className="amenities-grid">
              <div className="amenity-item">
                🍳 Kitchen: {property.kitchenType || "Full"}
              </div>
              <div className="amenity-item">
                {property.petsAllowed ? "🐕 Pets allowed" : "🚫 No pets"}
              </div>
              <div className="amenity-item">
                {property.smokingAllowed
                  ? "🚬 Smoking allowed"
                  : "🚭 No smoking"}
              </div>
              <div className="amenity-item">
                {property.eventsAllowed ? "🎉 Events allowed" : "🚫 No events"}
              </div>
            </div>
          </div>

          {/* Calendar Section */}
          <div className="calendar-section">
            <h3>
              {nights > 0
                ? `${nights} night${nights > 1 ? 's' : ''} in ${property.city || "City"}`
                : "Select check-in date"}
            </h3>
            <div className="date-inputs">
              <div className="date-input-group">
                <label>Check-in</label>
                <input
                  type="date"
                  value={checkIn}
                  onChange={(e) => setCheckIn(e.target.value)}
                  min={new Date().toISOString().split('T')[0]}
                />
              </div>
              <div className="date-input-group">
                <label>Checkout</label>
                <input
                  type="date"
                  value={checkOut}
                  onChange={(e) => setCheckOut(e.target.value)}
                  min={checkIn || new Date().toISOString().split('T')[0]}
                />
              </div>
            </div>
          </div>

          {/* Reviews Section */}
          <div className="reviews-section">
            <h3 className="reviews-header">
              ⭐ {property.ratingAverage || 4.38} ·{" "}
              {property.totalReviews || 16} reviews
            </h3>

            {/* Review Categories */}
            <div className="review-categories">
              {[
                { name: "Cleanliness", score: 4.1 },
                { name: "Accuracy", score: 4.3 },
                { name: "Check-in", score: 4.5 },
                { name: "Communication", score: 4.7 },
                { name: "Location", score: 4.2 },
                { name: "Value", score: 4.2 },
              ].map((category) => (
                <div key={category.name} className="review-category">
                  <div className="category-header">
                    <span>{category.name}</span>
                    <span className="category-score">{category.score}</span>
                  </div>
                  <div className="category-bar">
                    <div
                      className="category-bar-fill"
                      style={{ width: `${(category.score / 5) * 100}%` }}
                    ></div>
                  </div>
                </div>
              ))}
            </div>

            {/* Sample Reviews */}
            <div className="reviews-list">
              <div className="review-item">
                <div className="review-author">
                  <div className="author-avatar">A</div>
                  <div className="author-info">
                    <div className="author-name">Arham</div>
                    <div className="author-tenure">2 months on Airbnb</div>
                  </div>
                </div>
                <div className="review-meta">⭐⭐⭐⭐⭐ · 3 weeks ago</div>
                <p className="review-text">
                  A wonderful experience overall. The place was clean, and the
                  caretakers were incredibly sweet and attentive.
                </p>
              </div>

              <div className="review-item">
                <div className="review-author">
                  <div className="author-avatar">D</div>
                  <div className="author-info">
                    <div className="author-name">Deepak</div>
                    <div className="author-tenure">4 months on Airbnb</div>
                  </div>
                </div>
                <div className="review-meta">⭐⭐⭐⭐⭐ · 3 weeks ago</div>
                <p className="review-text">
                  Couple friendly and great place, totally worth it.
                </p>
              </div>
            </div>
          </div>
        </div>

        {/* Right Side - Booking Card */}
        <div className="booking-card">
          <div className="booking-card-inner">
            <div className="booking-price">
              <div className="price-amount">
                ₹{(property.pricePerNight || 1500).toLocaleString('en-IN')}{" "}
                <span className="price-nights">
                  for {nights || 5} night{(nights || 5) > 1 ? 's' : ''}
                </span>
              </div>
              <div className="booking-rating">
                ⭐ {property.ratingAverage || 4.38} ·{" "}
                {property.totalReviews || 16} reviews
              </div>
            </div>

            <div className="booking-form">
              <div className="booking-inputs">
                <div className="booking-input">
                  <label className="input-label">Check-in</label>
                  <input
                    type="date"
                    value={checkIn}
                    onChange={(e) => setCheckIn(e.target.value)}
                    min={new Date().toISOString().split('T')[0]}
                  />
                </div>
                <div className="booking-input">
                  <label className="input-label">Checkout</label>
                  <input
                    type="date"
                    value={checkOut}
                    onChange={(e) => setCheckOut(e.target.value)}
                    min={checkIn || new Date().toISOString().split('T')[0]}
                  />
                </div>
                <div className="booking-input">
                  <label className="input-label">Guests</label>
                  <select
                    value={guests}
                    onChange={(e) => setGuests(Number(e.target.value))}
                  >
                    {[...Array(property.maxGuests || 4)].map((_, i) => (
                      <option key={i + 1} value={i + 1}>
                        {i + 1} guest{i > 0 ? "s" : ""}
                      </option>
                    ))}
                  </select>
                </div>
              </div>
            </div>

            <button className="reserve-btn">Reserve</button>

            <p className="no-charge-text">You won't be charged yet</p>

            {nights > 0 && (
              <div className="price-breakdown">
                <div className="price-row">
                  <span className="price-row-label">
                    ₹{(property.pricePerNight || 1500).toLocaleString('en-IN')} × {nights} night{nights > 1 ? 's' : ''}
                  </span>
                  <span>₹{totalPrice.toLocaleString('en-IN')}</span>
                </div>
                <div className="price-total">
                  <span>Total</span>
                  <span>₹{totalPrice.toLocaleString('en-IN')}</span>
                </div>
              </div>
            )}

            <div className="price-note">🏷️ Prices include all fees</div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default PropertyDetails;