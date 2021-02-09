package com.yusys.agile.sysextendfield.service;

import com.yusys.agile.sysextendfield.domain.SysExtendField;

import java.util.List;

public interface SysExtendFieldService {

    List<SysExtendField> getAllSysExtendField(Byte issueType);

    List<SysExtendField> getField(Byte issueType);
}
