package com.project.wild.controller.rotation;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.wild.domain.DataRotation;
import com.project.wild.domain.Result;
import com.project.wild.enums.ResultCode;
import com.project.wild.service.DataRotationService;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 轮播图controller
 * @date 2025/01/07 02:50
 */
@Controller
@ResponseBody
@RequestMapping("rotation")
public class DataRotationController {

    @Autowired
    private DataRotationService dataRotationService;

    /** 分页获取轮播图 */
    @PostMapping("getDataRotationPage")
    public Result getDataRotationPage(@RequestBody DataRotation dataRotation) {
        Page<DataRotation> page = new Page<>(dataRotation.getPageNumber(),dataRotation.getPageSize());
        QueryWrapper<DataRotation> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(StringUtils.isNotBlank(dataRotation.getImage()),DataRotation::getImage,dataRotation.getImage());
        Page<DataRotation> dataRotationPage = dataRotationService.page(page, queryWrapper);
        return Result.success(dataRotationPage);
    }

    @GetMapping("getDataRotationList")
    public Result getDataRotationList() {
        List<DataRotation> list = dataRotationService.list();
        return Result.success(list);
    }

    /** 根据id获取轮播图 */
    @GetMapping("getDataRotationById")
    public Result getDataRotationById(@RequestParam("id")String id) {
        DataRotation dataRotation = dataRotationService.getById(id);
        return Result.success(dataRotation);
    }

    /** 保存轮播图 */
    @PostMapping("saveDataRotation")
    public Result saveDataRotation(@RequestBody DataRotation dataRotation) {
        boolean save = dataRotationService.save(dataRotation);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 编辑轮播图 */
    @PostMapping("editDataRotation")
    public Result editDataRotation(@RequestBody DataRotation dataRotation) {
        boolean save = dataRotationService.updateById(dataRotation);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 删除轮播图 */
    @GetMapping("removeDataRotation")
    public Result removeDataRotation(@RequestParam("ids")String ids) {
        if (StringUtils.isNotBlank(ids)) {
            String[] asList = ids.split(",");
            for (String id : asList) {
                dataRotationService.removeById(id);
            }
            return Result.success();
        } else {
            return Result.fail("轮播图id不能为空！");
        }
    }

}
