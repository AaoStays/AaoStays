import { 
  FiHome, FiCalendar, FiMessageSquare, 
  FiPlusCircle, FiLayers,FiClipboard,
  FiBell, FiUser 
} from "react-icons/fi";
import "../App.css";
import { useNavigate } from "react-router-dom";
import "../css/HostDashboard.css";
 
const HostDashboard = () => {

  const navigate = useNavigate();

  return (
    <div className="dashboard">

      {/* ------------------ SIDEBAR ------------------ */}
      <div className="sidebar">
        <h2 className="brand" onClick={() => navigate("/host/dashboard")}>
          AAO HOST
        </h2>

        <ul className="menu">
          <li onClick={() => navigate("/host/dashboard")}>
            <FiHome /> Dashboard
          </li>

          <li onClick={() => navigate("/host/manage-rooms")}>
            <FiLayers /> Manage Rooms
          </li>

          <li onClick={() => navigate("/addproperty")}>
            <FiPlusCircle /> Add New Property
          </li>

         

          

          <li onClick={() => navigate("/hostBookings")}>
          <FiClipboard/>   My Bookings
          </li>
        </ul>
      </div>

      {/* ------------------ MAIN CONTENT ------------------ */}
      <div className="content">

        {/* TOPBAR */}
        <div className="topbar">
          <h2>Host Dashboard</h2>

          <div className="top-actions">
            <FiBell className="icon" />
            <div className="profile">
              <FiUser />
            </div>
          </div>
        </div>

        {/* CARDS SECTION */}
        <div className="cards-section">
          <div className="card">
            <h3>Total Bookings</h3>
            <p className="value">128</p>
          </div>

          <div className="card">
            <h3>Upcoming Check-ins</h3>
            <p className="value">12</p>
          </div>

          <div className="card">
            <h3>Earnings</h3>
            <p className="value">₹ 45,200</p>
          </div>

          <div className="card">
            <h3>Occupancy</h3>
            <p className="value">87%</p>
          </div>
        </div>

        {/* QUICK ACCESS */}
        <h2 className="section-title">Quick Access</h2>

        <div className="modules-grid">
          <div className="module-card" onClick={() => navigate("/host/manage-rooms")}>
            Manage Rooms
          </div>

          <div className="module-card" onClick={() => navigate("/addproperty")}>
            Add New Property
          </div>
          <div className="module-card" onClick={()=> navigate("/hostProperties")}>My Properties</div>

          <div className="module-card">
            Booking Requests
          </div>

         
        </div>

      </div>
    </div>
  );
};

export default HostDashboard;
