package com.yusys.agile.headerfielduser.service.imp;

import com.yusys.agile.headerfield.domain.HeaderField;
import com.yusys.agile.headerfield.service.HeaderFieldService;
import com.yusys.agile.headerfielduser.dao.HeaderFieldUserMapper;
import com.yusys.agile.headerfielduser.domain.HeaderFieldUser;
import com.yusys.agile.headerfielduser.domain.HeaderFieldUserExample;
import com.yusys.agile.headerfielduser.dto.HeaderFieldListDTO;
import com.yusys.agile.headerfielduser.service.HeaderFieldUserService;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import com.yusys.portal.util.thread.UserThreadLocalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * :
 *
 * @Date: 2020/4/14
 */
@Service("headerFieldUserService")
public class HeaderFieldUserServiceImpl implements HeaderFieldUserService {
    private static final Logger log = LoggerFactory.getLogger(HeaderFieldUserServiceImpl.class);
    @Resource
    private HeaderFieldService headerFieldService;
    @Resource
    private HeaderFieldUserMapper headerFieldUserMapper;

    /**
     * 功能描述  查询应用的已排序的列头字段、高级搜索条件
     *
     * @param securityDTO
     * @param category
     * @return java.util.List<com.yusys.agile.headerfield.dto.HeaderFieldDTO>
     * @date 2020/4/14
     */
    @Override
    public List<HeaderFieldUser> queryVisibleHeaderFields(SecurityDTO securityDTO, Byte category, Byte isFilter) {
        HeaderFieldUserExample headerFieldUserExample = new HeaderFieldUserExample();
        headerFieldUserExample.setOrderByClause(" header_user_id asc");
        HeaderFieldUserExample.Criteria criteria = headerFieldUserExample.createCriteria();
        criteria.andUserIdEqualTo(securityDTO.getUserId())
                .andProjectIdEqualTo(securityDTO.getProjectId());
        if (category != null) {
            criteria.andCategoryEqualTo(category);
        }
        if (isFilter != null) {
            criteria.andIsFilterEqualTo(isFilter);
        } else {
            criteria.andIsFilterIsNull();
        }
        return headerFieldUserMapper.selectByExample(headerFieldUserExample);
    }

    /**
     * 功能描述  更新排序应用的列头,或者查询条件
     *
     * @param headerFieldListDTO
     * @param projectId
     * @return java.lang.Integer
     * @date 2020/4/15
     */
    @Override
    @Transactional
    public Map updateHeaderFieldUserList(HeaderFieldListDTO headerFieldListDTO, Long projectId) {
        Long userId = UserThreadLocalUtil.getUserInfo().getUserId();
        try {
            List<HeaderField> headerFields = headerFieldService.getAllHeaderField(headerFieldListDTO.getUpdateList());
            Map<Long, List<HeaderField>> headerFieldsMap = headerFields.stream().collect(Collectors.groupingBy(HeaderField::getFieldId));
            HeaderFieldUserExample headerFieldUserExample = new HeaderFieldUserExample();

            HeaderFieldUserExample.Criteria criteriaDelete = headerFieldUserExample.createCriteria();
            criteriaDelete
                    .andProjectIdEqualTo(projectId)
                    .andUserIdEqualTo(userId);
            if (headerFieldListDTO.getCategory() != null) {
                criteriaDelete.andCategoryEqualTo(headerFieldListDTO.getCategory());
            }

            if (headerFieldListDTO.getIsFilter() != null && Byte.parseByte("1") == headerFieldListDTO.getIsFilter()) {
                criteriaDelete.andIsFilterEqualTo(Byte.parseByte("1"));
            }
            headerFieldUserMapper.deleteByExample(headerFieldUserExample);
            for (int i = 0; i < headerFieldListDTO.getUpdateList().size(); i++) {
                HeaderFieldUser headerFieldUser = new HeaderFieldUser();
                headerFieldUser.setFieldId(headerFieldListDTO.getUpdateList().get(i));
                headerFieldUser.setFieldType(headerFieldsMap.get(headerFieldListDTO.getUpdateList().get(i)).get(0).getFieldType());
                headerFieldUser.setOrderNo(i);
                headerFieldUser.setProjectId(projectId);
                headerFieldUser.setUserId(userId);
                headerFieldUser.setCategory(headerFieldListDTO.getCategory());
                if (headerFieldListDTO.getIsFilter() != null && Byte.parseByte("1") == headerFieldListDTO.getIsFilter()) {
                    headerFieldUser.setIsFilter(Byte.parseByte("1"));
                }
                headerFieldUserMapper.insertSelective(headerFieldUser);
            }
            SecurityDTO securityDTO = new SecurityDTO();
            securityDTO.setProjectId(projectId);
            securityDTO.setUserId(userId);
            return headerFieldService.queryHeaderFields(securityDTO, headerFieldListDTO.getCategory(), headerFieldListDTO.getIsFilter());
        } catch (Exception e) {
            log.error("更新列头异常" + e);
        }
        return null;
    }

    @Override
    public Integer deleteCustomField(Long fieldId) {
        HeaderFieldUserExample headerFieldUserExample = new HeaderFieldUserExample();
        headerFieldUserExample.createCriteria()
                .andFieldIdEqualTo(fieldId);
        return headerFieldUserMapper.deleteByExample(headerFieldUserExample);
    }
}
