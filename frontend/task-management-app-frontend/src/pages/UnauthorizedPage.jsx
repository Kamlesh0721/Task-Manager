import React from "react";
import { Link as RouterLink } from "react-router-dom";
import Typography from "@mui/material/Typography";
import Link from "@mui/material/Link";
import Box from "@mui/material/Box";
import useAuth from "../hooks/useAuth.js";

const UnauthorizedPage = () => {
  const { user } = useAuth();
  return (
    <Box sx={{ textAlign: "center", p: 5 }}>
      <Typography variant="h3" component="h1" gutterBottom>
        403 - Unauthorized
      </Typography>
      <Typography variant="body1" gutterBottom>
        Sorry, you don't have permission to access this page.
      </Typography>
      {user && (
        <Typography variant="body2" color="text.secondary" gutterBottom>
          (Your current role: {user.role})
        </Typography>
      )}
      <Link component={RouterLink} to="/dashboard" variant="body1">
        Go to Dashboard
      </Link>
    </Box>
  );
};
export default UnauthorizedPage;
