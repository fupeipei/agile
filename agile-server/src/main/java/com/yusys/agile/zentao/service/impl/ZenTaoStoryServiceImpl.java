package com.yusys.agile.zentao.service.impl;

import com.yusys.agile.utils.ReflectObjectUtil;
import com.yusys.agile.zentao.dao.ZtModuleMapper;
import com.yusys.agile.zentao.dao.ZtProductMapper;
import com.yusys.agile.zentao.dao.ZtStoryMapper;
import com.yusys.agile.zentao.dao.ZtStoryspecMapper;
import com.yusys.agile.zentao.domain.*;
import com.yusys.agile.zentao.dto.ZtStoryDTO;
import com.yusys.agile.zentao.service.ZenTaoStoryService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ZenTaoStoryServiceImpl implements ZenTaoStoryService {


    @Resource
    private ZtStoryMapper ztStoryMapper;
    @Resource
    private ZtProductMapper ztProductMapper;
    @Resource
    private ZtModuleMapper  ztModuleMapper;
    @Resource
    private ZtStoryspecMapper ztStoryspecMapper;

    @Override
  // @Transactional(rollbackFor =  Exception.class,propagation = Propagation.REQUIRES_NEW,transactionManager.)
    public void addStory(ZtStoryDTO ztStoryDTO) {

        String productCode = ztStoryDTO.getCode();
        ZtStoryWithBLOBs ztStory = ReflectObjectUtil.copyProperties(ztStoryDTO,ZtStoryWithBLOBs.class);

        //根据产品编码获取对应的产品Id，无则新增
        ZtProductExample ztProductExample = new ZtProductExample();
        ztProductExample.createCriteria().andCodeEqualTo(productCode).andDeletedEqualTo("0");
        List<ZtProductWithBLOBs> ztProducts =  ztProductMapper.selectByExampleWithBLOBs(ztProductExample);
        if(CollectionUtils.isEmpty(ztProducts)){
            ZtProductWithBLOBs record = new ZtProductWithBLOBs();
            record.setCode(productCode);
            record.setName(productCode);
            record.setLine(0);
            record.setType("normal");
            record.setStatus("normal");
            record.setCreatedby("admin");
            record.setDeleted("0");
            record.setDesc("");
            record.setPo("");
            record.setQd("");
            record.setRd("");
            record.setWhitelist("");
            record.setCreateddate(new Date());
            record.setCreatedversion("10.0");
            record.setOrder(5);
            ztProductMapper.insertSelective(record);
            ztProducts =  ztProductMapper.selectByExampleWithBLOBs(ztProductExample);
        }
        ZtProduct ztProduct = ztProducts.get(0);
        ztStory.setProduct(ztProduct.getId());
        //获取模块id,无则新增
        ZtModuleExample ztModuleExample = new ZtModuleExample();
        ztModuleExample.createCriteria().andDeletedEqualTo("0");
        List<ZtModule> ztModules = ztModuleMapper.selectByExample(ztModuleExample);
        if(CollectionUtils.isEmpty(ztModules)){
            ZtModule ztModule = new ZtModule();
            ztModule.setBranch(0);
            ztModule.setRoot(1);
            ztModule.setName("默认模块");
            ztModule.setParent(0);
            ztModule.setPath(",1,");
            ztModule.setGrade((byte) 1);
            ztModule.setOrder((short) 10);
            ztModule.setType("story");
            ztModule.setDeleted("0");
            ztModule.setOwner("");
            ztModule.setCollector("");
            ztModuleMapper.insertSelective(ztModule);
            ztModules = ztModuleMapper.selectByExample(ztModuleExample);
        }
        ZtModule ztModule  = ztModules.get(0);
        ztStory.setModule(ztModule.getId());
        ztStory.setStatus("active");
        ztStory.setStage("projected");
        ztStory.setOpenedby("admin");
        ztStory.setTobug(0);
        ztStory.setDuplicatestory(0);
        ztStory.setVersion((short) 1);
        ztStory.setDeleted("0");
        ztStory.setEstimate((float) 0);
        ztStory.setPri((byte) 0);
        ztStory.setSource("");
        ztStory.setSourcenote("");
        ztStory.setOpeneddate(new Date());
        ztStory.setMailto("");
        ztStory.setAssigneddate(new Date());
        ztStory.setLastediteddate(new Date());
        ztStory.setReviewedby("");
        ztStory.setRevieweddate(new Date());
        ztStory.setCloseddate(new Date());
        ztStory.setClosedby("");
        ztStory.setClosedreason("");
        ztStory.setChildstories("");
        ztStory.setLinkstories("");
        ztStory.setKeywords("");
        ztStory.setColor("");
        ztStoryMapper.insertSelective(ztStory);
        ZtStoryExample example = new ZtStoryExample();
        example.createCriteria().andTitleEqualTo(ztStory.getTitle()).andDeletedEqualTo("0");
        List<ZtStory>  ztStories = ztStoryMapper.selectByExample(example);
        if(CollectionUtils.isNotEmpty(ztStories)){
            ZtStory ztStory1 = ztStories.get(0);
            ZtStoryspecWithBLOBs record = new ZtStoryspecWithBLOBs();
            record.setStory(ztStory1.getId());
            record.setSpec("");
            record.setVerify("");
            record.setVersion((short) 1);
            record.setTitle(ztStory1.getTitle());
            ztStoryspecMapper.insertSelective(record);
        }

    }
}
