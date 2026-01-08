import axios from "axios";

export default class ApiService{
   
static Base_URL = "http://localhost:8081";

  static getHeader() {
  const token = localStorage.getItem("token");

  const headers = {
    "Content-Type": "application/json",
  };

  
  if (token && token !== "null") {
    headers.Authorization = `Bearer ${token}`;
  }

  return headers;
}


  

   static async registerUser(registration){
    const response= await axios.post(`${this.Base_URL}/api/auth/register`, registration)
    return response.data
   }


  
   static async loginUser(login){
    const resonse = await axios.post(`${this.Base_URL}/api/auth/login`,login)
    return resonse.data
   }


   static logout(){
    localStorage.removeItem("token");
    window.location.href="/login";
   }
    
   static async getAllUsers() {
    try {
      const response = await axios.get(
        `${this.Base_URL}/api/users/getAll`,
        { headers: this.getHeader() }
      );
      return response.data; 
    } catch (error) {
      console.error("Error fetching users:", error);
      throw error;
    }
  }
    
 static async changeUserRole(userId, newRole) {
  try {
    const response = await axios.put(
      `${this.Base_URL}/api/users/update-role/${userId}?role=${newRole}`,
      {},
      { headers: this.getHeader() }
    );
    return response.data;
  } catch (error) {
    console.error("error in assigning role:", error);
    throw error;
  }
}

static async addProperty(propertyData) {
  try {
    const response = await axios.post(
      `${this.Base_URL}/api/v1/properties/addProperty`,
      propertyData,
      {
        headers: this.getHeader(),
      }
    );
    return response.data;
  } catch (error) {
    console.error("Error adding property:", error);
    throw error;
  }
}

static async uploadImages(propertyId, imageFiles) {
  try {
    const formData = new FormData();
    
    
    imageFiles.forEach((file) => {
      formData.append("images", file);
    });

    console.log("Uploading to property ID:", propertyId);
    console.log("Number of images:", imageFiles.length);

    const response = await axios.post(
      `${this.Base_URL}/api/v1/properties/images/upload/${propertyId}`,  // 👈 Added slash
      formData,
      {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
          "Content-Type": "multipart/form-data",
        },
      }
    );

    return response.data;

  } catch (error) {
    console.error("Error uploading images:", error);
    console.error("Error details:", error.response?.data);
    throw error;
  }
}

 
  static async getAllProperties() {
    try {
      const response = await axios.get(
        `${this.Base_URL}/api/v1/properties/getAll`
      );
      return response.data;
    } catch (error) {
      console.error("Error fetching properties:", error);
      throw error;
    }
  }

  static async getPropertyById(propertyId) {
    try {
      const response = await axios.get(
        `${this.Base_URL}/api/v1/properties/${propertyId}`
      );
      return response.data;
    } catch (error) {
      console.error("Error fetching property by ID:", error);
      throw error;
    }
  }

  static async searchProperties(params) {
    try {
      const response = await axios.get(
        `${this.Base_URL}/api/v1/properties/search`,
        { params }
      );
      return response.data;
    } catch (error) {
      console.error("Error searching properties:", error);
      throw error;
    }
  }

  static async getPropertyImages(propertyId) {
    try {
      const response = await axios.get(
        `${this.Base_URL}/api/v1/properties/${propertyId}/images`
      );
      return response.data;
    } catch (error) {
      console.error("Error fetching images:", error);
      throw error;
    }
  }
  


  static async addProperty(propertyData) {
    try {
      const response = await axios.post(
        `${this.Base_URL}/api/v1/properties/addProperty`,
        propertyData,
        { headers: this.getHeader() }
      );
      return response.data;
    } catch (error) {
      console.error("Error adding property:", error);
      throw error;
    }
  }

  static async updateProperty(propertyId, propertyData) {
    try {
      const response = await axios.put(
        `${this.Base_URL}/api/v1/properties/${propertyId}`,
        propertyData,
        { headers: this.getHeader() }
      );
      return response.data;
    } catch (error) {
      console.error("Error updating property:", error);
      throw error;
    }
  }

  static async deleteProperty(propertyId) {
    try {
      const response = await axios.delete(
        `${this.Base_URL}/api/v1/properties/${propertyId}`,
        { headers: this.getHeader() }
      );
      return response.data;
    } catch (error) {
      console.error("Error deleting property:", error);
      throw error;
    }
  }
// ⭐ Get ALL hosts (ADMIN only)
static async getAllHosts() {
  try {
    const response = await axios.get(
      `${this.Base_URL}/api/v1/hosts/getAll`,
      { headers: this.getHeader() }
    );
    return response.data;
  } catch (error) {
    console.error("Error fetching hosts:", error);
    throw error;
  }
}

static async getHostById(hostId) {
  try {
    const response = await axios.get(
      `${this.Base_URL}/api/v1/hosts/${hostId}`,
      { headers: this.getHeader() }
    );
    return response.data;
  } catch (error) {
    console.error(`Error fetching host ${hostId}:`, error);
    throw error;
  }
}


static async createHost(hostDto) {
  try {
    const response = await axios.post(
      `${this.Base_URL}/api/v1/hosts`,
      hostDto,
      { headers: this.getHeader() }
    );
    return response.data;
  } catch (error) {
    console.error("Error creating host:", error);
    throw error;
  }
}


static async updateHost(hostId, hostDto) {
  try {
    const response = await axios.put(
      `${this.Base_URL}/api/v1/hosts/${hostId}`,
      hostDto,
      { headers: this.getHeader() }
    );
    return response.data;
  } catch (error) {
    console.error(`Error updating host ${hostId}:`, error);
    throw error;
  }
}

static async deleteHost(hostId) {
  try {
    const response = await axios.delete(
      `${this.Base_URL}/api/v1/hosts/${hostId}`,
      { headers: this.getHeader() }
    );
    return response.data;
  } catch (error) {
    console.error(`Error deleting host ${hostId}:`, error);
    throw error;
  }
}


static async verifyIdentity(hostId) {
  try {
    const response = await axios.post(
      `${this.Base_URL}/api/v1/hosts/${hostId}/verify-identity`,
      {},
      { headers: this.getHeader() }
    );
    return response.data;
  } catch (error) {
    console.error(`Error verifying identity for host ${hostId}:`, error);
    throw error;
  }
}


static async activateHost(hostId) {
  try {
    const response = await axios.patch(
      `${this.Base_URL}/api/v1/hosts/activate/${hostId}`,
      {},
      { headers: this.getHeader() }
    );
    return response.data;
  } catch (error) {
    console.error(`Error activating host ${hostId}:`, error);
    throw error;
  }
}


static async deactivateHost(hostId) {
  try {
    const response = await axios.patch(
      `${this.Base_URL}/api/v1/hosts/${hostId}/deactivate`,
      {},
      { headers: this.getHeader() }
    );
    return response.data;
  } catch (error) {
    console.error(`Error deactivating host ${hostId}:`, error);
    throw error;
  }
}

static async completeHostProfile(hostDto) {
  try {
    const response = await axios.post(
      `${this.Base_URL}/api/v1/hosts/complete-profile`,
      hostDto,
      { headers: this.getHeader() }
    );
    return response.data;
  } catch (error) {
    console.error("Error completing profile:", error);
    throw error;
  }
}

static async getMyProperties() {
  try {
    const response = await axios.get(
      `${this.Base_URL}/api/v1/properties/hostProperties`,
      { headers: this.getHeader() }
    );
    return response.data; // ApiResponse
  } catch (error) {
    console.error("Error in fetching properties", error);
    throw error;
  }
}
static async getHostBookings() {
  try {
    const response = await axios.get(
      `${this.Base_URL}/api/v1/bookings/gethostBookings`,
      { headers: this.getHeader() }
    );
    return response.data; 
  } catch (error) {
    console.error("Error in fetching properties", error);
    throw error;
  }
}


static async addRoom(propertyId, roomData) {
  try {
    const response = await axios.post(
      `${this.Base_URL}/api/v1/rooms/${propertyId}`,
      roomData, 
      { headers: this.getHeader() }
    );

    return response.data;
  } catch (error) {
    console.error("Error in adding room", error);
    throw error;
  }
}




}
