import {
  FiHome,
  FiUsers,
  FiUserCheck,
  FiUserX,
  FiFileText,
  FiLayers,
  FiList,
  FiSettings,
  FiShield,
  FiPhoneCall,
  FiBell,
  FiUser
} from "react-icons/fi";
import "../App.css";
import { useNavigate } from "react-router-dom";

const AdminDashboard = () => {

        const navigate = useNavigate();

  return (
    <div className="admin-container">

      {/* ---------------------------------------------------------------- */}
      {/*                           SIDEBAR                                */}
      {/* ---------------------------------------------------------------- */}
      <div className="sidebar">
        <h2 className="brand">AAO ADMIN</h2>

        <ul className="menu">
          <li><FiHome /> Dashboard</li>
          <li onClick={() => navigate("/admin/users")}> 
           <FiUsers /> User Management
            </li>
 
          <li onClick={()=> navigate("/hostManagement")}><FiUserCheck /> Host Management</li>
          <li><FiUserX /> Employee Management</li>
          <li onClick={()=> navigate("/propertymanagement")}><FiFileText /> Property Management</li>
          <li><FiLayers /> Room Management</li>
          <li><FiList /> Amenities & Categories</li>
          <li><FiPhoneCall /> Contact Details</li>
          <li><FiSettings /> Employee Management</li>
          <li><FiShield /> Admin Management</li>
        </ul>
      </div>

      {/* ---------------------------------------------------------------- */}
      {/*                         MAIN CONTENT                              */}
      {/* ---------------------------------------------------------------- */}
      <main className="admin-content">

        {/* -------------------------- TOPBAR ---------------------------- */}
        <div className="admin-topbar">
          <div>
            <h2>Admin Dashboard</h2>
            <p>Welcome back, Administrator</p>
          </div>

          <div className="top-actions">
            <input type="text" placeholder="Search..." className="search-bar" />
            <FiBell className="icon" />
            <div className="admin-profile">
              <FiUser />
            </div>
          </div>
        </div>

        {/* ----------------------- ANALYTICS CARDS ----------------------- */}
        <div className="analytics-grid">

          <div className="analytic-card">
            <p>Total Users</p>
            <h2>450</h2>
          </div>

          <div className="analytic-card">
            <p>Total Hosts</p>
            <h2>60</h2>
          </div>

          <div className="analytic-card">
            <p>Total Guests</p>
            <h2>390</h2>
          </div>

          <div className="analytic-card">
            <p>Active Properties</p>
            <h2>72</h2>
          </div>

          <div className="analytic-card">
            <p>Pending Approvals</p>
            <h2>18</h2>
          </div>

          <div className="analytic-card">
            <p>KYC Pending</p>
            <h2>12</h2>
          </div>

        </div>

        {/* ----------------------- ADMIN MODULES ------------------------- */}
        <h3 className="section-title">Admin Modules</h3>

        <div className="modules-grid">
          <div className="module-card">User Management</div>
          <div className="module-card">Host Management</div>
          <div className="module-card">Guest Management</div>
          <div className="module-card">Property Management</div>
          <div className="module-card">Room Management</div>
          <div className="module-card">Amenities Management</div>
          <div className="module-card">Categories Management</div>
          <div className="module-card">Contact Details</div>
          <div className="module-card">Employee Management</div>
          <div className="module-card">Admin Management</div>
        </div>

        {/* ---------------------- RECENT ACTIVITY ------------------------ */}
        <h3 className="section-title">Recent Activity</h3>

        <div className="activity-box">
          <div className="activity-item">
            <span className="dot"></span>
            New user registration — 2 minutes ago
          </div>
          <div className="activity-item">
            <span className="dot"></span>
            Property listing approved — 15 minutes ago
          </div>
          <div className="activity-item">
            <span className="dot"></span>
            KYC verification completed — 1 hour ago
          </div>
          <div className="activity-item">
            <span className="dot"></span>
            Host account activated — 2 hours ago
          </div>
        </div>

      </main>
    </div>
  );
};

export default AdminDashboard;
