package com.example.demo.session;


import java.util.*;

public class LoginData {


    public static Map<String, String> logMap = new HashMap<>();
    public static List<String[]> logList = new ArrayList<>();

    public static void logMap() {
        logMap.put("max", "123abc");
        logMap.put("ilon", "mask");
        logMap.put("thor", "from asgard");
    }

    public static void logList(){

        logList.add(new String[]{"max", "123abc"});
        logList.add(new String[]{"ilon", "mask"});
        logList.add(new String[]{"thor", "from asgard"});

    }

    public static boolean checkLogList(String login, String password){
        for (String[] i:logList
        ) {
            if (i[0].equals(login) && i[1].equals(password)){
                return true;
            }
        }
        return false;
    }




}
