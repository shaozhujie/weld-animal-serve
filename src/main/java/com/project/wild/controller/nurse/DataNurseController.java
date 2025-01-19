package com.project.wild.controller.nurse;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.wild.common.enums.BusinessType;
import com.project.wild.common.enums.ResultCode;
import com.project.wild.config.utils.ShiroUtils;
import com.project.wild.domain.DataNurse;
import com.project.wild.domain.Result;
import com.project.wild.service.DataNurseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 看护controller
 * @date 2025/01/14 03:14
 */
@Controller
@ResponseBody
@RequestMapping("nurse")
public class DataNurseController {

    @Autowired
    private DataNurseService dataNurseService;

    /** 分页获取看护 */
    @PostMapping("getDataNursePage")
    public Result getDataNursePage(@RequestBody DataNurse dataNurse) {
        Page<DataNurse> page = new Page<>(dataNurse.getPageNumber(),dataNurse.getPageSize());
        QueryWrapper<DataNurse> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(StringUtils.isNotBlank(dataNurse.getAnimal()),DataNurse::getAnimal,dataNurse.getAnimal())
                .like(StringUtils.isNotBlank(dataNurse.getContent()),DataNurse::getContent,dataNurse.getContent())
                .eq(dataNurse.getState() != null,DataNurse::getState,dataNurse.getState())
                .eq(StringUtils.isNotBlank(dataNurse.getUserId()),DataNurse::getUserId,dataNurse.getUserId())
                .like(StringUtils.isNotBlank(dataNurse.getCreateBy()),DataNurse::getCreateBy,dataNurse.getCreateBy());
        Page<DataNurse> dataNursePage = dataNurseService.page(page, queryWrapper);
        return Result.success(dataNursePage);
    }

    /** 根据id获取看护 */
    @GetMapping("getDataNurseById")
    public Result getDataNurseById(@RequestParam("id")String id) {
        DataNurse dataNurse = dataNurseService.getById(id);
        return Result.success(dataNurse);
    }

    /** 保存看护 */
    @PostMapping("saveDataNurse")
    public Result saveDataNurse(@RequestBody DataNurse dataNurse) {
        dataNurse.setUserId(ShiroUtils.getUserInfo().getId());
        boolean save = dataNurseService.save(dataNurse);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 编辑看护 */
    @PostMapping("editDataNurse")
    public Result editDataNurse(@RequestBody DataNurse dataNurse) {
        boolean save = dataNurseService.updateById(dataNurse);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 删除看护 */
    @GetMapping("removeDataNurse")
    public Result removeDataNurse(@RequestParam("ids")String ids) {
        if (StringUtils.isNotBlank(ids)) {
            String[] asList = ids.split(",");
            for (String id : asList) {
                dataNurseService.removeById(id);
            }
            return Result.success();
        } else {
            return Result.fail("看护id不能为空！");
        }
    }

}
