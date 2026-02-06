import React from "react";
import { Link } from "react-router-dom";

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
      {/* NAVBAR */}
      <Navbar />

      {/* HERO SLIDER */}
      <HeroSlider />

      {/* SEARCH BAR */}
      <div className="search-section">
        <SearchBar />
      </div>

     

      {/* SECTIONS */}
      <Features />
      <Stays />
      <FAQ />
      <InstagramExplore />

      {/* FOOTER */}
      <Footer />
    </div>
  );
}
