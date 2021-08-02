package com.yusys.agile.scheduleplan.dao;

import com.yusys.agile.scheduleplan.domain.SEpicSystemRelate;
import com.yusys.agile.scheduleplan.domain.SEpicSystemRelateExample;
import java.util.List;

import com.yusys.agile.scheduleplan.dto.ToDoListDTO;
import org.apache.ibatis.annotations.Param;

public interface SEpicSystemRelateMapper {
    long countByExample(SEpicSystemRelateExample example);

    int deleteByExample(SEpicSystemRelateExample example);

    int deleteByPrimaryKey(Long relateId);

    int insert(SEpicSystemRelate record);

    int insertSelective(SEpicSystemRelate record);

    List<SEpicSystemRelate> selectByExample(SEpicSystemRelateExample example);

    SEpicSystemRelate selectByPrimaryKey(Long relateId);

    int updateByExampleSelective(@Param("record") SEpicSystemRelate record, @Param("example") SEpicSystemRelateExample example);

    int updateByExample(@Param("record") SEpicSystemRelate record, @Param("example") SEpicSystemRelateExample example);

    int updateByPrimaryKeySelective(SEpicSystemRelate record);

    int updateByPrimaryKey(SEpicSystemRelate record);

    List<ToDoListDTO> queryToDoList(@Param("epicId")Long epicId,@Param("title")String title,@Param("userId") Long userId,@Param("tenantCode")String tenantCode);
}