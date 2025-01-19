package com.project.wild.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.wild.domain.DataAction;
import com.project.wild.mapper.DataActionMapper;
import com.project.wild.service.DataActionService;
import org.springframework.stereotype.Service;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 行为记录service实现类
 * @date 2025/01/14 09:07
 */
@Service
public class DataActionServiceImpl extends ServiceImpl<DataActionMapper, DataAction> implements DataActionService {
}
