package com.project.wild.controller.leave;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.wild.common.enums.BusinessType;
import com.project.wild.common.enums.ResultCode;
import com.project.wild.config.utils.ShiroUtils;
import com.project.wild.domain.DataLeave;
import com.project.wild.domain.Result;
import com.project.wild.service.DataLeaveService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 请假controller
 * @date 2025/01/13 03:16
 */
@Controller
@ResponseBody
@RequestMapping("leave")
public class DataLeaveController {

    @Autowired
    private DataLeaveService dataLeaveService;

    /** 分页获取请假 */
    @PostMapping("getDataLeavePage")
    public Result getDataLeavePage(@RequestBody DataLeave dataLeave) {
        Page<DataLeave> page = new Page<>(dataLeave.getPageNumber(),dataLeave.getPageSize());
        QueryWrapper<DataLeave> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(StringUtils.isNotBlank(dataLeave.getUserId()),DataLeave::getUserId,dataLeave.getUserId())
                .like(StringUtils.isNotBlank(dataLeave.getName()),DataLeave::getName,dataLeave.getName())
                .eq(dataLeave.getLeaveTime() != null,DataLeave::getLeaveTime,dataLeave.getLeaveTime())
                .like(StringUtils.isNotBlank(dataLeave.getContent()),DataLeave::getContent,dataLeave.getContent())
                .eq(dataLeave.getState() != null,DataLeave::getState,dataLeave.getState());
        Page<DataLeave> dataLeavePage = dataLeaveService.page(page, queryWrapper);
        return Result.success(dataLeavePage);
    }

    /** 根据id获取请假 */
    @GetMapping("getDataLeaveById")
    public Result getDataLeaveById(@RequestParam("id")String id) {
        DataLeave dataLeave = dataLeaveService.getById(id);
        return Result.success(dataLeave);
    }

    /** 保存请假 */
    @PostMapping("saveDataLeave")
    public Result saveDataLeave(@RequestBody DataLeave dataLeave) {
        dataLeave.setUserId(ShiroUtils.getUserInfo().getId());
        boolean save = dataLeaveService.save(dataLeave);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 编辑请假 */
    @PostMapping("editDataLeave")
    public Result editDataLeave(@RequestBody DataLeave dataLeave) {
        boolean save = dataLeaveService.updateById(dataLeave);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 删除请假 */
    @GetMapping("removeDataLeave")
    public Result removeDataLeave(@RequestParam("ids")String ids) {
        if (StringUtils.isNotBlank(ids)) {
            String[] asList = ids.split(",");
            for (String id : asList) {
                dataLeaveService.removeById(id);
            }
            return Result.success();
        } else {
            return Result.fail("请假id不能为空！");
        }
    }

}
