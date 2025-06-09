import React, { useState } from "react";
import { useNavigate, Link as RouterLink } from "react-router-dom";
import useAuth from "../../hooks/useAuth.js";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import Link from "@mui/material/Link";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import Alert from "@mui/material/Alert";

const RegisterForm = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [email, setEmail] = useState("");
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  const { register } = useAuth();
  const navigate = useNavigate();

  const handleSubmit = async (event) => {
    event.preventDefault();
    setError("");
    setSuccess("");
    setIsLoading(true);
    try {
      const result = await register({ username, email, password });
      setSuccess(result.message || "Registration successful! Redirecting...");
      setIsLoading(false);
      setTimeout(() => navigate("/login"), 2500);
    } catch (err) {
      setError(err.message || "Registration failed.");
      setIsLoading(false);
    }
  };

  return (
    <Box component="form" noValidate onSubmit={handleSubmit} sx={{ mt: 3 }}>
      {/* No Typography needed here as AuthLayout provides title */}
      {error && (
        <Alert severity="error" sx={{ mb: 2 }}>
          {error}
        </Alert>
      )}
      {success && (
        <Alert severity="success" sx={{ mb: 2 }}>
          {success}
        </Alert>
      )}
      <Grid container spacing={2}>
        <Grid item xs={12}>
          <TextField
            autoComplete="username"
            name="username"
            required
            fullWidth
            id="username-reg"
            label="Username"
            autoFocus
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            disabled={isLoading}
          />
        </Grid>
        <Grid item xs={12}>
          <TextField
            required
            fullWidth
            id="email-reg"
            label="Email Address"
            name="email"
            autoComplete="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            disabled={isLoading}
          />
        </Grid>
        <Grid item xs={12}>
          <TextField
            required
            fullWidth
            name="password"
            label="Password"
            type="password"
            id="password-reg"
            autoComplete="new-password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            disabled={isLoading}
            inputProps={{ minLength: 6 }} // Basic validation hint
          />
        </Grid>
      </Grid>
      <Button
        type="submit"
        fullWidth
        variant="contained"
        sx={{ mt: 3, mb: 2 }}
        disabled={isLoading}
      >
        {isLoading ? "Signing Up..." : "Sign Up"}
      </Button>
      <Grid container justifyContent="flex-end">
        <Grid item>
          <Link component={RouterLink} to="/login" variant="body2">
            Already have an account? Sign in
          </Link>
        </Grid>
      </Grid>
    </Box>
  );
};
export default RegisterForm;
