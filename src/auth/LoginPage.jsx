import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import ApiService from "../service/ApiService";

const LoginPage = () => {
  const navigate = useNavigate();

  const [loginData, setLoginData] = useState({
    email: "",
    password: "",
  });

  const [error, setError] = useState("");

  const handleChange = (e) => {
    setLoginData({
      ...loginData,
      [e.target.name]: e.target.value,
    });
  };

const handleLogin = async (e) => {
  e.preventDefault();
  setError("");

  try {
    const response = await ApiService.loginUser(loginData);

    
    
    const auth = response.data;

    console.log("EXTRACTED AUTH:", auth);

    localStorage.setItem("token", auth.accessToken);
    localStorage.setItem("role", auth.role);
  
       const role = auth.role?.toUpperCase();
    if (auth.role === "HOST") {
      navigate("/host");
    } else if(auth.role==="ADMIN"){
      navigate("/admin")
    } else{
      navigate("/");
    }

  } catch (err) {
    console.error("LOGIN ERROR:", err);
    setError("Invalid email or password");
  }
};





  return (
    <div className="auth-container">
      <form className="auth-box" onSubmit={handleLogin}>
        
        <h2>Login</h2>

        {error && <p className="error-text">{error}</p>}

        <input
          type="email"
          name="email"
          placeholder="Email"
          value={loginData.email}
          onChange={handleChange}
          required
        />

        <input
          type="password"
          name="password"
          placeholder="Password"
          value={loginData.password}
          onChange={handleChange}
          required
        />

        <button type="submit" className="auth-btn">Login</button>

        <p className="auth-footer">
          Don’t have an account? <Link to="/register">Register</Link>

        </p>
      </form>
    </div>
  );
};

export default LoginPage;
