import { BrowserRouter, Routes, Route } from "react-router-dom";
import Navbar from "./components/Navbar";
import HeroSlider from "./components/HeroSlider";
import Features from "./components/Features";
import Stays from "./components/Stays";
import FAQ from "./components/FAQ";
import Footer from "./components/Footer";
import InstagramExplore from "./components/InstagramExplore";
import LoginPage from "./auth/LoginPage";
import RegisterPage from "./auth/RegisterPage";
import HostDashboard from "./host/HostDashboard";
import AdminDashboard from "./components/AdminDashboard";
import UserManagement from "./components/UserManagement";
import PropertyManagement from "./components/PropertyManagement";
import HostManagement from "./components/HostManagement";
import HostProperties from "./host/HostProperties";
import HostBookings from "./host/HostBookings";
import AddRoom from "./host/AddRoom";
import AddProperty from "./components/AddProperty";
import SearchResults from "./components/SearchResults";
import SearchBar from "./components/SearchBar";
import PropertyDetails from "./components/PropertyDetails";
import Home from "./home/Home";
import "./App.css";

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/* HOME */}
        <Route path="/" element={<Home/>} />

        {/* AUTH */}
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />

        {/* HOST */}
        <Route path="/host" element={<HostDashboard />} />
        <Route path="/hostProperties" element={<HostProperties />} />
        <Route path="/hostBookings" element={<HostBookings />} />
        <Route
          path="/host/properties/:propertyId/rooms/add"
          element={<AddRoom />}
        />

        {/* ADMIN */}
        <Route path="/admin" element={<AdminDashboard />} />
        <Route path="/admin/users" element={<UserManagement />} />
        <Route path="/propertyManagement" element={<PropertyManagement />} />
        <Route path="/hostManagement" element={<HostManagement />} />

        {/* PROPERTY */}
        <Route path="/addproperty" element={<AddProperty />} />
        <Route path="/properties/:id" element={<PropertyDetails />} />

        {/* SEARCH */}
        <Route path="/search-results" element={<SearchResults />} />
      </Routes>
    </BrowserRouter>
  );
}
