package com.yusys.agile.vcenter.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Date: 2021/2/3
 * 检查目前服务器是否在线
 *  windows:  ping 127.0.0.1 -n 10 -w 3000   的命令，该命令ping10次，等待每个响应的超时时间3秒
 */
public class Ping {

    private static final Logger logger = LoggerFactory.getLogger(Ping.class);

    public static boolean ping(String ipAddress) throws Exception {
        //超时应该在3钞以上
        int timeOut = 3000 ;
        boolean status = InetAddress.getByName(ipAddress).isReachable(timeOut);
        // 当返回值是true时，说明host是可用的，false则不可
        return status;
    }

    public static boolean ping(String ipAddress, int pingTimes, int timeOut) {
            BufferedReader in = null;
            Runtime r = Runtime.getRuntime();
            String pingCommand = "ping  -c " + pingTimes + " -w " + timeOut + ipAddress;
        try {
                String os = System.getProperty("os.name").toLowerCase();
                if (os.startsWith("win")) {
                    // 将要执行的ping命令,此命令是windows格式的命令
                    pingCommand = "ping " + ipAddress + " -n " + pingTimes    + " -w " + timeOut;
                }

                if (logger.isDebugEnabled()) {
                    logger.debug(pingCommand);
                }
                // 执行命令并获取输出
                Process p = r.exec(pingCommand);
                if (p == null) {
                    return false;
                }
                in = new BufferedReader(new InputStreamReader(p.getInputStream()));
                int connectedCount = 0;
                String line;
                // 逐行检查输出,计算类似出现=23ms TTL=62字样的次数
                while ((line = in.readLine()) != null) {
                    connectedCount += getCheckResult(line);
                }
                // 如果出现类似=23ms TTL=62这样的字样,出现的次数=测试次数则返回真
                return connectedCount == pingTimes;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }
    //若line含有=18ms TTL=16字样,说明已经ping通,返回1,否則返回0.
    private static int getCheckResult(String line) {  // System.out.println("控制台输出的结果为:"+line);
        Pattern pattern = Pattern.compile("(\\d+ms)(\\s+)(TTL=\\d+)",    Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            return 1;
        }
        return 0;
    }

    public static void main(String[] args){
        try{
            Ping.ping("10.1.236.226");
            Ping.ping("10.1.236.226",10,3000);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }
}
