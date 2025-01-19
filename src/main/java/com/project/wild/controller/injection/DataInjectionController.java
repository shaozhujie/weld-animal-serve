package com.project.wild.controller.injection;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.wild.common.enums.BusinessType;
import com.project.wild.common.enums.ResultCode;
import com.project.wild.config.utils.ShiroUtils;
import com.project.wild.domain.DataInjection;
import com.project.wild.domain.Result;
import com.project.wild.service.DataInjectionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 疫苗注射controller
 * @date 2025/01/14 09:52
 */
@Controller
@ResponseBody
@RequestMapping("injection")
public class DataInjectionController {

    @Autowired
    private DataInjectionService dataInjectionService;

    /** 分页获取疫苗注射 */
    @PostMapping("getDataInjectionPage")
    public Result getDataInjectionPage(@RequestBody DataInjection dataInjection) {
        Page<DataInjection> page = new Page<>(dataInjection.getPageNumber(),dataInjection.getPageSize());
        QueryWrapper<DataInjection> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(StringUtils.isNotBlank(dataInjection.getAnimal()),DataInjection::getAnimal,dataInjection.getAnimal())
                .like(StringUtils.isNotBlank(dataInjection.getContent()),DataInjection::getContent,dataInjection.getContent())
                .eq(dataInjection.getState() != null,DataInjection::getState,dataInjection.getState())
                .eq(StringUtils.isNotBlank(dataInjection.getUserId()),DataInjection::getUserId,dataInjection.getUserId())
                .eq(StringUtils.isNotBlank(dataInjection.getCreateBy()),DataInjection::getCreateBy,dataInjection.getCreateBy());
        Page<DataInjection> dataInjectionPage = dataInjectionService.page(page, queryWrapper);
        return Result.success(dataInjectionPage);
    }

    /** 根据id获取疫苗注射 */
    @GetMapping("getDataInjectionById")
    public Result getDataInjectionById(@RequestParam("id")String id) {
        DataInjection dataInjection = dataInjectionService.getById(id);
        return Result.success(dataInjection);
    }

    /** 保存疫苗注射 */
    @PostMapping("saveDataInjection")
    public Result saveDataInjection(@RequestBody DataInjection dataInjection) {
        dataInjection.setUserId(ShiroUtils.getUserInfo().getId());
        boolean save = dataInjectionService.save(dataInjection);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 编辑疫苗注射 */
    @PostMapping("editDataInjection")
    public Result editDataInjection(@RequestBody DataInjection dataInjection) {
        boolean save = dataInjectionService.updateById(dataInjection);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 删除疫苗注射 */
    @GetMapping("removeDataInjection")
    public Result removeDataInjection(@RequestParam("ids")String ids) {
        if (StringUtils.isNotBlank(ids)) {
            String[] asList = ids.split(",");
            for (String id : asList) {
                dataInjectionService.removeById(id);
            }
            return Result.success();
        } else {
            return Result.fail("疫苗注射id不能为空！");
        }
    }

}
