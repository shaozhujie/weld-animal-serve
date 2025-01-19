package com.project.wild.controller.ticket;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.wild.domain.DataTicket;
import com.project.wild.domain.Result;
import com.project.wild.enums.ResultCode;
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
 * @description: 门票controller
 * @date 2025/01/09 02:20
 */
@Controller
@ResponseBody
@RequestMapping("ticket")
public class DataTicketController {

    @Autowired
    private DataTicketService dataTicketService;

    /** 分页获取门票 */
    @PostMapping("getDataTicketPage")
    public Result getDataTicketPage(@RequestBody DataTicket dataTicket) {
        Page<DataTicket> page = new Page<>(dataTicket.getPageNumber(),dataTicket.getPageSize());
        QueryWrapper<DataTicket> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(StringUtils.isNotBlank(dataTicket.getName()),DataTicket::getName,dataTicket.getName());
        Page<DataTicket> dataTicketPage = dataTicketService.page(page, queryWrapper);
        return Result.success(dataTicketPage);
    }

    @GetMapping("getTicketOne")
    public Result getTicketOne() {
        QueryWrapper<DataTicket> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().orderByDesc(DataTicket::getCreateTime).last("limit 1");
        DataTicket ticket = dataTicketService.getOne(queryWrapper);
        return Result.success(ticket);
    }

    /** 根据id获取门票 */
    @GetMapping("getDataTicketById")
    public Result getDataTicketById(@RequestParam("id")String id) {
        DataTicket dataTicket = dataTicketService.getById(id);
        return Result.success(dataTicket);
    }

    @GetMapping("getOtherTicket")
    public Result getOtherTicket(@RequestParam("id")String id) {
        if (StringUtils.isNotBlank(id)) {
            QueryWrapper<DataTicket> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().ne(DataTicket::getId,id);
            List<DataTicket> ticketList = dataTicketService.list(queryWrapper);
            return Result.success(ticketList);
        } else {
            List<DataTicket> ticketList = dataTicketService.list();
            return Result.success(ticketList);
        }
    }

    /** 保存门票 */
    @PostMapping("saveDataTicket")
    public Result saveDataTicket(@RequestBody DataTicket dataTicket) {
        boolean save = dataTicketService.save(dataTicket);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 编辑门票 */
    @PostMapping("editDataTicket")
    public Result editDataTicket(@RequestBody DataTicket dataTicket) {
        boolean save = dataTicketService.updateById(dataTicket);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 删除门票 */
    @GetMapping("removeDataTicket")
    public Result removeDataTicket(@RequestParam("ids")String ids) {
        if (StringUtils.isNotBlank(ids)) {
            String[] asList = ids.split(",");
            for (String id : asList) {
                dataTicketService.removeById(id);
            }
            return Result.success();
        } else {
            return Result.fail("门票id不能为空！");
        }
    }

}
