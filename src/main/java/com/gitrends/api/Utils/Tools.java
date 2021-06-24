package com.gitrends.api.Utils;

public class Tools {
    public static int str2Int(String str) {
        StringBuilder sb = new StringBuilder();
        char[] chars = str.toCharArray();
        for(char c : chars) {
            if(c >= '0' && c <= '9') {
                sb.append(c);
            }
        }
        return Integer.parseInt(sb.toString());
    }
}
