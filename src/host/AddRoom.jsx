import React, { useState } from "react";
import { useParams } from "react-router-dom";
import ApiService from "../service/ApiService";

const initialFormData = {
  roomNumber: "",
  roomName: "",
  roomType: "",
  roomDescription: "",
  pricePerNight: "",
  baseGuests: "",
  maxGuests: "",
  extraGuestAllowed: false,
  extraGuestFee: "",
  bedType: "",
  bedCount: "",
  roomSizeSqft: "",
  hasBalcony: false,
  hasWindow: false,
  floorNumber: "",
};

const AddRoom = () => {
  const { propertyId } = useParams();
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [formData, setFormData] = useState(initialFormData);

  // Handle input change
  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;

    setFormData((prev) => ({
      ...prev,
      [name]: type === "checkbox" ? checked : value,
    }));
  };

  // Handle submit
  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsSubmitting(true);

    try {
      const payload = {
        ...formData,
        pricePerNight: Number(formData.pricePerNight),
        baseGuests: Number(formData.baseGuests),
        maxGuests: Number(formData.maxGuests),
        extraGuestFee: formData.extraGuestAllowed
          ? Number(formData.extraGuestFee)
          : 0,
        bedCount: Number(formData.bedCount),
        roomSizeSqft: Number(formData.roomSizeSqft),
        floorNumber: Number(formData.floorNumber),
      };

      await ApiService.addRoom(propertyId, payload);

      alert("Room added successfully!");


      setFormData(initialFormData);
    } catch (err) {
      console.error("Error adding room:", err);
      alert(
        "Failed to add room: " +
          (err.response?.data?.message || err.message)
      );
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div className="add-room-container">
      <div className="form-card">
        <div className="form-header">
          <h2>🛏️ Add New Room</h2>
          <p>Property ID: {propertyId}</p>
        </div>

        <form onSubmit={handleSubmit} className="form-body">

          {/* Room Information */}
          <div className="form-section">
            <h3>📋 Room Information</h3>

            <input
              name="roomNumber"
              placeholder="Room Number"
              value={formData.roomNumber}
              onChange={handleChange}
              required
            />

            <input
              name="roomName"
              placeholder="Room Name"
              value={formData.roomName}
              onChange={handleChange}
              required
            />

            <select
              name="roomType"
              value={formData.roomType}
              onChange={handleChange}
              required
            >
              <option value="">Select Room Type</option>
              <option value="SINGLE">Single</option>
              <option value="DOUBLE">Double</option>
              <option value="DELUXE">Deluxe</option>
              <option value="SUITE">Suite</option>
            </select>

            <textarea
              name="roomDescription"
              placeholder="Room Description"
              value={formData.roomDescription}
              onChange={handleChange}
            />
          </div>

          {/* Pricing & Guests */}
          <div className="form-section">
            <h3>💰 Pricing & Guests</h3>

            <input
              type="number"
              name="pricePerNight"
              placeholder="Price Per Night"
              value={formData.pricePerNight}
              onChange={handleChange}
              required
            />

            <input
              type="number"
              name="baseGuests"
              placeholder="Base Guests"
              value={formData.baseGuests}
              onChange={handleChange}
              required
            />

            <input
              type="number"
              name="maxGuests"
              placeholder="Max Guests"
              value={formData.maxGuests}
              onChange={handleChange}
              required
            />

            <label>
              <input
                type="checkbox"
                name="extraGuestAllowed"
                checked={formData.extraGuestAllowed}
                onChange={handleChange}
              />
              Extra Guest Allowed
            </label>

            {formData.extraGuestAllowed && (
              <input
                type="number"
                name="extraGuestFee"
                placeholder="Extra Guest Fee"
                value={formData.extraGuestFee}
                onChange={handleChange}
              />
            )}
          </div>

          {/* Room Features */}
          <div className="form-section">
            <h3>🛌 Room Features</h3>

            <input
              name="bedType"
              placeholder="Bed Type"
              value={formData.bedType}
              onChange={handleChange}
            />

            <input
              type="number"
              name="bedCount"
              placeholder="Bed Count"
              value={formData.bedCount}
              onChange={handleChange}
            />

            <input
              type="number"
              name="roomSizeSqft"
              placeholder="Room Size (sqft)"
              value={formData.roomSizeSqft}
              onChange={handleChange}
            />

            <input
              type="number"
              name="floorNumber"
              placeholder="Floor Number"
              value={formData.floorNumber}
              onChange={handleChange}
            />

            <label>
              <input
                type="checkbox"
                name="hasBalcony"
                checked={formData.hasBalcony}
                onChange={handleChange}
              />
              Balcony
            </label>

            <label>
              <input
                type="checkbox"
                name="hasWindow"
                checked={formData.hasWindow}
                onChange={handleChange}
              />
              Window
            </label>
          </div>

          <button type="submit" disabled={isSubmitting}>
            {isSubmitting ? "Adding Room..." : "Add Room"}
          </button>
        </form>
      </div>
    </div>
  );
};

export default AddRoom;
