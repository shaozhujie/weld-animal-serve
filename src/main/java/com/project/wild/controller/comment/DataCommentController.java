package com.project.wild.controller.comment;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.wild.common.enums.BusinessType;
import com.project.wild.common.enums.ResultCode;
import com.project.wild.config.utils.ShiroUtils;
import com.project.wild.domain.DataComment;
import com.project.wild.domain.Result;
import com.project.wild.domain.User;
import com.project.wild.service.DataCommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 评论controller
 * @date 2025/01/15 01:56
 */
@Controller
@ResponseBody
@RequestMapping("comment")
public class DataCommentController {

    @Autowired
    private DataCommentService dataCommentService;

    /** 分页获取评论 */
    @PostMapping("getDataCommentPage")
    public Result getDataCommentPage(@RequestBody DataComment dataComment) {
        Page<DataComment> page = new Page<>(dataComment.getPageNumber(),dataComment.getPageSize());
        QueryWrapper<DataComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(StringUtils.isNotBlank(dataComment.getTicketId()),DataComment::getTicketId,dataComment.getTicketId())
                .like(StringUtils.isNotBlank(dataComment.getContent()),DataComment::getContent,dataComment.getContent())
                .eq(StringUtils.isNotBlank(dataComment.getCreateBy()),DataComment::getCreateBy,dataComment.getCreateBy());
        Page<DataComment> dataCommentPage = dataCommentService.page(page, queryWrapper);
        return Result.success(dataCommentPage);
    }

    @PostMapping("getDataCommentList")
    public Result getDataCommentList(@RequestBody DataComment dataComment) {
        QueryWrapper<DataComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(StringUtils.isNotBlank(dataComment.getTicketId()),DataComment::getTicketId,dataComment.getTicketId())
                .like(StringUtils.isNotBlank(dataComment.getContent()),DataComment::getContent,dataComment.getContent())
                .eq(StringUtils.isNotBlank(dataComment.getCreateBy()),DataComment::getCreateBy,dataComment.getCreateBy())
                .orderByDesc(DataComment::getCreateTime);
        List<DataComment> dataCommentPage = dataCommentService.list(queryWrapper);
        return Result.success(dataCommentPage);
    }

    /** 根据id获取评论 */
    @GetMapping("getDataCommentById")
    public Result getDataCommentById(@RequestParam("id")String id) {
        DataComment dataComment = dataCommentService.getById(id);
        return Result.success(dataComment);
    }

    /** 保存评论 */
    @PostMapping("saveDataComment")
    public Result saveDataComment(@RequestBody DataComment dataComment) {
        User userInfo = ShiroUtils.getUserInfo();
        dataComment.setUserId(userInfo.getId());
        boolean save = dataCommentService.save(dataComment);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 编辑评论 */
    @PostMapping("editDataComment")
    public Result editDataComment(@RequestBody DataComment dataComment) {
        boolean save = dataCommentService.updateById(dataComment);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 删除评论 */
    @GetMapping("removeDataComment")
    public Result removeDataComment(@RequestParam("ids")String ids) {
        if (StringUtils.isNotBlank(ids)) {
            String[] asList = ids.split(",");
            for (String id : asList) {
                dataCommentService.removeById(id);
            }
            return Result.success();
        } else {
            return Result.fail("评论id不能为空！");
        }
    }

}
