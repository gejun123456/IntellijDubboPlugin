package com.bruce.dubboplugin.helper;

import java.io.PrintWriter;
import java.io.StringWriter;

public class CommonUtils {
    public static String printStackTrace(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }
}
