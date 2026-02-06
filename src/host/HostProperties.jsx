import React, { useState, useEffect } from "react";
import ApiService from "../service/ApiService";
import { useNavigate } from "react-router-dom";
import "../css/HostProperties.css";
const HostProperties = () => {
  const [properties, setProperties] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [isHost, setIsHost] = useState(true);
  
  const navigate = useNavigate();

  useEffect(() => {
    fetchMyProperties();
  }, []);
   
 const fetchMyProperties = async () => {
  setLoading(true);
  setError(null);

  try {
    const response = await ApiService.getMyProperties();


   
    setProperties(response.data || []);

  } catch (err) {
    console.error("Error fetching properties:", err);

    if (err.response?.status === 403) {
      setIsHost(false);
    } else {
      setError(err.response?.data?.message || "Failed to fetch properties");
    }
  } finally {
    setLoading(false);
  }
};



  // If user is not a host
  if (!loading && !isHost) {
    return (
      <div className="hostProperties-Container">
        <h2>Host Properties</h2>
        <div className="not-host-message">
          <p>You are not registered as a host yet.</p>
          <button className="become-host-btn">Become a Host</button>
        </div>
      </div>
    );
  }

  return (
    <div className="hostProperties-Container">
      <h2>My Properties {properties.length > 0 && `(${properties.length})`}</h2>

      {loading && <p>Loading properties...</p>}
      
      {error && <p style={{ color: 'red' }}>Error: {error}</p>}

      <div className="properties-grid">
        {properties.map((property) => (
          <div key={property.propertyId} className="propertyCard">
            {property.images && property.images.length > 0 && (
              <img 
                src={property.images[0].imageUrl} 
                alt={property.propertyName}
                className="property-image"
                onError={(e) => {
                  e.target.src = '/placeholder-property.jpg';
                }}
              />
            )}

            <div className="property-details">
              <h3>{property.propertyName}</h3>
              
              <p><strong>Type:</strong> {property.propertyType}</p>
              <p><strong>Category:</strong> {property.categoryType}</p>
              <p><strong>Location:</strong> {property.city}, {property.state}</p>
              <p><strong>Price:</strong> ₹{property.pricePerNight}/night</p>
              
              <div className="property-features">
                <span>🛏️ {property.bedrooms} Bedrooms</span>
                <span>🛁 {property.bathrooms} Bathrooms</span>
                <span>🛏 {property.beds} Beds</span>
              </div>

              <div className="property-status">
                <span className={`status-badge ${property.propertyStatus.toLowerCase()}`}>
                  {property.propertyStatus}
                </span>
                <span className={`approval-badge ${property.approvalStatus.toLowerCase()}`}>
                  {property.approvalStatus}
                </span>
              </div>

              {property.images && property.images.length > 1 && (
                <p className="image-count">📷 {property.images.length} photos</p>
              )}
            </div>
            <div className="view-button">
              <button>View Rooms</button>
             <button
  onClick={() =>
    navigate(`/host/properties/${property.propertyId}/rooms/add`)
  }
>
  Add Rooms
</button>


            </div>
          </div>
        ))}
      </div>

      {!loading && !error && properties.length === 0 && (
        <div className="no-properties">
          <p>You haven't added any properties yet.</p>
          <button className="add-property-btn">Add Your First Property</button>
        </div>
      )}
    </div>
  );
};

export default HostProperties;