package com.maddy8381.PersonalProjectMngmTool.services;

import com.maddy8381.PersonalProjectMngmTool.domain.Backlog;
import com.maddy8381.PersonalProjectMngmTool.domain.Project;
import com.maddy8381.PersonalProjectMngmTool.domain.User;
import com.maddy8381.PersonalProjectMngmTool.exceptions.ProjectIdException;
import com.maddy8381.PersonalProjectMngmTool.exceptions.ProjectNotFoundException;
import com.maddy8381.PersonalProjectMngmTool.repositories.BacklogRepository;
import com.maddy8381.PersonalProjectMngmTool.repositories.ProjectRepository;
import com.maddy8381.PersonalProjectMngmTool.repositories.UserRepository;
import jdk.nashorn.internal.ir.PropertyKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private BacklogRepository backlogRepository;
    @Autowired
    private UserRepository userRepository;

    public Project saveOrUpdateProject(Project project, String username){

        //Project.getId = null when we create new project

        //project.getId != null when we update project
        //find by db id -> null

        if(project.getId() != null){
            Project existingProject = projectRepository.findByProjectIdentifier(project.getProjectIdentifier());

            if(existingProject != null && (!existingProject.getProjectLeader().equals(username))){
                throw new ProjectNotFoundException("Project Not Found in Your Account.");
            }else if( existingProject == null )
                throw new ProjectNotFoundException("Project with Id : " + project.getProjectIdentifier() + " cannot be updated because it doesnt exists");
        }

        try {
            User user = userRepository.findByUsername(username);
            project.setUser(user);
            project.setProjectLeader(user.getUsername());
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());

            if(project.getId() == null){ //Create backlog only for new project. Id will be null for new Project.
                //But if it is update operation the id will not be null.
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            }

           /* When we r updating project fetch the backlog object associated with that project and pass it into project object so tht while
            updating backlog should not be null.*/
            if(project.getId() != null){
                project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
            }

            return projectRepository.save(project);
        }catch (Exception e){
            //Calling Custom Created Exception
            throw new ProjectIdException("Project Id "+ project.getProjectIdentifier().toUpperCase() + " already exists");
        }
    }

    public Project findProjectByIdentifier(String projectId, String username){
        //Only want to return the project if the user looking for it is the owner

        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if(project == null){
            throw new ProjectIdException("Project Id does not exist");
        }

        //If he is not the user who created tht project
        if(!project.getProjectLeader().equals(username)){
            throw new ProjectNotFoundException("Project Not Found in your account");
        }

        return project;
    }

    public Iterable<Project> findAllProjects(String username){
        return projectRepository.findAllByProjectLeader(username);
    }

    public void deleteProjectByIdentifier(String projectId, String username){

        projectRepository.delete(findProjectByIdentifier(projectId, username)); //No need to add in repository
    }
}


