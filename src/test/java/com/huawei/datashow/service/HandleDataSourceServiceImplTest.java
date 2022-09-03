package com.huawei.datashow.service;

import org.junit.jupiter.api.Test;
import java.io.IOException;

class HandleDataSourceServiceImplTest {

    @Test
    void saveDataSource() {
    }

    @Test
    void readDataSource() {
    }

    @Test
    void getDataSourceSize() {
    }

    @Test
    void removeDataSource() throws IOException {
        HandleDataSourceService handleDataSourceServiceImpl = new HandleDataSourceServiceImpl();
        handleDataSourceServiceImpl.removeDataSource("测试2");
    }

    @Test
    void getDataSourceList() {
    }

    @Test
    void editDataSource() {
    }

    @Test
    void saveEditDataSource() throws IOException {
        HandleDataSourceService handleDataSourceServiceImpl = new HandleDataSourceServiceImpl();
        handleDataSourceServiceImpl.saveEditDataSource("雷达数据3");
    }
}