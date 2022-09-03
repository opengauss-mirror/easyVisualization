package com.huawei.datashow.bean;

import lombok.Data;
import java.util.List;

@Data
public class DataSourceEditBean {
    private List<Integer> deleteRowIndex;
    private List<String> deleteColumnName;
}
