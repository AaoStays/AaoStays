import { FaHome, FaShieldAlt, FaStar, FaMapMarkerAlt } from "react-icons/fa";

export default function Features() {
  return (
    <section className="features">
      <div className="feature-grid">

        <div className="feature-card">
          <div className="icon-box blue">
            <FaHome />
          </div>
          <h3>Comfortable Stays</h3>
          <p>Relaxed rooms & homely feel</p>
        </div>

        <div className="feature-card">
          <div className="icon-box purple">
            <FaShieldAlt />
          </div>
          <h3>Secure Booking</h3>
          <p>Safe & verified stays</p>
        </div>

        <div className="feature-card">
          <div className="icon-box gold stars">
            <FaStar /><FaStar /><FaStar /><FaStar /><FaStar />
          </div>
          <h3>5 Star Ratings</h3>
          <p>Trusted by guests</p>
        </div>

        <div className="feature-card">
          <div className="icon-box green">
            <FaMapMarkerAlt />
          </div>
          <h3>Prime Locations</h3>
          <p>Near city centers</p>
        </div>

      </div>
    </section>
  );
}