package com.yusys.agile.dict.service;

import com.yusys.agile.dict.domain.SysDictDetail;

import java.util.List;

public interface SysDictService {

    /**
     *功能描述 查询局方负责人
     * @date 2021/2/1
      * @param pageNum
     * @param pageSize
     * @param detailName
     * @return java.util.List<com.yusys.agile.dict.domain.SysDictDetail>
     */
    List<SysDictDetail> getResponsiblePerson(Integer pageNum, Integer pageSize, String detailName);
}
