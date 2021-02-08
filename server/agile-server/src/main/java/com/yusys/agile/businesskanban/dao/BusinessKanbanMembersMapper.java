package com.yusys.agile.businesskanban.dao;

import com.yusys.agile.businesskanban.domain.BusinessKanbanMembers;
import com.yusys.agile.businesskanban.domain.BusinessKanbanMembersExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BusinessKanbanMembersMapper {
    long countByExample(BusinessKanbanMembersExample example);

    int deleteByExample(BusinessKanbanMembersExample example);

    int deleteByPrimaryKey(Long id);

    int insert(BusinessKanbanMembers record);

    int insertSelective(BusinessKanbanMembers record);

    List<BusinessKanbanMembers> selectByExample(BusinessKanbanMembersExample example);

    BusinessKanbanMembers selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") BusinessKanbanMembers record, @Param("example") BusinessKanbanMembersExample example);

    int updateByExample(@Param("record") BusinessKanbanMembers record, @Param("example") BusinessKanbanMembersExample example);

    int updateByPrimaryKeySelective(BusinessKanbanMembers record);

    int updateByPrimaryKey(BusinessKanbanMembers record);

    /**
     *   : zhaoyd6
     * @Date: 2020/4/30
     * @Description: 批量创建看板成员
     * @Param: [businessKanbanMembers]
     * @Return: int
     *
     */
    int batchCreate(@Param("collection") List<BusinessKanbanMembers> businessKanbanMembers);

}