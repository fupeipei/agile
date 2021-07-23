package com.yusys.agile.openapi.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.yusys.agile.externalapiconfig.dao.util.ExternalApiConfigUtil;
import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.issue.service.IssueService;
import com.yusys.agile.openapi.service.IssueSyncService;
import com.yusys.portal.common.service.IBsStaticDataService;
import com.yusys.portal.common.service.impl.BsStaticDataServiceImpl;
import com.yusys.portal.model.common.entity.BsStaticData;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.csource.common.MyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.*;

/**
 * @author chenxiannv
 */
@Service
public class IssueSyncServiceImpl implements IssueSyncService {

    private static final Logger LOGGER = LoggerFactory.getLogger(IssueSyncServiceImpl.class);


    private  String uri ;
    /**
     *   0:同步失败
     */
    private static final Byte cmp_sync_result_0 = Byte.decode("0");
    /**
     *   1:同步成功
     */
    private static final Byte cmp_sync_result_1 = Byte.decode("1");

    private static final String issueSyncState_add = "add";
    private static final String issueSyncState_update = "update";
    private static final String issueSyncState_delete = "delete";


    private static final String issueSyncState_Error = "未配置测试平台同步需求url,key:testIssueSyncUrl";

    @Resource
    private ExternalApiConfigUtil externalApiConfigUtil;
    @Resource
    private IssueService issueService;


    protected  void invoke( IssueSyncTask issueSyncTask,List<IssueDTO> list) throws Exception {

        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("deployPackage-pool-%d").build();
        ExecutorService executorService = new ThreadPoolExecutor(10, 200,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(180), threadFactory, new ThreadPoolExecutor.CallerRunsPolicy());
        try {


            Future<String> resultFeature = executorService.submit(issueSyncTask);
            /**
             *          当测试平台接口失败时，将issue表的cmp_sync_result设置 0
             *          cmp_sync_result字段值含义：-1:未同步 0:同步失败 1:同步成功
             */
            String result = resultFeature.get();
            if(Optional.ofNullable(result).isPresent()&&JSONObject.parseObject(result).containsKey("code")){
               String code = JSONObject.parseObject(result).getString("code");
                if("200".equals(code)){
                    LOGGER.info("与测试平台同步需求成功："+list.toString());
                    syncResult(list,cmp_sync_result_1);
               }else {
                    LOGGER.error("与测试平台同步需求报错："+list.toString());
                    syncResult(list,cmp_sync_result_0);
                    throw new MyException(issueSyncState_Error+"具体错误日志"+result);
                }
            }else{
                LOGGER.error("与测试平台同步需求报错："+list.toString());
                syncResult(list,cmp_sync_result_0);
                throw new MyException(issueSyncState_Error+"具体错误日志"+result);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            LOGGER.error("与测试平台同步需求报错："+list.toString());
            syncResult(list,cmp_sync_result_0);
            throw e;
        }
    }

    @Override
    public void issueSyncAdd(List<IssueDTO> list) throws Exception {
        String staticDataKey  = "testIssueSyncUrl";
        initUrl(staticDataKey);
        LOGGER.info("新增需求,开始与测试平台同步，报文 ："+list.toString());
        Map<String, Object> headers = new HashMap<>();
        headers.put("clientType","1");
        headers.put("Content-Type","application/json");
        IssueSyncTask issueSyncTask = new IssueSyncTask(uri,headers,issueSyncState_add,list.toString());
        invoke(issueSyncTask,list);
    }

    @Override
    public void issueSyncUpdate(List<IssueDTO> list) throws Exception {
        String staticDataKey  = "testIssueSyncUrl";
        initUrl(staticDataKey);
        LOGGER.info("变更需求,开始与测试平台同步，报文 ："+list.toString());
        Map<String, Object> headers = new HashMap<>();
        IssueSyncTask issueSyncTask = new IssueSyncTask(uri,headers,issueSyncState_update,list.toString());
        invoke(issueSyncTask,list);
    }

    @Override
    public void issueSyncDelete(List<Long> list) throws Exception {
        String staticDataKey  = "testIssueSyncUrl";
        initUrl(staticDataKey);
        JSONArray  jsonArray = new JSONArray();
        for (Long id:list) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("issueId",id);
            jsonArray.add(jsonObject);
        }
        LOGGER.info("变更需求,开始与测试平台同步，报文 ："+list.toString());
        Map<String, Object> headers = new HashMap<>();
        IssueSyncTask issueSyncTask = new IssueSyncTask(uri,headers,issueSyncState_delete,jsonArray.toJSONString());
        List<IssueDTO> result = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(list)){
            for (Long  id:list) {
                IssueDTO issueDTO1 = new IssueDTO();
                issueDTO1.setIssueId(id);
                result.add(issueDTO1);
            }
        }
        invoke(issueSyncTask,result);
    }

    @Override
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
    public void issueSyncStageIdAndLaneId(List<IssueDTO> list) {
        if(CollectionUtils.isNotEmpty(list)){
            issueService.updateIssueByIssueId(list);
        }
    }


    private void initUrl(String staticDataKey)throws Exception{
        //判断是否配置url
        String staticDataValue =  externalApiConfigUtil.getPropValue(staticDataKey);
        staticDataValue = "http://192.168.245.70:9060/agile/issue/sync/stage" ;
        if(StringUtils.isNotEmpty(staticDataValue)){
            this.uri = staticDataValue;
        }
        else{
            throw new MyException(issueSyncState_Error);
        }
    }
    /**
     * 设置issue表的cmp_sync_result 的值
     * @param list
     * @param cmpSyncResult
     */
    private void syncResult(List<IssueDTO> list,Byte cmpSyncResult){
        List<IssueDTO> result = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(list)){
            for (IssueDTO issueDTO:list) {
                IssueDTO issueDTO1 = new IssueDTO();
                issueDTO1.setIssueId(issueDTO.getIssueId());
                issueDTO1.setCmpSyncResult(cmpSyncResult);
                result.add(issueDTO1);
            }
            issueService.updateIssueByIssueId(list);
        }
    }
}
