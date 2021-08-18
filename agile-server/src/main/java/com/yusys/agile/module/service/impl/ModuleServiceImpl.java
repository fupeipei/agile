package com.yusys.agile.module.service.impl;

import com.yusys.agile.module.dao.ModuleMapper;
import com.yusys.agile.module.domain.Module;
import com.yusys.agile.module.domain.ModuleExample;
import com.yusys.agile.module.dto.ModuleDTO;
import com.yusys.agile.module.service.ModuleService;
import com.yusys.agile.utils.RoleCheck;
import com.github.pagehelper.PageHelper;
import com.yusys.portal.common.exception.BusinessException;
import com.yusys.portal.facade.client.api.IFacadeSystemApi;
import com.yusys.portal.facade.client.api.IFacadeUserApi;
import com.yusys.portal.model.common.enums.StateEnum;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import com.yusys.portal.model.facade.entity.SsoSystem;
import com.yusys.portal.model.facade.entity.SsoUser;
import com.yusys.portal.util.code.ReflectUtil;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @ClassName: ModuleServiceImpl
 * @Description: 模块管理 Service实现类
 * :
 * @CreateDate: 2020/05/25 16:15
 * @Version 1.0
 */
@Service
public class ModuleServiceImpl implements ModuleService {
    private final static Logger log = LoggerFactory.getLogger(ModuleServiceImpl.class);

    @Resource
    private ModuleMapper moduleMapper;
    @Autowired
    private IFacadeSystemApi iFacadeSystemApi;
    @Autowired
    private RoleCheck roleCheck;
    @Autowired
    private IFacadeUserApi iFacadeUserApi;
    /**
     * 百分号
     */
    private static final String PERCENT_SIGN = "%";

    private static final String CREATE_TIME_DESC = "CREATE_TIME DESC";

    @Override
    public List<ModuleDTO> listModule(String moduleName, Integer pageNum, Integer pageSize, Long systemId) {
        PageHelper.startPage(pageNum, pageSize);
        ModuleExample example = new ModuleExample();
        ModuleExample.Criteria criteria = example.createCriteria();
        criteria.andStateEqualTo(StateEnum.U.toString()).andSystemIdEqualTo(systemId);
        if (StringUtils.isNotEmpty(moduleName)) {
            criteria.andModuleNameLike(StringUtils.join(PERCENT_SIGN, moduleName, PERCENT_SIGN));
        }
        example.setOrderByClause(CREATE_TIME_DESC);
        List<ModuleDTO> modules = moduleMapper.selectByExampleWithBLOBsDTO(example);
        if (CollectionUtils.isNotEmpty(modules)) {
            modules.forEach(module -> {
//                SsoSystem ssoSystem = iFacadeSystemApi.querySystemBySystemId(module.getSystemId());
//                if (Optional.ofNullable(ssoSystem).isPresent()) {
//                    module.setProductName(ssoSystem.getSystemName());
//                }
                SsoUser ssoUser = iFacadeUserApi.queryUserById(module.getCreateUid());
                if (Optional.ofNullable(ssoUser).isPresent()){
                    module.setUserAccount(ssoUser.getUserAccount());
                    module.setUserName(ssoUser.getUserName());
                }
            });
        }
        return modules;
    }

    @Override
    public Module createOrUpdateModule(ModuleDTO moduleDTO) {
        Module module = ReflectUtil.copyProperties(moduleDTO, Module.class);
        if (Optional.ofNullable(module.getModuleId()).isPresent()) {
            Module module1 = moduleMapper.selectByPrimaryKey(module.getModuleId());
            if(!Optional.ofNullable(module1).isPresent()){
                throw new BusinessException("更新的模块信息不存在");
            }
           // Optional.ofNullable(module1).orElseThrow(() -> new BusinessException("更新的模块信息不存在"));
            moduleMapper.updateByPrimaryKeySelective(module);
        } else {
            moduleMapper.insert(module);
        }
        return module;
    }

    @Override
    public void deleteModule(Long moduleId) {
        ModuleExample example = new ModuleExample();
        ModuleExample.Criteria criteria = example.createCriteria();
        criteria.andModuleIdEqualTo(moduleId).andStateEqualTo(StateEnum.U.toString());
        List<Module> modules = moduleMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(modules)) {
            Module module = modules.get(0);
            module.setState(StateEnum.E.toString());
            moduleMapper.updateByPrimaryKeySelective(module);
        }
    }

    @Override
    public Module detailModule(Long moduleId) {
        return moduleMapper.selectByPrimaryKey(moduleId);
    }

    @Override
    public List<Module> listModuleBySystemId(Long systemId) {
        ModuleExample example = new ModuleExample();
        ModuleExample.Criteria criteria = example.createCriteria();
        criteria.andSystemIdEqualTo(systemId)
                .andStateEqualTo(StateEnum.U.toString());
        example.setOrderByClause(CREATE_TIME_DESC);
        List<Module> modules = moduleMapper.selectByExampleWithBLOBs(example);
        return modules;
    }

    @Override
    public boolean checkModuleNameUnique(String moduleName, Long moduleId) {
        ModuleExample example = new ModuleExample();
        ModuleExample.Criteria criteria = example.createCriteria();
        criteria.andModuleNameEqualTo(moduleName)
                .andStateEqualTo(StateEnum.U.toString());
        if (Optional.ofNullable(moduleId).isPresent()) {
            criteria.andModuleIdNotEqualTo(moduleId);
        }
        List<Module> modules = moduleMapper.selectByExampleWithBLOBs(example);
        if (CollectionUtils.isNotEmpty(modules)) {
            return false;
        }
        return true;
    }

    @Override
    public JSONObject listModuleBySystemIds(List<Long> systemIds) {
        ModuleExample example = new ModuleExample();
        ModuleExample.Criteria criteria = example.createCriteria();
        criteria.andSystemIdIn(systemIds)
                .andStateEqualTo(StateEnum.U.toString());
        example.setOrderByClause(CREATE_TIME_DESC);
        List<Module> modules = moduleMapper.selectByExampleWithBLOBs(example);
        //options: [{ label: '热门城市', options: [{ value: 'Shanghai', label: '上海' }, { value: 'Beijing', label: '北京' }] }, { label: '城市名', options: [{ value: 'Chengdu', label: '成都' }, { value: 'Shenzhen', label: '深圳' }, { value: 'Guangzhou', label: '广州' }, { value: 'Dalian', label: '大连' }] }],
        Map<Long, List<JSONObject>> resultMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(modules)) {
            modules.forEach(module -> {
                Long systemId = module.getSystemId();

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("label", module.getModuleName());
                jsonObject.put("value", module.getModuleId());

                if (resultMap.containsKey(systemId)) {
                    List<JSONObject> resultList = resultMap.get(systemId);
                    resultList.add(jsonObject);
                } else {
                    List<JSONObject> resultList = new ArrayList() {
                        {
                            add(jsonObject);
                        }
                    };
                    resultMap.put(systemId, resultList);
                }

            });
        }
        List<JSONObject> optionsList = new ArrayList<>();
        if (!resultMap.isEmpty()) {
            resultMap.forEach((k, v) -> {
                JSONObject resultJson = new JSONObject();
                SsoSystem ssoSystem = iFacadeSystemApi.querySystemBySystemId(k);
                if (Optional.ofNullable(ssoSystem).isPresent()) {
                    resultJson.put("label", ssoSystem.getSystemName());
                    resultJson.put("options", v);
                    optionsList.add(resultJson);
                }
            });
        }
        JSONObject resposeJson = new JSONObject();
        resposeJson.put("options", optionsList);
        return resposeJson;
    }

    @Override
    public List<Module> listModule() {
        ModuleExample example = new ModuleExample();
        ModuleExample.Criteria criteria = example.createCriteria();
        criteria.andStateEqualTo(StateEnum.U.toString());
        return moduleMapper.selectByExampleWithBLOBs(example);
    }
}
