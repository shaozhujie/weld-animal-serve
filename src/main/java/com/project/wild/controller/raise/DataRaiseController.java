package com.project.wild.controller.raise;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.wild.common.enums.BusinessType;
import com.project.wild.common.enums.ResultCode;
import com.project.wild.config.utils.ShiroUtils;
import com.project.wild.domain.DataRaise;
import com.project.wild.domain.Result;
import com.project.wild.domain.User;
import com.project.wild.service.DataRaiseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 饲养记录controller
 * @date 2025/01/13 10:38
 */
@Controller
@ResponseBody
@RequestMapping("raise")
public class DataRaiseController {

    @Autowired
    private DataRaiseService dataRaiseService;

    /** 分页获取饲养记录 */
    @PostMapping("getDataRaisePage")
    public Result getDataRaisePage(@RequestBody DataRaise dataRaise) {
        Page<DataRaise> page = new Page<>(dataRaise.getPageNumber(),dataRaise.getPageSize());
        QueryWrapper<DataRaise> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(StringUtils.isNotBlank(dataRaise.getAnimal()),DataRaise::getAnimal,dataRaise.getAnimal())
                .like(StringUtils.isNotBlank(dataRaise.getContent()),DataRaise::getContent,dataRaise.getContent())
                .eq(StringUtils.isNotBlank(dataRaise.getUserId()),DataRaise::getUserId,dataRaise.getUserId())
                .eq(StringUtils.isNotBlank(dataRaise.getCreateBy()),DataRaise::getCreateBy,dataRaise.getCreateBy())
                .eq(dataRaise.getCreateTime() != null,DataRaise::getCreateTime,dataRaise.getCreateTime())
                .eq(StringUtils.isNotBlank(dataRaise.getUpdateBy()),DataRaise::getUpdateBy,dataRaise.getUpdateBy())
                .eq(dataRaise.getUpdateTime() != null,DataRaise::getUpdateTime,dataRaise.getUpdateTime());
        Page<DataRaise> dataRaisePage = dataRaiseService.page(page, queryWrapper);
        return Result.success(dataRaisePage);
    }

    /** 根据id获取饲养记录 */
    @GetMapping("getDataRaiseById")
    public Result getDataRaiseById(@RequestParam("id")String id) {
        DataRaise dataRaise = dataRaiseService.getById(id);
        return Result.success(dataRaise);
    }

    /** 保存饲养记录 */
    @PostMapping("saveDataRaise")
    public Result saveDataRaise(@RequestBody DataRaise dataRaise) {
        User userInfo = ShiroUtils.getUserInfo();
        dataRaise.setUserId(userInfo.getId());
        boolean save = dataRaiseService.save(dataRaise);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 编辑饲养记录 */
    @PostMapping("editDataRaise")
    public Result editDataRaise(@RequestBody DataRaise dataRaise) {
        boolean save = dataRaiseService.updateById(dataRaise);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 删除饲养记录 */
    @GetMapping("removeDataRaise")
    public Result removeDataRaise(@RequestParam("ids")String ids) {
        if (StringUtils.isNotBlank(ids)) {
            String[] asList = ids.split(",");
            for (String id : asList) {
                dataRaiseService.removeById(id);
            }
            return Result.success();
        } else {
            return Result.fail("饲养记录id不能为空！");
        }
    }

}
