import { BrowserRouter, Routes, Route } from "react-router-dom";
import Navbar from "./components/Navbar";
import HeroSlider from "./components/HeroSlider";
import Features from "./components/Features";
import Stays from "./components/Stays";
import FAQ from "./components/FAQ";
import Footer from "./components/Footer";
import InstagramExplore from "./components/InstagramExplore";
import SearchResults from "./components/SearchResults";
import SearchBar from "./components/SearchBar";
import "./App.css";

export default function App() {
  return (
    <BrowserRouter>
      <Routes>

        {/* HOME PAGE */}
        <Route path="/" element={
          <>
            <Navbar />
            <HeroSlider />
                {/* SEARCH BAR BELOW SLIDER */}
    <div className="search-section">
      <SearchBar />
    </div>
            <Features />
            <Stays />
            <FAQ />
            <InstagramExplore />
            <Footer />
          </>
        } />

        {/* SEARCH RESULTS PAGE */}
        <Route path="/search-results" element={<SearchResults />} />

      </Routes>
    </BrowserRouter>
  );
}
