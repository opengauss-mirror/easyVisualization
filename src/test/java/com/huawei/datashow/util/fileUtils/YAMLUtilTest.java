package com.huawei.datashow.util.fileUtils;

import com.huawei.datashow.bean.DataSourceEditBean;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class YAMLUtilTest {

    @Test
    void createYAMLFile() throws IOException {
        YAMLUtil.createYAMLFile("雷达数据3");
    }

    @Test
    void writeYAMLFile() throws IOException {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        DataSourceEditBean dataSourceEditBean = new DataSourceEditBean();
        dataSourceEditBean.setDeleteRowIndex(list);
        YAMLUtil.writeYAMLFile("雷达数据3", dataSourceEditBean);
    }
}