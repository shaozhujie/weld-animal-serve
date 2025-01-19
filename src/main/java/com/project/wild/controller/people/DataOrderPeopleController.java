package com.project.wild.controller.people;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.wild.domain.DataOrderPeople;
import com.project.wild.domain.Result;
import com.project.wild.enums.ResultCode;
import com.project.wild.service.DataOrderPeopleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 订单预约信息controller
 * @date 2025/01/09 04:31
 */
@Controller
@ResponseBody
@RequestMapping("people")
public class DataOrderPeopleController {

    @Autowired
    private DataOrderPeopleService dataOrderPeopleService;

    /** 分页获取订单预约信息 */
    @PostMapping("getDataOrderPeoplePage")
    public Result getDataOrderPeoplePage(@RequestBody DataOrderPeople dataOrderPeople) {
        Page<DataOrderPeople> page = new Page<>(dataOrderPeople.getPageNumber(),dataOrderPeople.getPageSize());
        QueryWrapper<DataOrderPeople> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(StringUtils.isNotBlank(dataOrderPeople.getOrderId()),DataOrderPeople::getOrderId,dataOrderPeople.getOrderId())
                .like(StringUtils.isNotBlank(dataOrderPeople.getName()),DataOrderPeople::getName,dataOrderPeople.getName())
                .like(StringUtils.isNotBlank(dataOrderPeople.getIdCard()),DataOrderPeople::getIdCard,dataOrderPeople.getIdCard());
        Page<DataOrderPeople> dataOrderPeoplePage = dataOrderPeopleService.page(page, queryWrapper);
        return Result.success(dataOrderPeoplePage);
    }

    /** 根据id获取订单预约信息 */
    @GetMapping("getDataOrderPeopleById")
    public Result getDataOrderPeopleById(@RequestParam("id")String id) {
        DataOrderPeople dataOrderPeople = dataOrderPeopleService.getById(id);
        return Result.success(dataOrderPeople);
    }

    /** 保存订单预约信息 */
    @PostMapping("saveDataOrderPeople")
    public Result saveDataOrderPeople(@RequestBody DataOrderPeople dataOrderPeople) {
        boolean save = dataOrderPeopleService.save(dataOrderPeople);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 编辑订单预约信息 */
    @PostMapping("editDataOrderPeople")
    public Result editDataOrderPeople(@RequestBody DataOrderPeople dataOrderPeople) {
        boolean save = dataOrderPeopleService.updateById(dataOrderPeople);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 删除订单预约信息 */
    @GetMapping("removeDataOrderPeople")
    public Result removeDataOrderPeople(@RequestParam("ids")String ids) {
        if (StringUtils.isNotBlank(ids)) {
            String[] asList = ids.split(",");
            for (String id : asList) {
                dataOrderPeopleService.removeById(id);
            }
            return Result.success();
        } else {
            return Result.fail("订单预约信息id不能为空！");
        }
    }

}
