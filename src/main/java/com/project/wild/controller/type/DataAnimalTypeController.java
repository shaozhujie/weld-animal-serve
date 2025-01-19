package com.project.wild.controller.type;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.wild.domain.DataAnimalType;
import com.project.wild.domain.Result;
import com.project.wild.enums.ResultCode;
import com.project.wild.service.DataAnimalTypeService;
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
 * @description: 动物类型controller
 * @date 2025/01/07 03:46
 */
@Controller
@ResponseBody
@RequestMapping("type")
public class DataAnimalTypeController {

    @Autowired
    private DataAnimalTypeService dataAnimalTypeService;

    /** 分页获取动物类型 */
    @PostMapping("getDataAnimalTypePage")
    public Result getDataAnimalTypePage(@RequestBody DataAnimalType dataAnimalType) {
        Page<DataAnimalType> page = new Page<>(dataAnimalType.getPageNumber(),dataAnimalType.getPageSize());
        QueryWrapper<DataAnimalType> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(StringUtils.isNotBlank(dataAnimalType.getName()),DataAnimalType::getName,dataAnimalType.getName());
        Page<DataAnimalType> dataAnimalTypePage = dataAnimalTypeService.page(page, queryWrapper);
        return Result.success(dataAnimalTypePage);
    }

    @GetMapping("getDataAnimalTypeList")
    public Result getDataAnimalTypeList() {
        List<DataAnimalType> list = dataAnimalTypeService.list();
        return Result.success(list);
    }

    /** 根据id获取动物类型 */
    @GetMapping("getDataAnimalTypeById")
    public Result getDataAnimalTypeById(@RequestParam("id")String id) {
        DataAnimalType dataAnimalType = dataAnimalTypeService.getById(id);
        return Result.success(dataAnimalType);
    }

    /** 保存动物类型 */
    @PostMapping("saveDataAnimalType")
    public Result saveDataAnimalType(@RequestBody DataAnimalType dataAnimalType) {
        boolean save = dataAnimalTypeService.save(dataAnimalType);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 编辑动物类型 */
    @PostMapping("editDataAnimalType")
    public Result editDataAnimalType(@RequestBody DataAnimalType dataAnimalType) {
        boolean save = dataAnimalTypeService.updateById(dataAnimalType);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 删除动物类型 */
    @GetMapping("removeDataAnimalType")
    public Result removeDataAnimalType(@RequestParam("ids")String ids) {
        if (StringUtils.isNotBlank(ids)) {
            String[] asList = ids.split(",");
            for (String id : asList) {
                dataAnimalTypeService.removeById(id);
            }
            return Result.success();
        } else {
            return Result.fail("动物类型id不能为空！");
        }
    }

}
