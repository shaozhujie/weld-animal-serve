package com.project.wild.controller.blog;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.wild.domain.DataBlog;
import com.project.wild.domain.Result;
import com.project.wild.enums.ResultCode;
import com.project.wild.service.DataBlogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 新闻博客controller
 * @date 2025/01/09 04:50
 */
@Controller
@ResponseBody
@RequestMapping("blog")
public class DataBlogController {

    @Autowired
    private DataBlogService dataBlogService;

    /** 分页获取新闻博客 */
    @PostMapping("getDataBlogPage")
    public Result getDataBlogPage(@RequestBody DataBlog dataBlog) {
        Page<DataBlog> page = new Page<>(dataBlog.getPageNumber(),dataBlog.getPageSize());
        QueryWrapper<DataBlog> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(StringUtils.isNotBlank(dataBlog.getContent()),DataBlog::getContent,dataBlog.getContent())
                .eq(StringUtils.isNotBlank(dataBlog.getType()),DataBlog::getType,dataBlog.getType())
                .like(StringUtils.isNotBlank(dataBlog.getTitle()),DataBlog::getTitle,dataBlog.getTitle())
                .like(StringUtils.isNotBlank(dataBlog.getIntroduce()),DataBlog::getIntroduce,dataBlog.getIntroduce())
                .like(StringUtils.isNotBlank(dataBlog.getTags()),DataBlog::getTags,dataBlog.getTags());
        Page<DataBlog> dataBlogPage = dataBlogService.page(page, queryWrapper);
        return Result.success(dataBlogPage);
    }

    /** 根据id获取新闻博客 */
    @GetMapping("getDataBlogById")
    public Result getDataBlogById(@RequestParam("id")String id) {
        DataBlog dataBlog = dataBlogService.getById(id);
        return Result.success(dataBlog);
    }

    @GetMapping("getDataBlogOther")
    public Result getDataBlogOther(@RequestParam("id")String id) {
        QueryWrapper<DataBlog> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().ne(DataBlog::getId,id);
        List<DataBlog> list = dataBlogService.list(queryWrapper);
        return Result.success(list);
    }

    /** 保存新闻博客 */
    @PostMapping("saveDataBlog")
    public Result saveDataBlog(@RequestBody DataBlog dataBlog) {
        boolean save = dataBlogService.save(dataBlog);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 编辑新闻博客 */
    @PostMapping("editDataBlog")
    public Result editDataBlog(@RequestBody DataBlog dataBlog) {
        boolean save = dataBlogService.updateById(dataBlog);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 删除新闻博客 */
    @GetMapping("removeDataBlog")
    public Result removeDataBlog(@RequestParam("ids")String ids) {
        if (StringUtils.isNotBlank(ids)) {
            String[] asList = ids.split(",");
            for (String id : asList) {
                dataBlogService.removeById(id);
            }
            return Result.success();
        } else {
            return Result.fail("新闻博客id不能为空！");
        }
    }

}
