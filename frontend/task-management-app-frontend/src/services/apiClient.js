import axios from "axios";

const API_BASE_URL =
  import.meta.env.VITE_API_BASE_URL || "http://localhost:8080/api";
console.log("API Base URL:", API_BASE_URL);

const apiClient = axios.create({
  baseURL: API_BASE_URL,
  headers: { "Content-Type": "application/json" },
});

// Optional: Add interceptors later if needed for global error handling (like 401)
apiClient.interceptors.response.use(
  (response) => response,
  (error) => {
    console.error("API Error:", error.response || error.message);
    if (error.response && error.response.status === 401) {
      console.warn("API returned 401. Potential session expiry.");
      // Consider triggering logout or redirecting from component using the error
      // window.location.assign('/login?sessionExpired=true'); // Avoid this direct manipulation here if possible
    }
    return Promise.reject(error); // Important to reject so calling function knows about the error
  }
);

export default apiClient;
