package com.project.wild.controller.post;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.wild.common.enums.BusinessType;
import com.project.wild.common.enums.ResultCode;
import com.project.wild.domain.DataPost;
import com.project.wild.domain.Result;
import com.project.wild.domain.User;
import com.project.wild.service.DataPostService;
import com.project.wild.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 岗位controller
 * @date 2025/01/13 10:21
 */
@Controller
@ResponseBody
@RequestMapping("post")
public class DataPostController {

    @Autowired
    private DataPostService dataPostService;
    @Autowired
    private UserService userService;

    /** 分页获取岗位 */
    @PostMapping("getDataPostPage")
    public Result getDataPostPage(@RequestBody DataPost dataPost) {
        Page<DataPost> page = new Page<>(dataPost.getPageNumber(),dataPost.getPageSize());
        QueryWrapper<DataPost> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(StringUtils.isNotBlank(dataPost.getName()),DataPost::getName,dataPost.getName());
        Page<DataPost> dataPostPage = dataPostService.page(page, queryWrapper);
        return Result.success(dataPostPage);
    }

    @GetMapping("getDataPostList")
    public Result getDataPostList() {
        List<DataPost> list = dataPostService.list();
        return Result.success(list);
    }

    /** 根据id获取岗位 */
    @GetMapping("getDataPostById")
    public Result getDataPostById(@RequestParam("id")String id) {
        DataPost dataPost = dataPostService.getById(id);
        return Result.success(dataPost);
    }

    /** 保存岗位 */
    @PostMapping("saveDataPost")
    public Result saveDataPost(@RequestBody DataPost dataPost) {
        boolean save = dataPostService.save(dataPost);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 编辑岗位 */
    @PostMapping("editDataPost")
    public Result editDataPost(@RequestBody DataPost dataPost) {
        boolean save = dataPostService.updateById(dataPost);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 删除岗位 */
    @GetMapping("removeDataPost")
    public Result removeDataPost(@RequestParam("ids")String ids) {
        if (StringUtils.isNotBlank(ids)) {
            String[] asList = ids.split(",");
            for (String id : asList) {
                DataPost post = dataPostService.getById(id);
                QueryWrapper<User> queryWrapper = new QueryWrapper<>();
                queryWrapper.lambda().eq(User::getDept,post.getName());
                int count = userService.count(queryWrapper);
                if (count > 0) {
                    return Result.fail("该岗位下存在员工暂无法删除");
                } else {
                    dataPostService.removeById(id);
                }
            }
            return Result.success();
        } else {
            return Result.fail("岗位id不能为空！");
        }
    }

}
