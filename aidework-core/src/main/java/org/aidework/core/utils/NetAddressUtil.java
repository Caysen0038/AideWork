package org.aidework.core.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * 网络地址工具
 *
 * @author bkc
 */
public class NetAddressUtil {

    /**
     * 获取网络地址
     * @return
     * @throws SocketException
     * @throws UnknownHostException
     */
    public static InetAddress getNetAddress() throws SocketException, UnknownHostException {
        if(OSUtil.isWindows()){
            return getWindowsInetAddress();
        }
        if(OSUtil.isLinux()){
            return getLinuxInetAddress();
        }
        return null;
    }

    /**
     * windows系统下获取网络地址
     * @return
     * @throws UnknownHostException
     */
    public static InetAddress getWindowsInetAddress() throws UnknownHostException {
        return InetAddress.getLocalHost();
    }

    /**
     * linux系统下获取网络地址
     * @return
     * @throws UnknownHostException
     */
    public static InetAddress getLinuxInetAddress() throws SocketException {
        InetAddress ip = null;
        boolean flag = false;
        Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
        while (en.hasMoreElements()) {
            NetworkInterface intf = en.nextElement();
            String name = intf.getName();
            String prefix = null;
            // linux 下网卡命令共五种方式
            if (name.startsWith("eno")) {
                prefix = "eno";
            }
            if (name.startsWith("ens")) {
                prefix = "ens";
            }
            if (name.startsWith("eth")) {
                prefix = "eth";
            }
            if (name.startsWith("enx")) {
                prefix = "enx";
            }
            if (name.startsWith("enp")) {
                prefix = "enp";
            }
            if (prefix == null) {
                continue;
            }
            Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses();
            while (enumIpAddr.hasMoreElements()) {
                InetAddress inetAddress = enumIpAddr.nextElement();
                if (!inetAddress.isLoopbackAddress()) {
                    String ipaddress = inetAddress.getHostAddress().toString();
                    if (!ipaddress.contains("::")
                            && !ipaddress.contains("0:0:")
                            && !ipaddress.contains("fe80")) {
                        ip = inetAddress;
                        flag = true;
                    }
                }
                if (flag) {
                    break;
                }
            }
            if (!flag) {
                break;
            }
        }
        return ip;
    }
}
