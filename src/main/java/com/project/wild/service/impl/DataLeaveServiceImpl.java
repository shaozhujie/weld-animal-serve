package com.project.wild.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.wild.domain.DataLeave;
import com.project.wild.mapper.DataLeaveMapper;
import com.project.wild.service.DataLeaveService;
import org.springframework.stereotype.Service;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 请假service实现类
 * @date 2025/01/13 03:16
 */
@Service
public class DataLeaveServiceImpl extends ServiceImpl<DataLeaveMapper, DataLeave> implements DataLeaveService {
}
