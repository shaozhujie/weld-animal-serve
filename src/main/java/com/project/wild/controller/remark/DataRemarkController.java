package com.project.wild.controller.remark;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.wild.common.enums.BusinessType;
import com.project.wild.common.enums.ResultCode;
import com.project.wild.domain.DataRemark;
import com.project.wild.domain.Result;
import com.project.wild.service.DataRemarkService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 系统说明controller
 * @date 2025/01/14 11:21
 */
@Controller
@ResponseBody
@RequestMapping("remark")
public class DataRemarkController {

    @Autowired
    private DataRemarkService dataRemarkService;

    /** 分页获取系统说明 */
    @PostMapping("getDataRemarkPage")
    public Result getDataRemarkPage(@RequestBody DataRemark dataRemark) {
        Page<DataRemark> page = new Page<>(dataRemark.getPageNumber(),dataRemark.getPageSize());
        QueryWrapper<DataRemark> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(StringUtils.isNotBlank(dataRemark.getTel()),DataRemark::getTel,dataRemark.getTel())
                .eq(StringUtils.isNotBlank(dataRemark.getName()),DataRemark::getName,dataRemark.getName())
                .eq(StringUtils.isNotBlank(dataRemark.getEmail()),DataRemark::getEmail,dataRemark.getEmail())
                .eq(StringUtils.isNotBlank(dataRemark.getOpen()),DataRemark::getOpen,dataRemark.getOpen())
                .eq(StringUtils.isNotBlank(dataRemark.getOpenTime()),DataRemark::getOpenTime,dataRemark.getOpenTime())
                .eq(StringUtils.isNotBlank(dataRemark.getReadyClose()),DataRemark::getReadyClose,dataRemark.getReadyClose())
                .eq(StringUtils.isNotBlank(dataRemark.getCloseTime()),DataRemark::getCloseTime,dataRemark.getCloseTime())
                .eq(StringUtils.isNotBlank(dataRemark.getClose()),DataRemark::getClose,dataRemark.getClose())
                .eq(StringUtils.isNotBlank(dataRemark.getAddress()),DataRemark::getAddress,dataRemark.getAddress());
        Page<DataRemark> dataRemarkPage = dataRemarkService.page(page, queryWrapper);
        return Result.success(dataRemarkPage);
    }

    /** 根据id获取系统说明 */
    @GetMapping("getDataRemarkById")
    public Result getDataRemarkById(@RequestParam("id")String id) {
        DataRemark dataRemark = dataRemarkService.getById(id);
        return Result.success(dataRemark);
    }

    @GetMapping("getDataRemarkOne")
    public Result getDataRemarkOne() {
        DataRemark one = dataRemarkService.getOne(null);
        return Result.success(one);
    }

    /** 保存系统说明 */
    @PostMapping("saveDataRemark")
    public Result saveDataRemark(@RequestBody DataRemark dataRemark) {
        boolean save = dataRemarkService.save(dataRemark);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 编辑系统说明 */
    @PostMapping("editDataRemark")
    public Result editDataRemark(@RequestBody DataRemark dataRemark) {
        boolean save = dataRemarkService.updateById(dataRemark);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 删除系统说明 */
    @GetMapping("removeDataRemark")
    public Result removeDataRemark(@RequestParam("ids")String ids) {
        if (StringUtils.isNotBlank(ids)) {
            String[] asList = ids.split(",");
            for (String id : asList) {
                dataRemarkService.removeById(id);
            }
            return Result.success();
        } else {
            return Result.fail("系统说明id不能为空！");
        }
    }

}
