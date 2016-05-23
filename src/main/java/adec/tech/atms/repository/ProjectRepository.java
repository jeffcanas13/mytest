package adec.tech.atms.repository;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import adec.tech.atms.domain.AssignedMilestoneActivity;
import adec.tech.atms.domain.Project;
import adec.tech.atms.domain.mapper.LastProjectActivityMapper;
import adec.tech.atms.domain.mapper.ProjectMapper;
import adec.tech.atms.repository.util.TableQuery;
import adec.tech.atms.security.SecurityUtil;
import adec.tech.atms.util.Pagination;


/**
 * @author Jonathan Leijendekker
 *         Date: 2/22/2016
 *         Time: 1:04 PM
 */

@Repository
public class ProjectRepository extends JdbcDaoSupport {

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Autowired
    private Pagination pagination;

    @Autowired
    private TableQuery tablequery;

    public Map<String, Object> getLastProjectsByUser(Integer pageNumber) {

        UserDetails user = SecurityUtil.getCurrentUserLogin();

        int offSet = pagination.getOffset(pageNumber, 10);

        if (user == null)
            return null;

        final String sql =
                "SELECT * " +
                        "       FROM assigned_milestone_activity " +
                        " INNER JOIN assigned_milestone " +
                        "         ON assigned_milestone.id = assigned_milestone_activity.assigned_milestone_id " +
                        " INNER JOIN milestone " +
                        "         ON milestone.id = assigned_milestone.milestone_id " +
                        " INNER JOIN project " +
                        "         ON project.id = milestone.project_id " +
                        " INNER JOIN task " +
                        "         ON task.id = assigned_milestone_activity.task_id " +
                        " INNER JOIN user assigned_to " +
                        "         ON assigned_to.id = assigned_milestone.assigned_to " +
                        " INNER JOIN user assigned_by " +
                        "         ON assigned_by.id = assigned_milestone.assigned_by " +
                        "      WHERE assigned_to.employee_number = ? " +
                        "   ORDER BY assigned_milestone_activity.datetime_start DESC " +
                        "	LIMIT ?, 10; ";

        int totalRows = tablequery.getTotalRows(
                        "       assigned_milestone_activity " +
                        " INNER JOIN assigned_milestone " +
                        "         ON assigned_milestone.id = assigned_milestone_activity.assigned_milestone_id " +
                        " INNER JOIN milestone " +
                        "         ON milestone.id = assigned_milestone.milestone_id " +
                        " INNER JOIN project " +
                        "         ON project.id = milestone.project_id " +
                        " INNER JOIN task " +
                        "         ON task.id = assigned_milestone_activity.task_id " +
                        " INNER JOIN user assigned_to " +
                        "         ON assigned_to.id = assigned_milestone.assigned_to " +
                        " INNER JOIN user assigned_by " +
                        "         ON assigned_by.id = assigned_milestone.assigned_by " +
                        "      WHERE assigned_to.employee_number = ? "
                , new Object[]{user.getUsername()}).intValue();

        Map<String, Object> lastProjectActivitiesMap = new HashMap<>();

        List<AssignedMilestoneActivity> lastProjectActivities = getJdbcTemplate().query(sql, new LastProjectActivityMapper(), user.getUsername(), offSet);
        lastProjectActivitiesMap.put("LastProjects", lastProjectActivities);
        lastProjectActivitiesMap.put("TotalRows", totalRows);

        return lastProjectActivitiesMap;

    }
    
    public Map<String, Object> getProjectList(String searchQuery, Integer pageNumber) {

    	int offset = pagination.getOffset(pageNumber, 10);

//        UserDetails user = SecurityUtil.getCurrentUserLogin();
//
//        if (user == null)
//            return null;

        final String query =
					 " SELECT * " +  
					 "   			FROM project " +  
					 "   	  INNER JOIN milestone as source_milestone " +
					 "   	   		  ON source_milestone.project_id = project.id " +  
					 "   	  INNER JOIN assigned_milestone " +
					 "      		  ON source_milestone.id = assigned_milestone.milestone_id " +  
					 "   	  INNER JOIN user as created_by" +
					 "   		 	  ON assigned_milestone.assigned_to = created_by.id " +  
					 "   	  INNER JOIN vertical " +
					 "     			  ON project.vertical_id = vertical.id " +  
					 "		  INNER JOIN `group` as owner_group " +
					 "                ON vertical.id = owner_group.vertical_id " +
					 "        INNER JOIN status " +
					 "				  ON project.status_id = status.id " +
					 "				 AND CONCAT_WS('', project.name, project.code) " + 
					 "   			LIKE ? " +
					 "  		   LIMIT ?,10 ; " ;
        
        int totalRows = tablequery.getTotalRows(
        			 "   				 project " +  
   					 "   	  INNER JOIN milestone as source_milestone " +
   					 "   	   		  ON source_milestone.project_id = project.id " +  
   					 "   	  INNER JOIN assigned_milestone " +
   					 "      		  ON source_milestone.id = assigned_milestone.milestone_id " +  
   					 "   	  INNER JOIN user as created_by" +
   					 "   		 	  ON assigned_milestone.assigned_to = created_by.id " +  
   					 "   	  INNER JOIN vertical " +
   					 "     			  ON project.vertical_id = vertical.id " +  
   					 "		  INNER JOIN `group` as owner_group " +
   					 "                ON vertical.id = owner_group.vertical_id " +
   					 "        INNER JOIN status " +
   					 "				  ON project.status_id = status.id " +
   					 "				 AND CONCAT_WS('', project.name, project.code) " + 
   					 "   			LIKE ? ; " 
   					  
                         
                 		, new Object[] {"%" + searchQuery + "%"}).intValue();

        Map<String, Object> projectMap = new HashMap<>();
        
        List<Project> projectList = getJdbcTemplate().query(query, new ProjectMapper() , "%" + searchQuery + "%", offset);
        
        projectMap.put("ProjectList", projectList);
        projectMap.put("TotalRows", totalRows);

        return projectMap;
    }
}
