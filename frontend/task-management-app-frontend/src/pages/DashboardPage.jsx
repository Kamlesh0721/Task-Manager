import React from "react";
import { Link as RouterLink } from "react-router-dom";
import useAuth from "../hooks/useAuth.js";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import Button from "@mui/material/Button";
import Box from "@mui/material/Box";

const DashboardPage = () => {
  const { user } = useAuth();
  return (
    <Container maxWidth="md">
      {" "}
      {/* Adjust maxWidth as needed */}
      <Typography variant="h4" component="h1" gutterBottom>
        Dashboard
      </Typography>
      <Typography variant="h6" gutterBottom>
        Welcome, {user?.username || "User"}!
      </Typography>
      <Typography variant="body1" color="text.secondary" paragraph>
        Your current role is: <strong>{user?.role}</strong>.
      </Typography>
      <Typography variant="body1" paragraph>
        This is your main dashboard. Use the navigation bar to manage your tasks
        or access admin features (if applicable).
      </Typography>
      <Box sx={{ mt: 3 }}>
        <Button
          variant="contained"
          component={RouterLink} // Use RouterLink for navigation
          to="/tasks"
        >
          View My Tasks
        </Button>
        {/* Conditionally render Admin link if needed here too */}
      </Box>
    </Container>
  );
};
export default DashboardPage;
