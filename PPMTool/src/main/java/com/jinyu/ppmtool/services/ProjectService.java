package com.jinyu.ppmtool.services;

import com.jinyu.ppmtool.domain.Backlog;
import com.jinyu.ppmtool.domain.Project;
import com.jinyu.ppmtool.domain.User;
import com.jinyu.ppmtool.exceptions.ProjectIdException;
import com.jinyu.ppmtool.exceptions.ProjectNotFoundException;
import com.jinyu.ppmtool.repositories.BacklogRepository;
import com.jinyu.ppmtool.repositories.ProjectRepository;
import com.jinyu.ppmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Service 业务逻辑层: 存放业务逻辑处理，不直接对数据库进行操作，有接口和接口实现类，提供 controller 层调用方法。
 */
@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private UserRepository userRepository;

    public Project saveOrUpdateProject(Project project, String username){

        if(project.getId() != null){
            Project existingProject = projectRepository.findByProjectIdentifier(project.getProjectIdentifier());
            if(existingProject !=null &&(!existingProject.getProjectLeader().equals(username))){
                throw new ProjectNotFoundException("Project not found in your account");
            }else if(existingProject == null){
                throw new ProjectNotFoundException("Project with ID: '"+project.getProjectIdentifier()+"' cannot be updated because it doesn't exist");
            }
        }

        // 遇到ProjectIdentifier不unique的情况，catch error
        try{
            User user = userRepository.findByUsername(username);
            project.setUser(user);
            project.setProjectLeader(user.getUsername());

            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());

            if(project.getId()==null){
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            }

            if(project.getId()!=null){
                project.setBacklog(backlogRepository.findByProjectIdentifier(
                        project.getProjectIdentifier().toUpperCase()));
            }

            return projectRepository.save(project);
        }catch (Exception e){
            throw new ProjectIdException("Project ID '" +
                    project.getProjectIdentifier().toUpperCase() +
                    "' is already exist");
        }
    }


    public Project findProjectByIdentifier(String projectId, String username){
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if(project == null){
            throw new ProjectIdException("Project ID '" +
                    projectId +
                    "' doesn't exist");
        }

        if(!project.getProjectLeader().equals(username)){
            throw new ProjectNotFoundException("Project not found in your account");
        }

        return project;
    }

    public Iterable<Project> findAllProjects(String username){
        return projectRepository.findAllByProjectLeader(username);
    }

    public void deleteProjectByIdentifier(String projectId, String username){
        projectRepository.delete(findProjectByIdentifier(projectId, username));
    }
}
