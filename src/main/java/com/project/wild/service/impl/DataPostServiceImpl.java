package com.project.wild.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.wild.domain.DataPost;
import com.project.wild.mapper.DataPostMapper;
import com.project.wild.service.DataPostService;
import org.springframework.stereotype.Service;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 岗位service实现类
 * @date 2025/01/13 10:21
 */
@Service
public class DataPostServiceImpl extends ServiceImpl<DataPostMapper, DataPost> implements DataPostService {
}
