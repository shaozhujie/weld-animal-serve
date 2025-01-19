package com.project.wild.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.wild.domain.OperateLog;
import com.project.wild.mapper.OperateLogMapper;
import com.project.wild.service.OperateLogService;
import org.springframework.stereotype.Service;

/**
 * @author shaozhujie
 * @version 1.0
 * @description: 操作日志service实现类
 * @date 2023/9/22 9:55
 */
@Service
public class OperateLogServiceImpl extends ServiceImpl<OperateLogMapper, OperateLog> implements OperateLogService {
}
