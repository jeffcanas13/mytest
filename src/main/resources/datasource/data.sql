USE atms;

INSERT INTO status
	 VALUES
			(1, 'ACTIVE'),
		  (2, 'INACTIVE'),
      (3, 'DROPPED'),
      (4, 'DELETED'),
      (5, 'COMPLETED');

INSERT INTO assigned_milestone_status
	 VALUES
	    (1, 'In Progress', 1, 1),
      (2, 'Projectized', 2, 1),
      (3, 'For Review', 3, 1),
      (4, 'Cancelled', 4, 1),
      (5, 'Re-Assigned', 5, 1),
      (6, 'Done', 6, 1),
      (7, 'Closed', 7, 1);

INSERT INTO group_member_role
	 VALUES
			(1, 'ATMS Admin', 1, 1),
      (2, 'Vertical Head', 2, 1),
      (3, 'Group Head/Group Admin', 3, 1),
      (4, 'Task Assignee', 4, 1);

INSERT INTO task
   VALUES
      (1, 'BREAK', 'Break', 'Break', 1, 1, NOW());

INSERT INTO user(id, employee_number, first_name, last_name, email, password, status_id, created_by, created_date)
   VALUES
      (1, 'admin', 'Admin', 'Account', 'admin@atms.com', '$2a$16$p/yCJY1zQ8uS8mFHWUyXNOpTFX.fY6xGCkiswTsXCpFIYAay.yRl6', 1, 1, NOW());