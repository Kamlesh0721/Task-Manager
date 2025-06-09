package com.self.TaskManager.service.impl;

import com.self.TaskManager.repository.TaskRepository;
import com.self.TaskManager.repository.UserRepository;
import com.self.TaskManager.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService {

    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    // You might inject other services or repositories needed for admin operations
    // For example, a configuration repository, audit log service, etc.

    // Dummy storage for application settings for this example
    private final Map<String, String> applicationSettings = new HashMap<>();

    @Autowired
    public AdminServiceImpl(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        // Initialize some dummy settings
        applicationSettings.put("FEATURE_X_ENABLED", "true");
        applicationSettings.put("MAX_LOGIN_ATTEMPTS", "5");
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getApplicationStatistics() {
        logger.info("Fetching application statistics");
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", userRepository.count());
        stats.put("totalTasks", taskRepository.count());
        // Add more stats as needed (e.g., active sessions if you track them, DB health)
        stats.put("appVersion", "1.0.0-SNAPSHOT"); // Example
        return stats;
    }

    @Override
    public String getApplicationSetting(String settingKey) {
        logger.info("Fetching application setting for key: {}", settingKey);
        // In a real app, this would come from a database, properties file, or a config server
        return applicationSettings.getOrDefault(settingKey.toUpperCase(), "Setting not found");
    }

    @Override
    @Transactional // If settings are persisted
    public boolean updateApplicationSetting(String settingKey, String settingValue) {
        logger.info("Updating application setting - Key: {}, Value: {}", settingKey, settingValue);
        // In a real app, validate the key and value, and persist the change
        if (applicationSettings.containsKey(settingKey.toUpperCase())) {
            applicationSettings.put(settingKey.toUpperCase(), settingValue);
            // Persist to DB or config store here
            logger.info("Setting '{}' updated to '{}'", settingKey, settingValue);
            return true;
        }
        logger.warn("Attempted to update non-existent setting: {}", settingKey);
        return false; // Or throw ResourceNotFoundException("ApplicationSetting", "key", settingKey);
    }

    @Override
    public String triggerMaintenanceTask(String taskName) {
        logger.info("Admin triggered maintenance task: {}", taskName);
        // Implement actual maintenance logic here (e.g., call other services)
        // This is highly application-specific.
        switch (taskName.toLowerCase()) {
            case "clearcache":
                // cacheManager.clearAllCaches(); // Example
                return "Cache clearing task initiated.";
            case "reindexdata":
                // searchService.reindexAll(); // Example
                return "Data re-indexing task initiated.";
            default:
                return "Unknown maintenance task: " + taskName;
        }
    }
}