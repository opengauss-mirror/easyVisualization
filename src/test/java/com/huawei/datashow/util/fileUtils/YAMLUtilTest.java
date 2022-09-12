package com.huawei.datashow.util.fileUtils;

import com.huawei.datashow.bean.DataSourceEditBean;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


class YAMLUtilTest {

    @Test
    void getPath() {
        String fileName = "test";
        File yamlDir = new File(CommonUtil.DATA_SOURCE_EDIT_DIR + "/" + fileName + "-edit.yaml");
        try {
            yamlDir.createNewFile();
            Assertions.assertEquals(yamlDir.getPath(), YAMLUtil.getPath(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            yamlDir.delete();
        }
    }

    @Test
    void getYaml() {
        Assertions.assertEquals(new Yaml().getClass(), YAMLUtil.getYaml().getClass());
    }

    @Test
    void createYAMLFile() throws IOException {
        YAMLUtil.createYAMLFile("test");
        File yamlDir = new File(CommonUtil.DATA_SOURCE_EDIT_DIR + "/test-edit.yaml");
        Assertions.assertTrue(yamlDir.exists());
        yamlDir.delete();
    }

    @Test
    void writeYAMLFile() throws IOException {
        YAMLUtil.createYAMLFile("test");
        List<Integer> list = new ArrayList<>();
        List<String> list1 = new ArrayList<>();
        list.add(1);
        list.add(2);
        DataSourceEditBean dataSourceEditBean = new DataSourceEditBean();
        dataSourceEditBean.setDeleteRowIndex(list);
        dataSourceEditBean.setDeleteColumnName(list1);
        YAMLUtil.writeYAMLFile("test", dataSourceEditBean);
        DataSourceEditBean sourceEditBean = YAMLUtil.readYAMLFile("test");
        Assertions.assertEquals(dataSourceEditBean, sourceEditBean);
        File yamlDir = new File(CommonUtil.DATA_SOURCE_EDIT_DIR + "/test-edit.yaml");
        yamlDir.delete();
    }

    @Test
    void removeYAMLFile() {
        File yamlDir = new File(CommonUtil.DATA_SOURCE_EDIT_DIR + "/test-edit.yaml");
        try {
            yamlDir.createNewFile();
            YAMLUtil.removeYAMLFile("test");
            Assertions.assertTrue(!yamlDir.exists());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            yamlDir.delete();
        }
    }

    @Test
    void readYAMLFile() throws IOException {
        YAMLUtil.createYAMLFile("test");
        List<Integer> list = new ArrayList<>();
        List<String> list1 = new ArrayList<>();
        list.add(1);
        list.add(2);
        DataSourceEditBean dataSourceEditBean = new DataSourceEditBean();
        dataSourceEditBean.setDeleteRowIndex(list);
        dataSourceEditBean.setDeleteColumnName(list1);
        YAMLUtil.writeYAMLFile("test", dataSourceEditBean);
        DataSourceEditBean sourceEditBean = YAMLUtil.readYAMLFile("test");
        Assertions.assertEquals(dataSourceEditBean, sourceEditBean);
        File yamlDir = new File(CommonUtil.DATA_SOURCE_EDIT_DIR + "/test-edit.yaml");
        yamlDir.delete();
    }
}