package com.project.wild.controller.department;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.wild.common.enums.ResultCode;
import com.project.wild.domain.DataDepartment;
import com.project.wild.domain.Result;
import com.project.wild.domain.User;
import com.project.wild.service.DataDepartmentService;
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
 * @description: 部门controller
 * @date 2025/01/13 10:38
 */
@Controller
@ResponseBody
@RequestMapping("department")
public class DataDepartmentController {

    @Autowired
    private DataDepartmentService dataDepartmentService;
    @Autowired
    private UserService userService;

    /** 分页获取部门 */
    @PostMapping("getDataDepartmentPage")
    public Result getDataDepartmentPage(@RequestBody DataDepartment dataDepartment) {
        Page<DataDepartment> page = new Page<>(dataDepartment.getPageNumber(),dataDepartment.getPageSize());
        QueryWrapper<DataDepartment> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(StringUtils.isNotBlank(dataDepartment.getName()),DataDepartment::getName,dataDepartment.getName());
        Page<DataDepartment> dataDepartmentPage = dataDepartmentService.page(page, queryWrapper);
        return Result.success(dataDepartmentPage);
    }

    @GetMapping("getDataDepartmentList")
    public Result getDataDepartmentPage() {
        List<DataDepartment> list = dataDepartmentService.list();
        return Result.success(list);
    }

    /** 根据id获取部门 */
    @GetMapping("getDataDepartmentById")
    public Result getDataDepartmentById(@RequestParam("id")String id) {
        DataDepartment dataDepartment = dataDepartmentService.getById(id);
        return Result.success(dataDepartment);
    }

    /** 保存部门 */
    @PostMapping("saveDataDepartment")
    public Result saveDataDepartment(@RequestBody DataDepartment dataDepartment) {
        boolean save = dataDepartmentService.save(dataDepartment);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 编辑部门 */
    @PostMapping("editDataDepartment")
    public Result editDataDepartment(@RequestBody DataDepartment dataDepartment) {
        boolean save = dataDepartmentService.updateById(dataDepartment);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 删除部门 */
    @GetMapping("removeDataDepartment")
    public Result removeDataDepartment(@RequestParam("ids")String ids) {
        if (StringUtils.isNotBlank(ids)) {
            String[] asList = ids.split(",");
            for (String id : asList) {
                DataDepartment department = dataDepartmentService.getById(id);
                QueryWrapper<User> queryWrapper = new QueryWrapper<>();
                queryWrapper.lambda().eq(User::getDept,department.getName());
                int count = userService.count(queryWrapper);
                if (count > 0) {
                    return Result.fail("该部门下存在员工暂无法删除");
                } else {
                    dataDepartmentService.removeById(id);
                }
            }
            return Result.success();
        } else {
            return Result.fail("部门id不能为空！");
        }
    }

}
