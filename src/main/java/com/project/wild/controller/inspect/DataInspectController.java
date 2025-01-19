package com.project.wild.controller.inspect;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.wild.common.enums.BusinessType;
import com.project.wild.common.enums.ResultCode;
import com.project.wild.config.utils.ShiroUtils;
import com.project.wild.domain.DataInspect;
import com.project.wild.domain.Result;
import com.project.wild.service.DataInspectService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 动物体检controller
 * @date 2025/01/13 11:07
 */
@Controller
@ResponseBody
@RequestMapping("inspect")
public class DataInspectController {

    @Autowired
    private DataInspectService dataInspectService;

    /** 分页获取动物体检 */
    @PostMapping("getDataInspectPage")
    public Result getDataInspectPage(@RequestBody DataInspect dataInspect) {
        Page<DataInspect> page = new Page<>(dataInspect.getPageNumber(),dataInspect.getPageSize());
        QueryWrapper<DataInspect> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(StringUtils.isNotBlank(dataInspect.getAnimal()),DataInspect::getAnimal,dataInspect.getAnimal())
                .like(StringUtils.isNotBlank(dataInspect.getContent()),DataInspect::getContent,dataInspect.getContent())
                .eq(dataInspect.getState() != null,DataInspect::getState,dataInspect.getState())
                .eq(StringUtils.isNotBlank(dataInspect.getUserId()),DataInspect::getUserId,dataInspect.getUserId())
                .eq(StringUtils.isNotBlank(dataInspect.getCreateBy()),DataInspect::getCreateBy,dataInspect.getCreateBy());
        Page<DataInspect> dataInspectPage = dataInspectService.page(page, queryWrapper);
        return Result.success(dataInspectPage);
    }

    /** 根据id获取动物体检 */
    @GetMapping("getDataInspectById")
    public Result getDataInspectById(@RequestParam("id")String id) {
        DataInspect dataInspect = dataInspectService.getById(id);
        return Result.success(dataInspect);
    }

    /** 保存动物体检 */
    @PostMapping("saveDataInspect")
    public Result saveDataInspect(@RequestBody DataInspect dataInspect) {
        dataInspect.setUserId(ShiroUtils.getUserInfo().getId());
        boolean save = dataInspectService.save(dataInspect);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 编辑动物体检 */
    @PostMapping("editDataInspect")
    public Result editDataInspect(@RequestBody DataInspect dataInspect) {
        boolean save = dataInspectService.updateById(dataInspect);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 删除动物体检 */
    @GetMapping("removeDataInspect")
    public Result removeDataInspect(@RequestParam("ids")String ids) {
        if (StringUtils.isNotBlank(ids)) {
            String[] asList = ids.split(",");
            for (String id : asList) {
                dataInspectService.removeById(id);
            }
            return Result.success();
        } else {
            return Result.fail("动物体检id不能为空！");
        }
    }

}
