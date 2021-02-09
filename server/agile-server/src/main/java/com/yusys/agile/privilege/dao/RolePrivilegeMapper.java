package com.yusys.agile.privilege.dao;

import com.yusys.agile.privilege.domain.RolePrivilege;
import com.yusys.agile.privilege.domain.RolePrivilegeExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface RolePrivilegeMapper {
    long countByExample(RolePrivilegeExample example);

    int deleteByExample(RolePrivilegeExample example);

    int insert(RolePrivilege record);

    int insertSelective(RolePrivilege record);

    List<RolePrivilege> selectByExample(RolePrivilegeExample example);

    int updateByExampleSelective(@Param("record") RolePrivilege record, @Param("example") RolePrivilegeExample example);

    int updateByExample(@Param("record") RolePrivilege record, @Param("example") RolePrivilegeExample example);
}