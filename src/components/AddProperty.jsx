import React, { useState } from "react";
import ApiService from "../service/ApiService";
import "../css/AddProperty.css"

const AddProperty = () => {
  const [images, setImages] = useState([]);
  const [imagePreviews, setImagePreviews] = useState([]);
  const [isSubmitting, setIsSubmitting] = useState(false);

  const [formData, setFormData] = useState({
    propertyName: "",
    propertyType: "",
    categoryType: "",
    description: "",
    addressLine1: "",
    addressLine2: "",
    city: "",
    state: "",
    country: "",
    postalCode: "",
    bedrooms: "",
    beds: "",
    bathrooms: "",
    pricePerNight: "",
    baseGuest: "",
    maxGuest: "",
    cleaningFee: "",
    extraGuestAllowed: false,
    extraGuestFee: "",
    kitchenType: "",
    currency: "INR",
    instantBooking: false,
    eventsAllowed: false,
    petsAllowed: false,
  });

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData({
      ...formData,
      [name]: type === "checkbox" ? checked : value,
    });
  };

  const handleImageChange = (e) => {
    const files = Array.from(e.target.files);
    setImages(files);

    const previews = files.map((file) => URL.createObjectURL(file));
    imagePreviews.forEach((url) => URL.revokeObjectURL(url));
    setImagePreviews(previews);
  };

  const removeImage = (index) => {
    const newImages = images.filter((_, i) => i !== index);
    const newPreviews = imagePreviews.filter((_, i) => i !== index);
    URL.revokeObjectURL(imagePreviews[index]);
    setImages(newImages);
    setImagePreviews(newPreviews);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsSubmitting(true);

    try {
      const payload = {
        ...formData,
        bedrooms: Number(formData.bedrooms),
        beds: Number(formData.beds),
        bathrooms: Number(formData.bathrooms),
        pricePerNight: Number(formData.pricePerNight),
        baseGuest: Number(formData.baseGuest),
        maxGuest: Number(formData.maxGuest),
        cleaningFee: Number(formData.cleaningFee),
        extraGuestFee: Number(formData.extraGuestFee),
      };

      const res = await ApiService.addProperty(payload);
      const propertyId = res.data.propertyId;

      if (images.length > 0) {
        await ApiService.uploadImages(propertyId, images);
      }

      alert("Property and images added successfully!");

      imagePreviews.forEach((url) => URL.revokeObjectURL(url));

      setFormData({
        propertyName: "",
        propertyType: "",
        categoryType: "",
        description: "",
        addressLine1: "",
        addressLine2: "",
        city: "",
        state: "",
        country: "",
        postalCode: "",
        bedrooms: "",
        beds: "",
        bathrooms: "",
        pricePerNight: "",
        baseGuest: "",
        maxGuest: "",
        cleaningFee: "",
        extraGuestAllowed: false,
        extraGuestFee: "",
        kitchenType: "",
        currency: "INR",
        instantBooking: false,
        eventsAllowed: false,
        petsAllowed: false,
      });

      setImages([]);
      setImagePreviews([]);
    } catch (err) {
      console.error("Error:", err);
      alert(
        "Failed to add property: " +
          (err.response?.data?.message || err.message)
      );
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div className="add-property-container">
      <div className="form-card">
        <div className="form-header">
          <h2>🏡 Add New Property</h2>
          <p>Fill in the details to list your property</p>
        </div>

        <form onSubmit={handleSubmit} className="form-body">
          {/* Basic Information */}
          <div className="form-section">
            <h3 className="section-title">
              <span className="section-icon">📋</span>
              Basic Information
            </h3>
            <div className="form-grid">
              <div className="form-group">
                <label>
                  Property Name <span className="required">*</span>
                </label>
                <input
                  name="propertyName"
                  placeholder="Enter property name"
                  value={formData.propertyName}
                  required
                  onChange={handleChange}
                />
              </div>

              <div className="form-group">
                <label>Property Type</label>
                <input
                  name="propertyType"
                  placeholder="e.g., Apartment, Villa"
                  value={formData.propertyType}
                  onChange={handleChange}
                />
              </div>

              <div className="form-group">
                <label>Category Type</label>
                <input
                  name="categoryType"
                  placeholder="e.g., Beachfront, Urban"
                  value={formData.categoryType}
                  onChange={handleChange}
                />
              </div>

              <div className="form-group">
                <label>Kitchen Type</label>
                <input
                  name="kitchenType"
                  placeholder="e.g., Full, Kitchenette"
                  value={formData.kitchenType}
                  onChange={handleChange}
                />
              </div>
            </div>

            <div className="form-group full-width">
              <label>Description</label>
              <textarea
                name="description"
                placeholder="Describe your property..."
                value={formData.description}
                onChange={handleChange}
              />
            </div>
          </div>

          {/* Location Details */}
          <div className="form-section">
            <h3 className="section-title">
              <span className="section-icon">📍</span>
              Location Details
            </h3>
            <div className="form-grid">
              <div className="form-group full-width">
                <label>
                  Address Line 1 <span className="required">*</span>
                </label>
                <input
                  name="addressLine1"
                  placeholder="Street address"
                  value={formData.addressLine1}
                  required
                  onChange={handleChange}
                />
              </div>

              <div className="form-group full-width">
                <label>Address Line 2</label>
                <input
                  name="addressLine2"
                  placeholder="Apartment, suite, unit, etc."
                  value={formData.addressLine2}
                  onChange={handleChange}
                />
              </div>

              <div className="form-group">
                <label>
                  City <span className="required">*</span>
                </label>
                <input
                  name="city"
                  placeholder="City"
                  value={formData.city}
                  required
                  onChange={handleChange}
                />
              </div>

              <div className="form-group">
                <label>State</label>
                <input
                  name="state"
                  placeholder="State/Province"
                  value={formData.state}
                  onChange={handleChange}
                />
              </div>

              <div className="form-group">
                <label>Country</label>
                <input
                  name="country"
                  placeholder="Country"
                  value={formData.country}
                  onChange={handleChange}
                />
              </div>

              <div className="form-group">
                <label>Postal Code</label>
                <input
                  name="postalCode"
                  placeholder="ZIP/Postal code"
                  value={formData.postalCode}
                  onChange={handleChange}
                />
              </div>
            </div>
          </div>

          {/* Property Details */}
          <div className="form-section">
            <h3 className="section-title">
              <span className="section-icon">🛏️</span>
              Property Details
            </h3>
            <div className="form-grid">
              <div className="form-group">
                <label>
                  Bedrooms <span className="required">*</span>
                </label>
                <input
                  type="number"
                  name="bedrooms"
                  placeholder="Number of bedrooms"
                  value={formData.bedrooms}
                  required
                  min="0"
                  onChange={handleChange}
                />
              </div>

              <div className="form-group">
                <label>
                  Beds <span className="required">*</span>
                </label>
                <input
                  type="number"
                  name="beds"
                  placeholder="Number of beds"
                  value={formData.beds}
                  required
                  min="0"
                  onChange={handleChange}
                />
              </div>

              <div className="form-group">
                <label>
                  Bathrooms <span className="required">*</span>
                </label>
                <input
                  type="number"
                  name="bathrooms"
                  placeholder="Number of bathrooms"
                  value={formData.bathrooms}
                  required
                  min="0"
                  step="0.5"
                  onChange={handleChange}
                />
              </div>
            </div>
          </div>

          {/* Pricing & Guests */}
          <div className="form-section">
            <h3 className="section-title">
              <span className="section-icon">💰</span>
              Pricing & Guest Information
            </h3>
            <div className="form-grid">
              <div className="form-group">
                <label>
                  Price Per Night (INR) <span className="required">*</span>
                </label>
                <input
                  type="number"
                  name="pricePerNight"
                  placeholder="₹0"
                  value={formData.pricePerNight}
                  required
                  min="0"
                  onChange={handleChange}
                />
              </div>

              <div className="form-group">
                <label>
                  Cleaning Fee (INR) <span className="required">*</span>
                </label>
                <input
                  type="number"
                  name="cleaningFee"
                  placeholder="₹0"
                  value={formData.cleaningFee}
                  required
                  min="0"
                  onChange={handleChange}
                />
              </div>

              <div className="form-group">
                <label>
                  Base Guests <span className="required">*</span>
                </label>
                <input
                  type="number"
                  name="baseGuest"
                  placeholder="Number of base guests"
                  value={formData.baseGuest}
                  required
                  min="1"
                  onChange={handleChange}
                />
              </div>

              <div className="form-group">
                <label>
                  Max Guests <span className="required">*</span>
                </label>
                <input
                  type="number"
                  name="maxGuest"
                  placeholder="Maximum guests allowed"
                  value={formData.maxGuest}
                  required
                  min="1"
                  onChange={handleChange}
                />
              </div>

              <div className="form-group">
                <label>
                  Extra Guest Fee (INR) <span className="required">*</span>
                </label>
                <input
                  type="number"
                  name="extraGuestFee"
                  placeholder="₹0"
                  value={formData.extraGuestFee}
                  required
                  min="0"
                  onChange={handleChange}
                />
              </div>
            </div>
          </div>

          {/* Property Images */}
          <div className="form-section">
            <h3 className="section-title">
              <span className="section-icon">📸</span>
              Property Images
            </h3>
            <div className="image-upload-section">
              <label className="upload-label">Upload Property Photos</label>
              <div className="file-input-wrapper">
                <input
                  type="file"
                  id="file-upload"
                  accept="image/*"
                  multiple
                  onChange={handleImageChange}
                />
                <label htmlFor="file-upload" className="file-input-label">
                  <span className="upload-icon">📤</span>
                  Choose Images (Multiple)
                </label>
              </div>

              {imagePreviews.length > 0 && (
                <div className="preview-container">
                  <p className="preview-count">
                    Selected: {imagePreviews.length} image(s)
                  </p>
                  <div className="preview-grid">
                    {imagePreviews.map((preview, index) => (
                      <div key={index} className="preview-item">
                        <img
                          src={preview}
                          alt={`Preview ${index + 1}`}
                        />
                        <button
                          type="button"
                          onClick={() => removeImage(index)}
                          className="remove-btn"
                        >
                          ×
                        </button>
                        <div className="preview-label">Image {index + 1}</div>
                      </div>
                    ))}
                  </div>
                </div>
              )}
            </div>
          </div>

          {/* Amenities & Policies */}
          <div className="form-section">
            <h3 className="section-title">
              <span className="section-icon">✨</span>
              Amenities & Policies
            </h3>
            <div className="checkbox-group">
              <label className="checkbox-item">
                <input
                  type="checkbox"
                  name="instantBooking"
                  checked={formData.instantBooking}
                  onChange={handleChange}
                />
                <span>⚡ Instant Booking</span>
              </label>

              <label className="checkbox-item">
                <input
                  type="checkbox"
                  name="extraGuestAllowed"
                  checked={formData.extraGuestAllowed}
                  onChange={handleChange}
                />
                <span>👥 Extra Guests Allowed</span>
              </label>

              <label className="checkbox-item">
                <input
                  type="checkbox"
                  name="eventsAllowed"
                  checked={formData.eventsAllowed}
                  onChange={handleChange}
                />
                <span>🎉 Events Allowed</span>
              </label>

              <label className="checkbox-item">
                <input
                  type="checkbox"
                  name="petsAllowed"
                  checked={formData.petsAllowed}
                  onChange={handleChange}
                />
                <span>🐾 Pets Allowed</span>
              </label>
            </div>
          </div>

          <button 
            type="submit" 
            className="submit-btn" 
            disabled={isSubmitting}
          >
            {isSubmitting ? "Adding Property..." : "Add Property"}
          </button>
        </form>
      </div>
    </div>
  );
};

export default AddProperty;