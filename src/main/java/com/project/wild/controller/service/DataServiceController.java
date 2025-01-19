package com.project.wild.controller.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.wild.common.enums.ResultCode;
import com.project.wild.config.utils.ShiroUtils;
import com.project.wild.domain.DataService;
import com.project.wild.domain.Result;
import com.project.wild.domain.User;
import com.project.wild.service.DataServiceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 园区设施维修controller
 * @date 2025/01/13 04:22
 */
@Controller
@ResponseBody
@RequestMapping("service")
public class DataServiceController {

    @Autowired
    private DataServiceService dataServiceService;

    /** 分页获取园区设施维修 */
    @PostMapping("getDataServicePage")
    public Result getDataServicePage(@RequestBody DataService dataService) {
        Page<DataService> page = new Page<>(dataService.getPageNumber(),dataService.getPageSize());
        QueryWrapper<DataService> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(StringUtils.isNotBlank(dataService.getPark()),DataService::getPark,dataService.getPark())
                .like(StringUtils.isNotBlank(dataService.getContent()),DataService::getContent,dataService.getContent())
                .eq(dataService.getState() != null,DataService::getState,dataService.getState())
                .eq(StringUtils.isNotBlank(dataService.getUserId()),DataService::getUserId,dataService.getUserId());
        Page<DataService> dataServicePage = dataServiceService.page(page, queryWrapper);
        return Result.success(dataServicePage);
    }

    /** 根据id获取园区设施维修 */
    @GetMapping("getDataServiceById")
    public Result getDataServiceById(@RequestParam("id")String id) {
        DataService dataService = dataServiceService.getById(id);
        return Result.success(dataService);
    }

    /** 保存园区设施维修 */
    @PostMapping("saveDataService")
    public Result saveDataService(@RequestBody DataService dataService) {
        User userInfo = ShiroUtils.getUserInfo();
        dataService.setUserId(userInfo.getId());
        boolean save = dataServiceService.save(dataService);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 编辑园区设施维修 */
    @PostMapping("editDataService")
    public Result editDataService(@RequestBody DataService dataService) {
        boolean save = dataServiceService.updateById(dataService);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 删除园区设施维修 */
    @GetMapping("removeDataService")
    public Result removeDataService(@RequestParam("ids")String ids) {
        if (StringUtils.isNotBlank(ids)) {
            String[] asList = ids.split(",");
            for (String id : asList) {
                dataServiceService.removeById(id);
            }
            return Result.success();
        } else {
            return Result.fail("园区设施维修id不能为空！");
        }
    }

}
