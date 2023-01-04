package com.post.zybx.dto;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ReadConverterContext;
import com.alibaba.excel.converters.WriteConverterContext;
import com.alibaba.excel.metadata.data.WriteCellData;

/**
 * create by Luler on 2023/1/3 11:32
 *
 * @description 自定义数据格式转换
 */
public class AlertStatusConvert implements Converter<String> {

    /**
     * 这里读的时候会调用
     *
     * @param context
     * @return
     */
    /*@Override
    public String convertToJavaData(ReadConverterContext<?> context) {
        return "自定义：" + context.getReadCellData().getStringValue();
    }*/

    /**
     * 这里是写的时候会调用
     *
     * @return
     */
    @Override
    public WriteCellData<?> convertToExcelData(WriteConverterContext<String> context) {
        if (context.getValue().equals("0")){
            context.setValue("新数据");
        }
        if (context.getValue().equals("1")){
            context.setValue("未核销数据");
        }
        if (context.getValue().equals("2")){
            context.setValue("已核销");
        }
        return new WriteCellData<>(context.getValue());
    }

}
