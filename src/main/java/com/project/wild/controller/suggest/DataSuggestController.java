package com.project.wild.controller.suggest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.wild.domain.DataSuggest;
import com.project.wild.domain.Result;
import com.project.wild.enums.ResultCode;
import com.project.wild.service.DataSuggestService;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 意见建议controller
 * @date 2025/01/07 04:44
 */
@Controller
@ResponseBody
@RequestMapping("suggest")
public class DataSuggestController {

    @Autowired
    private DataSuggestService dataSuggestService;

    /** 分页获取意见建议 */
    @PostMapping("getDataSuggestPage")
    public Result getDataSuggestPage(@RequestBody DataSuggest dataSuggest) {
        Page<DataSuggest> page = new Page<>(dataSuggest.getPageNumber(),dataSuggest.getPageSize());
        QueryWrapper<DataSuggest> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(StringUtils.isNotBlank(dataSuggest.getName()),DataSuggest::getName,dataSuggest.getName())
                .like(StringUtils.isNotBlank(dataSuggest.getEmail()),DataSuggest::getEmail,dataSuggest.getEmail())
                .like(StringUtils.isNotBlank(dataSuggest.getTel()),DataSuggest::getTel,dataSuggest.getTel())
                .like(StringUtils.isNotBlank(dataSuggest.getSuggest()),DataSuggest::getSuggest,dataSuggest.getSuggest())
                .like(StringUtils.isNotBlank(dataSuggest.getContent()),DataSuggest::getContent,dataSuggest.getContent())
                .like(StringUtils.isNotBlank(dataSuggest.getCreateBy()),DataSuggest::getCreateBy,dataSuggest.getCreateBy());
        Page<DataSuggest> dataSuggestPage = dataSuggestService.page(page, queryWrapper);
        return Result.success(dataSuggestPage);
    }

    /** 根据id获取意见建议 */
    @GetMapping("getDataSuggestById")
    public Result getDataSuggestById(@RequestParam("id")String id) {
        DataSuggest dataSuggest = dataSuggestService.getById(id);
        return Result.success(dataSuggest);
    }

    /** 保存意见建议 */
    @PostMapping("saveDataSuggest")
    public Result saveDataSuggest(@RequestBody DataSuggest dataSuggest) {
        boolean save = dataSuggestService.save(dataSuggest);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 编辑意见建议 */
    @PostMapping("editDataSuggest")
    public Result editDataSuggest(@RequestBody DataSuggest dataSuggest) {
        boolean save = dataSuggestService.updateById(dataSuggest);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 删除意见建议 */
    @GetMapping("removeDataSuggest")
    public Result removeDataSuggest(@RequestParam("ids")String ids) {
        if (StringUtils.isNotBlank(ids)) {
            String[] asList = ids.split(",");
            for (String id : asList) {
                dataSuggestService.removeById(id);
            }
            return Result.success();
        } else {
            return Result.fail("意见建议id不能为空！");
        }
    }

}
