package com.maddy8381.PersonalProjectMngmTool.services;

import com.maddy8381.PersonalProjectMngmTool.domain.Backlog;
import com.maddy8381.PersonalProjectMngmTool.domain.Project;
import com.maddy8381.PersonalProjectMngmTool.domain.ProjectTask;
import com.maddy8381.PersonalProjectMngmTool.exceptions.ProjectNotFoundException;
import com.maddy8381.PersonalProjectMngmTool.repositories.BacklogRepository;
import com.maddy8381.PersonalProjectMngmTool.repositories.ProjectRepository;
import com.maddy8381.PersonalProjectMngmTool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectService projectService;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username){

            //ProjectTasks to be added to a specific project, project != null, Backlog exists
            Backlog backlog = projectService.findProjectByIdentifier(projectIdentifier, username).getBacklog(); //backlogRepository.findByProjectIdentifier(projectIdentifier);
            //Set the backlog to the ProjectTask
            projectTask.setBacklog(backlog);

            //we want our projectSequence  to be like this - IDPRO-1, IDPRO-2
            Integer BacklogSequence = backlog.getPTSequence();
            //Update the Backlog Sequence
            BacklogSequence++;
            backlog.setPTSequence(BacklogSequence);
            //Add Sequence to Project Task
            projectTask.setProjectSequence(projectIdentifier+"-"+ BacklogSequence);
            projectTask.setProjectIdentifier(projectIdentifier);

            //Initial Priority when priority is null
            if(projectTask.getPriority() == null || projectTask.getPriority() == 0 )
                projectTask.setPriority(3); //Low Priority

            //Initial Status when status is null
            if(projectTask.getStatus() == "" || projectTask.getStatus() == null)
                projectTask.setStatus("TO_DO");

            return projectTaskRepository.save(projectTask);

    }

    public Iterable<ProjectTask> findBacklogById(String id, String username){

        projectService.findProjectByIdentifier(id, username); //Checking user is allowed to actually work with this project

        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }

    public ProjectTask findProjectTaskByProjectSequence(String backlog_id, String pt_id, String username){

        projectService.findProjectByIdentifier(backlog_id, username); //Checking user is allowed to actually work with this project

        //make sure that our task exists
        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);
        if(projectTask == null){
            throw new ProjectNotFoundException("Project Task : " + pt_id + "  not found");
        }
        //make sure that the backlog/project id in the path corresponds to right project
        if(!projectTask.getProjectIdentifier().equals(backlog_id)){
            throw new ProjectNotFoundException("Project Task: " + pt_id + " does not exist in project: "+ backlog_id);
        }

        return projectTask;
    }

    //Update Project Task
    public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id, String username){

        //Find existing project task
        ProjectTask projectTask = findProjectTaskByProjectSequence(backlog_id, pt_id, username); //this has validation already
        //Replace it with updated task
        projectTask = updatedTask;

        //Save update
        return projectTaskRepository.save(projectTask);
    }


    public void deletePTByProjectSequence(String backlog_id, String pt_id, String username){
        ProjectTask projectTask = findProjectTaskByProjectSequence(backlog_id, pt_id, username);

        projectTaskRepository.delete(projectTask);
    }
}
