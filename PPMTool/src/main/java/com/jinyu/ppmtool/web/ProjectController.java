package com.jinyu.ppmtool.web;

import com.jinyu.ppmtool.domain.Project;
import com.jinyu.ppmtool.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 相当于@Controller+@ResponseBody两个注解的结合，
// 返回json数据不需要在方法前面加@ResponseBody注解了，
// 但使用@RestController这个注解，就不能返回jsp,html页面，视图解析器无法解析jsp,html页面
@RestController

// 用于映射url到控制器类的一个特定处理程序方法。
// 可用于方法或者类上面。也就是可以通过url找到对应的方法。
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    // @postMapping = @requestMapping(method = RequestMethod.POST)
    @PostMapping("")
    // @RequestBody主要用来接收前端传递给后端的json字符串中的数据的(请求体中的数据的)；
    // GET方式无请求体，所以使用@RequestBody接收数据时，前端不能使用GET方式提交数据，
    // 而是用POST方式进行提交。

    // 使用POSTMAN测试！！！
    public ResponseEntity<Project> createNewProject(@RequestBody Project project){

        Project project1 = projectService.saveOrUpdateProject(project);

        // ResponseEntity可以定义返回的HttpStatus（状态码）和HttpHeaders（消息头：请求头和响应头）
        return new ResponseEntity<Project>(project, HttpStatus.CREATED);
    }
}
