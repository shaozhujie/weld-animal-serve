package com.project.wild.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.wild.domain.DataDepartment;
import com.project.wild.mapper.DataDepartmentMapper;
import com.project.wild.service.DataDepartmentService;
import org.springframework.stereotype.Service;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 部门service实现类
 * @date 2025/01/13 10:38
 */
@Service
public class DataDepartmentServiceImpl extends ServiceImpl<DataDepartmentMapper, DataDepartment> implements DataDepartmentService {
}
