package com.post.zybx.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * create by Luler on 2023/1/12 10:53
 *
 * @description
 */
@Data
@AllArgsConstructor
@TableName("tb_login")
public class TbLogin {
    @TableId(type = IdType.AUTO,value = "login_id")
    private Integer loginId;
    private String username;
    private String pwd;
    private String nickname;

    public TbLogin(String username, String pwd){
        this.username = username;
        this.pwd = pwd;
    }

    public TbLogin(){}
}
