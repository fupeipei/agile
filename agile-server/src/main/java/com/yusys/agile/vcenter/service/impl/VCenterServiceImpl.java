package com.yusys.agile.vcenter.service.impl;

import com.github.pagehelper.page.PageMethod;
import com.yusys.agile.vcenter.dao.VcenterApplicationMapper;
import com.yusys.agile.vcenter.dao.VcenterDevMapper;
import com.yusys.agile.vcenter.dao.VcenterVmIpMapper;
import com.yusys.agile.vcenter.domain.*;
import com.yusys.agile.vcenter.service.VCenterService;
import com.yusys.agile.vcenter.utils.Ping;
import com.yusys.agile.vcenter.utils.Session;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.vmware.vim25.*;
import com.vmware.vim25.mo.*;
import com.yusys.portal.util.thread.UserThreadLocalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * :
 *
 * @Date: 2021/2/25
 * @Description: TODO
 */
@Service
public class VCenterServiceImpl implements VCenterService {

    private static final Logger log = LoggerFactory.getLogger(VCenterServiceImpl.class);
    @Resource
    private VcenterDevMapper vcenterDevMapper;
    @Resource
    private VcenterVmIpMapper vcenterVmIpMapper;
    @Resource
    private VcenterApplicationMapper vcenterApplicationMapper;

    static String VirtualMachine = "VirtualMachine";
    static String error = " 查询数据中心异常";
    static String ClusterComputeResource = "ClusterComputeResource";


    @Override
    public List<VcenterDev> getDev() {
        try {
            VcenterDevExample example = new VcenterDevExample();
            example.createCriteria().andStateEqualTo(Byte.parseByte("0"));
            return vcenterDevMapper.selectByExample(example);
        } catch (Exception e) {
            log.error("查询环境异常" + e);
        }
        return null;
    }

    @Override
    public List<Map> getTemplateName(Integer devId) {

        ServiceInstance serviceInstance = null;
        List result = Lists.newArrayList();
        try {
            VcenterDev vcenterDev = vcenterDevMapper.selectByPrimaryKey(Long.parseLong(devId.toString()));
            if (vcenterDev != null) {
                serviceInstance = Session.getInstance(vcenterDev.getHostUrl(), vcenterDev.getUserName(), vcenterDev.getPassword());
                InventoryNavigator inventoryNavigator = new InventoryNavigator(serviceInstance.getRootFolder());
                try {
                    ManagedEntity[] template = (ManagedEntity[]) inventoryNavigator.searchManagedEntities(VirtualMachine);
                    for (int i = 0; i < template.length; i++) {
                        com.vmware.vim25.mo.VirtualMachine vm = (com.vmware.vim25.mo.VirtualMachine) template[i];
                        if (vm.getConfig().isTemplate() == true) {
                            Map map = new HashMap();
                            map.put("name", vm.getConfig().getName());
                            map.put("guestName", vm.getConfig().getGuestFullName());
                            map.put("code", i);
                            result.add(map);
                        }
                    }
                } catch (RemoteException e) {
                    log.error("查询集群中心异常" + e);
                }
            }
        } catch (Exception e) {
            log.error("查询模板异常" + e);
        } finally {
            if (serviceInstance != null) {
                serviceInstance.getServerConnection().logout();
            }
        }
        return result;
    }

    @Override
    public List<Map> getDatacenter(Integer devId) {

        ServiceInstance serviceInstance = null;
        List result = Lists.newArrayList();
        try {
            VcenterDev vcenterDev = vcenterDevMapper.selectByPrimaryKey(Long.parseLong(devId.toString()));
            if (vcenterDev != null) {
                serviceInstance = Session.getInstance(vcenterDev.getHostUrl(), vcenterDev.getUserName(), vcenterDev.getPassword());
                InventoryNavigator inventoryNavigator = new InventoryNavigator(serviceInstance.getRootFolder());
                try {
                    ManagedEntity[] datacenters = inventoryNavigator.searchManagedEntities("Datacenter");
                    for (int i = 0; i < datacenters.length; i++) {
                        Datacenter datacenter = (Datacenter) datacenters[i];
                        Map map = new HashMap();
                        map.put("name", datacenter.getName());
                        map.put("code", i);
                        result.add(map);
                    }
                } catch (RemoteException e) {
                    log.error(error + e);
                }
            }
        } catch (Exception e) {
            log.error(error + e);
        } finally {
            if (serviceInstance != null) {
                serviceInstance.getServerConnection().logout();
            }
        }
        return result;

    }

    @Override
    public List<Map> getClusterComputeResource(Integer devId) {

        ServiceInstance serviceInstance = null;
        List result = Lists.newArrayList();
        try {
            VcenterDev vcenterDev = vcenterDevMapper.selectByPrimaryKey(Long.parseLong(devId.toString()));
            if (vcenterDev != null) {
                serviceInstance = Session.getInstance(vcenterDev.getHostUrl(), vcenterDev.getUserName(), vcenterDev.getPassword());
                InventoryNavigator inventoryNavigator = new InventoryNavigator(serviceInstance.getRootFolder());
                try {
                    ManagedEntity[] clusterComputeResources = inventoryNavigator.searchManagedEntities(ClusterComputeResource);
                    for (int i = 0; i < clusterComputeResources.length; i++) {
                        ClusterComputeResource cluster = (ClusterComputeResource) clusterComputeResources[i];
                        Map map = new HashMap();
                        map.put("name", cluster.getName());
                        map.put("code", i);
                        result.add(map);
                    }
                } catch (RemoteException e) {
                    log.error("查询集群异常" + e);
                }
            }
        } catch (Exception e) {
            log.error(error + e);
        } finally {
            if (serviceInstance != null) {
                serviceInstance.getServerConnection().logout();
            }
        }
        return result;

    }

    @Override
    public List<Map> getHost(Integer devId, String clusterComputeResourceName) {

        ServiceInstance serviceInstance = null;
        List result = Lists.newArrayList();
        try {
            VcenterDev vcenterDev = vcenterDevMapper.selectByPrimaryKey(Long.parseLong(devId.toString()));
            if (vcenterDev != null) {
                serviceInstance = Session.getInstance(vcenterDev.getHostUrl(), vcenterDev.getUserName(), vcenterDev.getPassword());
                InventoryNavigator inventoryNavigator = new InventoryNavigator(serviceInstance.getRootFolder());
                try {
                    ManagedEntity[] clusterComputeResources = inventoryNavigator.searchManagedEntities(ClusterComputeResource);
                    for (int i = 0; i < clusterComputeResources.length; i++) {
                        ClusterComputeResource cluster = (ClusterComputeResource) clusterComputeResources[i];
                        if (clusterComputeResourceName.equals(cluster.getName())) {
                            HostSystem[] hostSystems = cluster.getHosts();
                            if (hostSystems != null && hostSystems.length > 0) {
                                for (int j = 0; j < hostSystems.length; j++) {
                                    Map map = new HashMap();
                                    map.put("name", hostSystems[j].getName());
                                    map.put("code", j);
                                    result.add(map);
                                }
                            }
                        }
                    }
                } catch (RemoteException e) {
                    log.error("查询主机异常" + e);
                }
            }
        } catch (Exception e) {
            log.error(error + e);
        } finally {
            if (serviceInstance != null) {
                serviceInstance.getServerConnection().logout();
            }
        }
        return result;
    }

    @Override
    public List<Map> getFolder(Integer devId) {

        ServiceInstance serviceInstance = null;
        List result = Lists.newArrayList();
        try {
            VcenterDev vcenterDev = vcenterDevMapper.selectByPrimaryKey(Long.parseLong(devId.toString()));
            if (vcenterDev != null) {
                serviceInstance = Session.getInstance(vcenterDev.getHostUrl(), vcenterDev.getUserName(), vcenterDev.getPassword());
                InventoryNavigator inventoryNavigator = new InventoryNavigator(serviceInstance.getRootFolder());
                try {
                    ManagedEntity[] folders = inventoryNavigator.searchManagedEntities("Folder");
                    result = dealData(folders);
                } catch (RemoteException e) {
                    log.error("查询存放位置异常" + e);
                }
            }
        } catch (Exception e) {
            log.error(error + e);
        } finally {
            if (serviceInstance != null) {
                serviceInstance.getServerConnection().logout();
            }
        }
        return result;

    }

    @Override
    public List<Map> getCustomizationSpec(Integer devId) {
        ServiceInstance serviceInstance = null;
        List result = Lists.newArrayList();
        try {
            VcenterDev vcenterDev = vcenterDevMapper.selectByPrimaryKey(Long.parseLong(devId.toString()));
            if (vcenterDev != null) {
                serviceInstance = Session.getInstance(vcenterDev.getHostUrl(), vcenterDev.getUserName(), vcenterDev.getPassword());
                try {
                    CustomizationSpecInfo[] infos = serviceInstance.getCustomizationSpecManager().getInfo();
                    for (int i = 0; i < infos.length; i++) {
                        Map map = new HashMap();
                        map.put("name", infos[i].getName());
                        map.put("code", i);
                        result.add(map);
                    }
                } catch (Exception e) {
                    log.error("查询自定义规范异常" + e);
                }
            }
        } catch (Exception e) {
            log.error(error + e);
        } finally {
            if (serviceInstance != null) {
                serviceInstance.getServerConnection().logout();
            }
        }
        return result;

    }

    @Override
    public List<Map> getDatastore(Integer devId, String clusterComputeResourceName) {

        ServiceInstance serviceInstance = null;
        List result = Lists.newArrayList();

        try {
            VcenterDev vcenterDev = vcenterDevMapper.selectByPrimaryKey(Long.parseLong(devId.toString()));
            if (vcenterDev != null) {
                serviceInstance = Session.getInstance(vcenterDev.getHostUrl(), vcenterDev.getUserName(), vcenterDev.getPassword());
                try {
                    Folder rootFolder = serviceInstance.getRootFolder();
                    InventoryNavigator inventoryNavigator = new InventoryNavigator(rootFolder);
                    ManagedEntity[] clusterComputeResources = inventoryNavigator.searchManagedEntities(ClusterComputeResource);
                    for (int i = 0; i < clusterComputeResources.length; i++) {
                        ClusterComputeResource cluster = (ClusterComputeResource) clusterComputeResources[i];
                        if (cluster.getName().equals(clusterComputeResourceName)) {
                            Datastore[] datastores = cluster.getDatastores();
                            result = dealData(datastores);
                        }
                    }
                } catch (Exception e) {
                    log.error("查询存储器异常" + e);
                }
            }
        } catch (Exception e) {
            log.error(error + e);
        } finally {
            if (serviceInstance != null) {
                serviceInstance.getServerConnection().logout();
            }
        }
        return result;

    }

    @Override
    public List<Map> getPoolName(Integer devId) {

        ServiceInstance serviceInstance = null;
        List result = Lists.newArrayList();
        try {
            VcenterDev vcenterDev = vcenterDevMapper.selectByPrimaryKey(Long.parseLong(devId.toString()));
            if (vcenterDev != null) {
                serviceInstance = Session.getInstance(vcenterDev.getHostUrl(), vcenterDev.getUserName(), vcenterDev.getPassword());
                Folder rootFolder = serviceInstance.getRootFolder();
                InventoryNavigator inventoryNavigator = new InventoryNavigator(rootFolder);
                try {
                    ManagedEntity[] resourcePools = inventoryNavigator.searchManagedEntities("ResourcePool");
                    result = dealData(resourcePools);
                } catch (Exception e) {
                    log.error("资源池异常" + e);
                }
            }
        } catch (Exception e) {
            log.error(error + e);
        } finally {
            if (serviceInstance != null) {
                serviceInstance.getServerConnection().logout();
            }
        }
        return result;

    }

    @Override
    public List<VcenterVmIp> getIP(Long devId) {
        VcenterVmIpExample example = new VcenterVmIpExample();
        VcenterVmIpExample.Criteria criteria = example.createCriteria();
        criteria.andDevIdEqualTo(devId).andStateEqualTo(Byte.parseByte("0"));
        return vcenterVmIpMapper.selectByExample(example);
    }

    @Override
    public List<VcenterApplication> getList(VcenterApplication vcenterApplication) {
        // 不传page信息时查全部数据
        if (null != vcenterApplication.getPageNum() && null != vcenterApplication.getPageSize()) {
            PageMethod.startPage(vcenterApplication.getPageNum(), vcenterApplication.getPageSize());
        }
        VcenterApplicationExample vcenterApplicationExample = new VcenterApplicationExample();
        VcenterApplicationExample.Criteria criteria = vcenterApplicationExample.createCriteria();
        if (vcenterApplication.getVirtualMachineName() != null && !vcenterApplication.getVirtualMachineName().equals("")) {
            criteria.andVirtualMachineNameLike(vcenterApplication.getVirtualMachineName());
        }
        return vcenterApplicationMapper.selectByExample(vcenterApplicationExample);
    }

    @Override
    @Transactional
    public void CreateVMByTemplate(CreateVMDTO createVMDTO) {
        ServiceInstance si = null;
        try {
            VcenterDev vcenterDev = vcenterDevMapper.selectByPrimaryKey(createVMDTO.getDevId());
            if (vcenterDev != null) {
                si = Session.getInstance(vcenterDev.getHostUrl(), vcenterDev.getUserName(), vcenterDev.getPassword());
                Folder rootFolder = si.getRootFolder();
                InventoryNavigator inventoryNavigator = new InventoryNavigator(rootFolder);
                try {
                    //虚拟机模板name
                    String templateVMName = createVMDTO.getTemplateVMName();
                    VirtualMachine templateVM = null;
                    String poolName = "";
                    ResourcePool pool = null;
                    ComputeResource computerResource = null;
                    String datastoreName = createVMDTO.getDatastoreName();
                    Datastore datastore = null;
                    String customizationSpecName = createVMDTO.getCustomizationSpecName();
                    String folderName = createVMDTO.getFolderName();
                    Folder folder = null;
                    ManagedEntity[] clusterComputeResources = inventoryNavigator.searchManagedEntities(ClusterComputeResource);
                    for (int i = 0; i < clusterComputeResources.length; i++) {
                        ClusterComputeResource cluster = (ClusterComputeResource) clusterComputeResources[i];
                        //集群关联DataStore
                        Map<String, Long> dsMap = new HashMap<String, Long>();
                        Datastore[] datastores = cluster.getDatastores();
                        if (datastores != null && datastores.length > 0) {
                            for (int k = 0; k < datastores.length; k++) {
                                //寻找存储器
                                if (datastoreName.equals(datastores[k].getName())) {
                                    datastore = datastores[k];
                                }
                            }
                        }
                    }
                    for (int i = 0; i < createVMDTO.getVcenterApplicationList().size(); i++) {
                        VcenterApplication vcenterApplication = createVMDTO.getVcenterApplicationList().get(i);
                        String vmName = vcenterApplication.getVirtualMachineName();
                        vcenterApplication.setDataCenter(createVMDTO.getDatacenterName());
                        vcenterApplication.setDevId(createVMDTO.getDevId());
                        vcenterApplication.setTemplateName(createVMDTO.getTemplateVMName());
                        vcenterApplication.setCustomizationSpecName(customizationSpecName);
                        vcenterApplication.setFolderName(createVMDTO.getFolderName());
                        vcenterApplication.setHostName(createVMDTO.getHostName());
                        //创建虚拟机
                        //虚拟机CPU和内存配置信息
                        VirtualMachineConfigSpec configSpec = new VirtualMachineConfigSpec();
                        // 设置CPU核数
                        configSpec.setNumCPUs(Integer.parseInt(vcenterApplication.getCpuNum().toString()));
                        // 设置内存大小
                        configSpec.setMemoryMB(vcenterApplication.getMemoryNum());
                        // 设置虚拟机名称
                        configSpec.setName(vcenterApplication.getVirtualMachineName());
                        //虚拟机模板
                        templateVM = (VirtualMachine) inventoryNavigator.searchManagedEntity(
                                VirtualMachine, templateVMName);
                        VirtualMachineRelocateSpec virtualMachineRelocateSpec = new VirtualMachineRelocateSpec();
                        if (null != poolName && !"".equals(poolName)) {
                            pool = (ResourcePool) inventoryNavigator.searchManagedEntity("ResourcePool", poolName);
                            virtualMachineRelocateSpec.setPool(pool.getMOR());
                        } else {
                            computerResource = (ComputeResource) inventoryNavigator.searchManagedEntity("ComputeResource",
                                    createVMDTO.getClusterComputeResourceName());
                            virtualMachineRelocateSpec.setPool(computerResource.getResourcePool().getMOR());
                            for (int k = 0; k < computerResource.getHosts().length; k++) {
                                if (createVMDTO.getHostName().equals(computerResource.getHosts()[k].getName())) {
                                    virtualMachineRelocateSpec.setHost(computerResource.getHosts()[k].getMOR());
                                }
                            }
                        }
                        try {
                            folder = (Folder) inventoryNavigator
                                    .searchManagedEntity("Folder",
                                            folderName);
                        } catch (Exception e) {
                            log.info("Folder查询失败" + e.getMessage());
                        }
                        VirtualMachineCloneSpec cloneSpec = new VirtualMachineCloneSpec();
                        cloneSpec.setLocation(virtualMachineRelocateSpec);
                        cloneSpec.setPowerOn(true);
                        cloneSpec.setTemplate(false);
                        cloneSpec.setConfig(configSpec);
                        cloneSpec.setCustomization(setCustomizationSpec(si, vcenterApplication));
                        Long userId = UserThreadLocalUtil.getUserInfo().getUserId();
                        String userName = UserThreadLocalUtil.getUserInfo().getUserName();
                        vcenterApplication.setCreateUid(userId);
                        vcenterApplication.setCreateName(userName);
                        vcenterApplication.setCreateTime(new Date());
                        vcenterApplicationMapper.insertSelective(vcenterApplication);
                        Task task = templateVM.cloneVM_Task(
                                folder,
                                vmName,
                                cloneSpec);
                        log.info("Launching the VM clone task. " +
                                "Please wait ...");
                        String result = task.waitForTask();
                        if (result.equals(Task.SUCCESS)) {
                            log.info("VM got cloned successfully.");
                            if (Ping.ping(vcenterApplication.getIpAddress())) {
                                vcenterApplication.setState(Byte.parseByte("1"));
                                vcenterApplication.setUpdateTime(new Date());
                                vcenterApplicationMapper.updateByPrimaryKeySelective(vcenterApplication);
                                vcenterVmIpMapper.updateByIp(vcenterApplication.getIpAddress());
                            }
                            VirtualMachine vm = (VirtualMachine) new InventoryNavigator(si.getRootFolder()).searchManagedEntity(VirtualMachine, vmName);
                            if (vm == null) {
                                log.info("Not found vm:" + vmName);
                            }
                        } else {
                            log.error(result);
                            log.error("Failure -: VM cannot be cloned");
                        }
                    }
                } catch (Exception e) {
                    log.error("资源池异常" + e);
                }
            }

        } catch (Exception e) {
            log.error(error + e);
        } finally {
            if (si != null) {
                si.getServerConnection().logout();
            }
        }
    }


    // 获取虚拟机磁盘管理的controlerkey
    public int getcontrollerkey(VirtualMachine vm) {
        int controllerkey = 0;
        if (vm != null) {
            VirtualDevice[] devices = vm.getConfig().getHardware().getDevice();
            if (devices != null && devices.length > 0) {
                for (int i = 0; i < devices.length; i++) {
                    VirtualDevice device = devices[i];
                    if (device instanceof VirtualDisk) {
                        controllerkey = device.getControllerKey();
                    }
                }
            }
        }
        return controllerkey;
    }

    // 获取虚拟机已生成key
    public int getkey(VirtualMachine vm) {
        int key = 0;
        if (vm != null) {
            VirtualDevice[] devices = vm.getConfig().getHardware().getDevice();
            if (devices != null && devices.length > 0) {
                for (int i = 0; i < devices.length; i++) {
                    VirtualDevice device = devices[i];
                    if (device instanceof VirtualDisk) {
                        key = device.getKey();
                    }
                }
            }
        }
        key = key + 1;
        return key;
    }

    // 获取虚拟机已生成UnitNumber
    public int getUnitNumber(VirtualMachine vm) {
        int UnitNumber = 0;
        if (vm != null) {
            VirtualDevice[] devices = vm.getConfig().getHardware().getDevice();
            if (devices != null && devices.length > 0) {
                for (int i = 0; i < devices.length; i++) {
                    VirtualDevice device = devices[i];
                    if (device instanceof VirtualDisk) {
                        UnitNumber = device.getUnitNumber();
                    }
                }
            }
        }
        UnitNumber = UnitNumber + 1;
        return UnitNumber;
    }

    public String randomString(int strLength) {
        Random rnd = ThreadLocalRandom.current();
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < strLength; i++) {
            boolean isChar = (rnd.nextInt(2) % 2 == 0);// 输出字母还是数字
            if (isChar) { // 字符串
                int choice = rnd.nextInt(2) % 2 == 0 ? 65 : 97; // 取得大写字母还是小写字母
                ret.append((char) (choice + rnd.nextInt(26)));
            } else { // 数字
                ret.append(Integer.toString(rnd.nextInt(10)));
            }
        }
        return ret.toString();
    }

    //查询用户已经创建的自定义规范
    public CustomizationSpec getCustomizationSpec(ServiceInstance instance, String customizationName) {
        CustomizationSpec customizationSpec = null;
        CustomizationSpecItem customizationSpecItem = null;
        CustomizationSpecManager manager = instance.getCustomizationSpecManager();
        CustomizationSpecInfo[] infos = manager.getInfo();
        if (infos != null && infos.length > 0) {
            for (CustomizationSpecInfo info : infos) {
                if (info.getName().equalsIgnoreCase(customizationName)) {
                    try {
                        customizationSpecItem = manager.getCustomizationSpec(customizationName);
                        customizationSpec = customizationSpecItem.getSpec();
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }

                }
            }
        }
        return customizationSpec;
    }

    public CustomizationSpec setCustomizationSpec(ServiceInstance si, VcenterApplication vcenterApplication) {
        String ip = vcenterApplication.getIpAddress();
        VcenterVmIpExample vcenterVmIpExample = new VcenterVmIpExample();
        vcenterVmIpExample.createCriteria().andIpAddressEqualTo(vcenterApplication.getIpAddress());
        List<VcenterVmIp> vcenterVmIps = vcenterVmIpMapper.selectByExample(vcenterVmIpExample);
        String subnetMask = vcenterVmIps.get(0).getSubnetMask();
        String[] gateway = new String[]{vcenterVmIps.get(0).getGateway()};
        String[] dnsServer = new String[]{vcenterVmIps.get(0).getDns()};
        String domain = vcenterVmIps.get(0).getDomain();

        CustomizationSpec customizationSpec = getCustomizationSpec(si, vcenterApplication.getVirtualMachineName());
        CustomizationFixedIp fixedIp = new CustomizationFixedIp();
        fixedIp.setIpAddress(ip);
        CustomizationIPSettings customizationIPSettings = new CustomizationIPSettings();
        customizationIPSettings.setIp(fixedIp);
        customizationIPSettings.setSubnetMask(subnetMask);
        customizationIPSettings.setGateway(gateway);
        customizationIPSettings.setDnsDomain(domain);
        customizationIPSettings.setDnsServerList(dnsServer);
        CustomizationAdapterMapping customizationAdapterMappings = new CustomizationAdapterMapping();
        customizationAdapterMappings.setAdapter(customizationIPSettings);
        customizationSpec.setNicSettingMap(new CustomizationAdapterMapping[]{customizationAdapterMappings});

        if (!vcenterApplication.getCustomizationSpecName().contains("Linux")) {
            CustomizationWinOptions options = new CustomizationWinOptions();
            options.setChangeSID(true);
            options.setDeleteAccounts(false);
            customizationSpec.setOptions(options);
            CustomizationSysprep sysprep = new CustomizationSysprep();
            CustomizationGuiUnattended unattended = new CustomizationGuiUnattended();
            unattended.setTimeZone(4);
            unattended.setAutoLogon(false);
            unattended.setAutoLogonCount(0);
            sysprep.setGuiUnattended(unattended);

            CustomizationUserData userData = new CustomizationUserData();
            CustomizationFixedName fixedName = new CustomizationFixedName();
            fixedName.setName(vcenterApplication.getVirtualMachineName());
            userData.setComputerName(fixedName);
            userData.setFullName(domain);
            userData.setOrgName(domain);
            sysprep.setUserData(userData);
            customizationSpec.setIdentity(sysprep);
        } else {
            CustomizationLinuxPrep linuxPrep = new CustomizationLinuxPrep();
            linuxPrep.setDomain(domain);
            CustomizationFixedName fixedName = new CustomizationFixedName();
            fixedName.setName(vcenterApplication.getVirtualMachineName());
            linuxPrep.setHostName(fixedName);
            customizationSpec.setIdentity(linuxPrep);
        }
        return customizationSpec;
    }

    /**
     * 功能描述  处理代码逻辑
     *
     * @param managedEntities
     * @return java.util.List<java.util.Map>
     * @date 2021/2/13
     */

    List<Map> dealData(ManagedEntity[] managedEntities) {
        List result = Lists.newArrayList();
        if (managedEntities != null && managedEntities.length > 0) {
            for (int k = 0; k < managedEntities.length; k++) {
                Map map = new HashMap();
                map.put("name", managedEntities[k].getName());
                map.put("code", k);
                result.add(map);
            }
        }
        return result;
    }

    /**
     * 功能描述  前后端联调测试数据
     *
     * @param name
     * @return java.util.List<java.util.Map>
     * @date 2021/2/14
     */
    List<Map> testData(String name) {
        List result = Lists.newArrayList();
        for (int i = 0; i < 6; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("name", name + "1");
            map.put("code", String.valueOf(i));
            result.add(map);
        }
        return result;
    }
}
