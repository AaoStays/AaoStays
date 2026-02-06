import React, { useEffect, useState } from "react";
import ApiService from "../service/ApiService";


const HostManagement = () => {
  const [hosts, setHosts] = useState([]);

  useEffect(() => {
    fetchHosts();
  }, []);

  const fetchHosts = async () => {
    try {
      const response = await ApiService.getAllHosts();
      setHosts(response.data); // backend returns {status,message,data}
    } catch (error) {
      console.error("Error fetching hosts:", error);
    }
  };


  const handleVerify = async (hostId) => {
    try {
      await ApiService.verifyIdentity(hostId);
      fetchHosts(); // refresh table
    } catch (error) {
      console.error("Error verifying host:", error);
    }
  };


  const handleActivate = async (hostId) => {
    try {
      await ApiService.activateHost(hostId);
      fetchHosts();
    } catch (error) {
      console.error("Error activating host:", error);
    }
  };


  const handleDeactivate = async (hostId) => {
    try {
      await ApiService.deactivateHost(hostId);
      fetchHosts();
    } catch (error) {
      console.error("Error deactivating host:", error);
    }
  };


  const handleDelete = async (hostId) => {
    if (!window.confirm("Are you sure you want to delete this host?")) return;

    try {
      await ApiService.deleteHost(hostId);
      fetchHosts();
    } catch (error) {
      console.error("Error deleting host:", error);
    }
  };

  return (
    <div className="host-management-container">
      <h2 className="host-title">Host Management</h2>

      <table className="host-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Photo</th>
            <th>Name</th>
            <th>Email</th>
            <th>Verified</th>
            <th>Status</th>
            <th className="actions-col">Actions</th>
          </tr>
        </thead>

        <tbody>
          {hosts?.map((host) => (
            <tr key={host.hostId}>
              <td>{host.hostId}</td>

              <td>
                <img
                  src={host.profilePhotoUrl || "/default-avatar.png"}
                  alt="host"
                  className="host-avatar"
                />
              </td>

              <td>{host.fullName || "No Name"}</td>
              <td>{host.email}</td>

              <td>
                <span
                  className={
                    host.identityVerified ? "badge verified" : "badge not-verified"
                  }
                >
                  {host.identityVerified ? "Verified" : "Not Verified"}
                </span>
              </td>

              <td>
                <span
                  className={host.active ? "badge active" : "badge inactive"}
                >
                  {host.active ? "Active" : "Inactive"}
                </span>
              </td>

              <td className="action-buttons">
                <button
                  className="btn verify"
                  onClick={() => handleVerify(host.hostId)}
                >
                  Verify
                </button>

                <button
                  className="btn activate"
                  onClick={() => handleActivate(host.hostId)}
                >
                  Activate
                </button>

                <button
                  className="btn deactivate"
                  onClick={() => handleDeactivate(host.hostId)}
                >
                  Deactivate
                </button>

                <button className="btn edit">
                  Edit
                </button>

                <button
                  className="btn delete"
                  onClick={() => handleDelete(host.hostId)}
                >
                  Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default HostManagement;
