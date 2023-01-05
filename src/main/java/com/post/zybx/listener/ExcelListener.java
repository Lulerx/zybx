package com.post.zybx.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.ListUtils;
import com.post.zybx.bean.TbUser;
import com.post.zybx.service.TbUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * create by Luler on 2022/12/5 15:51
 *
 * @description
 */
public class ExcelListener extends AnalysisEventListener<TbUser> {
    private static final Logger log = LoggerFactory.getLogger(ExcelListener.class);

    /**
     * 每隔100条存储数据库，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 2000;
    /**
     * 缓存的数据
     */
    private List<TbUser> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    private TbUserService tbUserService;


    public ExcelListener(TbUserService tbUserService){
        this.tbUserService = tbUserService;
    }

    //一行一行读取excel内容
    @Override
    public void invoke(TbUser tbUser, AnalysisContext analysisContext) {
//        System.out.println("excel内容：" + tbUser);
//        log.info("=================== 开始读取 Excel 内容！");
        cachedDataList.add(tbUser);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    //读取excel表头信息
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
//        System.out.println("表头：" + headMap);
        log.info("=====================开始清空表数据");
        tbUserService.deleteAll();
        log.info("=====================清空表数据完成，开始读取 Excel 内容！");
    }

    //读取完之后执行
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("=============== 读取Excel完成！");
        saveData();
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        log.info("====================={}条数据，开始存储数据库！", cachedDataList.size());
        tbUserService.inertListVo(cachedDataList);
        log.info("=====================存储数据库成功！");
    }
}
