package com.project.wild.controller.order;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.wild.config.utils.ShiroUtils;
import com.project.wild.domain.*;
import com.project.wild.enums.ResultCode;
import com.project.wild.service.DataOrderPeopleService;
import com.project.wild.service.DataOrderService;
import com.project.wild.service.DataTicketService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 订单controller
 * @date 2025/01/09 03:57
 */
@Controller
@ResponseBody
@RequestMapping("order")
public class DataOrderController {

    @Autowired
    private DataOrderService dataOrderService;
    @Autowired
    private DataOrderPeopleService dataOrderPeopleService;
    @Autowired
    private DataTicketService dataTicketService;

    /** 分页获取订单 */
    @PostMapping("getDataOrderPage")
    public Result getDataOrderPage(@RequestBody DataOrder dataOrder) {
        Page<DataOrder> page = new Page<>(dataOrder.getPageNumber(),dataOrder.getPageSize());
        QueryWrapper<DataOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(StringUtils.isNotBlank(dataOrder.getTicketId()),DataOrder::getTicketId,dataOrder.getTicketId())
                .eq(StringUtils.isNotBlank(dataOrder.getUserId()),DataOrder::getUserId,dataOrder.getUserId())
                .eq(StringUtils.isNotBlank(dataOrder.getName()),DataOrder::getName,dataOrder.getName())
                .eq(dataOrder.getState() != null,DataOrder::getState,dataOrder.getState())
                .like(StringUtils.isNotBlank(dataOrder.getRealName()),DataOrder::getRealName,dataOrder.getRealName())
                .like(StringUtils.isNotBlank(dataOrder.getTel()),DataOrder::getTel,dataOrder.getTel())
                .like(StringUtils.isNotBlank(dataOrder.getCreateBy()),DataOrder::getCreateBy,dataOrder.getCreateBy());
        Page<DataOrder> dataOrderPage = dataOrderService.page(page, queryWrapper);
        return Result.success(dataOrderPage);
    }

    @PostMapping("getOrderMoney")
    public Result getOrderMoney(@RequestBody JSONObject jsonObject) {
        String startTime = jsonObject.getString("startTime");
        String endTime = jsonObject.getString("endTime");
        QueryWrapper<DataOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().ge(StringUtils.isNotBlank(startTime),DataOrder::getCreateTime,startTime)
                .le(StringUtils.isNotBlank(endTime),DataOrder::getCreateTime,endTime);
        List<DataOrder> orderList = dataOrderService.list(queryWrapper);
        JSONObject json = new JSONObject();
        json.put("count",orderList.size());
        Integer refund = 0;
        Integer use = 0;
        Integer other = 0;
        float money = 0;
        for (DataOrder order : orderList) {
            if (order.getState() == 1) {
                refund ++;
            }
            if (order.getState() == 2) {
                use ++;
                money += order.getPrice();
            }
            if (order.getState() == 0) {
                other ++;
                money += order.getPrice();
            }
        }
        json.put("refund",refund);
        json.put("use",use);
        json.put("other",other);
        json.put("money",money);
        return Result.success(json);
    }

    @GetMapping("getUserOrder")
    public Result getUserOrder() {
        User userInfo = ShiroUtils.getUserInfo();
        QueryWrapper<DataOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(DataOrder::getUserId,userInfo.getId()).orderByDesc(DataOrder::getCreateTime);
        List<DataOrder> orderList = dataOrderService.list(queryWrapper);
        for (DataOrder order : orderList) {
            QueryWrapper<DataOrderPeople> wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(DataOrderPeople::getOrderId,order.getId());
            List<DataOrderPeople> peopleList = dataOrderPeopleService.list(wrapper);
            order.setOrderPeople(peopleList);
        }
        return Result.success(orderList);
    }

    /** 根据id获取订单 */
    @GetMapping("getDataOrderById")
    public Result getDataOrderById(@RequestParam("id")String id) {
        DataOrder dataOrder = dataOrderService.getById(id);
        return Result.success(dataOrder);
    }

    /** 保存订单 */
    @PostMapping("saveDataOrder")
    @Transactional(rollbackFor = Exception.class)
    public Result saveDataOrder(@RequestBody DataOrder dataOrder) {
        User userInfo = ShiroUtils.getUserInfo();
        dataOrder.setUserId(userInfo.getId());
        DataTicket ticket = dataTicketService.getById(dataOrder.getTicketId());
        //判断库存
        if (ticket.getNum() - 1 < 0) {
            return Result.fail("库存不足,无法预约");
        }
        if (!(ticket.getStartTime().before(dataOrder.getUseTime()) && ticket.getEndTime().after(dataOrder.getUseTime()))) {
            return Result.fail("预约日期不可用");
        }
        ticket.setNum(ticket.getNum() - 1);
        ticket.setSale(ticket.getSale() + 1);
        dataTicketService.updateById(ticket);
        dataOrder.setName(ticket.getName());
        dataOrder.setPrice(ticket.getPrice());
        dataOrder.setImage(ticket.getImage());
        String idStr = IdWorker.getIdStr();
        dataOrder.setId(idStr);
        boolean save = dataOrderService.save(dataOrder);
        for (DataOrderPeople dataOrderPeople : dataOrder.getOrderPeople()) {
            dataOrderPeople.setOrderId(idStr);
        }
        dataOrderPeopleService.saveBatch(dataOrder.getOrderPeople());
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 编辑订单 */
    @PostMapping("editDataOrder")
    public Result editDataOrder(@RequestBody DataOrder dataOrder) {
        DataOrder order = dataOrderService.getById(dataOrder.getId());
        DataTicket ticket = dataTicketService.getById(order.getTicketId());
        if (!(ticket.getStartTime().before(order.getUseTime()) && ticket.getEndTime().after(order.getUseTime()))) {
            return Result.fail("预约日期不可用");
        }
        boolean save = dataOrderService.updateById(dataOrder);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 删除订单 */
    @GetMapping("removeDataOrder")
    @Transactional(rollbackFor = Exception.class)
    public Result removeDataOrder(@RequestParam("ids")String ids) {
        if (StringUtils.isNotBlank(ids)) {
            String[] asList = ids.split(",");
            for (String id : asList) {
                dataOrderService.removeById(id);
                QueryWrapper<DataOrderPeople> queryWrapper = new QueryWrapper<>();
                queryWrapper.lambda().eq(DataOrderPeople::getOrderId,id);
                dataOrderPeopleService.remove(queryWrapper);
            }
            return Result.success();
        } else {
            return Result.fail("订单id不能为空！");
        }
    }

}
