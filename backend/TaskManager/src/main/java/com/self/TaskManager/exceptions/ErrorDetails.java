package com.self.TaskManager.exceptions;

import java.time.LocalDateTime;

record ErrorDetails(LocalDateTime timestamp, int status, String error, String message, String path) {}
