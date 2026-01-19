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
        <a>Home</a>
        <a>About</a>
        <a>Location</a>
        <a>Contact</a>
        <a>Login</a>
        <a className="signup">Sign Up</a>
      </div>
    </nav>
  );
}
