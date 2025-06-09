import React, { useState, useEffect, useCallback } from "react";
import * as taskService from "../services/taskService.js";
import Container from "@mui/material/Container";
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import CircularProgress from "@mui/material/CircularProgress";
import Alert from "@mui/material/Alert";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import ListItemText from "@mui/material/ListItemText";
import Paper from "@mui/material/Paper"; // For form background
import Divider from "@mui/material/Divider";
// Import icons for later use
// import EditIcon from '@mui/icons-material/Edit';
// import DeleteIcon from '@mui/icons-material/Delete';
// import IconButton from '@mui/material/IconButton';

const TasksPage = () => {
  const [tasks, setTasks] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState("");
  const [newTaskTitle, setNewTaskTitle] = useState("");
  const [newTaskDesc, setNewTaskDesc] = useState("");
  const [isSubmitting, setIsSubmitting] = useState(false);

  const fetchTasks = useCallback(async () => {
    setIsLoading(true);
    setError("");
    try {
      const data = await taskService.getTasks();
      setTasks(data || []);
    } catch (err) {
      setError(err.message || "Failed to load tasks.");
      setTasks([]);
    } finally {
      setIsLoading(false);
    }
  }, []); // useCallback dependency

  useEffect(() => {
    fetchTasks();
  }, [fetchTasks]); // Depend on the memoized fetchTasks

  const handleAddTask = async (e) => {
    e.preventDefault();
    if (!newTaskTitle) return;
    setIsSubmitting(true);
    setError("");
    try {
      const createdTask = await taskService.createTask({
        title: newTaskTitle,
        description: newTaskDesc,
        status: "PENDING", // Example default status if needed
      });
      // Option 1: Add directly (may not have full data if backend modifies it)
      // setTasks(prevTasks => [...prevTasks, createdTask]);
      // Option 2: Refetch the list to get the latest data
      await fetchTasks(); // Ensures list is up-to-date
      setNewTaskTitle("");
      setNewTaskDesc("");
    } catch (err) {
      setError(err.message || "Failed to add task.");
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <Container maxWidth="md">
      <Typography variant="h4" component="h1" gutterBottom>
        My Tasks
      </Typography>

      {/* Add Task Form */}
      <Paper elevation={2} sx={{ p: 3, mb: 4 }}>
        {" "}
        {/* Use Paper for background/elevation */}
        <Box component="form" onSubmit={handleAddTask} noValidate>
          <Typography variant="h6" gutterBottom>
            Add New Task
          </Typography>
          {error && (
            <Alert severity="error" sx={{ mb: 2 }}>
              {error}
            </Alert>
          )}
          <TextField
            label="Task Title"
            variant="outlined"
            fullWidth
            required
            value={newTaskTitle}
            onChange={(e) => setNewTaskTitle(e.target.value)}
            disabled={isSubmitting}
            sx={{ mb: 2 }}
          />
          <TextField
            label="Task Description (Optional)"
            variant="outlined"
            fullWidth
            multiline
            rows={3}
            value={newTaskDesc}
            onChange={(e) => setNewTaskDesc(e.target.value)}
            disabled={isSubmitting}
            sx={{ mb: 2 }}
          />
          <Button
            type="submit"
            variant="contained"
            disabled={isSubmitting}
            startIcon={
              isSubmitting ? (
                <CircularProgress size={20} color="inherit" />
              ) : null
            }
          >
            {isSubmitting ? "Adding..." : "Add Task"}
          </Button>
        </Box>
      </Paper>

      {/* Task List */}
      <Typography variant="h6" gutterBottom>
        Existing Tasks
      </Typography>
      {isLoading ? (
        <Box sx={{ display: "flex", justifyContent: "center", p: 3 }}>
          <CircularProgress />
        </Box>
      ) : tasks.length > 0 ? (
        <List component={Paper} elevation={1}>
          {" "}
          {/* List within Paper */}
          {tasks.map((task, index) => (
            <React.Fragment key={task.id}>
              <ListItem
                alignItems="flex-start"
                // secondaryAction={ // Add buttons later
                //   <>
                //     <IconButton edge="end" aria-label="edit">
                //       <EditIcon />
                //     </IconButton>
                //     <IconButton edge="end" aria-label="delete">
                //       <DeleteIcon />
                //     </IconButton>
                //   </>
                // }
              >
                <ListItemText
                  primary={task.title}
                  secondary={
                    <>
                      <Typography
                        sx={{ display: "inline" }}
                        component="span"
                        variant="body2"
                        color="text.primary"
                      >
                        {task.description || "No description"}
                      </Typography>
                      {` — Status: ${task.status || "N/A"}`}
                    </>
                  }
                />
              </ListItem>
              {/* Add Divider between items */}
              {index < tasks.length - 1 && <Divider component="li" />}
            </React.Fragment>
          ))}
        </List>
      ) : (
        <Typography variant="body1" color="text.secondary" sx={{ mt: 2 }}>
          No tasks found. Add one above!
        </Typography>
      )}
    </Container>
  );
};
export default TasksPage;
