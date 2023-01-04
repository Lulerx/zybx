package com.post.zybx.bean;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * create by Luler on 2022/12/26 15:19
 *
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_user")
public class TbUser {
    @ExcelIgnore
    @TableId(type = IdType.AUTO,value = "uid")
    private Integer uid;
    @ExcelProperty(value = "所属机构代码")
    private String deptId;
    //所属机构
    @ExcelProperty(value = "所属机构")
    private String deptName;
    @ExcelProperty(value = "网点代码")
    private String branchId;
    @ExcelProperty(value = "网点名称")
    private String branchName;
    @ExcelProperty(value = "保单号")
    private String orderNum;
    @ExcelProperty(value = "电子邮件地址")
    private String email;
    @ExcelProperty(value = "手机号码")
    private String phone;
    @ExcelProperty(value = "性别")
//    @ExcelIgnore
    private String sex;
    @ExcelProperty(value = "投保人年龄")
    private Integer age;
    @ExcelProperty(value = "职业名称")
    private String jobTitle;
    @ExcelProperty(value = "证件类型")
    @TableField("id_type")
    private String IDType;
    @ExcelProperty(value = "证件号码")
    @TableField("id_card")
    private String IDCard;
    @ExcelProperty(value = "国籍")
    private String nationality;
    @ExcelProperty(value = "地址")
    private String address;
    @ExcelProperty(value = "承保日期")
    private String startDate;
    @ExcelIgnore
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai")
    private Date updateTime;

}
