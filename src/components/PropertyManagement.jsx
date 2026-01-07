import React, { useEffect, useState } from "react";
import ApiService from "../service/ApiService";

const PropertyManagement = () => {
  const [properties, setProperties] = useState([]);
  const [loading, setLoading] = useState(false);

  const [selected, setSelected] = useState(null);
  const [editOpen, setEditOpen] = useState(false);

  const [imagesOpen, setImagesOpen] = useState(false);
  const [images, setImages] = useState([]);

  const [propertyId, setPropertyId] = useState("");

  const userRole = localStorage.getItem("role");
  const isHostOrAdmin = userRole === "HOST" || userRole === "ADMIN";

  // ---------------------------------------------------
  // Fetch All Properties
  // ---------------------------------------------------
  const fetchAll = async () => {
    setLoading(true);
    try {
      const res = await ApiService.getAllProperties();
      setProperties(res.data || []);
    } catch (err) {
      console.error("Error loading properties:", err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchAll();
  }, []);

  // ---------------------------------------------------
  // Get Property By ID
  // ---------------------------------------------------
  const handleGetById = async () => {
    if (!propertyId) return alert("Enter a Property ID!");
    try {
      const res = await ApiService.getPropertyById(propertyId);
      setProperties([res.data]);
    } catch (err) {
      console.error("Error fetching property:", err);
      alert("Property not found");
    }
  };

  // ---------------------------------------------------
  // View Property
  // ---------------------------------------------------
  const handleView = async (id) => {
    try {
      const res = await ApiService.getPropertyById(id);
      setSelected(res.data);
      setEditOpen(true);
    } catch (err) {
      console.error(err);
    }
  };

  // ---------------------------------------------------
  // Edit Property
  // ---------------------------------------------------
  const handleEdit = async (id) => {
    if (!isHostOrAdmin) return alert("Not authorized");
    handleView(id);
  };

  // ---------------------------------------------------
  // Update Property
  // ---------------------------------------------------
  const handleSave = async (updated) => {
    try {
      await ApiService.updateProperty(updated.propertyId, updated);
      alert("Updated successfully");
      setEditOpen(false);
      fetchAll();
    } catch (err) {
      console.error(err);
    }
  };

  // ---------------------------------------------------
  // Delete Property
  // ---------------------------------------------------
  const handleDelete = async (id) => {
    if (!isHostOrAdmin) return alert("Not authorized");
    if (!window.confirm("Delete this property?")) return;

    try {
      await ApiService.deleteProperty(id);
      alert("Deleted successfully");
      fetchAll();
    } catch (err) {
      console.error(err);
    }
  };

  // ---------------------------------------------------
  // Get Images
  // ---------------------------------------------------
  const handleImages = async (id) => {
    try {
      const res = await ApiService.getPropertyImages(id);
      setImages(res || []);
      setImagesOpen(true);
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <div className="pm-container">
      <h2>Property Management</h2>

      {/* Search by ID */}
      <div className="pm-search">
        <input
          type="number"
          placeholder="Enter Property ID"
          value={propertyId}
          onChange={(e) => setPropertyId(e.target.value)}
        />
        <button onClick={handleGetById}>Get Property</button>
        <button onClick={fetchAll}>Get All</button>
      </div>

      {/* LIST */}
      {loading ? (
        <p>Loading...</p>
      ) : (
        <>
          {properties.map((p) => (
            <div key={p.propertyId} className="property-card-wide">
              <img
                src={
                  p.images?.[0]?.imageUrl ||
                  "https://via.placeholder.com/400?text=No+Image"
                }
                alt="cover"
                className="pm-img"
              />

              <div className="pm-details">
                <h3>{p.propertyName}</h3>
                <p>{p.city}, {p.state}</p>
                <p>₹{p.pricePerNight}</p>
                <p>{p.description}</p>

                <div className="pm-actions">
                  <button onClick={() => handleView(p.propertyId)}>View</button>

                  {isHostOrAdmin && (
                    <>
                      <button onClick={() => handleEdit(p.propertyId)}>Edit</button>
                      <button onClick={() => handleDelete(p.propertyId)}>Delete</button>
                    </>
                  )}

                  <button onClick={() => handleImages(p.propertyId)}>Images</button>
                </div>
              </div>
            </div>
          ))}
        </>
      )}

      {/* EDIT MODAL */}
      {editOpen && selected && (
        <div className="modal-overlay">
          <div className="modal-box">
            <h3>Edit Property</h3>

            <input
              value={selected.propertyName || ""}
              onChange={(e) =>
                setSelected({ ...selected, propertyName: e.target.value })
              }
              placeholder="Title"
            />

            <input
              value={selected.city || ""}
              onChange={(e) =>
                setSelected({ ...selected, city: e.target.value })
              }
              placeholder="City"
            />

            <input
              value={selected.state || ""}
              onChange={(e) =>
                setSelected({ ...selected, state: e.target.value })
              }
              placeholder="State"
            />

            <input
              value={selected.pricePerNight || ""}
              onChange={(e) =>
                setSelected({ ...selected, pricePerNight: e.target.value })
              }
              placeholder="Price"
            />

            <textarea
              value={selected.description || ""}
              onChange={(e) =>
                setSelected({ ...selected, description: e.target.value })
              }
            />

            <button onClick={() => handleSave(selected)}>Save</button>
            <button onClick={() => setEditOpen(false)}>Close</button>
          </div>
        </div>
      )}

      {/* IMAGES MODAL */}
      {imagesOpen && (
        <div className="modal-overlay">
          <div className="modal-box">
            <h3>Property Images</h3>

            {images.length === 0 ? (
              <p>No Images</p>
            ) : (
              <div className="pm-image-grid">
                {images.map((img, i) => (
                  <img key={i} src={img.imageUrl} alt="property" />
                ))}
              </div>
            )}

            <button onClick={() => setImagesOpen(false)}>Close</button>
          </div>
        </div>
      )}
    </div>
  );
};

export default PropertyManagement;
