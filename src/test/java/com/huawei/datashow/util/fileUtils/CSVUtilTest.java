package com.huawei.datashow.util.fileUtils;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

class CSVUtilTest {

    @Test
    void writeCSVFile() {
        List<Map> list = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            Map<String, Integer> map = new LinkedHashMap<>();
            map.put("age1", i);
            map.put("age2", i * 2);
            list.add(map);
        }
        CSVUtil.writeCSVFile("testCSVa", list,true);
    }

    @Test
    void readCSVFile() throws IOException {
        // String dataA = CSVUtil.readCSVFile("雷达数据3", 10, 0, CSVUtil.SHOW_DATA_SOURCE_MODE);
        String dataB = CSVUtil.readCSVFile("李亮杰_学生信息", 10, 0, CSVUtil.SAVE_DATA_SOURCE_EDIT_MODE);
        System.out.println(dataB);

    }

    @Test
    void removeCSVFile() {
        String fileName = "smbms_3Ddata";
        System.out.println(Paths.get(fileName));
        try {
            Files.delete(Paths.get(fileName));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    @Test
    void getRowCount() throws IOException {
        int rowCount = CSVUtil.getRowCount("雷达数据3");
        System.out.println(rowCount);
    }
}