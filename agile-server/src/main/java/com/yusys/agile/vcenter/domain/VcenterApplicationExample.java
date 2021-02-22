package com.yusys.agile.vcenter.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VcenterApplicationExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public VcenterApplicationExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andDevIdIsNull() {
            addCriterion("dev_id is null");
            return (Criteria) this;
        }

        public Criteria andDevIdIsNotNull() {
            addCriterion("dev_id is not null");
            return (Criteria) this;
        }

        public Criteria andDevIdEqualTo(Long value) {
            addCriterion("dev_id =", value, "devId");
            return (Criteria) this;
        }

        public Criteria andDevIdNotEqualTo(Long value) {
            addCriterion("dev_id <>", value, "devId");
            return (Criteria) this;
        }

        public Criteria andDevIdGreaterThan(Long value) {
            addCriterion("dev_id >", value, "devId");
            return (Criteria) this;
        }

        public Criteria andDevIdGreaterThanOrEqualTo(Long value) {
            addCriterion("dev_id >=", value, "devId");
            return (Criteria) this;
        }

        public Criteria andDevIdLessThan(Long value) {
            addCriterion("dev_id <", value, "devId");
            return (Criteria) this;
        }

        public Criteria andDevIdLessThanOrEqualTo(Long value) {
            addCriterion("dev_id <=", value, "devId");
            return (Criteria) this;
        }

        public Criteria andDevIdIn(List<Long> values) {
            addCriterion("dev_id in", values, "devId");
            return (Criteria) this;
        }

        public Criteria andDevIdNotIn(List<Long> values) {
            addCriterion("dev_id not in", values, "devId");
            return (Criteria) this;
        }

        public Criteria andDevIdBetween(Long value1, Long value2) {
            addCriterion("dev_id between", value1, value2, "devId");
            return (Criteria) this;
        }

        public Criteria andDevIdNotBetween(Long value1, Long value2) {
            addCriterion("dev_id not between", value1, value2, "devId");
            return (Criteria) this;
        }

        public Criteria andTemplateNameIsNull() {
            addCriterion("template_name is null");
            return (Criteria) this;
        }

        public Criteria andTemplateNameIsNotNull() {
            addCriterion("template_name is not null");
            return (Criteria) this;
        }

        public Criteria andTemplateNameEqualTo(String value) {
            addCriterion("template_name =", value, "templateName");
            return (Criteria) this;
        }

        public Criteria andTemplateNameNotEqualTo(String value) {
            addCriterion("template_name <>", value, "templateName");
            return (Criteria) this;
        }

        public Criteria andTemplateNameGreaterThan(String value) {
            addCriterion("template_name >", value, "templateName");
            return (Criteria) this;
        }

        public Criteria andTemplateNameGreaterThanOrEqualTo(String value) {
            addCriterion("template_name >=", value, "templateName");
            return (Criteria) this;
        }

        public Criteria andTemplateNameLessThan(String value) {
            addCriterion("template_name <", value, "templateName");
            return (Criteria) this;
        }

        public Criteria andTemplateNameLessThanOrEqualTo(String value) {
            addCriterion("template_name <=", value, "templateName");
            return (Criteria) this;
        }

        public Criteria andTemplateNameLike(String value) {
            addCriterion("template_name like", value, "templateName");
            return (Criteria) this;
        }

        public Criteria andTemplateNameNotLike(String value) {
            addCriterion("template_name not like", value, "templateName");
            return (Criteria) this;
        }

        public Criteria andTemplateNameIn(List<String> values) {
            addCriterion("template_name in", values, "templateName");
            return (Criteria) this;
        }

        public Criteria andTemplateNameNotIn(List<String> values) {
            addCriterion("template_name not in", values, "templateName");
            return (Criteria) this;
        }

        public Criteria andTemplateNameBetween(String value1, String value2) {
            addCriterion("template_name between", value1, value2, "templateName");
            return (Criteria) this;
        }

        public Criteria andTemplateNameNotBetween(String value1, String value2) {
            addCriterion("template_name not between", value1, value2, "templateName");
            return (Criteria) this;
        }

        public Criteria andVirtualMachineNameIsNull() {
            addCriterion("virtual_machine_name is null");
            return (Criteria) this;
        }

        public Criteria andVirtualMachineNameIsNotNull() {
            addCriterion("virtual_machine_name is not null");
            return (Criteria) this;
        }

        public Criteria andVirtualMachineNameEqualTo(String value) {
            addCriterion("virtual_machine_name =", value, "virtualMachineName");
            return (Criteria) this;
        }

        public Criteria andVirtualMachineNameNotEqualTo(String value) {
            addCriterion("virtual_machine_name <>", value, "virtualMachineName");
            return (Criteria) this;
        }

        public Criteria andVirtualMachineNameGreaterThan(String value) {
            addCriterion("virtual_machine_name >", value, "virtualMachineName");
            return (Criteria) this;
        }

        public Criteria andVirtualMachineNameGreaterThanOrEqualTo(String value) {
            addCriterion("virtual_machine_name >=", value, "virtualMachineName");
            return (Criteria) this;
        }

        public Criteria andVirtualMachineNameLessThan(String value) {
            addCriterion("virtual_machine_name <", value, "virtualMachineName");
            return (Criteria) this;
        }

        public Criteria andVirtualMachineNameLessThanOrEqualTo(String value) {
            addCriterion("virtual_machine_name <=", value, "virtualMachineName");
            return (Criteria) this;
        }

        public Criteria andVirtualMachineNameLike(String value) {
            addCriterion("virtual_machine_name like", value, "virtualMachineName");
            return (Criteria) this;
        }

        public Criteria andVirtualMachineNameNotLike(String value) {
            addCriterion("virtual_machine_name not like", value, "virtualMachineName");
            return (Criteria) this;
        }

        public Criteria andVirtualMachineNameIn(List<String> values) {
            addCriterion("virtual_machine_name in", values, "virtualMachineName");
            return (Criteria) this;
        }

        public Criteria andVirtualMachineNameNotIn(List<String> values) {
            addCriterion("virtual_machine_name not in", values, "virtualMachineName");
            return (Criteria) this;
        }

        public Criteria andVirtualMachineNameBetween(String value1, String value2) {
            addCriterion("virtual_machine_name between", value1, value2, "virtualMachineName");
            return (Criteria) this;
        }

        public Criteria andVirtualMachineNameNotBetween(String value1, String value2) {
            addCriterion("virtual_machine_name not between", value1, value2, "virtualMachineName");
            return (Criteria) this;
        }

        public Criteria andCpuNumIsNull() {
            addCriterion("cpu_num is null");
            return (Criteria) this;
        }

        public Criteria andCpuNumIsNotNull() {
            addCriterion("cpu_num is not null");
            return (Criteria) this;
        }

        public Criteria andCpuNumEqualTo(Byte value) {
            addCriterion("cpu_num =", value, "cpuNum");
            return (Criteria) this;
        }

        public Criteria andCpuNumNotEqualTo(Byte value) {
            addCriterion("cpu_num <>", value, "cpuNum");
            return (Criteria) this;
        }

        public Criteria andCpuNumGreaterThan(Byte value) {
            addCriterion("cpu_num >", value, "cpuNum");
            return (Criteria) this;
        }

        public Criteria andCpuNumGreaterThanOrEqualTo(Byte value) {
            addCriterion("cpu_num >=", value, "cpuNum");
            return (Criteria) this;
        }

        public Criteria andCpuNumLessThan(Byte value) {
            addCriterion("cpu_num <", value, "cpuNum");
            return (Criteria) this;
        }

        public Criteria andCpuNumLessThanOrEqualTo(Byte value) {
            addCriterion("cpu_num <=", value, "cpuNum");
            return (Criteria) this;
        }

        public Criteria andCpuNumIn(List<Byte> values) {
            addCriterion("cpu_num in", values, "cpuNum");
            return (Criteria) this;
        }

        public Criteria andCpuNumNotIn(List<Byte> values) {
            addCriterion("cpu_num not in", values, "cpuNum");
            return (Criteria) this;
        }

        public Criteria andCpuNumBetween(Byte value1, Byte value2) {
            addCriterion("cpu_num between", value1, value2, "cpuNum");
            return (Criteria) this;
        }

        public Criteria andCpuNumNotBetween(Byte value1, Byte value2) {
            addCriterion("cpu_num not between", value1, value2, "cpuNum");
            return (Criteria) this;
        }

        public Criteria andMemoryNumIsNull() {
            addCriterion("memory_num is null");
            return (Criteria) this;
        }

        public Criteria andMemoryNumIsNotNull() {
            addCriterion("memory_num is not null");
            return (Criteria) this;
        }

        public Criteria andMemoryNumEqualTo(Long value) {
            addCriterion("memory_num =", value, "memoryNum");
            return (Criteria) this;
        }

        public Criteria andMemoryNumNotEqualTo(Long value) {
            addCriterion("memory_num <>", value, "memoryNum");
            return (Criteria) this;
        }

        public Criteria andMemoryNumGreaterThan(Long value) {
            addCriterion("memory_num >", value, "memoryNum");
            return (Criteria) this;
        }

        public Criteria andMemoryNumGreaterThanOrEqualTo(Long value) {
            addCriterion("memory_num >=", value, "memoryNum");
            return (Criteria) this;
        }

        public Criteria andMemoryNumLessThan(Long value) {
            addCriterion("memory_num <", value, "memoryNum");
            return (Criteria) this;
        }

        public Criteria andMemoryNumLessThanOrEqualTo(Long value) {
            addCriterion("memory_num <=", value, "memoryNum");
            return (Criteria) this;
        }

        public Criteria andMemoryNumIn(List<Long> values) {
            addCriterion("memory_num in", values, "memoryNum");
            return (Criteria) this;
        }

        public Criteria andMemoryNumNotIn(List<Long> values) {
            addCriterion("memory_num not in", values, "memoryNum");
            return (Criteria) this;
        }

        public Criteria andMemoryNumBetween(Long value1, Long value2) {
            addCriterion("memory_num between", value1, value2, "memoryNum");
            return (Criteria) this;
        }

        public Criteria andMemoryNumNotBetween(Long value1, Long value2) {
            addCriterion("memory_num not between", value1, value2, "memoryNum");
            return (Criteria) this;
        }

        public Criteria andDiskCapacityIsNull() {
            addCriterion("disk_capacity is null");
            return (Criteria) this;
        }

        public Criteria andDiskCapacityIsNotNull() {
            addCriterion("disk_capacity is not null");
            return (Criteria) this;
        }

        public Criteria andDiskCapacityEqualTo(Long value) {
            addCriterion("disk_capacity =", value, "diskCapacity");
            return (Criteria) this;
        }

        public Criteria andDiskCapacityNotEqualTo(Long value) {
            addCriterion("disk_capacity <>", value, "diskCapacity");
            return (Criteria) this;
        }

        public Criteria andDiskCapacityGreaterThan(Long value) {
            addCriterion("disk_capacity >", value, "diskCapacity");
            return (Criteria) this;
        }

        public Criteria andDiskCapacityGreaterThanOrEqualTo(Long value) {
            addCriterion("disk_capacity >=", value, "diskCapacity");
            return (Criteria) this;
        }

        public Criteria andDiskCapacityLessThan(Long value) {
            addCriterion("disk_capacity <", value, "diskCapacity");
            return (Criteria) this;
        }

        public Criteria andDiskCapacityLessThanOrEqualTo(Long value) {
            addCriterion("disk_capacity <=", value, "diskCapacity");
            return (Criteria) this;
        }

        public Criteria andDiskCapacityIn(List<Long> values) {
            addCriterion("disk_capacity in", values, "diskCapacity");
            return (Criteria) this;
        }

        public Criteria andDiskCapacityNotIn(List<Long> values) {
            addCriterion("disk_capacity not in", values, "diskCapacity");
            return (Criteria) this;
        }

        public Criteria andDiskCapacityBetween(Long value1, Long value2) {
            addCriterion("disk_capacity between", value1, value2, "diskCapacity");
            return (Criteria) this;
        }

        public Criteria andDiskCapacityNotBetween(Long value1, Long value2) {
            addCriterion("disk_capacity not between", value1, value2, "diskCapacity");
            return (Criteria) this;
        }

        public Criteria andIpAddressIsNull() {
            addCriterion("ip_Address is null");
            return (Criteria) this;
        }

        public Criteria andIpAddressIsNotNull() {
            addCriterion("ip_Address is not null");
            return (Criteria) this;
        }

        public Criteria andIpAddressEqualTo(String value) {
            addCriterion("ip_Address =", value, "ipAddress");
            return (Criteria) this;
        }

        public Criteria andIpAddressNotEqualTo(String value) {
            addCriterion("ip_Address <>", value, "ipAddress");
            return (Criteria) this;
        }

        public Criteria andIpAddressGreaterThan(String value) {
            addCriterion("ip_Address >", value, "ipAddress");
            return (Criteria) this;
        }

        public Criteria andIpAddressGreaterThanOrEqualTo(String value) {
            addCriterion("ip_Address >=", value, "ipAddress");
            return (Criteria) this;
        }

        public Criteria andIpAddressLessThan(String value) {
            addCriterion("ip_Address <", value, "ipAddress");
            return (Criteria) this;
        }

        public Criteria andIpAddressLessThanOrEqualTo(String value) {
            addCriterion("ip_Address <=", value, "ipAddress");
            return (Criteria) this;
        }

        public Criteria andIpAddressLike(String value) {
            addCriterion("ip_Address like", value, "ipAddress");
            return (Criteria) this;
        }

        public Criteria andIpAddressNotLike(String value) {
            addCriterion("ip_Address not like", value, "ipAddress");
            return (Criteria) this;
        }

        public Criteria andIpAddressIn(List<String> values) {
            addCriterion("ip_Address in", values, "ipAddress");
            return (Criteria) this;
        }

        public Criteria andIpAddressNotIn(List<String> values) {
            addCriterion("ip_Address not in", values, "ipAddress");
            return (Criteria) this;
        }

        public Criteria andIpAddressBetween(String value1, String value2) {
            addCriterion("ip_Address between", value1, value2, "ipAddress");
            return (Criteria) this;
        }

        public Criteria andIpAddressNotBetween(String value1, String value2) {
            addCriterion("ip_Address not between", value1, value2, "ipAddress");
            return (Criteria) this;
        }

        public Criteria andSubnetMaskIsNull() {
            addCriterion("subnet_mask is null");
            return (Criteria) this;
        }

        public Criteria andSubnetMaskIsNotNull() {
            addCriterion("subnet_mask is not null");
            return (Criteria) this;
        }

        public Criteria andSubnetMaskEqualTo(String value) {
            addCriterion("subnet_mask =", value, "subnetMask");
            return (Criteria) this;
        }

        public Criteria andSubnetMaskNotEqualTo(String value) {
            addCriterion("subnet_mask <>", value, "subnetMask");
            return (Criteria) this;
        }

        public Criteria andSubnetMaskGreaterThan(String value) {
            addCriterion("subnet_mask >", value, "subnetMask");
            return (Criteria) this;
        }

        public Criteria andSubnetMaskGreaterThanOrEqualTo(String value) {
            addCriterion("subnet_mask >=", value, "subnetMask");
            return (Criteria) this;
        }

        public Criteria andSubnetMaskLessThan(String value) {
            addCriterion("subnet_mask <", value, "subnetMask");
            return (Criteria) this;
        }

        public Criteria andSubnetMaskLessThanOrEqualTo(String value) {
            addCriterion("subnet_mask <=", value, "subnetMask");
            return (Criteria) this;
        }

        public Criteria andSubnetMaskLike(String value) {
            addCriterion("subnet_mask like", value, "subnetMask");
            return (Criteria) this;
        }

        public Criteria andSubnetMaskNotLike(String value) {
            addCriterion("subnet_mask not like", value, "subnetMask");
            return (Criteria) this;
        }

        public Criteria andSubnetMaskIn(List<String> values) {
            addCriterion("subnet_mask in", values, "subnetMask");
            return (Criteria) this;
        }

        public Criteria andSubnetMaskNotIn(List<String> values) {
            addCriterion("subnet_mask not in", values, "subnetMask");
            return (Criteria) this;
        }

        public Criteria andSubnetMaskBetween(String value1, String value2) {
            addCriterion("subnet_mask between", value1, value2, "subnetMask");
            return (Criteria) this;
        }

        public Criteria andSubnetMaskNotBetween(String value1, String value2) {
            addCriterion("subnet_mask not between", value1, value2, "subnetMask");
            return (Criteria) this;
        }

        public Criteria andGatewayIsNull() {
            addCriterion("gateway is null");
            return (Criteria) this;
        }

        public Criteria andGatewayIsNotNull() {
            addCriterion("gateway is not null");
            return (Criteria) this;
        }

        public Criteria andGatewayEqualTo(String value) {
            addCriterion("gateway =", value, "gateway");
            return (Criteria) this;
        }

        public Criteria andGatewayNotEqualTo(String value) {
            addCriterion("gateway <>", value, "gateway");
            return (Criteria) this;
        }

        public Criteria andGatewayGreaterThan(String value) {
            addCriterion("gateway >", value, "gateway");
            return (Criteria) this;
        }

        public Criteria andGatewayGreaterThanOrEqualTo(String value) {
            addCriterion("gateway >=", value, "gateway");
            return (Criteria) this;
        }

        public Criteria andGatewayLessThan(String value) {
            addCriterion("gateway <", value, "gateway");
            return (Criteria) this;
        }

        public Criteria andGatewayLessThanOrEqualTo(String value) {
            addCriterion("gateway <=", value, "gateway");
            return (Criteria) this;
        }

        public Criteria andGatewayLike(String value) {
            addCriterion("gateway like", value, "gateway");
            return (Criteria) this;
        }

        public Criteria andGatewayNotLike(String value) {
            addCriterion("gateway not like", value, "gateway");
            return (Criteria) this;
        }

        public Criteria andGatewayIn(List<String> values) {
            addCriterion("gateway in", values, "gateway");
            return (Criteria) this;
        }

        public Criteria andGatewayNotIn(List<String> values) {
            addCriterion("gateway not in", values, "gateway");
            return (Criteria) this;
        }

        public Criteria andGatewayBetween(String value1, String value2) {
            addCriterion("gateway between", value1, value2, "gateway");
            return (Criteria) this;
        }

        public Criteria andGatewayNotBetween(String value1, String value2) {
            addCriterion("gateway not between", value1, value2, "gateway");
            return (Criteria) this;
        }

        public Criteria andDnsIsNull() {
            addCriterion("dns is null");
            return (Criteria) this;
        }

        public Criteria andDnsIsNotNull() {
            addCriterion("dns is not null");
            return (Criteria) this;
        }

        public Criteria andDnsEqualTo(String value) {
            addCriterion("dns =", value, "dns");
            return (Criteria) this;
        }

        public Criteria andDnsNotEqualTo(String value) {
            addCriterion("dns <>", value, "dns");
            return (Criteria) this;
        }

        public Criteria andDnsGreaterThan(String value) {
            addCriterion("dns >", value, "dns");
            return (Criteria) this;
        }

        public Criteria andDnsGreaterThanOrEqualTo(String value) {
            addCriterion("dns >=", value, "dns");
            return (Criteria) this;
        }

        public Criteria andDnsLessThan(String value) {
            addCriterion("dns <", value, "dns");
            return (Criteria) this;
        }

        public Criteria andDnsLessThanOrEqualTo(String value) {
            addCriterion("dns <=", value, "dns");
            return (Criteria) this;
        }

        public Criteria andDnsLike(String value) {
            addCriterion("dns like", value, "dns");
            return (Criteria) this;
        }

        public Criteria andDnsNotLike(String value) {
            addCriterion("dns not like", value, "dns");
            return (Criteria) this;
        }

        public Criteria andDnsIn(List<String> values) {
            addCriterion("dns in", values, "dns");
            return (Criteria) this;
        }

        public Criteria andDnsNotIn(List<String> values) {
            addCriterion("dns not in", values, "dns");
            return (Criteria) this;
        }

        public Criteria andDnsBetween(String value1, String value2) {
            addCriterion("dns between", value1, value2, "dns");
            return (Criteria) this;
        }

        public Criteria andDnsNotBetween(String value1, String value2) {
            addCriterion("dns not between", value1, value2, "dns");
            return (Criteria) this;
        }

        public Criteria andDomainIsNull() {
            addCriterion("domain is null");
            return (Criteria) this;
        }

        public Criteria andDomainIsNotNull() {
            addCriterion("domain is not null");
            return (Criteria) this;
        }

        public Criteria andDomainEqualTo(String value) {
            addCriterion("domain =", value, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainNotEqualTo(String value) {
            addCriterion("domain <>", value, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainGreaterThan(String value) {
            addCriterion("domain >", value, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainGreaterThanOrEqualTo(String value) {
            addCriterion("domain >=", value, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainLessThan(String value) {
            addCriterion("domain <", value, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainLessThanOrEqualTo(String value) {
            addCriterion("domain <=", value, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainLike(String value) {
            addCriterion("domain like", value, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainNotLike(String value) {
            addCriterion("domain not like", value, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainIn(List<String> values) {
            addCriterion("domain in", values, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainNotIn(List<String> values) {
            addCriterion("domain not in", values, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainBetween(String value1, String value2) {
            addCriterion("domain between", value1, value2, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainNotBetween(String value1, String value2) {
            addCriterion("domain not between", value1, value2, "domain");
            return (Criteria) this;
        }

        public Criteria andDataCenterIsNull() {
            addCriterion("data_center is null");
            return (Criteria) this;
        }

        public Criteria andDataCenterIsNotNull() {
            addCriterion("data_center is not null");
            return (Criteria) this;
        }

        public Criteria andDataCenterEqualTo(String value) {
            addCriterion("data_center =", value, "dataCenter");
            return (Criteria) this;
        }

        public Criteria andDataCenterNotEqualTo(String value) {
            addCriterion("data_center <>", value, "dataCenter");
            return (Criteria) this;
        }

        public Criteria andDataCenterGreaterThan(String value) {
            addCriterion("data_center >", value, "dataCenter");
            return (Criteria) this;
        }

        public Criteria andDataCenterGreaterThanOrEqualTo(String value) {
            addCriterion("data_center >=", value, "dataCenter");
            return (Criteria) this;
        }

        public Criteria andDataCenterLessThan(String value) {
            addCriterion("data_center <", value, "dataCenter");
            return (Criteria) this;
        }

        public Criteria andDataCenterLessThanOrEqualTo(String value) {
            addCriterion("data_center <=", value, "dataCenter");
            return (Criteria) this;
        }

        public Criteria andDataCenterLike(String value) {
            addCriterion("data_center like", value, "dataCenter");
            return (Criteria) this;
        }

        public Criteria andDataCenterNotLike(String value) {
            addCriterion("data_center not like", value, "dataCenter");
            return (Criteria) this;
        }

        public Criteria andDataCenterIn(List<String> values) {
            addCriterion("data_center in", values, "dataCenter");
            return (Criteria) this;
        }

        public Criteria andDataCenterNotIn(List<String> values) {
            addCriterion("data_center not in", values, "dataCenter");
            return (Criteria) this;
        }

        public Criteria andDataCenterBetween(String value1, String value2) {
            addCriterion("data_center between", value1, value2, "dataCenter");
            return (Criteria) this;
        }

        public Criteria andDataCenterNotBetween(String value1, String value2) {
            addCriterion("data_center not between", value1, value2, "dataCenter");
            return (Criteria) this;
        }

        public Criteria andCustomizationSpecNameIsNull() {
            addCriterion("customization_spec_name is null");
            return (Criteria) this;
        }

        public Criteria andCustomizationSpecNameIsNotNull() {
            addCriterion("customization_spec_name is not null");
            return (Criteria) this;
        }

        public Criteria andCustomizationSpecNameEqualTo(String value) {
            addCriterion("customization_spec_name =", value, "customizationSpecName");
            return (Criteria) this;
        }

        public Criteria andCustomizationSpecNameNotEqualTo(String value) {
            addCriterion("customization_spec_name <>", value, "customizationSpecName");
            return (Criteria) this;
        }

        public Criteria andCustomizationSpecNameGreaterThan(String value) {
            addCriterion("customization_spec_name >", value, "customizationSpecName");
            return (Criteria) this;
        }

        public Criteria andCustomizationSpecNameGreaterThanOrEqualTo(String value) {
            addCriterion("customization_spec_name >=", value, "customizationSpecName");
            return (Criteria) this;
        }

        public Criteria andCustomizationSpecNameLessThan(String value) {
            addCriterion("customization_spec_name <", value, "customizationSpecName");
            return (Criteria) this;
        }

        public Criteria andCustomizationSpecNameLessThanOrEqualTo(String value) {
            addCriterion("customization_spec_name <=", value, "customizationSpecName");
            return (Criteria) this;
        }

        public Criteria andCustomizationSpecNameLike(String value) {
            addCriterion("customization_spec_name like", value, "customizationSpecName");
            return (Criteria) this;
        }

        public Criteria andCustomizationSpecNameNotLike(String value) {
            addCriterion("customization_spec_name not like", value, "customizationSpecName");
            return (Criteria) this;
        }

        public Criteria andCustomizationSpecNameIn(List<String> values) {
            addCriterion("customization_spec_name in", values, "customizationSpecName");
            return (Criteria) this;
        }

        public Criteria andCustomizationSpecNameNotIn(List<String> values) {
            addCriterion("customization_spec_name not in", values, "customizationSpecName");
            return (Criteria) this;
        }

        public Criteria andCustomizationSpecNameBetween(String value1, String value2) {
            addCriterion("customization_spec_name between", value1, value2, "customizationSpecName");
            return (Criteria) this;
        }

        public Criteria andCustomizationSpecNameNotBetween(String value1, String value2) {
            addCriterion("customization_spec_name not between", value1, value2, "customizationSpecName");
            return (Criteria) this;
        }

        public Criteria andFolderNameIsNull() {
            addCriterion("folder_name is null");
            return (Criteria) this;
        }

        public Criteria andFolderNameIsNotNull() {
            addCriterion("folder_name is not null");
            return (Criteria) this;
        }

        public Criteria andFolderNameEqualTo(String value) {
            addCriterion("folder_name =", value, "folderName");
            return (Criteria) this;
        }

        public Criteria andFolderNameNotEqualTo(String value) {
            addCriterion("folder_name <>", value, "folderName");
            return (Criteria) this;
        }

        public Criteria andFolderNameGreaterThan(String value) {
            addCriterion("folder_name >", value, "folderName");
            return (Criteria) this;
        }

        public Criteria andFolderNameGreaterThanOrEqualTo(String value) {
            addCriterion("folder_name >=", value, "folderName");
            return (Criteria) this;
        }

        public Criteria andFolderNameLessThan(String value) {
            addCriterion("folder_name <", value, "folderName");
            return (Criteria) this;
        }

        public Criteria andFolderNameLessThanOrEqualTo(String value) {
            addCriterion("folder_name <=", value, "folderName");
            return (Criteria) this;
        }

        public Criteria andFolderNameLike(String value) {
            addCriterion("folder_name like", value, "folderName");
            return (Criteria) this;
        }

        public Criteria andFolderNameNotLike(String value) {
            addCriterion("folder_name not like", value, "folderName");
            return (Criteria) this;
        }

        public Criteria andFolderNameIn(List<String> values) {
            addCriterion("folder_name in", values, "folderName");
            return (Criteria) this;
        }

        public Criteria andFolderNameNotIn(List<String> values) {
            addCriterion("folder_name not in", values, "folderName");
            return (Criteria) this;
        }

        public Criteria andFolderNameBetween(String value1, String value2) {
            addCriterion("folder_name between", value1, value2, "folderName");
            return (Criteria) this;
        }

        public Criteria andFolderNameNotBetween(String value1, String value2) {
            addCriterion("folder_name not between", value1, value2, "folderName");
            return (Criteria) this;
        }

        public Criteria andHostNameIsNull() {
            addCriterion("host_name is null");
            return (Criteria) this;
        }

        public Criteria andHostNameIsNotNull() {
            addCriterion("host_name is not null");
            return (Criteria) this;
        }

        public Criteria andHostNameEqualTo(String value) {
            addCriterion("host_name =", value, "hostName");
            return (Criteria) this;
        }

        public Criteria andHostNameNotEqualTo(String value) {
            addCriterion("host_name <>", value, "hostName");
            return (Criteria) this;
        }

        public Criteria andHostNameGreaterThan(String value) {
            addCriterion("host_name >", value, "hostName");
            return (Criteria) this;
        }

        public Criteria andHostNameGreaterThanOrEqualTo(String value) {
            addCriterion("host_name >=", value, "hostName");
            return (Criteria) this;
        }

        public Criteria andHostNameLessThan(String value) {
            addCriterion("host_name <", value, "hostName");
            return (Criteria) this;
        }

        public Criteria andHostNameLessThanOrEqualTo(String value) {
            addCriterion("host_name <=", value, "hostName");
            return (Criteria) this;
        }

        public Criteria andHostNameLike(String value) {
            addCriterion("host_name like", value, "hostName");
            return (Criteria) this;
        }

        public Criteria andHostNameNotLike(String value) {
            addCriterion("host_name not like", value, "hostName");
            return (Criteria) this;
        }

        public Criteria andHostNameIn(List<String> values) {
            addCriterion("host_name in", values, "hostName");
            return (Criteria) this;
        }

        public Criteria andHostNameNotIn(List<String> values) {
            addCriterion("host_name not in", values, "hostName");
            return (Criteria) this;
        }

        public Criteria andHostNameBetween(String value1, String value2) {
            addCriterion("host_name between", value1, value2, "hostName");
            return (Criteria) this;
        }

        public Criteria andHostNameNotBetween(String value1, String value2) {
            addCriterion("host_name not between", value1, value2, "hostName");
            return (Criteria) this;
        }

        public Criteria andStateIsNull() {
            addCriterion("state is null");
            return (Criteria) this;
        }

        public Criteria andStateIsNotNull() {
            addCriterion("state is not null");
            return (Criteria) this;
        }

        public Criteria andStateEqualTo(Byte value) {
            addCriterion("state =", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotEqualTo(Byte value) {
            addCriterion("state <>", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThan(Byte value) {
            addCriterion("state >", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThanOrEqualTo(Byte value) {
            addCriterion("state >=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThan(Byte value) {
            addCriterion("state <", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThanOrEqualTo(Byte value) {
            addCriterion("state <=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateIn(List<Byte> values) {
            addCriterion("state in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotIn(List<Byte> values) {
            addCriterion("state not in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateBetween(Byte value1, Byte value2) {
            addCriterion("state between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotBetween(Byte value1, Byte value2) {
            addCriterion("state not between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andTenantCodeIsNull() {
            addCriterion("tenant_code is null");
            return (Criteria) this;
        }

        public Criteria andTenantCodeIsNotNull() {
            addCriterion("tenant_code is not null");
            return (Criteria) this;
        }

        public Criteria andTenantCodeEqualTo(String value) {
            addCriterion("tenant_code =", value, "tenantCode");
            return (Criteria) this;
        }

        public Criteria andTenantCodeNotEqualTo(String value) {
            addCriterion("tenant_code <>", value, "tenantCode");
            return (Criteria) this;
        }

        public Criteria andTenantCodeGreaterThan(String value) {
            addCriterion("tenant_code >", value, "tenantCode");
            return (Criteria) this;
        }

        public Criteria andTenantCodeGreaterThanOrEqualTo(String value) {
            addCriterion("tenant_code >=", value, "tenantCode");
            return (Criteria) this;
        }

        public Criteria andTenantCodeLessThan(String value) {
            addCriterion("tenant_code <", value, "tenantCode");
            return (Criteria) this;
        }

        public Criteria andTenantCodeLessThanOrEqualTo(String value) {
            addCriterion("tenant_code <=", value, "tenantCode");
            return (Criteria) this;
        }

        public Criteria andTenantCodeLike(String value) {
            addCriterion("tenant_code like", value, "tenantCode");
            return (Criteria) this;
        }

        public Criteria andTenantCodeNotLike(String value) {
            addCriterion("tenant_code not like", value, "tenantCode");
            return (Criteria) this;
        }

        public Criteria andTenantCodeIn(List<String> values) {
            addCriterion("tenant_code in", values, "tenantCode");
            return (Criteria) this;
        }

        public Criteria andTenantCodeNotIn(List<String> values) {
            addCriterion("tenant_code not in", values, "tenantCode");
            return (Criteria) this;
        }

        public Criteria andTenantCodeBetween(String value1, String value2) {
            addCriterion("tenant_code between", value1, value2, "tenantCode");
            return (Criteria) this;
        }

        public Criteria andTenantCodeNotBetween(String value1, String value2) {
            addCriterion("tenant_code not between", value1, value2, "tenantCode");
            return (Criteria) this;
        }

        public Criteria andCreateUidIsNull() {
            addCriterion("create_uid is null");
            return (Criteria) this;
        }

        public Criteria andCreateUidIsNotNull() {
            addCriterion("create_uid is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUidEqualTo(Long value) {
            addCriterion("create_uid =", value, "createUid");
            return (Criteria) this;
        }

        public Criteria andCreateUidNotEqualTo(Long value) {
            addCriterion("create_uid <>", value, "createUid");
            return (Criteria) this;
        }

        public Criteria andCreateUidGreaterThan(Long value) {
            addCriterion("create_uid >", value, "createUid");
            return (Criteria) this;
        }

        public Criteria andCreateUidGreaterThanOrEqualTo(Long value) {
            addCriterion("create_uid >=", value, "createUid");
            return (Criteria) this;
        }

        public Criteria andCreateUidLessThan(Long value) {
            addCriterion("create_uid <", value, "createUid");
            return (Criteria) this;
        }

        public Criteria andCreateUidLessThanOrEqualTo(Long value) {
            addCriterion("create_uid <=", value, "createUid");
            return (Criteria) this;
        }

        public Criteria andCreateUidIn(List<Long> values) {
            addCriterion("create_uid in", values, "createUid");
            return (Criteria) this;
        }

        public Criteria andCreateUidNotIn(List<Long> values) {
            addCriterion("create_uid not in", values, "createUid");
            return (Criteria) this;
        }

        public Criteria andCreateUidBetween(Long value1, Long value2) {
            addCriterion("create_uid between", value1, value2, "createUid");
            return (Criteria) this;
        }

        public Criteria andCreateUidNotBetween(Long value1, Long value2) {
            addCriterion("create_uid not between", value1, value2, "createUid");
            return (Criteria) this;
        }

        public Criteria andCreateNameIsNull() {
            addCriterion("create_name is null");
            return (Criteria) this;
        }

        public Criteria andCreateNameIsNotNull() {
            addCriterion("create_name is not null");
            return (Criteria) this;
        }

        public Criteria andCreateNameEqualTo(String value) {
            addCriterion("create_name =", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameNotEqualTo(String value) {
            addCriterion("create_name <>", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameGreaterThan(String value) {
            addCriterion("create_name >", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameGreaterThanOrEqualTo(String value) {
            addCriterion("create_name >=", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameLessThan(String value) {
            addCriterion("create_name <", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameLessThanOrEqualTo(String value) {
            addCriterion("create_name <=", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameLike(String value) {
            addCriterion("create_name like", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameNotLike(String value) {
            addCriterion("create_name not like", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameIn(List<String> values) {
            addCriterion("create_name in", values, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameNotIn(List<String> values) {
            addCriterion("create_name not in", values, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameBetween(String value1, String value2) {
            addCriterion("create_name between", value1, value2, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameNotBetween(String value1, String value2) {
            addCriterion("create_name not between", value1, value2, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}