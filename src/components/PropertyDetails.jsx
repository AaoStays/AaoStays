
import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import "../css/PropertyDetails.css";

function PropertyDetails() {
  const { id } = useParams();

  const [property, setProperty] = useState(null);
  const [images, setImages] = useState([]);
  const [primaryImage, setPrimaryImage] = useState(null);
  const [loading, setLoading] = useState(true);

  const [rooms, setRooms] = useState([]);
  const [checkIn, setCheckIn] = useState("");
  const [checkOut, setCheckOut] = useState("");
  const [guests, setGuests] = useState(1);
  const [bookingLoading, setBookingLoading] = useState(false);
  const [selectedOption, setSelectedOption] = useState("")
  const [selectedRoom, setSelectedRoom] = useState(null);



  useEffect(() => {
    axios
      .get(`http://localhost:8080/api/v1/properties/${id}`)
      .then((res) => {
        setProperty(res.data.data);
      })
      .catch((err) => {
        console.error("Error fetching property:", err);
      });
  }, [id]);

  // 🔹 Fetch Property Images
  useEffect(() => {
    axios
      .get(`http://localhost:8080/api/v1/properties/${id}/images`)
      .then((res) => {
        setImages(res.data);

        const primary =
          res.data.find((img) => img.isPrimary) || res.data[0];
        setPrimaryImage(primary);
      })
      .catch((err) => {
        console.error("Error fetching images:", err);
      })
      .finally(() => setLoading(false));
  }, [id]);

  // 🔹 Fetch Available Rooms
  useEffect(() => {
    axios
      .get(`http://localhost:8080/api/v1/rooms/property/${id}/available`)
      .then((res) => setRooms(res.data.data || []))
      .catch((err) => console.error("Error fetching rooms:", err));
  }, [id]);

  const handleBookingOptionChange = (value) => {
  setSelectedOption(value);

  if (value === "PROPERTY") {
      setSelectedRoom(null);
    } else {
      const room = rooms.find(r => r.roomId === Number(value));
      setSelectedRoom(room || null);
    }
  };
  // 🔹 BOOK ROOM FUNCTION
  const bookRoom = async (roomId) => {
    if (!checkIn || !checkOut) {
      alert("Please select check-in and check-out dates");
      return;
    }

    try {
      setBookingLoading(true);

      await axios.post(
        `http://localhost:8080/api/v1/rooms/${roomId}/book`,
        {
          checkInDate: checkIn,
          checkOutDate: checkOut,
          guests: guests,
        }
      );

      alert("Room booked successfully 🎉");

  
      const res = await axios.get(
        `http://localhost:8080/api/v1/rooms/property/${id}/available`
      );
      setRooms(res.data.data || []);
    } catch (err) {
      alert(err.response?.data?.message || "Booking failed");
    } finally {
      setBookingLoading(false);
    }
  };
  
  const handleBookNow = () => {
    if (checkIn || checkOut) {
      alert("Please select check-in and check-out dates");
      return;
    }

    const phoneNumber = "918638605575";

    const message = `
    Hello AAOStays team 👋
    I would like to book the ${property.title || "property"}.

    Number of guests: ${guests}

    Please confirm availability and booking details.
    Thank you.
        `;

    const whatsappUrl = `https://wa.me/${phoneNumber}?text=${encodeURIComponent(
      message.trim()
    )}`;

    window.open(whatsappUrl, "_blank");
  };
  
  
  if (loading) return <p className="loading">Loading property...</p>;
  if (!property) return <p>Property not found</p>;
  
  return (
  
    
    <div className="property-details-container">

      <div className="property-content">
        {/* HEADER SECTION */}
        <div className="property-header">
          <div className="property-title-section">
            <h1 className="property-name">{property.propertyName}</h1>
            <div className="property-location">
              <span className="location-icon">📍</span>
              <span className="city">{property.city}, India</span>
            </div>
            <div className="property-type-tag">{property.propertyType}</div>
          </div>
          <div className="property-actions">
            <button className="wishlist-btn">
              <span className="heart">❤️</span> Save
            </button>
          </div>
        </div>

        {/* MAIN IMAGE */}
      <div className="gallery">
        <div className="main-image">
          {primaryImage ? (
            <img src={primaryImage.imageUrl} alt="Primary" />
          ) : (
            <p>No image</p>
          )}
        </div>

        <div className="thumbnails">
          {images.map((img) => (
            <img
              key={img.imageId}
              src={img.imageUrl}
              alt="thumb"
              className={
                primaryImage?.imageId === img.imageId
                  ? "thumb active"
                  : "thumb"
              }
              onClick={() => setPrimaryImage(img)}
            />
          ))}
        </div>
      </div>

        {/* MAIN CONTENT GRID */}
        <div className="content-grid">
          {/* LEFT COLUMN - PROPERTY INFO */}
          <div className="property-info">
            {/* DESCRIPTION */}
            <div className="description-card">
              <h3 className="section-title">About this property</h3>
              <p className="description-text">{property.description || "A comfortable stay with all necessary amenities for a relaxing experience."}</p>
            </div>

            {/* AMENITIES */}
            <div className="amenities-card">
              <h3 className="section-title">About Property</h3>
              <div className="amenities-grid">
                <div className="amenity-item">
                  <span className="amenity-icon">👥</span>
                  <div className="amenity-details">
                    <p className="amenity-label">Guests</p>
                    <p className="amenity-value">{property.baseGuests} base • {property.maxGuests} max</p>
                  </div>
                </div>
                <div className="amenity-item">
                  <span className="amenity-icon">🛏️</span>
                  <div className="amenity-details">
                    <p className="amenity-label">Sleeping</p>
                    <p className="amenity-value">{property.bedrooms} bedroom • {property.beds} beds</p>
                  </div>
                </div>
                <div className="amenity-item">
                  <span className="amenity-icon">🚿</span>
                  <div className="amenity-details">
                    <p className="amenity-label">Bathrooms</p>
                    <p className="amenity-value">{property.bathrooms} bathroom • {property.restrooms} restroom</p>
                  </div>
                </div>
                <div className="amenity-item">
                  <span className="amenity-icon">🍳</span>
                  <div className="amenity-details">
                    <p className="amenity-label">Kitchen</p>
                    <p className="amenity-value">{property.kitchenType || "Fully equipped"}</p>
                  </div>
                </div>
              </div>
            </div>

            {/* POLICIES */}
            <div className="policies-card">
              <h3 className="section-title">House Rules</h3>
              <div className="policies-list">
                <div className={`policy-tag ${property.petsAllowed ? 'allowed' : 'not-allowed'}`}>
                  {property.petsAllowed ? '🐕 Pets Allowed' : '🚫 No Pets'}
                </div>
                <div className={`policy-tag ${property.smokingAllowed ? 'allowed' : 'not-allowed'}`}>
                  {property.smokingAllowed ? '🚬 Smoking Allowed' : '🚫 No Smoking'}
                </div>
                <div className={`policy-tag ${property.eventsAllowed ? 'allowed' : 'not-allowed'}`}>
                  {property.eventsAllowed ? '🎉 Events Allowed' : '🚫 No Events'}
                </div>
                <div className={`policy-tag ${property.extraGuestAllowed ? 'allowed' : 'not-allowed'}`}>
                  {property.extraGuestAllowed ? '👥 Extra Guests: ₹' + property.extraGuestFee : '🚫 No Extra Guests'}
                </div>
              </div>
            </div>
          </div>

          {/* RIGHT COLUMN - BOOKING CARD */}
          <div className="booking-card">
            <div className="booking-header">
              <h3 className="booking-title">Book Your Stay</h3>
              <span className={`status-badge ${property.isActive ? "active" : "inactive"}`}>
                {property.isActive ? "✅ Available" : "❌ Not Available"}
              </span>
            </div>

            {/* PRICE SECTION */}
            <div className="price-section">
              <p className="price-label">Price per night</p>
              <p className="price-amount">
                ₹
                {selectedOption === "PROPERTY"
                  ? property.pricePerNight
                  : selectedRoom
                  ? selectedRoom.pricePerNight
                  : "—"}
                <span className="price-unit"> / night</span>
              </p>
            </div>

            {/* DATES */}
        <div className="check-timings">
         <div className="check-in"> 
          <p className="time-label">Check-in</p> 
          <p className="time-value">{property.checkInTime || "2:00 PM"}</p> 
         </div> 
         <div className="check-out">
            <p className="time-label">Check-out</p>
            <p className="time-value">{property.checkOutTime || "11:00 AM"}</p>
          </div> 
          
        </div>     
            <div className="booking-form">
              <div className="date-inputs">
                <div className="input-group">
                  <label>Check-in Date</label>
                  <input className="date-input"
                    type="date"
                    value={checkIn}
                    onChange={(e) => setCheckIn(e.target.value)}
                  />
                </div>

                <div className="input-group">
                  <label>Check-out Date</label>
                  <input className="date-input"
                    type="date"
                    value={checkOut}
                    onChange={(e) => setCheckOut(e.target.value)}
                  />
                </div>
              </div>

              {/* BOOKING TYPE DROPDOWN */}
              <div className="input-group">
                <label>Select Booking Type</label>
                <select className="date-input"
                  value={selectedOption}
                  onChange={(e) => handleBookingOptionChange(e.target.value)}
                >
                  <option value="">-- Select Option --</option>

                  {/* ✅ Entire Property */}
                  <option value="PROPERTY">
                    Entire Property — ₹{property.pricePerNight}/night
                  </option>

                  {/* ✅ Available Rooms */}
                  {rooms.map((room) => (
                    <option key={room.roomId} value={room.roomId}>
                      {room.roomType} — ₹{room.pricePerNight}/night
                    </option>
                  ))}
                </select>
              </div>

              {/* GUESTS */}
              <div className="input-group">
                <label>Guests</label>
                <select className="guest-select"
                  value={guests}
                  onChange={(e) => setGuests(Number(e.target.value))}
                >
                  {Array.from({
                    length:
                      selectedOption === "PROPERTY"
                        ? property.maxGuests
                        : selectedRoom?.maxGuests || 4,
                  }).map((_, i) => (
                    <option key={i + 1} value={i + 1}>
                      {i + 1} Guest{i > 0 ? "s" : ""}
                    </option>
                  ))}
                </select>
              </div>

              {/* SUMMARY */}
              {selectedOption === "PROPERTY" && (
                <div className="selected-room-summary">
                  <p><strong>Booking:</strong> Entire Property</p>
                  <p><strong>Max Guests:</strong> {property.maxGuests}</p>
                </div>
              )}

              {selectedRoom && (
                <div className="selected-room-summary">
                  <p><strong>Room:</strong> {selectedRoom.roomName}</p>
                  <p><strong>Type:</strong> {selectedRoom.roomType}</p>
                  <p><strong>Max Guests:</strong> {selectedRoom.maxGuests}</p>
                </div>
              )}

              {/* BOOK BUTTON */}
              <button
                className="book-now-btn"
                disabled={!selectedOption || bookingLoading}
                onClick={() =>
                  selectedOption === "PROPERTY"
                    ? handleBookNow()        // existing property booking
                    : bookRoom(selectedRoom.roomId) // room booking
                }
              >
                {bookingLoading ? "Booking..." : "Book Now"}
              </button>
            </div>

            <div className="booking-note">
              <p>💡 Free cancellation up to 24 hours before check-in</p>
            </div>
          </div>
       </div>
      </div>
    </div>
  );
}

export default PropertyDetails;
