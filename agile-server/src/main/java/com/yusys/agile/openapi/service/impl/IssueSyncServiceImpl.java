package com.yusys.agile.openapi.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.issue.service.IssueService;
import com.yusys.agile.openapi.service.IssueSyncService;
import com.yusys.agile.utils.HttpComponentsUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Service
public class IssueSyncServiceImpl implements IssueSyncService {

    private static final Logger LOGGER = LoggerFactory.getLogger(IssueSyncServiceImpl.class);

    public static ExecutorService executorService = new ThreadPoolExecutor(10, 10,
            30L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(1000), new ThreadPoolExecutor.CallerRunsPolicy());

    private static  String uri = "";

    @Resource
    private IssueService issueService;

    @Override
    public void issueSyncAdd(List<IssueDTO> list) {

        executorService.execute(() -> {
            Map map = new ConcurrentHashMap<>();
            map.put("state","add");
            map.put("data",list);
            List list1 = new ArrayList();
            list1.add(map);
            Map<String, Object> headers = new HashMap<>();
            try {
                LOGGER.info("开始与测试平台同步需求，报文："+list1.toString());
                HttpComponentsUtils.postWithHeader(uri,headers,list1.toString());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                LOGGER.error("与测试平台同步需求报错，报文："+list1.toString());
            }
        });
    }

    @Override
    public void issueSyncUpdate(List<IssueDTO> list) {
        executorService.execute(() -> {
            Map map = new ConcurrentHashMap<>();
            map.put("state","update");
            map.put("data",list);
            List list1 = new ArrayList();
            list1.add(map);
            Map<String, Object> headers = new HashMap<>();
            try {
                LOGGER.info("开始与测试平台同步需求，报文："+list1.toString());
                HttpComponentsUtils.postWithHeader(uri,headers,list1.toString());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                LOGGER.error("与测试平台同步需求报错，报文："+list1.toString());
            }
        });
    }

    @Override
    public void issueSyncDelete(List<Long> list) {
        JSONArray  jsonArray = new JSONArray();
        for (Long id:list) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("issueId",id);
            jsonArray.add(jsonObject);
        }
        executorService.execute(() -> {
            Map map = new ConcurrentHashMap<>();
            map.put("state","delete");
            map.put("data",jsonArray);
            List list1 = new ArrayList();
            list1.add(map);
            Map<String, Object> headers = new HashMap<>();
            try {
                LOGGER.info("开始与测试平台同步需求，报文："+list1.toString());
                HttpComponentsUtils.postWithHeader(uri,headers,list1.toString());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                LOGGER.error("与测试平台同步需求报错，报文："+list1.toString());
            }
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
    public void issueSyncStageIdAndLaneId(List<IssueDTO> list) {
        if(CollectionUtils.isNotEmpty(list)){
            issueService.updateStageIdAndLaneId(list);
        }

    }
}
