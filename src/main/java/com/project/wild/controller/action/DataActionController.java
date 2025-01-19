package com.project.wild.controller.action;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.wild.common.enums.BusinessType;
import com.project.wild.common.enums.ResultCode;
import com.project.wild.config.utils.ShiroUtils;
import com.project.wild.domain.DataAction;
import com.project.wild.domain.Result;
import com.project.wild.service.DataActionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 行为记录controller
 * @date 2025/01/14 09:07
 */
@Controller
@ResponseBody
@RequestMapping("action")
public class DataActionController {

    @Autowired
    private DataActionService dataActionService;

    /** 分页获取行为记录 */
    @PostMapping("getDataActionPage")
    public Result getDataActionPage(@RequestBody DataAction dataAction) {
        Page<DataAction> page = new Page<>(dataAction.getPageNumber(),dataAction.getPageSize());
        QueryWrapper<DataAction> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(StringUtils.isNotBlank(dataAction.getAnimal()),DataAction::getAnimal,dataAction.getAnimal())
                .like(StringUtils.isNotBlank(dataAction.getContent()),DataAction::getContent,dataAction.getContent())
                .eq(dataAction.getState() != null,DataAction::getState,dataAction.getState())
                .eq(StringUtils.isNotBlank(dataAction.getUserId()),DataAction::getUserId,dataAction.getUserId())
                .eq(StringUtils.isNotBlank(dataAction.getCreateBy()),DataAction::getCreateBy,dataAction.getCreateBy());
        Page<DataAction> dataActionPage = dataActionService.page(page, queryWrapper);
        return Result.success(dataActionPage);
    }

    /** 根据id获取行为记录 */
    @GetMapping("getDataActionById")
    public Result getDataActionById(@RequestParam("id")String id) {
        DataAction dataAction = dataActionService.getById(id);
        return Result.success(dataAction);
    }

    /** 保存行为记录 */
    @PostMapping("saveDataAction")
    public Result saveDataAction(@RequestBody DataAction dataAction) {
        dataAction.setUserId(ShiroUtils.getUserInfo().getId());
        boolean save = dataActionService.save(dataAction);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 编辑行为记录 */
    @PostMapping("editDataAction")
    public Result editDataAction(@RequestBody DataAction dataAction) {
        boolean save = dataActionService.updateById(dataAction);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 删除行为记录 */
    @GetMapping("removeDataAction")
    public Result removeDataAction(@RequestParam("ids")String ids) {
        if (StringUtils.isNotBlank(ids)) {
            String[] asList = ids.split(",");
            for (String id : asList) {
                dataActionService.removeById(id);
            }
            return Result.success();
        } else {
            return Result.fail("行为记录id不能为空！");
        }
    }

}
