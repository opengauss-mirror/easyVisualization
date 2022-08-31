package com.huawei.datashow.util.fileUtils;

import java.io.File;


public class CommonUtil {
    public static String DATA_SOURCE_DIR = System.getProperty("user.dir") + "/data-source";
    public static String DATA_SOURCE_EDIT_DIR = System.getProperty("user.dir") + "/data-source-edit";

    public static void checkDir(String dirName) {
        File dir = new File(dirName);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }
}
