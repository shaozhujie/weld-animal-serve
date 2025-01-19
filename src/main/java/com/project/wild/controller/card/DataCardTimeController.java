package com.project.wild.controller.card;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.wild.common.enums.ResultCode;
import com.project.wild.domain.DataCardTime;
import com.project.wild.domain.Result;
import com.project.wild.service.DataCardTimeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 考勤设置controller
 * @date 2025/01/13 11:33
 */
@Controller
@ResponseBody
@RequestMapping("time")
public class DataCardTimeController {

    @Autowired
    private DataCardTimeService dataCardTimeService;

    /** 分页获取考勤设置 */
    @PostMapping("getDataCardTimePage")
    public Result getDataCardTimePage(@RequestBody DataCardTime dataCardTime) {
        Page<DataCardTime> page = new Page<>(dataCardTime.getPageNumber(),dataCardTime.getPageSize());
        QueryWrapper<DataCardTime> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(StringUtils.isNotBlank(dataCardTime.getStartTime()),DataCardTime::getStartTime,dataCardTime.getStartTime())
                .eq(StringUtils.isNotBlank(dataCardTime.getEndTime()),DataCardTime::getEndTime,dataCardTime.getEndTime());
        Page<DataCardTime> dataCardTimePage = dataCardTimeService.page(page, queryWrapper);
        return Result.success(dataCardTimePage);
    }

    /** 根据id获取考勤设置 */
    @GetMapping("getDataCardTimeById")
    public Result getDataCardTimeById(@RequestParam("id")String id) {
        DataCardTime dataCardTime = dataCardTimeService.getById(id);
        return Result.success(dataCardTime);
    }

    /** 保存考勤设置 */
    @PostMapping("saveDataCardTime")
    public Result saveDataCardTime(@RequestBody DataCardTime dataCardTime) {
        boolean save = dataCardTimeService.save(dataCardTime);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 编辑考勤设置 */
    @PostMapping("editDataCardTime")
    public Result editDataCardTime(@RequestBody DataCardTime dataCardTime) {
        boolean save = dataCardTimeService.updateById(dataCardTime);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 删除考勤设置 */
    @GetMapping("removeDataCardTime")
    public Result removeDataCardTime(@RequestParam("ids")String ids) {
        if (StringUtils.isNotBlank(ids)) {
            String[] asList = ids.split(",");
            for (String id : asList) {
                dataCardTimeService.removeById(id);
            }
            return Result.success();
        } else {
            return Result.fail("考勤设置id不能为空！");
        }
    }

}
