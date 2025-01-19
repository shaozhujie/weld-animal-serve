package com.project.wild.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.wild.domain.LoginLog;
import com.project.wild.mapper.LoginLogMapper;
import com.project.wild.service.LoginLogService;
import org.springframework.stereotype.Service;

/**
 * @author shaozhujie
 * @version 1.0
 * @description: 登陆日志service实现类
 * @date 2023/9/23 8:52
 */
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements LoginLogService {

}
