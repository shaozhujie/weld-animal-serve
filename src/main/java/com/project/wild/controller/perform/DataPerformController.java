package com.project.wild.controller.perform;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.wild.domain.DataPerform;
import com.project.wild.domain.Result;
import com.project.wild.enums.ResultCode;
import com.project.wild.service.DataPerformService;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 展演信息controller
 * @date 2025/01/09 01:51
 */
@Controller
@ResponseBody
@RequestMapping("perform")
public class DataPerformController {

    @Autowired
    private DataPerformService dataPerformService;

    /** 分页获取展演信息 */
    @PostMapping("getDataPerformPage")
    public Result getDataPerformPage(@RequestBody DataPerform dataPerform) {
        Page<DataPerform> page = new Page<>(dataPerform.getPageNumber(),dataPerform.getPageSize());
        QueryWrapper<DataPerform> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(StringUtils.isNotBlank(dataPerform.getContent()),DataPerform::getContent,dataPerform.getContent())
                .like(StringUtils.isNotBlank(dataPerform.getTitle()),DataPerform::getTitle,dataPerform.getTitle())
                .eq(dataPerform.getStartTime() != null,DataPerform::getStartTime,dataPerform.getStartTime())
                .like(StringUtils.isNotBlank(dataPerform.getPark()),DataPerform::getPark,dataPerform.getPark())
                .like(StringUtils.isNotBlank(dataPerform.getIntroduce()),DataPerform::getIntroduce,dataPerform.getIntroduce());
        Page<DataPerform> dataPerformPage = dataPerformService.page(page, queryWrapper);
        return Result.success(dataPerformPage);
    }

    /** 根据id获取展演信息 */
    @GetMapping("getDataPerformById")
    public Result getDataPerformById(@RequestParam("id")String id) {
        DataPerform dataPerform = dataPerformService.getById(id);
        return Result.success(dataPerform);
    }

    /** 保存展演信息 */
    @PostMapping("saveDataPerform")
    public Result saveDataPerform(@RequestBody DataPerform dataPerform) {
        boolean save = dataPerformService.save(dataPerform);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 编辑展演信息 */
    @PostMapping("editDataPerform")
    public Result editDataPerform(@RequestBody DataPerform dataPerform) {
        boolean save = dataPerformService.updateById(dataPerform);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 删除展演信息 */
    @GetMapping("removeDataPerform")
    public Result removeDataPerform(@RequestParam("ids")String ids) {
        if (StringUtils.isNotBlank(ids)) {
            String[] asList = ids.split(",");
            for (String id : asList) {
                dataPerformService.removeById(id);
            }
            return Result.success();
        } else {
            return Result.fail("展演信息id不能为空！");
        }
    }

}
