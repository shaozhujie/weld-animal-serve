package com.project.wild.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Date;
import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 系统说明
 * @date 2025/01/14 11:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("data_remark")
public class DataRemark implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 电话
     */
    private String tel;

    /**
     * 名称
     */
    private String name;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 开放日
     */
    private String open;

    /**
     * 开放时间
     */
    private String openTime;

    /**
     * 准备关闭时间
     */
    private String readyClose;

    /**
     * 关闭时间
     */
    private String closeTime;

    /**
     * 关闭日
     */
    private String close;

    /**
     * 地址
     */
    private String address;

    @TableField(exist = false)
    private Integer pageNumber;

    @TableField(exist = false)
    private Integer pageSize;
}
