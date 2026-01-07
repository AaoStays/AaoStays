import React, { useState } from "react";
import ApiService from "../service/ApiService";
import axios from "axios";

const AddProperty = () => {
  const [formData, setFormData] = useState({
    hostId: "",
    propertyName: "",
    propertyType: "",
    categoryType: "",
    description: "",
    addressLine1: "",
    city: "",
    pricePerNight: "",
  });

  const [imageFiles, setImageFiles] = useState([]);

  // Handle input changes
  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  // Handle image selection
  const handleImageInput = (e) => {
    setImageFiles([...e.target.files]);
  };

  // STEP 1: Create property (SEND TOKEN via ApiService)
  const createProperty = async () => {
    const response = await ApiService.addProperty(formData);  // <-- TOKEN INCLUDED
    return response.data.propertyId; // backend returns propertyDto inside data
  };

  // STEP 2: Upload images AFTER propertyId exists
  const uploadPropertyImages = async (propertyId) => {
    const uploaded = [];

    for (let img of imageFiles) {
      const form = new FormData();
      form.append("images", img); // backend expects "images"

      const res = await axios.post(
        `http://localhost:8080/api/properties/${propertyId}/images/upload`,
        form,
        {
          headers: {
            "Content-Type": "multipart/form-data",
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
        }
      );

      uploaded.push(...res.data);
    }

    return uploaded;
  };

  // MAIN SUBMIT HANDLER
  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      // 1️⃣ Create property and get ID
      const propertyId = await createProperty();

      // 2️⃣ Upload images based on propertyId
      await uploadPropertyImages(propertyId);

      alert("Property and images added successfully!");

      // Optional: Reset form
      setFormData({
        hostId: "",
        propertyName: "",
        propertyType: "",
        categoryType: "",
        description: "",
        addressLine1: "",
        city: "",
        pricePerNight: "",
      });
      setImageFiles([]);

    } catch (err) {
      console.error("Error adding property:", err);
      alert("Error adding property");
    }
  };

  return (
    <div className="container">
      <h1>Add New Property</h1>

      <form onSubmit={handleSubmit}>
        
        <input
          type="number"
          name="hostId"
          placeholder="Host ID"
          onChange={handleChange}
          required
        />

        <input
          type="text"
          name="propertyName"
          placeholder="Property Name"
          onChange={handleChange}
          required
        />

        <input
          type="text"
          name="propertyType"
          placeholder="Property Type"
          onChange={handleChange}
        />

        <input
          type="text"
          name="categoryType"
          placeholder="Category Type"
          onChange={handleChange}
        />

        <textarea
          name="description"
          placeholder="Description"
          onChange={handleChange}
        />

        <input
          type="text"
          name="addressLine1"
          placeholder="Address Line 1"
          onChange={handleChange}
        />

        <input
          type="text"
          name="city"
          placeholder="City"
          onChange={handleChange}
        />

        <input
          type="number"
          name="pricePerNight"
          placeholder="Price Per Night"
          onChange={handleChange}
        />

        {/* IMAGE UPLOAD */}
        <label>Upload Images</label>
        <input
          type="file"
          multiple
          accept="image/*"
          onChange={handleImageInput}
        />

        <button type="submit">Add Property</button>
      </form>
    </div>
  );
};

export default AddProperty;
