import { Link } from "react-router-dom";

export default function Navbar() {
  return (
    <nav className="navbar">
      <div className="nav-logo">
        <img src="/logo.png" alt="Aaostays Logo" />
        <div className="logo-text">
          <span className="logo-main">AaoStays</span>
          <span className="logo-sub">আমাৰ অতিথি</span>
        </div>
      </div>

      <div className="nav-links">
        <Link to="/">Home</Link>
        <Link to="/about">About</Link>
        <Link to="/location">Location</Link>
        <Link to="/contact">Contact</Link>

        <Link to="/login" className="btn-login">Login</Link>
        <Link to="/register" className="btn-login">Signup</Link>
      </div>
    </nav>
  );
}
