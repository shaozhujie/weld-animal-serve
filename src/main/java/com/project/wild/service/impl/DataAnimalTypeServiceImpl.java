package com.project.wild.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.wild.domain.DataAnimalType;
import com.project.wild.mapper.DataAnimalTypeMapper;
import com.project.wild.service.DataAnimalTypeService;
import org.springframework.stereotype.Service;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 动物类型service实现类
 * @date 2025/01/07 03:46
 */
@Service
public class DataAnimalTypeServiceImpl extends ServiceImpl<DataAnimalTypeMapper, DataAnimalType> implements DataAnimalTypeService {
}
