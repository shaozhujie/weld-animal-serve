package com.project.wild.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.wild.domain.DataCard;
import com.project.wild.mapper.DataCardMapper;
import com.project.wild.service.DataCardService;
import org.springframework.stereotype.Service;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 考勤service实现类
 * @date 2025/01/13 11:43
 */
@Service
public class DataCardServiceImpl extends ServiceImpl<DataCardMapper, DataCard> implements DataCardService {
}
