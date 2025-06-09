package com.self.TaskManager.controller;

import com.self.TaskManager.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')") // Secure all endpoints in this controller for ADMINs
@CrossOrigin(origins = "*", maxAge = 3600) // Adjust or use global CORS config
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    /**
     * GET /api/admin/stats : Retrieves application-level statistics.
     */
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getApplicationStatistics() {
        Map<String, Object> stats = adminService.getApplicationStatistics();
        logger.info("Admin requested application statistics.");
        return ResponseEntity.ok(stats);
    }

    /**
     * GET /api/admin/settings/{key} : Retrieves a specific application setting.
     */
    @GetMapping("/settings/{key}")
    public ResponseEntity<String> getApplicationSetting(@PathVariable String key) {
        String settingValue = adminService.getApplicationSetting(key);
        logger.info("Admin requested application setting for key: {}", key);
        if (settingValue.equals("Setting not found")) { // Or check for null if service returns null
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(settingValue);
        }
        return ResponseEntity.ok(settingValue);
    }

    /**
     * PUT /api/admin/settings/{key} : Updates a specific application setting.
     * Expects a plain text request body with the new value.
     */
    @PutMapping("/settings/{key}")
    public ResponseEntity<String> updateApplicationSetting(@PathVariable String key, @RequestBody String value) {
        boolean success = adminService.updateApplicationSetting(key, value);
        if (success) {
            logger.info("Admin updated application setting - Key: {}, New Value: {}", key, value);
            return ResponseEntity.ok("Setting '" + key + "' updated successfully.");
        } else {
            logger.warn("Admin failed to update application setting for key: {}", key);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Setting '" + key + "' not found or update failed.");
            // Or throw an exception from service and let GlobalExceptionHandler handle it.
        }
    }

    /**
     * POST /api/admin/maintenance/trigger : Triggers a system-wide maintenance task.
     * Expects a plain text request body with the task name.
     */
    @PostMapping("/maintenance/trigger")
    public ResponseEntity<String> triggerMaintenanceTask(@RequestBody String taskName) {
        // Trim whitespace from taskName if necessary
        String taskNameToTrigger = taskName.trim();
        if (taskNameToTrigger.isEmpty()) {
            return ResponseEntity.badRequest().body("Task name cannot be empty.");
        }
        String resultMessage = adminService.triggerMaintenanceTask(taskNameToTrigger);
        logger.info("Admin triggered maintenance task: {}, Result: {}", taskNameToTrigger, resultMessage);
        if (resultMessage.startsWith("Unknown maintenance task")) {
            return ResponseEntity.badRequest().body(resultMessage);
        }
        return ResponseEntity.ok(resultMessage);
    }
}