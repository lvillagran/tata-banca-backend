package com.banca.microservicios.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class getServerIPAddress {
    public static String getServerIPAddress() {
        try {
            InetAddress localhost = InetAddress.getLocalHost();
            String ipAddress = localhost.getHostAddress();
            return ipAddress;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
    }
}
