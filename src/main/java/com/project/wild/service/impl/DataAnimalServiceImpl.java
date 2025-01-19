package com.project.wild.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.wild.domain.DataAnimal;
import com.project.wild.mapper.DataAnimalMapper;
import com.project.wild.service.DataAnimalService;
import org.springframework.stereotype.Service;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 动物service实现类
 * @date 2025/01/07 08:35
 */
@Service
public class DataAnimalServiceImpl extends ServiceImpl<DataAnimalMapper, DataAnimal> implements DataAnimalService {
}
