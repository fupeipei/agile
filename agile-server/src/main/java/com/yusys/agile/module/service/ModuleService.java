package com.yusys.agile.module.service;


import com.yusys.agile.module.domain.Module;
import com.yusys.agile.module.dto.ModuleDTO;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * @ClassName: ModuleService
 * @Description: 模块管理Service
 * :
 * @CreateDate: 2020/05/25 16:15
 * @Version 1.0
 */
public interface ModuleService {

    /**
     * 获取Module分页列表
     *
     * @param moduleName 模块标识
     * @param pageNum    分页数
     * @param pageSize   分页条数
     * @param systemId  系统ID
     * @return
     */
    List<ModuleDTO> listModule(String moduleName, Integer pageNum, Integer pageSize, Long systemId);

    /**
     * 模块创建/更新
     *
     * @param moduleDTO   模块Body
     * @return
     */
    Module createOrUpdateModule(ModuleDTO moduleDTO);

    /**
     * 模块删除
     *
     * @param moduleId    模块ID
     * @return
     */
    void deleteModule(Long moduleId);

    /**
     * 模块详情
     *
     * @param moduleId 模块ID
     * @return
     */
    Module detailModule(Long moduleId);

    /**
     * 根据产品ID查询模块列表
     *
     * @param systemId
     * @return
     */
    List<Module> listModuleBySystemId(Long systemId);

    /**
     * 校验模块标题唯一性
     *
     * @param moduleName 模块名称
     * @param moduleId   模块编号
     * @return
     */
    boolean checkModuleNameUnique(String moduleName, Long moduleId);

    /**
     * 根据产品ID集合查询模块列表
     *
     * @param systemIds
     * @return
     */
    JSONObject listModuleBySystemIds(List<Long> systemIds);


    /**
     * 根据所有查询模块列表
     *
     * @return
     */
    List<Module> listModule();
}
