package com.project.wild.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.wild.domain.DataOrder;
import com.project.wild.mapper.DataOrderMapper;
import com.project.wild.service.DataOrderService;
import org.springframework.stereotype.Service;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 订单service实现类
 * @date 2025/01/09 03:57
 */
@Service
public class DataOrderServiceImpl extends ServiceImpl<DataOrderMapper, DataOrder> implements DataOrderService {
}