package com.jinyu.ppmtool.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @Entity: 实体层: 存放的是实体类，属性值与数据库值保持一致，实现 setter 和 getter 方法。
 *          使用@Entity进行实体类的持久化操作，当JPA检测到我们的实体类当中有
 *          @Entity 注解的时候，会在数据库中生成对应的表结构信息。
 *          实体类是属性对象，用于供给其他层引用，该层的属性往往和数据库的表结构各个字段相同
 */
@Entity
public class Project {

    // @Id: 声明此属性为主键。该属性值可以通过应该自身创建
    @Id
    // @GeneratedValue: 指定主键的生成策略
    // IDENTITY：主键由数据库自动生成（主要是自动增长型）
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // validation 验证
    // @NotBlank：验证注解的元素值不为空（不为null、去除首位空格后长度为0），
    // 不同于@NotEmpty，@NotBlank只应用于字符串且在比较时会去除字符串的空格
    @NotBlank(message = "Project name is required")
    private String projectName;

    @NotBlank(message = "Project Identifier is required")
    // @Size: 限制字符长度必须在min到max之间
    @Size(min = 4, max = 5, message = "Please use 4 to 5 characters")
    // @Column: 用来标识实体类中属性与数据表中字段的对应关系
    @Column(updatable = false, unique = true)
    private String projectIdentifier;

    @NotBlank(message = "Project description is required")
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date start_date;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date end_date;

    @Column(updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date created_At;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updated_At;

    public Project() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectIdentifier() {
        return projectIdentifier;
    }

    public void setProjectIdentifier(String projectIdentifier) {
        this.projectIdentifier = projectIdentifier;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public Date getCreated_At() {
        return created_At;
    }

    public void setCreated_At(Date created_At) {
        this.created_At = created_At;
    }

    public Date getUpdated_At() {
        return updated_At;
    }

    public void setUpdated_At(Date updated_At) {
        this.updated_At = updated_At;
    }

    @PrePersist
    protected void onCreate(){
        this.created_At = new Date();
    }

    @PreUpdate
    protected void onUpdate(){
        this.updated_At = new Date();
    }
}
