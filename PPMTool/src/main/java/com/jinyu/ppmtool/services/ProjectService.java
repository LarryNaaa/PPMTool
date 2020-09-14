package com.jinyu.ppmtool.services;

import com.jinyu.ppmtool.domain.Project;
import com.jinyu.ppmtool.exceptions.ProjectIdException;
import com.jinyu.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project){
        // 遇到ProjectIdentifier不unique的情况，catch error
        try{
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        }catch (Exception e){
            throw new ProjectIdException("Project ID '" +
                    project.getProjectIdentifier().toUpperCase() +
                    "' is already exist");
        }
    }


    public Project findProjectByIdentifier(String projectId){
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if(project == null){
            throw new ProjectIdException("Project ID '" +
                    projectId +
                    "' doesn't exist");
        }
        return project;
    }

    public Iterable<Project> findAllProjects(){
        return projectRepository.findAll();
    }

    public void deleteProjectByIdentifier(String projectId){
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if(project == null){
            throw new ProjectIdException("Cannot delete Project ID '" +
                    projectId +
                    "', because it doesn't exist");
        }

        projectRepository.delete(project);
    }
}
