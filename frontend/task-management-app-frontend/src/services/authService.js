// src/services/authService.js
import apiClient from "./apiClient";

const LOGIN_ENDPOINT = "/auth/login"; // This is correct relative to API_BASE_URL
const REGISTER_ENDPOINT = "/auth/register"; // << UPDATED from /auth/signup

export const login = async (credentials) => {
  // credentials will be { email, password } from LoginForm
  try {
    const response = await apiClient.post(LOGIN_ENDPOINT, credentials);
    // Backend AuthResponse: { token, type, id, email, name, roles }

    // Validate the expected structure from AuthResponse
    if (
      response.data &&
      response.data.token &&
      response.data.id && // Check for id
      response.data.email && // Check for email
      response.data.name && // Check for name
      Array.isArray(response.data.roles) // Check if roles is an array
    ) {
      // The AuthContext's login function expects the full AuthResponse structure
      return response.data;
    } else {
      console.error(
        "Invalid login response structure from server:",
        response.data
      );
      throw new Error(
        "Login successful, but received unexpected data from the server."
      );
    }
  } catch (error) {
    // Error handling can be enhanced here if backend provides structured errors
    const errorMessage =
      error.response?.data?.message || // Standard Spring Boot error message
      error.message ||
      "Login failed. Please check your credentials.";
    console.error("Login service error:", error.response || error);
    throw new Error(errorMessage);
  }
};

export const register = async (userData) => {
  // userData will be { name, email, password } from RegisterForm
  try {
    const response = await apiClient.post(REGISTER_ENDPOINT, userData);
    // Backend returns UserProfileResponse: { id, email, name, roles }

    // Validate the expected structure from UserProfileResponse
    if (
      response.data &&
      response.data.id &&
      response.data.email &&
      response.data.name &&
      Array.isArray(response.data.roles)
    ) {
      // AuthContext's register function expects UserProfileResponse
      return response.data;
    } else {
      console.error(
        "Invalid registration response structure from server:",
        response.data
      );
      throw new Error(
        "Registration successful, but received unexpected profile data from the server."
      );
    }
  } catch (error) {
    // Extract more specific error messages if backend provides them (e.g., for validation)
    const backendError = error.response?.data;
    let errorMessage = "Registration failed.";

    if (backendError) {
      if (backendError.message) {
        errorMessage = backendError.message;
      } else if (
        backendError.errors &&
        typeof backendError.errors === "object"
      ) {
        // For Spring Boot @Valid errors which might come as an object { field: message }
        // Or an array of error objects.
        // The formatValidationErrors function from RegisterForm.jsx could be moved to a shared util if needed.
        errorMessage = Object.values(backendError.errors).join(", ");
      } else if (Array.isArray(backendError.errors)) {
        errorMessage = backendError.errors
          .map((e) =>
            typeof e === "object" ? e.defaultMessage || e.message : e
          )
          .join(", ");
      }
    } else if (error.message) {
      errorMessage = error.message;
    }

    console.error("Register service error:", error.response || error);
    throw new Error(errorMessage);
  }
};
