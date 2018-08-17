package com.ardikars.common.net;

import org.junit.Test;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Set;

public class MacAddressTest extends BaseTest {

    private void print(NetworkInterface network) throws SocketException {
        System.out.print(network.getIndex() + " : " + network.getName() + " : ");
        byte[] mac = network.getHardwareAddress();
        if (mac == null) {
            System.out.println();
            return;
        }
        System.out.print("Current MAC address : ");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mac.length; i++) {
            sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
        }
        System.out.println(sb.toString());
    }
    @Test
    public void firstTest() {
        InetAddress ip;
        try {
            ip = InetAddress.getLocalHost();
            System.out.println("Current IP address : " + ip.getHostAddress());
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            print(network);
        } catch (UnknownHostException e) {

            e.printStackTrace();

        } catch (SocketException e) {

            e.printStackTrace();

        }
    }

    @Test
    public void secondTest() throws SocketException {
        Enumeration<NetworkInterface> networkInterface = NetworkInterface.getNetworkInterfaces();
        while (networkInterface.hasMoreElements()) {
            NetworkInterface network = networkInterface.nextElement();
            print(network);
        }
    }

    @Test
    public void thirdTest() throws SocketException {
        Collection<com.ardikars.common.net.NetworkInterface> networkInterfaceSet
                = com.ardikars.common.net.NetworkInterface.getNetworkInterfaces();
        for (com.ardikars.common.net.NetworkInterface networkInterface : networkInterfaceSet) {
            System.out.println(networkInterface);
        }
    }

}
