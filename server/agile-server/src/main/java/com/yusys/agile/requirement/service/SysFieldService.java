package com.yusys.agile.requirement.service;

import com.yusys.agile.requirement.domain.SysField;

import java.util.List;

/**
 *   :
 * @Date: 2021/3/15
 * @Description: TODO
 */
public interface  SysFieldService {

    List<SysField> getAllSysField(Byte issueType);
}
