package org.appspot.apprtc.account;

/**
 * Created by ownder on 15/05/17.
 */

public class ChangeIP {

    public static String IP = "172.18.89.120";
    //public static String IP = "192.168.43.228";

    public static void setIP(String IP) {
        ChangeIP.IP = IP;
    }

    public static String getIP() {
        return IP;
    }

}
