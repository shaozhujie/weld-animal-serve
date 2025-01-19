package com.project.wild.controller.animal;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.wild.domain.DataAnimal;
import com.project.wild.domain.Result;
import com.project.wild.enums.ResultCode;
import com.project.wild.service.DataAnimalService;
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
 * @description: 动物controller
 * @date 2025/01/07 08:35
 */
@Controller
@ResponseBody
@RequestMapping("animal")
public class DataAnimalController {

    @Autowired
    private DataAnimalService dataAnimalService;

    /** 分页获取动物 */
    @PostMapping("getDataAnimalPage")
    public Result getDataAnimalPage(@RequestBody DataAnimal dataAnimal) {
        Page<DataAnimal> page = new Page<>(dataAnimal.getPageNumber(),dataAnimal.getPageSize());
        QueryWrapper<DataAnimal> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(dataAnimal.getState() != null,DataAnimal::getState,dataAnimal.getState())
                .like(StringUtils.isNotBlank(dataAnimal.getType()),DataAnimal::getType,dataAnimal.getType())
                .like(StringUtils.isNotBlank(dataAnimal.getPark()),DataAnimal::getPark,dataAnimal.getPark())
                .like(StringUtils.isNotBlank(dataAnimal.getName()),DataAnimal::getName,dataAnimal.getName())
                .like(StringUtils.isNotBlank(dataAnimal.getRealName()),DataAnimal::getRealName,dataAnimal.getRealName())
                .like(StringUtils.isNotBlank(dataAnimal.getAddress()),DataAnimal::getAddress,dataAnimal.getAddress());
        Page<DataAnimal> dataAnimalPage = dataAnimalService.page(page, queryWrapper);
        return Result.success(dataAnimalPage);
    }

    @GetMapping("getDataAnimalList")
    public Result getDataAnimalList() {
        List<DataAnimal> list = dataAnimalService.list();
        return Result.success(list);
    }

    @GetMapping("getAnimalIndexList")
    public Result getAnimalIndexList() {
        QueryWrapper<DataAnimal> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(DataAnimal::getState,0).orderByDesc(DataAnimal::getCreateTime).last("limit 8");
        List<DataAnimal> animalList = dataAnimalService.list(queryWrapper);
        return Result.success(animalList);
    }

    /** 根据id获取动物 */
    @GetMapping("getDataAnimalById")
    public Result getDataAnimalById(@RequestParam("id")String id) {
        DataAnimal dataAnimal = dataAnimalService.getById(id);
        return Result.success(dataAnimal);
    }

    @GetMapping("getDataAnimalOther")
    public Result getDataAnimalOther(@RequestParam("id")String id) {
        QueryWrapper<DataAnimal> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().ne(DataAnimal::getId,id).last("limit 4");
        List<DataAnimal> animalList = dataAnimalService.list(queryWrapper);
        return Result.success(animalList);
    }

    /** 保存动物 */
    @PostMapping("saveDataAnimal")
    public Result saveDataAnimal(@RequestBody DataAnimal dataAnimal) {
        boolean save = dataAnimalService.save(dataAnimal);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 编辑动物 */
    @PostMapping("editDataAnimal")
    public Result editDataAnimal(@RequestBody DataAnimal dataAnimal) {
        boolean save = dataAnimalService.updateById(dataAnimal);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 删除动物 */
    @GetMapping("removeDataAnimal")
    public Result removeDataAnimal(@RequestParam("ids")String ids) {
        if (StringUtils.isNotBlank(ids)) {
            String[] asList = ids.split(",");
            for (String id : asList) {
                dataAnimalService.removeById(id);
            }
            return Result.success();
        } else {
            return Result.fail("动物id不能为空！");
        }
    }

}
