import React from 'react';
import CircularProgress from '@mui/material/CircularProgress';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';

// Simple full-page overlay spinner
const LoadingSpinner = ({ message = "Loading..." }) => {
  return (
    <Box
      sx={{
        position: 'fixed', // Or 'absolute' if within a positioned container
        top: 0,
        left: 0,
        width: '100%',
        height: '100%',
        display: 'flex',
        flexDirection: 'column',
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: 'rgba(255, 255, 255, 0.7)', // Optional overlay background
        zIndex: (theme) => theme.zIndex.drawer + 1, // Ensure it's on top
      }}
    >
      <CircularProgress />
      {message && <Typography sx={{ mt: 2 }}>{message}</Typography>}
    </Box>
  );
};

export default LoadingSpinner;