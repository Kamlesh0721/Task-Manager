import React from "react";
import { NavLink as RouterNavLink, useNavigate } from "react-router-dom"; // Use alias
import useAuth from "../../hooks/useAuth.js";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import IconButton from "@mui/material/IconButton"; // Potentially for menu icon later
// import MenuIcon from '@mui/icons-material/Menu';

const AppNavbar = () => {
  const { user, isAdmin, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate("/login", { replace: true });
  };

  // Style for active NavLink buttons
  const activeStyle = {
    textDecoration: "underline",
    // Add other active styles if needed
  };

  return (
    <AppBar position="static">
      {" "}
      {/* Or "fixed", "sticky" etc. */}
      <Toolbar>
        {/* Optional Menu Icon for Mobile */}
        {/* <IconButton
          size="large"
          edge="start"
          color="inherit"
          aria-label="menu"
          sx={{ mr: 2 }}
        >
          <MenuIcon />
        </IconButton> */}

        {/* App Title/Brand */}
        <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
          <RouterNavLink
            to="/dashboard"
            style={{ color: "inherit", textDecoration: "none" }}
          >
            TaskApp
          </RouterNavLink>
        </Typography>

        {/* Navigation Links for Logged-in Users */}
        {user && (
          <Box sx={{ display: { xs: "none", sm: "block" } }}>
            {" "}
            {/* Hide on extra small screens */}
            <Button
              color="inherit"
              component={RouterNavLink}
              to="/dashboard"
              style={({ isActive }) => (isActive ? activeStyle : undefined)}
            >
              Dashboard
            </Button>
            <Button
              color="inherit"
              component={RouterNavLink}
              to="/tasks"
              style={({ isActive }) => (isActive ? activeStyle : undefined)}
            >
              My Tasks
            </Button>
            {isAdmin && (
              <Button
                color="inherit"
                component={RouterNavLink}
                to="/admin"
                style={({ isActive }) => (isActive ? activeStyle : undefined)}
              >
                Admin Panel
              </Button>
            )}
          </Box>
        )}

        {/* User Info & Logout */}
        {user ? (
          <Box sx={{ display: "flex", alignItems: "center", ml: 2 }}>
            <Typography
              variant="body2"
              sx={{ mr: 2, display: { xs: "none", sm: "block" } }}
            >
              Hi, {user.username}!
            </Typography>
            <Button color="inherit" variant="outlined" onClick={handleLogout}>
              Logout
            </Button>
          </Box>
        ) : (
          // Optional: Show Login button if not logged in (but usually protected routes handle this)
          <Button color="inherit" component={RouterNavLink} to="/login">
            Login
          </Button>
        )}
      </Toolbar>
    </AppBar>
  );
};

export default AppNavbar;
