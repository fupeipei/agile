package com.yusys.agile.easyexcel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

/**
 *  @Description: 监听器
 *  @author: zhao_yd
 *  @Date: 2021/6/2 5:09 下午
 *
 */

public class ExcelListener extends AnalysisEventListener {

    List<Object> list = Lists.newArrayList();
    /**
     * 每隔5000条可以处理一批数据
     */
    private static final int BATCH_COUNT = 5000;

    @Override
    public void invoke(Object data, AnalysisContext analysisContext) {
        list.add(data);
        // 在这里可以做一些其他的操作  就考自己去拓展了
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            //todo save()
            // 存储完成清理 list
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // save();
    }
}
