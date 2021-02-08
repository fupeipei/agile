package com.yusys.agile.businesskanban.service;

import com.yusys.agile.businesskanban.dto.BusinessKanbanDTO;
import com.yusys.agile.utils.page.PageQuery;


import java.util.List;

/**
 * @Date: 2021/2/1
 * @Description: 事务看板服务
 */
public interface BusinessKanbanService {

    /**
     * @Date: 2021/2/1
     * @Description: 创建事务看板
     * @Param: [businessKanbanDTO]
     * @Return: int
     *
     */
    int createBusinessKanban(BusinessKanbanDTO businessKanbanDTO);

    /**
     * @Date: 2021/2/1
     * @Description: 删除事务看板
     * @Param: [kanbanId]
     * @Return: int
     *
     */
    int deleteBusinessKanban(Long kanbanId);

    /**
     * @Date: 2021/2/1
     * @Description: 更新事务看板
     * @Param: [businessKanbanDTO]
     * @Return: int
     *
     */
    int updateBusinessKanban(BusinessKanbanDTO businessKanbanDTO);

    /**
     * @Date: 2021/2/1
     * @Description: 查询看板列表
     * @Param: [query]
     * @Return: java.util.List<com.yusys.agile.businesskanban.dto.BusinessKanbanDTO>
     *
     */
    List<BusinessKanbanDTO> getBusinessKanbanList(PageQuery<BusinessKanbanDTO> query) throws Exception;

    /**
     * @Date: 2021/2/1
     * @Description: 查看看板列表不带分页
     * @Param: * @param businessKanbanDTO
     * @Return: java.util.List<com.yusys.agile.businesskanban.dto.BusinessKanbanDTO>
     */
    List<BusinessKanbanDTO> selectBusinessKanbanListNoPage(BusinessKanbanDTO businessKanbanDTO) throws Exception;

    /**
     * @Date: 2021/2/1
     * @Description:
     * @Param: [query]
     * @Return: int
     *
     */
    int countBusinessKanbanList(PageQuery<BusinessKanbanDTO> query);


}
