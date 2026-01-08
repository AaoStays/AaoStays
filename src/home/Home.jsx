import React from "react";
import Navbar from "../components/Navbar";
import HeroSlider from "../components/HeroSlider";
import SearchBar from "../components/SearchBar";
import Features from "../components/Features";
import Stays from "../components/Stays";
import FAQ from "../components/FAQ";
import InstagramExplore from "../components/InstagramExplore";
import Footer from "../components/Footer";
import "../App.css";
import ApiService from "../service/ApiService";

export default function Home() {
  return (
    <div className="App">
      <Navbar />
      <HeroSlider />

      {/* SEARCH BAR BELOW HERO */}
      <div className="search-section">
        <SearchBar />
      </div>

<<<<<<< HEAD
      <Features />
      <Stays />
      <FAQ />
      <InstagramExplore />
      <Footer />
=======
          <Link to="/login" className="btn-login">Login</Link>
          <Link to="/register" className="btn-login">Signup</Link>

          <button onClick={()=> ApiService.logout()}>logout</button>


        </nav>
      </header>

      {/* HERO SECTION */}
      <section id="home" className="hero">
        <div className="hero-content">
          <p className="hero-subtitle">A Chain of Homestays.</p>
          <h1 className="hero-title">Welcome to AaoStays</h1>
          <p className="hero-tagline">Your Comfort, Our Priority</p>

          <div className="hero-divider">
            <span>• • • • • • •</span>
          </div>

          <p className="hero-text">
            Multiple Locations, One Promise- A Cozy Stay at a Smart Price.
            <br />
            Now in Guwahati!
          </p>

          <div className="hero-buttons">
            <a href="tel:+918638605575" className="btn btn-outline">
              Call us
            </a>
            <a
              href="https://api.whatsapp.com/send?phone=918638605575"
              className="btn btn-filled"
            >
              Write us
            </a>
          </div>

          <div className="hero-rating">
            <span>★★★★★</span>
            <p>Your Comfort, Our Priority</p>
          </div>
        </div>
      </section>

      {/* COMFORTABLE STAYS SECTION */}
      <section id="about" className="comfortable">
        <div className="comfortable-inner">
          <div className="comfortable-text-top">
            <h2>Comfortable Stays</h2>
            <p>
              Affordable homestays in Guwahati for a relaxing experience without
              unnecessary frills.
            </p>
          </div>

          <div className="comfortable-main">
            <div className="comfortable-image">
              {/* yahan ek main room photo lagao */}
              <div className="image-placeholder">
                Image of Living Room
              </div>
            </div>

            <div className="comfortable-points">
              <div className="point">
                <h3>Affordable Pricing</h3>
                <p>
                  Enjoy comfortable accommodations at budget-friendly rates
                  tailored for every traveler.
                </p>
              </div>
              <div className="point">
                <h3>Guwahati Location</h3>
                <p>
                  Experience the charm of Guwahati with our convenient homestay
                  options.
                </p>
              </div>
              <div className="point">
                <h3>Direct Inquiries</h3>
                <p>
                  Contact us directly for bookings and inquiries about our
                  homestay services.
                </p>
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* HOMESTAY ROOMS / GALLERY SECTION */}
      <section id="location" className="homestays">
        <div className="homestays-header">
          <h2>Cozy Homestay Room</h2>
          <p>
            Experience comfort and affordability in our cozy homestay room,
            perfect for relaxation after exploring Guwahati. Enjoy a peaceful
            stay with all essential amenities provided.
          </p>
        </div>

        <div className="homestay-cards">
          <div className="homestay-card">
            <div className="card-image">Room Image 1</div>
            <h3>Beltola Homestay</h3>
            <p className="card-tags">Free Wi-Fi, AC • Private Bathroom, TV</p>
            <button className="btn btn-outline">BOOK NOW</button>
          </div>

          <div className="homestay-card">
            <div className="card-image">Room Image 2</div>
            <h3>Bora Homestay</h3>
            <p className="card-tags">
              Comfortable, Affordable, Clean • Kitchenette, Free Parking
            </p>
            <button className="btn btn-outline">BOOK NOW</button>
          </div>

          <div className="homestay-card">
            <div className="card-image">Room Image 3</div>
            <h3>Barshapara Homestay</h3>
            <p className="card-tags">Air Conditioning, Wi-Fi • Balcony</p>
            <button className="btn btn-outline">BOOK NOW</button>
          </div>
        </div>
      </section>

      {/* FOOTER */}
      <footer id="contact" className="footer">
        <div className="footer-inner">
          <div className="footer-col">
            <h4>Contact</h4>
            <p>Get in touch for inquiries and bookings.</p>
            <div className="social-row">
              <span className="social-circle">f</span>
              <span className="social-circle">in</span>
              <span className="social-circle">wa</span>
            </div>
          </div>

          <div className="footer-col about-col">
            <h4>ABOUT US</h4>
            <p>
              AT AAOSTAYS, WE BELIEVE THAT TRAVEL IS BEST EXPERIENCED WHEN IT
              FEELS LIKE HOME. WE ARE A FAST-GROWING HOMESTAY CHAIN IN
              GUWAHATI, OFFERING CLEAN, COMFORTABLE, AND BUDGET-FRIENDLY STAYS
              ACROSS THE CITY. WHETHER YOU&apos;RE A SOLO TRAVELER, A FAMILY ON
              VACATION, A STUDENT, OR A PROFESSIONAL VISITING ASSAM, WE PROVIDE
              THE PERFECT BALANCE OF WARMTH, SAFETY, AND LOCAL HOSPITALITY.
            </p>
          </div>

          <div className="footer-col location-col">
            <h4>LOCATION</h4>
            <p>+91-8638605575</p>
            <p>info@aaostays.com</p>

            <form className="footer-form">
              <label>Your Email Address</label>
              <input type="email" placeholder="Enter your email here" />
              <button type="submit" className="btn btn-filled small">
                Submit Your Inquiry
              </button>
            </form>
          </div>
        </div>

        <div className="footer-bottom">
          <p>© 2025. All rights reserved.</p>
        </div>
      </footer>
>>>>>>> a75579e (updated frontend)
    </div>
  );
}
