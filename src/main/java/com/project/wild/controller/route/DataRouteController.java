package com.project.wild.controller.route;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.wild.domain.DataRoute;
import com.project.wild.domain.Result;
import com.project.wild.enums.ResultCode;
import com.project.wild.service.DataRouteService;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 游览路线controller
 * @date 2025/01/09 10:51
 */
@Controller
@ResponseBody
@RequestMapping("route")
public class DataRouteController {

    @Autowired
    private DataRouteService dataRouteService;

    /** 分页获取游览路线 */
    @PostMapping("getDataRoutePage")
    public Result getDataRoutePage(@RequestBody DataRoute dataRoute) {
        Page<DataRoute> page = new Page<>(dataRoute.getPageNumber(),dataRoute.getPageSize());
        QueryWrapper<DataRoute> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(StringUtils.isNotBlank(dataRoute.getContent()),DataRoute::getContent,dataRoute.getContent())
                .like(StringUtils.isNotBlank(dataRoute.getTitle()),DataRoute::getTitle,dataRoute.getTitle())
                .like(StringUtils.isNotBlank(dataRoute.getIntroduce()),DataRoute::getIntroduce,dataRoute.getIntroduce());
        Page<DataRoute> dataRoutePage = dataRouteService.page(page, queryWrapper);
        return Result.success(dataRoutePage);
    }

    /** 根据id获取游览路线 */
    @GetMapping("getDataRouteById")
    public Result getDataRouteById(@RequestParam("id")String id) {
        DataRoute dataRoute = dataRouteService.getById(id);
        return Result.success(dataRoute);
    }

    /** 保存游览路线 */
    @PostMapping("saveDataRoute")
    public Result saveDataRoute(@RequestBody DataRoute dataRoute) {
        boolean save = dataRouteService.save(dataRoute);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 编辑游览路线 */
    @PostMapping("editDataRoute")
    public Result editDataRoute(@RequestBody DataRoute dataRoute) {
        boolean save = dataRouteService.updateById(dataRoute);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 删除游览路线 */
    @GetMapping("removeDataRoute")
    public Result removeDataRoute(@RequestParam("ids")String ids) {
        if (StringUtils.isNotBlank(ids)) {
            String[] asList = ids.split(",");
            for (String id : asList) {
                dataRouteService.removeById(id);
            }
            return Result.success();
        } else {
            return Result.fail("游览路线id不能为空！");
        }
    }

}
