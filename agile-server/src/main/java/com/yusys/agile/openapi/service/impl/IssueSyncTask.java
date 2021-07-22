package com.yusys.agile.openapi.service.impl;


import com.yusys.agile.utils.HttpComponentsUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

public class IssueSyncTask implements Callable {

    private Map<String, Object> headers;

    private String body;

    private String url;

    private String state;

    private String list;


    public IssueSyncTask( String url,Map<String, Object> headers,String state,String list) {
        this.headers = headers;
        this.url = url;
        this.state = state;
        this.list = list;
    }
    @Override
    public String call() throws Exception {
        Map map = new ConcurrentHashMap<>();
        map.put("state",state);
        map.put("data",list);
        List list1 = new ArrayList();
        list1.add(map);
        body = list1.toString();
        return  HttpComponentsUtils.postWithHeader(url,headers,body);
    }
}
