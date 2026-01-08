import { BrowserRouter, Routes, Route } from "react-router-dom";
<<<<<<< HEAD
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
=======
import Home from "./home/Home";
import LoginPage from "./auth/LoginPage";
import RegisterPage from "./auth/RegisterPage";
import HostDashboard from "./host/HostDashboard";
import AdminDashboard from "./components/AdminDashboard";
import UserManagement from "./components/UserManagement";
import AddProperty from "./components/addProperty";
import PropertyManagement from "./components/PropertyManagement";
import HostManagement from "./components/HostManagement";
import HostProperties from "./host/HostProperties";
import HostBookings from "./host/HostBookings";
import AddRoom from "./host/AddRoom";
>>>>>>> a75579e (updated frontend)

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
<<<<<<< HEAD
=======
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />
        <Route path="/host" element={<HostDashboard />} />
         <Route path="/admin" element={<AdminDashboard/>}/>
         <Route path="/admin/users" element={<UserManagement />} />
 <Route path="/addproperty" element={<AddProperty />} />     
  <Route path="/propertyManagement" element={<PropertyManagement/>}/>
 <Route path="/hostManagement" element={<HostManagement/>}/>
 <Route path="/hostProperties" element={<HostProperties/>}/>
 <Route path="/hostBookings" element={<HostBookings/>}/>
<Route
  path="/host/properties/:propertyId/rooms/add"
  element={<AddRoom />}
/>
  </Routes>
>>>>>>> a75579e (updated frontend)

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
