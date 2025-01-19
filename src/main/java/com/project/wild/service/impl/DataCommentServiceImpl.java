package com.project.wild.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.wild.domain.DataComment;
import com.project.wild.mapper.DataCommentMapper;
import com.project.wild.service.DataCommentService;
import org.springframework.stereotype.Service;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 评论service实现类
 * @date 2025/01/15 01:56
 */
@Service
public class DataCommentServiceImpl extends ServiceImpl<DataCommentMapper, DataComment> implements DataCommentService {
}
