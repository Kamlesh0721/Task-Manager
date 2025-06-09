import React from "react";
import { Outlet } from "react-router-dom";
import Container from "@mui/material/Container";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined"; // Example Icon
import Avatar from "@mui/material/Avatar";

const AuthLayout = () => {
  return (
    <Container component="main" maxWidth="xs">
      {" "}
      {/* xs for small form factor */}
      <Box
        sx={{
          marginTop: 8, // MUI spacing units (8 * 8px = 64px)
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
        }}
      >
        <Avatar sx={{ m: 1, bgcolor: "secondary.main" }}>
          <LockOutlinedIcon />
        </Avatar>
        <Typography component="h1" variant="h5">
          Task App
        </Typography>
        {/* Box for the form content with card-like appearance */}
        <Box
          sx={{
            mt: 3,
            width: "100%",
            p: 3,
            bgcolor: "background.paper",
            borderRadius: 1,
            boxShadow: 3,
          }}
        >
          <Outlet /> {/* Renders Login or Register form */}
        </Box>
      </Box>
    </Container>
  );
};
export default AuthLayout;
