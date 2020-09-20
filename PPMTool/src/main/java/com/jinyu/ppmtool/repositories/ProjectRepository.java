package com.jinyu.ppmtool.repositories;

import com.jinyu.ppmtool.domain.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @Repository 数据访问层: 对数据库进行持久化操作，他的方法使针对数据库操作的，基本上用的就是增删改查
 * 表明这个类具有对对象进行CRUD（增删改查）的功能
 * 居于业务层和数据层之间，将两者隔开，在内部封装数据查询和存储的逻辑
 * 定义的接口要实现对数据的操作
 * CrudRepository: 基本的CRUD操作
 */
@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {
    @Override
    Iterable<Project> findAllById(Iterable<Long> iterable);

    Project findByProjectIdentifier(String projectId);

    @Override
    Iterable<Project> findAll();

    Iterable<Project> findAllByProjectLeader(String username);
}
