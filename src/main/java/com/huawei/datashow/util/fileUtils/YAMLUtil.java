package com.huawei.datashow.util.fileUtils;

import com.huawei.datashow.bean.DataSourceEditBean;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


public class YAMLUtil {
    public static String getPath(String fileName) {
        return CommonUtil.DATA_SOURCE_EDIT_DIR + "/" + fileName + "-edit.yaml";
    }

    public static Yaml getYaml() {
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        return new Yaml(dumperOptions);
    }

    public static void createYAMLFile(String fileName) throws IOException {
        CommonUtil.checkDir(CommonUtil.DATA_SOURCE_EDIT_DIR);
        FileWriter fileWriter = new FileWriter(new File(getPath(fileName)));
        Yaml yaml = getYaml();
        LinkedHashMap<String, Object> dataSourceEdit = new LinkedHashMap<>();
        dataSourceEdit.put("deleteRowIndex", new ArrayList<>());
        dataSourceEdit.put("deleteColumnName", new ArrayList<>());
        yaml.dump(dataSourceEdit, fileWriter);
    }

    public static void writeYAMLFile(String fileName, DataSourceEditBean dataSourceEditBean) throws IOException {
        DataSourceEditBean dataSourceEditBeanYaml = readYAMLFile(fileName);

        List<Integer> deleteRowIndexYaml = dataSourceEditBeanYaml.getDeleteRowIndex();
        List<String> deleteColumnNameYaml = dataSourceEditBeanYaml.getDeleteColumnName();

        if (dataSourceEditBean.getDeleteRowIndex() != null) {
            for (int rowIndex : dataSourceEditBean.getDeleteRowIndex()) {
                if (!deleteRowIndexYaml.contains(rowIndex)) {
                    deleteRowIndexYaml.add(rowIndex);
                }
            }
        }

        if (dataSourceEditBean.getDeleteColumnName() != null) {
            for (String columnName : dataSourceEditBean.getDeleteColumnName()) {
                if (!deleteColumnNameYaml.contains(columnName)) {
                    deleteColumnNameYaml.add(columnName);
                }
            }
        }

        dataSourceEditBeanYaml.setDeleteRowIndex(deleteRowIndexYaml);
        dataSourceEditBeanYaml.setDeleteColumnName(deleteColumnNameYaml);

        Yaml yaml = getYaml();
        yaml.dump(dataSourceEditBeanYaml, new FileWriter(new File(getPath(fileName))));
    }

    public static void removeYAMLFile(String fileName) throws IOException {
        Files.delete(Paths.get(getPath(fileName)));
    }

    public static DataSourceEditBean readYAMLFile(String fileName) throws FileNotFoundException {
        Yaml yaml = getYaml();
        FileInputStream fileInputStream = new FileInputStream(new File(getPath(fileName)));
        return yaml.loadAs(fileInputStream, DataSourceEditBean.class);
    }

}
