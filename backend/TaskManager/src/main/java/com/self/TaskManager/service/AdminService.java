package com.self.TaskManager.service;

import java.util.Map; // Example return type for stats

public interface AdminService {

    /**
     * Retrieves application-level statistics.
     * (Example: number of users, number of tasks, active sessions etc.)
     *
     * @return A map or a custom DTO containing application statistics.
     */
    Map<String, Object> getApplicationStatistics();

    /**
     * Retrieves a specific application setting.
     * (Example: a feature flag, email notification settings)
     *
     * @param settingKey The key of the setting to retrieve.
     * @return The value of the setting, or null/error if not found.
     */
    String getApplicationSetting(String settingKey);

    /**
     * Updates a specific application setting.
     *
     * @param settingKey The key of the setting to update.
     * @param settingValue The new value for the setting.
     * @return true if update was successful, false otherwise.
     */
    boolean updateApplicationSetting(String settingKey, String settingValue);

    /**
     * Triggers a system-wide maintenance task.
     * (Example: clear cache, re-index data)
     *
     * @return A message indicating the status of the triggered task.
     */
    String triggerMaintenanceTask(String taskName);
}