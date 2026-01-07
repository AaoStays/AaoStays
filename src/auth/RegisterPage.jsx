import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import ApiService from "../service/ApiService";


const RegisterPage = () => {
  const navigate = useNavigate();

  const [registration, setRegistration] = useState({
    email: "",
    password: "",
    fullName: "",
    phoneNumber: "",
    userType: "USER",      
    dateOfBirth: "",
    address: ""
  });

  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const handleChange = (e) => {
    setRegistration({
      ...registration,
      [e.target.name]: e.target.value,
    });
  };
  
  const handleSignup = async (e) => {
    e.preventDefault();
    setError("");
    setSuccess("");

    try {
      const response = await ApiService.registerUser(registration);
      console.log("Registration Successful:", response);

      setSuccess("Account created successfully!");
      
     
      setTimeout(() => navigate("/login"), 1000);

    } catch (err) {
      console.error(err);
      setError("Registration failed. Please try again.");
    }
  };

  return (
    <div className="auth-container">
      <form className="auth-box" onSubmit={handleSignup}>
        
        <h2>Create Account</h2>

        {error && <p className="error-text">{error}</p>}
        {success && <p className="success-text">{success}</p>}

        <input
          type="text"
          name="fullName"
          placeholder="Full Name"
          value={registration.fullName}
          onChange={handleChange}
          required
        />

        <input
          type="email"
          name="email"
          placeholder="Email"
          value={registration.email}
          onChange={handleChange}
          required
        />

        <input
          type="text"
          name="phoneNumber"
          placeholder="Phone Number"
          value={registration.phoneNumber}
          onChange={handleChange}
          required
        />

        <input
          type="password"
          name="password"
          placeholder="Password"
          value={registration.password}
          onChange={handleChange}
          required
        />

        <input
          type="date"
          name="dateOfBirth"
          placeholder="Date of Birth"
          value={registration.dateOfBirth}
          onChange={handleChange}
        />

        <input
          type="text"
          name="address"
          placeholder="Address"
          value={registration.address}
          onChange={handleChange}
        />
          <input
          type="text"
          name="userType"
          placeholder="role"
          value={registration.userType}
          onChange={handleChange}
          required
        />

        <button type="submit" className="auth-btn">Signup</button>

        <p className="auth-footer">
          Already have an account? <Link to="/login">Login</Link>
        </p>
      </form>
    </div>
  );
};

export default RegisterPage;
