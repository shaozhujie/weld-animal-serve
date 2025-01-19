package com.project.wild.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.wild.domain.DataBlogType;
import com.project.wild.mapper.DataBlogTypeMapper;
import com.project.wild.service.DataBlogTypeService;
import org.springframework.stereotype.Service;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 新闻与博客分类service实现类
 * @date 2025/01/09 03:45
 */
@Service
public class DataBlogTypeServiceImpl extends ServiceImpl<DataBlogTypeMapper, DataBlogType> implements DataBlogTypeService {
}
