import React from "react";
import { Navigate, Outlet, useLocation } from "react-router-dom";
import useAuth from "../hooks/useAuth.js";
import LoadingSpinner from "../components/common/LoadingSpinner.jsx"; // Import MUI spinner

export const ROLES = { USER: "USER", ADMIN: "ADMIN" };

const ProtectedRoute = ({ allowedRoles }) => {
  const { user, isAuthenticated, isLoading } = useAuth();
  const location = useLocation();

  if (isLoading) {
    // AuthProvider now shows the main spinner, so this might not be strictly needed,
    // but can be kept as a fallback or removed if AuthProvider handles it reliably.
    // return <LoadingSpinner message="Verifying access..." />;
    return null; // Rely on AuthProvider's spinner
  }

  if (!isAuthenticated) {
    return <Navigate to="/login" state={{ from: location }} replace />;
  }

  const isAuthorized = !allowedRoles || allowedRoles.includes(user?.role);

  if (!isAuthorized) {
    return <Navigate to="/unauthorized" state={{ from: location }} replace />;
  }

  return <Outlet />;
};

export default ProtectedRoute;
