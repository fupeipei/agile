package com.yusys.agile.requirement.service;

import com.yusys.agile.requirement.domain.SysExtendField;

import java.util.List;

public interface SysExtendFieldService {



    List<SysExtendField> getAllSysExtendField(Byte issueType);
}
