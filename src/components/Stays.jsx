import { useEffect, useState } from "react";
import ApiService from "../service/ApiService";
import { useNavigate } from "react-router-dom";

export default function Stays() {
  const [stays, setStays] = useState([]);
  const [loading, setLoading] = useState(true);

  const navigate=useNavigate();

  useEffect(() => {
    const fetchStays = async () => {
      try {
        const response = await ApiService.getAllProperties();
        setStays(response.data || []);
      } catch (err) {
        console.error("Error fetching stays", err);
      } finally {
        setLoading(false);
      }
    };

    fetchStays();
  }, []);

  if (loading) {
    return <section className="stays"><h3>Loading stays...</h3></section>;
  }

  return (
    <section className="stays">
      <h2>Family-Friendly Stays</h2>
      <h3>Comfortable homes for the whole family</h3>
<div className="stay-grid">
  {stays.map((property) => (
    <div
      className="stay-card"
      key={property.propertyId}
      onClick={() => navigate(`/properties/${property.propertyId}`)}
      style={{ cursor: "pointer" }}
    >
      {/* IMAGE */}
      {property.images && property.images.length > 0 ? (
        <img
          src={property.images[0].imageUrl}
          alt={property.propertyName}
          onError={(e) => {
            e.target.src = "/placeholder-property.jpg";
          }}
        />
      ) : (
        <img src="/placeholder-property.jpg" alt="No image" />
      )}

      <h4>{property.propertyName}</h4>
      <p className="price">₹{property.pricePerNight}/night</p>
      <h5>{property.description}</h5>

      {/* Book button */}
      <button
        className="book-btn"
        onClick={(e) => {
          e.stopPropagation(); // ⛔ prevent double navigation
          navigate(`/properties/${property.propertyId}`);
        }}
      >
        Book Now
      </button>
    </div>
  ))}
</div>

    </section>
  );
}
