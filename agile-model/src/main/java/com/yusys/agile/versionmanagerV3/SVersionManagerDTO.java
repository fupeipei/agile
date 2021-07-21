package com.yusys.agile.versionmanagerV3;

import com.google.common.collect.Lists;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author 李艮艮
 */
@Data
public class SVersionManagerDTO implements Serializable {

    private Long versionManagerId;

    private String versionName;

    private String versionDescribe;

    private Date releaseDate;

    private Date raiseTestDate;

    private int relateNum = 0;

    private String createName;

    private Date createTime;

    private List<Long> versionIssueRelateIds;

    private List<SVersionIssueRelateDTO> sVersionIssueRelateDTOList = Lists.newArrayList();


}
