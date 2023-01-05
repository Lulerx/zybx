package com.post.zybx.dto;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.WriteConverterContext;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.post.zybx.utils.TmUtil;

/**
 * create by Luler on 2023/1/3 11:32
 *
 * @description 自定义数据格式转换 - 证件号脱敏
 */
public class AlertIdCardConvert implements Converter<String> {

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
        String cardEncrypt = TmUtil.idCardEncrypt(context.getValue());
        return new WriteCellData<>(cardEncrypt);
    }

}
