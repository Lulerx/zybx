package com.post.zybx.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * create by Luler on 2022/12/26 15:33
 *
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_result")
public class UserAlert {
    @TableId(type = IdType.AUTO,value = "uid")
    private Integer uid;
    private String orderNum;
    private String email;
    private String phone;
    private String sex;
    private Integer age;
    //职业名称
    private String jobTitle;
    //证件类型
    @TableField("id_type")
    private String IDType;
    //证件号码
    @TableField("id_card")
    private String IDCard;
    //国籍
    private String nationality;
    private String address;
    //所属机构
    private String dept;
    //出单网点
    private String branch;
    //承保时间
    private String startDate;
    //模型Id
    private Integer modelId;
    private Date updateTime;
}
