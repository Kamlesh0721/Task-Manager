import React from "react";
import { Outlet } from "react-router-dom";
import Box from "@mui/material/Box";
import AppNavbar from "../common/AppNavbar.jsx"; // Import the new Navbar
import Typography from "@mui/material/Typography"; // For Footer

const MainLayout = () => {
  return (
    <Box sx={{ display: "flex", flexDirection: "column", minHeight: "100vh" }}>
      <AppNavbar /> {/* Use the MUI Navbar */}
      <Box
        component="main"
        sx={{ flexGrow: 1, py: 3, px: { xs: 1, sm: 2, md: 3 } }}
      >
        {" "}
        {/* Responsive padding */}
        <Outlet />
      </Box>
      <Box
        component="footer"
        sx={{
          p: 2,
          mt: "auto",
          bgcolor: "background.paper",
          borderTop: "1px solid",
          borderColor: "divider",
          textAlign: "center",
        }}
      >
        <Typography variant="body2" color="text.secondary">
          © {new Date().getFullYear()} Task App
        </Typography>
      </Box>
    </Box>
  );
};
export default MainLayout;
