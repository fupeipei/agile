package com.yusys.agile.businesskanban.dao;

import com.yusys.agile.businesskanban.domain.BusinessKanban;
import com.yusys.agile.businesskanban.domain.BusinessKanbanExample;
import com.yusys.agile.businesskanban.dto.BusinessKanbanDTO;
import com.yusys.agile.utils.page.PageQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BusinessKanbanMapper {
    long countByExample(BusinessKanbanExample example);

    int deleteByExample(BusinessKanbanExample example);

    int deleteByPrimaryKey(Long kanbanId);

    int insert(BusinessKanban record);

    int insertSelective(BusinessKanban record);

    List<BusinessKanban> selectByExample(BusinessKanbanExample example);

    BusinessKanban selectByPrimaryKey(Long kanbanId);

    int updateByExampleSelective(@Param("record") BusinessKanban record, @Param("example") BusinessKanbanExample example);

    int updateByExample(@Param("record") BusinessKanban record, @Param("example") BusinessKanbanExample example);

    int updateByPrimaryKeySelective(BusinessKanban record);

    int updateByPrimaryKey(BusinessKanban record);


    /**
     *
     * @Date: 2021/2/6
     * @Description: 按条件查询看板信息
     * @Param: [query]
     * @Return: java.util.List<com.yusys.agile.businesskanban.domain.BusinessKanban>
     *
     */
    List<BusinessKanban> selectByProjectId(PageQuery<BusinessKanbanDTO> query);

    /**
     *
     * @Date: 2021/2/6
     * @Description: 查询总记录数
     * @Param: [query]
     * @Return: int
     *
     */
    int countBusinessKanbanList(PageQuery<BusinessKanbanDTO> query);

    /**
     *
     * @Date: 2021/2/16 17:26
     * @Description: 获取看板列表（无分页）
     * @Param: * @param query
     * @Return: java.util.List<com.yusys.agile.businesskanban.domain.BusinessKanban>
     */
    List<BusinessKanban> selectByProjectIdNoPage(BusinessKanbanDTO query);


}