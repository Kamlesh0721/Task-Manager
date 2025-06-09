import React, { createContext, useState, useEffect, useCallback } from "react";
import apiClient from "../services/apiClient.js";
import * as authService from "../services/authService.js";
import LoadingSpinner from "../components/common/LoadingSpinner.jsx";

const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const checkLoggedIn = async () => {
      console.log("AuthContext: Checking initial login status...");
      setIsLoading(true);
      const storedUser = localStorage.getItem("user");
      if (storedUser) {
        try {
          const parsedUser = JSON.parse(storedUser);
          setUser(parsedUser);
          apiClient.defaults.headers.common[
            "Authorization"
          ] = `Bearer ${parsedUser.token}`;
          console.log(
            "AuthContext: User found in storage:",
            parsedUser.username
          );
        } catch (error) {
          console.error("AuthContext: Invalid stored user data", error);
          localStorage.removeItem("user");
          setUser(null);
          delete apiClient.defaults.headers.common["Authorization"];
        }
      } else {
        console.log("AuthContext: No user found in storage.");
      }
      setIsLoading(false); // Finished initial check
    };
    checkLoggedIn();
  }, []);

  // login, logout, register functions (Keep logic from previous step)
  const login = useCallback(async (credentials) => {
    try {
      const userData = await authService.login(credentials);
      setUser(userData);
      localStorage.setItem("user", JSON.stringify(userData));
      apiClient.defaults.headers.common[
        "Authorization"
      ] = `Bearer ${userData.token}`;
      console.log("AuthContext: Login successful:", userData.username);
      return userData;
    } catch (error) {
      console.error("AuthContext: Login failed:", error);
      setUser(null);
      localStorage.removeItem("user");
      delete apiClient.defaults.headers.common["Authorization"];
      throw error;
    }
  }, []);

  const logout = useCallback(() => {
    console.log("AuthContext: Logging out...");
    setUser(null);
    localStorage.removeItem("user");
    delete apiClient.defaults.headers.common["Authorization"];
  }, []);

  const register = useCallback(async (userData) => {
    try {
      const responseData = await authService.register(userData);
      console.log(
        "AuthContext: Registration successful (response):",
        responseData
      );
      return responseData;
    } catch (error) {
      console.error("AuthContext: Registration failed:", error);
      throw error;
    }
  }, []);

  const value = {
    user,
    isAuthenticated: !!user,
    isAdmin: user?.role === "ADMIN",
    isLoading,
    login,
    logout,
    register,
  };

  return (
    <AuthContext.Provider value={value}>
      {/* Use MUI Loading Spinner */}
      {isLoading ? <LoadingSpinner message="Authenticating..." /> : children}
    </AuthContext.Provider>
  );
};

export default AuthContext;
