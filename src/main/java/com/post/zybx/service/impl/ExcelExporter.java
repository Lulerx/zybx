package com.post.zybx.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.post.zybx.common.CommonPage;
import com.post.zybx.service.PageQuerier;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * create by Luler on 2022/12/29 14:18
 *
 * @description 分页查询导出 Excel
 */
public class ExcelExporter<T> {
    /** 实现分页查询 */
    private PageQuerier<T> pageQuerier;

    public ExcelExporter(PageQuerier<T> pageQuerier) {
        this.pageQuerier = pageQuerier;
    }
    /**
     * 分页查询数据并写入到下载输出流
     *
     * @param response HttpServletResponse
     * @param fileName 文件名，如：测试.xlsx
     * @param header  表头映射类
     * @throws IOException
     */
    public void write(HttpServletResponse response, String fileName, Class header) throws IOException {

        // 一定要对文件名做URL编码，否则会出现中文乱码
        String file = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString());
        response.reset();
        response.addHeader("Content-Disposition", "attachment;filename=" + file);
        response.addHeader("filename", file);
        response.setContentType("application/octet-stream; charset=UTF-8");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        ExcelWriter writer = new ExcelWriterBuilder()
                .autoCloseStream(true)
                .automaticMergeHead(true) // 这里一定要设置为true，否则在无法实现表头列合并
                .excelType(ExcelTypeEnum.XLSX)
                .file(response.getOutputStream())
                .head(header)
//                .registerWriteHandler(contentStyleStrategy)
                .build();
        // xlsx文件单个工作簿上限是1048575行,这里如果超过104W需要分Sheet
        long sheetMax = 1048575;
        // 每一个工作簿可写入的数据量，需要把表头所占的行算上
        long sheetTotal = 1;
        // 工作簿编号
        int sheetNum = 1;
        // 分页数
        int pageNo = 1;
        // 每页查询数量
        int pageSize = 50000;
//        int pageSize = 20; //用于测试
        WriteSheet writeSheet = EasyExcel.writerSheet("sheet"+sheetNum).head(header).build();
        // 先查询第一页，得到总数
        Page<T> page = pageQuerier.findPage(new CommonPage<T>(pageNo, pageSize));
        writer.write(page.getRecords(), writeSheet);
        long totalCount = page.getTotal();
        if (totalCount <= 0 || totalCount <= pageSize) {
            // 没有数据或只有一页数据
            writer.finish();
            return;
        }

        sheetTotal += page.getSize();
        long total = page.getTotal();
        long fetch = page.getSize();
        for (;;) {
            // 只查询分页数据，不查询总数
            pageNo++;
            List<T> list = pageQuerier.findPage(new CommonPage<T>(pageNo, pageSize)).getRecords();
            fetch += list.size();
            sheetTotal += list.size();
            if (sheetTotal >= sheetMax) {
                sheetTotal = 1 + list.size();
                sheetNum++;
                writeSheet = EasyExcel.writerSheet("sheet"+sheetNum).head(header).build();
            }
            writer.write(list, writeSheet);
            if (fetch >= total) {
                writer.finish();
                break;
            }
        }
        writer.finish();
    }
}
