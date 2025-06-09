import React from "react";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import Alert from "@mui/material/Alert";

const AdminDashboardPage = () => {
  return (
    <Container maxWidth="md">
      <Typography variant="h4" component="h1" gutterBottom>
        Admin Dashboard
      </Typography>
      <Alert severity="info" sx={{ mb: 2 }}>
        This page is accessible only to Administrators.
      </Alert>
      <Typography variant="body1">
        Admin-specific content and user management features will go here.
      </Typography>
      {/* Add Admin components like UserManagementTable later */}
    </Container>
  );
};
export default AdminDashboardPage;
