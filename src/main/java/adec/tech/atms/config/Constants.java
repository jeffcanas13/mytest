package adec.tech.atms.config;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jonathan Leijendekker
 *         Date: 1/13/2016
 *         Time: 12:18 PM
 */
public class Constants {

    /**
     * PROJECT CONSTATNTS
     */
    public static final String PACKAGE_NAME = "adec.tech.atms";

    /**
     * SPRING PROFILES
     */
    public static final String SPRING_PROFILE_DEVELOPMENT = "dev";
    public static final String SPRING_PROFILE_PRODUCTION = "prod";

    /**
     * ERROR MESSAGES
     */

    // TASK
    public static final String SPECIFY_TASK = "Please specify a task";
    public static final String INVALID_TASK_ID_FORMAT = "Invalid task";
    public static final String TASK_NOT_ASSIGNED = "You cannot use the task";
    public static final String TASK_DOES_NOT_EXIST = "The task does not exist";

    // ACTIVITY
    public static final String INVALID_ACTIVITY_ID_FORMAT = "Invalid activity";
    public static final String INVALID_ACTIVITY = "Milestone activity is invalid";

    // PAGINATION
    public static final String INVALID_PAGE_NUMBER = "Invalid page number";

    // PROJECT
    public static final String INVALID_PROJECT_ID_FORMAT = "Project id is invalid";

    // MILESTONE
    public static final String INVALID_MILESTONE_ID_FORMAT = "Invalid milestone";
    public static final String MILESTONE_NOT_ASSIGNED = "You cannot task in this milestone";
    public static final String MILESTONE_TASK_OUT_FAILED = "Cannot task out the specific activity";
    public static final String MILESTONE_TITLE_REQUIRED = "Title is required";
    public static final String MILESTONE_TITLE_MAX = "Title can only accept up to 32 characters";
    public static final String MILESTONE_DESCRIPTION_MAX = "Description can only accept up to 128 characters";
    public static final String MILESTONE_TARGET_HOURS_MIN = "Minimum target hour is 0.00";
    public static final String MILESTONE_TARGET_HOURS_MAX = "Maximum target hours is 99999.99";
    public static final String INVALID_MILESTONE_STATUS = "Invalid milestone status";

    // VERTICAL
    public static final String VERTICAL_ALREADY_EXISTS = "Vertical already exists";
    public static final String VERTICAL_DOES_NOT_EXIST = "Vertical does not exist";
    public static final String EMPTY_VERTICAL_NAME = "Vertical name cannot be empty";
    public static final String EMPTY_VERTICAL_CODE = "Vertical code cannot be empty";
    public static final String INACTIVE_VERTICAL = "Vertical is inactive";
    public static final String INVALID_VERTICAL_ID_FORMAT = "Invalid vertical";
    public static final String VERTICAL_INACTIVATION_FAILED = "Cannot active vertical";
    public static final String VERTICAL_ACTIVATION_FAILED = "Cannot active vertical";

    // STATUS    
    public static final String SPECIFY_STATUS = "Please specify a status";
    public static final String INVALID_STATUS_ID_FORMAT = "Invalid status";

    /**
     * SUCCESS MESSAGES
     */
    public static final String SUCCESS = "Success";
    public static final String SUCCESSFULLY_TASKED_IN = "The milestone was successfully tasked in";
    public static final String SUCCESSFULLY_TASKED_OUT = "The activity was successfully tasked out";
    public static final String SUCCESSFULLY_CREATED_VERTICAL = "Vertical was successfully created";

    public enum Status {
        ACTIVE(1), INACTIVE(2), DROPPED(3), DELETED(4), COMPLETED(5);

        private int i;

        Status(final int i) {
            this.i = i;
        }

        private static Map<Integer, Status> map = new HashMap<>();

        static {
            for(Status stat: Status.values()) {
                map.put(stat.i, stat);
            }
        }

        public static Status getStatus(int i) {
            return map.get(i);
        }
    }

}
