import { createBrowserRouter, Navigate } from "react-router-dom";
import AuthLayout from "../components/Layout/AuthLayout.jsx";
import MainLayout from "../components/Layout/MainLayout.jsx";
import ProtectedRoute, { ROLES } from "./ProtectedRoute.jsx";

// Import Pages (we'll create simple placeholders next)
import LoginPage from "../pages/LoginPage.jsx";
import RegisterPage from "../pages/RegisterPage.jsx";
import DashboardPage from "../pages/DashboardPage.jsx";
import NotFoundPage from "../pages/NotFoundPage.jsx";
import UnauthorizedPage from "../pages/UnauthorizedPage.jsx";

const router = createBrowserRouter([
  // Public routes
  {
    element: <AuthLayout />,
    children: [
      { path: "/login", element: <LoginPage /> },
      { path: "/register", element: <RegisterPage /> },
      // Redirect root to login initially if not handled by protected route
      { path: "/", element: <Navigate to="/login" replace /> },
    ],
  },
  // Protected routes (requires login)
  {
    element: <MainLayout />,
    children: [
      {
        // This route requires USER or ADMIN role
        element: <ProtectedRoute allowedRoles={[ROLES.USER, ROLES.ADMIN]} />,
        children: [
          // Redirect root to dashboard if logged in
          { path: "/", element: <Navigate to="/dashboard" replace /> },
          { path: "/dashboard", element: <DashboardPage /> },
          // Add future protected routes here (like /tasks)
        ],
      },
      // Add future ADMIN only routes here inside another ProtectedRoute element
    ],
  },
  // Other top-level routes
  { path: "/unauthorized", element: <UnauthorizedPage /> },
  { path: "*", element: <NotFoundPage /> }, // Catch-all 404
]);

export default router;
