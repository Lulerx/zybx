package com.post.zybx.dto;

import com.post.zybx.bean.TbModel;
import com.post.zybx.service.TbModelService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * create by Luler on 2023/1/5 11:17
 *
 * @description 全局静态字典
 */
@Component
public class DictionaryMap {

    private static HashMap<String, String> hashMap = new HashMap<>();

    public static DictionaryMap dictMap;

    @Resource
    private TbModelService tbModelService;


    /**
     * 查询数据库，完成静态字典初始化
     */
    @PostConstruct //@PostConstruct注解的方法在项目启动的时候执行这个方法
    public void queryDic() {
        dictMap = this;
        dictMap.tbModelService = this.tbModelService;
        List<TbModel> list = dictMap.tbModelService.findAll();

        for (int i = 0; i < list.size(); i++) {
            TbModel model = list.get(i);
            String fieldName = model.getModelId() + "";
            String fieldValue  = model.getModelName();

            String key = "model_" + fieldName;
            hashMap.put(key,fieldValue);
        }

    }


    /**
     * 获取字典
     *
     * @param fieldName 格式 model_1001、model1002
     * @return
     */
    public static String getFieldDetail(String fieldName) {
        StringBuilder sb = new StringBuilder();
        StringBuilder keySb = sb.append("model_").append(fieldName);
        String key = keySb.toString();
        String value = hashMap.get(key);
        return value;
    }


}
