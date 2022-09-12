package com.huawei.datashow.util.fileUtils;

import org.junit.jupiter.api.Test;

import java.io.File;


class CommonUtilTest {

    @Test
    void checkDir() {
        String dir = CommonUtil.DATA_SOURCE_DIR + "/test";
        CommonUtil.checkDir(dir);
        File file = new File(dir);
        if (file.exists()) {
            file.delete();
        }
    }
}