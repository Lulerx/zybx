package com.post.zybx.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * create by Luler on 2023/1/5 11:21
 *
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_model")
public class TbModel {
    private Integer modelId;
    private String modelName;
    private String readme;
}
