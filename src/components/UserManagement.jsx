import { useState } from "react";
import ApiService from "../service/ApiService";

function UserManagement() {
  const [users, setUsers] = useState([]);
  const [showTable, setShowTable] = useState(false);
const loadAllUsers = async () => {
  try {
    const data = await ApiService.getAllUsers();
    console.log("API RESPONSE:", data);  

    setUsers(data.data  );  
    setShowTable(true);
  } catch (err) {
    console.error("Error fetching users:", err);
  }
};
const changeRole = async (userId, role) => {
  try {
    await ApiService.changeUserRole(userId, role);
    alert("Role updated successfully!");
    loadAllUsers();
  } catch (err) {
    alert(err?.response?.data?.message || "Failed to change role");
  }
};



  return (
    <div>

  
      <button onClick={loadAllUsers} className="all-users-btn">
        All Users
      </button>

      {showTable && (
       <table className="user-table">
  <thead>
    <tr>
      <th>ID</th>
      <th>Name</th>
      <th>Email</th>
      <th>Role Status</th>
      <th>Actions</th>
    </tr>
  </thead>

  <tbody>
    {users.map((u) => (
      <tr key={u.id}>
        <td>{u.id}</td>
        <td>{u.fullName}</td>
        <td>{u.email}</td>
        <td>{u.userType}</td>

        <td>
          <button className="action-btn view-btn">View</button>
          <button className="action-btn edit-btn">Edit</button>
          <button className="action-btn delete-btn">Delete</button>

          <select
            className="role-dropdown"
            onChange={(e) => changeRole(u.id, e.target.value)}
          >
            <option value="">Assign Role</option>
            <option value="ADMIN">Admin</option>
            <option value="HOST">Host</option>
            <option value="EMPLOYEE">Employee</option>
          </select>
        </td>
      </tr>
    ))}
  </tbody>
</table>

      )}

    </div>
  );
}

export default UserManagement;
