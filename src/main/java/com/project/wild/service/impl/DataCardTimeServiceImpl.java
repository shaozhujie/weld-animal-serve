package com.project.wild.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.wild.domain.DataCardTime;
import com.project.wild.mapper.DataCardTimeMapper;
import com.project.wild.service.DataCardTimeService;
import org.springframework.stereotype.Service;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 考勤设置service实现类
 * @date 2025/01/13 11:33
 */
@Service
public class DataCardTimeServiceImpl extends ServiceImpl<DataCardTimeMapper, DataCardTime> implements DataCardTimeService {
}
