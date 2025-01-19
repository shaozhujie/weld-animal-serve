package com.project.wild.controller.card;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.wild.common.enums.BusinessType;
import com.project.wild.common.enums.ResultCode;
import com.project.wild.config.utils.ShiroUtils;
import com.project.wild.domain.DataCard;
import com.project.wild.domain.DataCardTime;
import com.project.wild.domain.Result;
import com.project.wild.domain.User;
import com.project.wild.service.DataCardService;
import com.project.wild.service.DataCardTimeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 考勤controller
 * @date 2025/01/13 11:43
 */
@Controller
@ResponseBody
@RequestMapping("card")
public class DataCardController {

    @Autowired
    private DataCardService dataCardService;
    @Autowired
    private DataCardTimeService dataCardTimeService;

    /** 分页获取考勤 */
    @PostMapping("getDataCardPage")
    public Result getDataCardPage(@RequestBody DataCard dataCard) {
        Page<DataCard> page = new Page<>(dataCard.getPageNumber(),dataCard.getPageSize());
        QueryWrapper<DataCard> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(StringUtils.isNotBlank(dataCard.getUserId()),DataCard::getUserId,dataCard.getUserId())
                .eq(dataCard.getOperTime() != null,DataCard::getOperTime,dataCard.getOperTime())
                .eq(dataCard.getState() != null,DataCard::getState,dataCard.getState())
                .like(StringUtils.isNotBlank(dataCard.getCreateBy()),DataCard::getCreateBy,dataCard.getCreateBy())
                .eq(dataCard.getCreateTime() != null,DataCard::getCreateTime,dataCard.getCreateTime())
                .eq(StringUtils.isNotBlank(dataCard.getUpdateBy()),DataCard::getUpdateBy,dataCard.getUpdateBy())
                .eq(dataCard.getUpdateTime() != null,DataCard::getUpdateTime,dataCard.getUpdateTime());
        Page<DataCard> dataCardPage = dataCardService.page(page, queryWrapper);
        return Result.success(dataCardPage);
    }

    /** 根据id获取考勤 */
    @GetMapping("getDataCardById")
    public Result getDataCardById(@RequestParam("id")String id) {
        DataCard dataCard = dataCardService.getById(id);
        return Result.success(dataCard);
    }

    /** 保存考勤 */
    @PostMapping("saveDataCard")
    public Result saveDataCard(@RequestBody DataCard dataCard) throws ParseException {
        DataCardTime cardTime = dataCardTimeService.getOne(null);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = dateFormat.format(new Date());
        String startTime = format + " " + cardTime.getStartTime() + ":00";
        String endTime = format + " " + cardTime.getEndTime() + ":00";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start = simpleDateFormat.parse(startTime);
        Date end = simpleDateFormat.parse(endTime);
        User userInfo = ShiroUtils.getUserInfo();
        dataCard.setUserId(userInfo.getId());
        dataCard.setOperTime(new Date());
        //判断是否迟到
        dataCard.setState(0);
        if (dataCard.getType() == 0) {
            if (start.before(new Date())) {
                dataCard.setState(1);
            }
        }
        if (dataCard.getType() == 1) {
            //判断有没有上班打卡
            QueryWrapper<DataCard> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(DataCard::getOperTime,format).eq(DataCard::getType,0).eq(DataCard::getUserId,userInfo.getId());
            int count = dataCardService.count(queryWrapper);
            if (count <= 0) {
                return Result.fail("当日暂未打卡上班,不能打卡下班");
            }
            if (end.after(new Date())) {
                dataCard.setState(2);
            }
        }
        //判断有没有重复操作
        QueryWrapper<DataCard> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(DataCard::getOperTime,format)
                .eq(DataCard::getType,dataCard.getType()).eq(DataCard::getUserId,dataCard.getUserId());
        int count = dataCardService.count(wrapper);
        if (count > 0) {
            return Result.fail("请勿重复打卡");
        }
        boolean save = dataCardService.save(dataCard);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 编辑考勤 */
    @PostMapping("editDataCard")
    public Result editDataCard(@RequestBody DataCard dataCard) {
        boolean save = dataCardService.updateById(dataCard);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 删除考勤 */
    @GetMapping("removeDataCard")
    public Result removeDataCard(@RequestParam("ids")String ids) {
        if (StringUtils.isNotBlank(ids)) {
            String[] asList = ids.split(",");
            for (String id : asList) {
                dataCardService.removeById(id);
            }
            return Result.success();
        } else {
            return Result.fail("考勤id不能为空！");
        }
    }

}
