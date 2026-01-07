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

export default function Home() {
  return (
    <div className="App">
      <Navbar />
      <HeroSlider />

      {/* SEARCH BAR BELOW HERO */}
      <div className="search-section">
        <SearchBar />
      </div>

      <Features />
      <Stays />
      <FAQ />
      <InstagramExplore />
      <Footer />
    </div>
  );
}
