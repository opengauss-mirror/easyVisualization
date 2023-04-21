package com.huawei.datashow.service;

import com.alibaba.fastjson.JSON;
import com.huawei.datashow.bean.DataSourceEditBean;
import com.huawei.datashow.util.SQLUtil;
import com.huawei.datashow.util.fileUtils.CSVUtil;
import com.huawei.datashow.util.fileUtils.CommonUtil;
import com.huawei.datashow.util.fileUtils.YAMLUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.UUID;


@Service
public class HandleDataSourceServiceImpl implements HandleDataSourceService {
    @Autowired
    OpenGaussDataBaseService openGaussDataBaseServiceImpl;

    @Override
    public void saveDataSource(String pollName, String sql, String dataSourceName) throws Exception {
        String countSql = SQLUtil.getCountSql(sql);
        List<Map> arrayList = JSON.parseObject(openGaussDataBaseServiceImpl.getSourceData(pollName, countSql), ArrayList.class);
        int count = (int) arrayList.get(0).get("count");
        int batch = count / 1000;

        if (batch > 0) {
            for (int i = 0; i < batch; i++) {
                String jsonListMap = openGaussDataBaseServiceImpl.getSourceData(pollName, sql + String.format(" limit %d, %d", i * 1000, 1000));
                List<Map> listMap = JSON.parseObject(jsonListMap, ArrayList.class);
                CSVUtil.writeCSVFile(dataSourceName, listMap, i != 0);
            }
            String jsonListMap = openGaussDataBaseServiceImpl.getSourceData(pollName, sql + String.format(" limit %d, %d", batch * 1000, count - batch * 1000 + 1));
            List<Map> listMap = JSON.parseObject(jsonListMap, ArrayList.class);
            CSVUtil.writeCSVFile(dataSourceName, listMap, true);
        } else {
            String jsonListMap = openGaussDataBaseServiceImpl.getSourceData(pollName, sql + String.format(" limit %d, %d", 0, count));
            List<Map> listMap = JSON.parseObject(jsonListMap, ArrayList.class);
            CSVUtil.writeCSVFile(dataSourceName, listMap, false);
        }
        YAMLUtil.createYAMLFile(dataSourceName);
    }

    @Override
    public String readDataSource(String dataSourceName, int startIndex, int limit) throws IOException {
        return CSVUtil.readCSVFile(dataSourceName, limit, startIndex, CSVUtil.SHOW_DATA_SOURCE_MODE);
    }

    @Override
    public int getDataSourceSize(String dataSourceName) throws IOException {
        return CSVUtil.getRowCount(dataSourceName);
    }

    @Override
    public void removeDataSource(String dataSourceName) throws IOException {
        CSVUtil.removeCSVFile(dataSourceName);
        YAMLUtil.removeYAMLFile(dataSourceName);
    }

    @Override
    public String getDataSourceList() {
        File dir = new File(CommonUtil.DATA_SOURCE_DIR);

        if (!dir.exists()) {
            dir.mkdir();
        }
        File[] files = dir.listFiles();
        List<String> dataSourceList = new ArrayList<>();
        for (File file : files) {
            if (file.isFile()) {
                dataSourceList.add(file.getName());
            }
        }
        return JSON.toJSONString(dataSourceList);
    }

    @Override
    public void editDataSource(String dataSourceName, DataSourceEditBean dataSourceEditBean) throws IOException {
        YAMLUtil.writeYAMLFile(dataSourceName, dataSourceEditBean);
    }

    @Override
    public void reloadDataSource(String dataSourceName) throws IOException {
        YAMLUtil.createYAMLFile(dataSourceName);
    }

    @Override
    public void saveEditDataSource(String dataSourceName) throws IOException {
        int rowCount = CSVUtil.getRowCount(dataSourceName);
        int batch = rowCount / 1000;

        String uuid = UUID.randomUUID().toString();
        String tmpFileName = dataSourceName + uuid;
        if (batch > 0) {
            for (int i = 0; i < batch; i++) {
                String s = CSVUtil.readCSVFile(dataSourceName, 1000, i * 1000, CSVUtil.SAVE_DATA_SOURCE_EDIT_MODE);
                CSVUtil.writeCSVFile(tmpFileName, (List<Map>) JSON.parseObject(s, LinkedHashMap.class).get("sourceData"), i != 0);
            }
            String s = CSVUtil.readCSVFile(dataSourceName, rowCount - batch * 1000, batch * 1000, CSVUtil.SAVE_DATA_SOURCE_EDIT_MODE);
            CSVUtil.writeCSVFile(tmpFileName, (List<Map>) JSON.parseObject(s, LinkedHashMap.class).get("sourceData"), true);
        } else {
            String s = CSVUtil.readCSVFile(dataSourceName, rowCount, 0, CSVUtil.SAVE_DATA_SOURCE_EDIT_MODE);
            CSVUtil.writeCSVFile(tmpFileName, (List<Map>) JSON.parseObject(s, LinkedHashMap.class).get("sourceData"), false);
        }


        CSVUtil.removeCSVFile(dataSourceName);
        File oldFile = new File(CSVUtil.getPath(tmpFileName));
        oldFile.renameTo(new File(CSVUtil.getPath(dataSourceName)));


        YAMLUtil.createYAMLFile(dataSourceName);
    }

    @Override
    public boolean getEditStatus(String dataSourceName) throws IOException {
        boolean flag = false;
        DataSourceEditBean dataSourceEditBean = YAMLUtil.readYAMLFile(dataSourceName);
        if (dataSourceEditBean.getDeleteColumnName().size() > 0 || dataSourceEditBean.getDeleteRowIndex().size() > 0) {
            flag = true;
        }
        return flag;
    }
}