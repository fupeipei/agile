package com.yusys.agile.module.dao;

import com.yusys.agile.module.domain.Module;
import com.yusys.agile.module.domain.ModuleExample;
import com.yusys.agile.module.dto.ModuleDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ModuleMapper {
    long countByExample(ModuleExample example);

    int deleteByExample(ModuleExample example);

    int deleteByPrimaryKey(Long moduleId);

    int insert(Module record);

    int insertSelective(Module record);

    List<Module> selectByExampleWithBLOBs(ModuleExample example);

    List<ModuleDTO> selectByExampleWithBLOBsDTO(ModuleExample example);

    List<Module> selectByExample(ModuleExample example);

    Module selectByPrimaryKey(Long moduleId);

    int updateByExampleSelective(@Param("record") Module record, @Param("example") ModuleExample example);

    int updateByExampleWithBLOBs(@Param("record") Module record, @Param("example") ModuleExample example);

    int updateByExample(@Param("record") Module record, @Param("example") ModuleExample example);

    int updateByPrimaryKeySelective(Module record);

    int updateByPrimaryKeyWithBLOBs(Module record);

    int updateByPrimaryKey(Module record);
}