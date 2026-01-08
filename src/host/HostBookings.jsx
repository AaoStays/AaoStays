import { useEffect, useState } from "react";
import ApiService from "../service/ApiService";
import "../css/HostBookingsCss.css";
const HostBookings = () => {


  const [bookings, setBookings] = useState([]);
  const [loading, setLoading] = useState(true);
  const [errorStatus, setErrorStatus] = useState(null);
  const [message, setMessage] = useState("");


  useEffect(() => {
    const fetchHostBookings = async () => {
      try {
        setLoading(true);

        const res = await ApiService.getHostBookings();
      
        const data = res.data;

 
        const normalizedBookings = Array.isArray(data) ? data : [data];

        setBookings(normalizedBookings);
        setMessage(res.message || "");
        setErrorStatus(null);

      } catch (err) {
        const status = err.response?.status;
        const msg =
          err.response?.data?.message || "Unable to fetch bookings";

        setErrorStatus(status);
        setMessage(msg);
        setBookings([]);
      } finally {
        setLoading(false);
      }
    };

    fetchHostBookings();
  }, []);

  // ===== UI =====
  return (
    <div className="hostBooking-container">
      <h2>My Bookings</h2>

      {/* LOADING */}
      {loading && <p>Loading bookings...</p>}

      {/* ERROR */}
      {!loading && errorStatus && (
        <p style={{ color: "red" }}>{message}</p>
      )}

      {/* EMPTY */}
      {!loading && !errorStatus && bookings.length === 0 && (
        <p>No bookings found for your properties.</p>
      )}

      {/* BOOKINGS GRID */}
      {!loading && bookings.length > 0 && (
        <div className="bookings-grid">
          {bookings.map((booking) => (
            <div key={booking.bookingId} className="bookingcard">

              <h3>{booking.propertyName}</h3>

              <p>
                <strong>Confirmation Code:</strong>{" "}
                {booking.bookingConfirmationCode}
              </p>

              <p>
                <strong>Stay:</strong>{" "}
                {booking.checkInDate} → {booking.checkOutDate}
              </p>

              <p>
                <strong>Status:</strong>{" "}
                <span
                  className={`status ${booking.bookingStatus?.toLowerCase()}`}
                >
                  {booking.bookingStatus}
                </span>
              </p>

              <p>
                <strong>Total Amount:</strong> ₹{booking.totalAmount}
              </p>

            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default HostBookings;
