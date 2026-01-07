import { useLocation } from "react-router-dom";
import { useEffect, useState } from "react";
import axios from "axios";
import "../home/Home.css";

export default function SearchResults() {

  const { search } = useLocation();
  const [properties, setProperties] = useState([]);

  useEffect(() => {
    axios.get(`http://localhost:8080/api/properties/search${search}`)
      .then(res => setProperties(res.data.data));
  }, [search]);

  return (
    <div className="stays">
      <h2>Search Results</h2>
      <h3>Available stays matching your filters</h3>

      <div className="stay-grid">
        {properties.map(p => (
          <div className="stay-card" key={p.id}>
            <img src={p.imageUrl} alt="" />
            <h4>{p.name}</h4>
            <p className="price">₹ {p.price} / night</p>
            <h5>{p.city}, {p.state}</h5>
          </div>
        ))}
      </div>
    </div>
  );
}
