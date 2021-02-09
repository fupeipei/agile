package com.yusys.agile.vcenter.utils;

import com.vmware.vim25.mo.ServiceInstance;

import java.net.URL;

public class Session {
    static String serverName = "";
    static String userName = "";
    static String passWord = "";
    static String url = "";


    static ServiceInstance serviceInstance = null;


    public synchronized static ServiceInstance getInstance(String serverName, String userName, String passWord) throws Exception {

        initServiceInstance(serverName, userName, passWord);
        return serviceInstance;

    }


    public static void initServiceInstance(String ip, String userNameTmp, String passWordTmp) throws Exception {
        serverName = ip;
        userName = userNameTmp;
        passWord = passWordTmp;

        url = "https://" + serverName + "/sdk";
        if (serviceInstance == null) {
            serviceInstance = new ServiceInstance(
                    new URL(url), userName, passWord, true);
        }
    }
}
