package com.project.wild.controller.blogType;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.wild.domain.DataBlogType;
import com.project.wild.domain.Result;
import com.project.wild.enums.ResultCode;
import com.project.wild.service.DataBlogTypeService;
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
 * @description: 新闻与博客分类controller
 * @date 2025/01/09 03:45
 */
@Controller
@ResponseBody
@RequestMapping("type")
public class DataBlogTypeController {

    @Autowired
    private DataBlogTypeService dataBlogTypeService;

    /** 分页获取新闻与博客分类 */
    @PostMapping("getDataBlogTypePage")
    public Result getDataBlogTypePage(@RequestBody DataBlogType dataBlogType) {
        Page<DataBlogType> page = new Page<>(dataBlogType.getPageNumber(),dataBlogType.getPageSize());
        QueryWrapper<DataBlogType> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(StringUtils.isNotBlank(dataBlogType.getName()),DataBlogType::getName,dataBlogType.getName());
        Page<DataBlogType> dataBlogTypePage = dataBlogTypeService.page(page, queryWrapper);
        return Result.success(dataBlogTypePage);
    }

    @GetMapping("getDataBlogTypeList")
    public Result getDataBlogTypeList() {
        List<DataBlogType> list = dataBlogTypeService.list();
        return Result.success(list);
    }

    /** 根据id获取新闻与博客分类 */
    @GetMapping("getDataBlogTypeById")
    public Result getDataBlogTypeById(@RequestParam("id")String id) {
        DataBlogType dataBlogType = dataBlogTypeService.getById(id);
        return Result.success(dataBlogType);
    }

    /** 保存新闻与博客分类 */
    @PostMapping("saveDataBlogType")
    public Result saveDataBlogType(@RequestBody DataBlogType dataBlogType) {
        boolean save = dataBlogTypeService.save(dataBlogType);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 编辑新闻与博客分类 */
    @PostMapping("editDataBlogType")
    public Result editDataBlogType(@RequestBody DataBlogType dataBlogType) {
        boolean save = dataBlogTypeService.updateById(dataBlogType);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 删除新闻与博客分类 */
    @GetMapping("removeDataBlogType")
    public Result removeDataBlogType(@RequestParam("ids")String ids) {
        if (StringUtils.isNotBlank(ids)) {
            String[] asList = ids.split(",");
            for (String id : asList) {
                dataBlogTypeService.removeById(id);
            }
            return Result.success();
        } else {
            return Result.fail("新闻与博客分类id不能为空！");
        }
    }

}
