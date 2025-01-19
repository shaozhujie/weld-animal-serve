package com.project.wild.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.wild.domain.DataTicket;
import com.project.wild.mapper.DataTicketMapper;
import com.project.wild.service.DataTicketService;
import org.springframework.stereotype.Service;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 门票service实现类
 * @date 2025/01/09 02:20
 */
@Service
public class DataTicketServiceImpl extends ServiceImpl<DataTicketMapper, DataTicket> implements DataTicketService {
}