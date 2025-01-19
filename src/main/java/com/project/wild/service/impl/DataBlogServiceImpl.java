package com.project.wild.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.wild.domain.DataBlog;
import com.project.wild.mapper.DataBlogMapper;
import com.project.wild.service.DataBlogService;
import org.springframework.stereotype.Service;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 新闻博客service实现类
 * @date 2025/01/09 04:50
 */
@Service
public class DataBlogServiceImpl extends ServiceImpl<DataBlogMapper, DataBlog> implements DataBlogService {
}
