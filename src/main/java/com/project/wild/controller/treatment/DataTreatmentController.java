package com.project.wild.controller.treatment;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.wild.common.enums.BusinessType;
import com.project.wild.common.enums.ResultCode;
import com.project.wild.config.utils.ShiroUtils;
import com.project.wild.domain.DataTreatment;
import com.project.wild.domain.Result;
import com.project.wild.service.DataTreatmentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 诊断治疗controller
 * @date 2025/01/14 08:47
 */
@Controller
@ResponseBody
@RequestMapping("treatment")
public class DataTreatmentController {

    @Autowired
    private DataTreatmentService dataTreatmentService;

    /** 分页获取诊断治疗 */
    @PostMapping("getDataTreatmentPage")
    public Result getDataTreatmentPage(@RequestBody DataTreatment dataTreatment) {
        Page<DataTreatment> page = new Page<>(dataTreatment.getPageNumber(),dataTreatment.getPageSize());
        QueryWrapper<DataTreatment> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(StringUtils.isNotBlank(dataTreatment.getAnimal()),DataTreatment::getAnimal,dataTreatment.getAnimal())
                .like(StringUtils.isNotBlank(dataTreatment.getContent()),DataTreatment::getContent,dataTreatment.getContent())
                .eq(dataTreatment.getState() != null,DataTreatment::getState,dataTreatment.getState())
                .eq(StringUtils.isNotBlank(dataTreatment.getUserId()),DataTreatment::getUserId,dataTreatment.getUserId())
                .eq(StringUtils.isNotBlank(dataTreatment.getCreateBy()),DataTreatment::getCreateBy,dataTreatment.getCreateBy());
        Page<DataTreatment> dataTreatmentPage = dataTreatmentService.page(page, queryWrapper);
        return Result.success(dataTreatmentPage);
    }

    @GetMapping("getDataTreatmentList")
    public Result getDataTreatmentList() {
        List<DataTreatment> list = dataTreatmentService.list();
        return Result.success(list);
    }

    /** 根据id获取诊断治疗 */
    @GetMapping("getDataTreatmentById")
    public Result getDataTreatmentById(@RequestParam("id")String id) {
        DataTreatment dataTreatment = dataTreatmentService.getById(id);
        return Result.success(dataTreatment);
    }

    /** 保存诊断治疗 */
    @PostMapping("saveDataTreatment")
    public Result saveDataTreatment(@RequestBody DataTreatment dataTreatment) {
        dataTreatment.setUserId(ShiroUtils.getUserInfo().getId());
        boolean save = dataTreatmentService.save(dataTreatment);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 编辑诊断治疗 */
    @PostMapping("editDataTreatment")
    public Result editDataTreatment(@RequestBody DataTreatment dataTreatment) {
        boolean save = dataTreatmentService.updateById(dataTreatment);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 删除诊断治疗 */
    @GetMapping("removeDataTreatment")
    public Result removeDataTreatment(@RequestParam("ids")String ids) {
        if (StringUtils.isNotBlank(ids)) {
            String[] asList = ids.split(",");
            for (String id : asList) {
                dataTreatmentService.removeById(id);
            }
            return Result.success();
        } else {
            return Result.fail("诊断治疗id不能为空！");
        }
    }

}
