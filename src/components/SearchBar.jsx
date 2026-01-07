import { useState } from "react";
import { useNavigate } from "react-router-dom";
import "../home/Home.css";


export default function SearchBar() {
  const navigate = useNavigate();

  const [filters, setFilters] = useState({
    city: "",
    state: "",
    categoryType: "",
    propertyType: "",
    minPrice: "",
    maxPrice: ""
  });

  const handleChange = (e) => {
    setFilters({ ...filters, [e.target.name]: e.target.value });
  };

  const handleSearch = () => {
    const query = new URLSearchParams(filters).toString();
    navigate(`/search-results?${query}`);
  };

  return (
    <div className="search-box">
      <select name="city" onChange={handleChange}>
        <option value="">City</option>
        <option>Goa</option>
        <option>Guwahati</option>
        <option>Bangalore</option>
      </select>

      <select name="state" onChange={handleChange}>
        <option value="">State</option>
        <option>Assam</option>
        <option>Karnataka</option>
        <option>Goa</option>
      </select>

      <select name="categoryType" onChange={handleChange}>
        <option value="">Category</option>
        <option>Premium</option>
        <option>Budget</option>
        <option>Luxury</option>
      </select>

      <select name="propertyType" onChange={handleChange}>
        <option value="">Property Type</option>
        <option>Villa</option>
        <option>Homestay</option>
        <option>Apartment</option>
      </select>

      <input name="minPrice" placeholder="Min ₹" onChange={handleChange} />
      <input name="maxPrice" placeholder="Max ₹" onChange={handleChange} />

      <button onClick={handleSearch}>Search</button>
    </div>
  );
}