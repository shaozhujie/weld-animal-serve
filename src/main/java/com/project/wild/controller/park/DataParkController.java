package com.project.wild.controller.park;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.wild.domain.DataPark;
import com.project.wild.domain.Result;
import com.project.wild.enums.ResultCode;
import com.project.wild.service.DataParkService;
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
 * @description: 动物园区controller
 * @date 2025/01/08 03:27
 */
@Controller
@ResponseBody
@RequestMapping("park")
public class DataParkController {

    @Autowired
    private DataParkService dataParkService;

    /** 分页获取动物园区 */
    @PostMapping("getDataParkPage")
    public Result getDataParkPage(@RequestBody DataPark dataPark) {
        Page<DataPark> page = new Page<>(dataPark.getPageNumber(),dataPark.getPageSize());
        QueryWrapper<DataPark> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(StringUtils.isNotBlank(dataPark.getName()),DataPark::getName,dataPark.getName());
        Page<DataPark> dataParkPage = dataParkService.page(page, queryWrapper);
        return Result.success(dataParkPage);
    }

    @GetMapping("getDataParkList")
    public Result getDataParkList() {
        List<DataPark> list = dataParkService.list();
        return Result.success(list);
    }

    /** 根据id获取动物园区 */
    @GetMapping("getDataParkById")
    public Result getDataParkById(@RequestParam("id")String id) {
        DataPark dataPark = dataParkService.getById(id);
        return Result.success(dataPark);
    }

    /** 保存动物园区 */
    @PostMapping("saveDataPark")
    public Result saveDataPark(@RequestBody DataPark dataPark) {
        boolean save = dataParkService.save(dataPark);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 编辑动物园区 */
    @PostMapping("editDataPark")
    public Result editDataPark(@RequestBody DataPark dataPark) {
        boolean save = dataParkService.updateById(dataPark);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 删除动物园区 */
    @GetMapping("removeDataPark")
    public Result removeDataPark(@RequestParam("ids")String ids) {
        if (StringUtils.isNotBlank(ids)) {
            String[] asList = ids.split(",");
            for (String id : asList) {
                dataParkService.removeById(id);
            }
            return Result.success();
        } else {
            return Result.fail("动物园区id不能为空！");
        }
    }

}
