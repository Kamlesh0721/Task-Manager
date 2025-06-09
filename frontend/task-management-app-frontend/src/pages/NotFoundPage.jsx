import React from "react";
import { Link as RouterLink } from "react-router-dom"; // Use alias for clarity
import Typography from "@mui/material/Typography";
import Link from "@mui/material/Link"; // MUI Link
import Box from "@mui/material/Box"; // For centering

const NotFoundPage = () => (
  <Box sx={{ textAlign: "center", p: 5 }}>
    <Typography variant="h3" component="h1" gutterBottom>
      404 - Not Found
    </Typography>
    <Typography variant="body1" gutterBottom>
      Oops! The page you're looking for doesn't exist.
    </Typography>
    <Link component={RouterLink} to="/" variant="body1">
      Go Home
    </Link>
  </Box>
);
export default NotFoundPage;
