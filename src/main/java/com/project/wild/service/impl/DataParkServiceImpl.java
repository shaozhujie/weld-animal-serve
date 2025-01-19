package com.project.wild.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.wild.domain.DataPark;
import com.project.wild.mapper.DataParkMapper;
import com.project.wild.service.DataParkService;
import org.springframework.stereotype.Service;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 动物园区service实现类
 * @date 2025/01/08 03:27
 */
@Service
public class DataParkServiceImpl extends ServiceImpl<DataParkMapper, DataPark> implements DataParkService {
}
