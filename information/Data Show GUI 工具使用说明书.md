# Data Show GUI 工具使用说明书

## v1.0.2

<span class="smallcaps">\
</span>


# 一 修订记录

| 版本号 | 修订人 | 修改内容                                                     | 修订日期  |
| ------ | ------ | ------------------------------------------------------------ | --------- |
| v1.0.0 | 李亮杰 | 试运行版本                                                   | 2022.8.29 |
| v1.0.1 | 李亮杰 | 1.增加对“添加数据项”的说明  2.增加对“重新加载数据源”的说明  3.增加对“从文件导入”的文件大小说明  4.增加对“从.txt文件中导入”的说明  5.移除“其他设置”中查看数据项的说明  6.规范文档格式 | 2022.9.8  |
| v1.0.2 | 李亮杰 | 1.修改对于“删除某行某列”的说明  2.增加对3D散点图的说明       | 2022.9.13 |



# 二 系统概述

## 2.1 项目背景

openGauss是华为自研的开源的关系型数据库数据库。随着数据库使用场景日渐丰富、数据库使用方式更加多样，对于数据（库）可视化的要求应运而生。然而，目前市面上现有的可视化工具、可视化软件，不支持openGauss数据库；华为自研的Data Studio工具能将数据库里的数据以表格的形式体现，却暂未实现以柱状图、饼状图等图表的形式展示数据。

基于上述背景以及痛点，本工具支持用户从openGauss数据库导入数据、支持用户上传利用Data Studio工具从openGauss数据库导出的CSV、Excel文件，继而以柱状图、饼状图、折线图、散点图、组合图、条形图、雷达图、3D散点图进行展示。

## 2.2 项目目标

| 序号 | 功能                              | 详细说明                                                     | 具体目标                                                     |
| ---- | --------------------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| 1    | 支持openGauss数据源               | 支持源数据通过jdbc执行sql查询后得到                          | 支持通过sql来得到源数据，包括去重、排序、分组语句。          |
| 2    | 支持openGauss导出的数据表单       | 支持从Data Studio等客户端工具导出的xlsx、txt、csv等数据输入方式 | 支持从xlsx、txt、csv导入待分析数据，必要时可允许用户定制数据格式转换函数，包括将字符类型转为整型等。 |
| 3    | 支持用户设置/移除感兴趣的行或者列 | 从源导入数据后，允许用户再次对数据进行排序、筛选、删除、重新载入操作 | 1.数据操作后，可以通过重新载入恢复源导入的数据     2.可以对数据排序     3.可以筛选某几行或某几列     4.可以删除某几行或某几列     5. 2.3.4可以进行任意步后，剩余数据将可以进行下一步分析 |
| 4    | 绘制可视化图表                    | 利用echarts可以对数据呈现出柱状、折线、饼图、条形图、面积图、XY散点图、曲面图、雷达图，以及组合图 | 1.支持柱状、折线、饼图、条形图、面积图、XY散点图、曲面图、雷达图及组合图3D散点图     2.支持多路数据以不同的图形呈现     3.支持图例切换     4.可以在图形上对图例进行筛选显示 |



## 2.3 Data Show GUI主要操作流程

准备操作

(1)切换到DataShowGUI.jar的目录

(2)命令行输入java -jar DataShowGUI.jar

(3)浏览器输入localhost:8083

### 2.3.1 添加数据源

![](https://gitee.com/liliangjie123/easyVisualization/raw/master/information/img/2.3.1.png)

STEP 1 点击"添加数据源"按钮

STEP 2 在右侧选择添加数据源的方式

### 2.3.2 筛选数据

![](https://gitee.com/liliangjie123/easyVisualization/raw/master/information/img/2.3.2.png)

STEP 1 刷新已有数据源

STEP 2 选择要筛选的数据源

### 2.3.3 可视化

![图形用户界面, 应用程序 描述已自动生成](https://gitee.com/liliangjie123/easyVisualization/raw/master/information/img/2.3.3.png)

STEP 1 保存对数据源进行的修改

STEP 2 点击"可视化"按钮进行可视化配置

STEP 3 选择可视化图表类型

STEP 4 点击"添加echarts数据项"进行数据项配置

![图形用户界面, 网站 描述已自动生成](https://gitee.com/liliangjie123/easyVisualization/raw/master/information/img/2.3.3_1.png)

STEP 5 配置数据项

STEP 6 保存数据项配置

![图形用户界面, 应用程序 描述已自动生成](https://gitee.com/liliangjie123/easyVisualization/raw/master/information/img/2.3.3_2.png)

**说明：**

可以配置多个数据项

STEP 7 点击"绘制可视化图表"进行可视化

![图表, 条形图 描述已自动生成](https://gitee.com/liliangjie123/easyVisualization/raw/master/information/img/2.3.3_3.png)

# 三 功能说明

按照模块，介绍Data Show GUI系统各个功能的使用方法。

## 请务必注意阅读文档中"说明"处的内容

## 3.1 添加数据源

根据需要，添加数据源

![](https://gitee.com/liliangjie123/easyVisualization/raw/master/information/img/3.1.png)

**说明：**

Data Show GUI没有权限调用数据库保存数据，因此所有的提交保存的数据源都是以文件的形式保存在磁盘中的，对数据源的操作效率会受工具运行环境性能以及数据量的影响。

### 3.1.1 从数据库中导入数据

从数据库中导入数据，操作步骤如下

#### 3.1.1.1 建立数据库连接

![](https://gitee.com/liliangjie123/easyVisualization/raw/master/information/img/3.1.1.1.png)

#### 3.1.1.2 选择数据库连接

![图形用户界面, 应用程序, Teams 描述已自动生成](https://gitee.com/liliangjie123/easyVisualization/raw/master/information/img/3.1.1.2.png)

#### 3.1.1.3 筛选数据

![](https://gitee.com/liliangjie123/easyVisualization/raw/master/information/img/3.1.1.3.png)

**说明：**

"数据分组"和"数据筛选"条件不能并存

#### 3.1.1.4 保存数据源

![](https://gitee.com/liliangjie123/easyVisualization/raw/master/information/img/3.1.1.4.png)

**说明：**

-   数据源名称不要包含"." "/" "\\"等特殊符号

-   数据源名称不要重复

-   如果数据源（表）数据过多，保存数据源需要等待较长时间

#### 3.1.1.5 查看已有数据源

![图形用户界面, 应用程序, 表格 描述已自动生成](https://gitee.com/liliangjie123/easyVisualization/raw/master/information/img/3.1.1.5.png)

提示操作成功时，点击刷新，可以获取最新的数据源列表

![图形用户界面, 应用程序, 表格 描述已自动生成](https://gitee.com/liliangjie123/easyVisualization/raw/master/information/img/3.1.1.5_1.png)

### 3.1.2 从外部文件导入数据

**说明：**

-   一次只能上传一个文件
-   文件名将作为数据源名称
-   文件名不要与已有数据源名称重复
-   如果文件内数据量太大，解析文件并保存为数据源需要等待较长时间
-   文件大小上限为5MB

#### 3.1.2.1 从.xls .xlsx文件中导入

![](https://gitee.com/liliangjie123/easyVisualization/raw/master/information/img/3.1.2.1.png)

**说明：**

-   只加载文件中的第一个sheet

-   务必保证文件的第一行是表头

#### 3.1.2.2 从.csv文件中导入

**说明：**

-   操作方式同3.1.2.1

-   务必保证文件的第一行是表头（可通过其他客户端工具导出csv文件时设置）

#### **3.1.2.3 从.txt文件中导入**

**说明：**

-   操作方式同3.1.2.1

-   务必保证文件的第一行是表头（可通过其他客户端工具导出txt文件时设置）

-   txt文件的分隔符需设置为" , "

## **3.2 筛选数据源**

### 3.2.1 查看数据源

![](https://gitee.com/liliangjie123/easyVisualization/raw/master/information/img/3.2.1.png)

### 3.2.2 删除数据源

![](https://gitee.com/liliangjie123/easyVisualization/raw/master/information/img/3.2.2.png)

### 3.2.3 删除某一行或某一列

![](https://gitee.com/liliangjie123/easyVisualization/raw/master/information/img/3.2.3.png)

### 3.2.4 批量删除某几行

![](https://gitee.com/liliangjie123/easyVisualization/raw/master/information/img/3.2.4.png)

**说明：**

不论是"删除某一行"还是"批量删除某几行"， "保存修改"前，删除的数据并未真正删除，只是在后台做了标记，要真正删除需要点击"保存修改"。

### 3.2.5 重新加载数据源

**说明：**


![](https://gitee.com/liliangjie123/easyVisualization/raw/master/information/img/3.2.5.png)

以删除前三行为例

![](https://gitee.com/liliangjie123/easyVisualization/raw/master/information/img/3.2.5_1.png)

![](https://gitee.com/liliangjie123/easyVisualization/raw/master/information/img/3.2.5_2.png)

点击确认按钮后可重新加载数据源

![](https://gitee.com/liliangjie123/easyVisualization/raw/master/information/img/3.2.5_3.jpg)

### 3.2.6 保存修改

![](https://gitee.com/liliangjie123/easyVisualization/raw/master/information/img/3.2.6.jpg)

![图形用户界面, 应用程序, Teams 描述已自动生成](https://gitee.com/liliangjie123/easyVisualization/raw/master/information/img/3.2.6_1.jpg)

**说明：**

-   若数据量过大，"保存修改"耗时会比较长

-   要对筛选后的数据源进行可视化操作，必须"保存修改"后再进行，因此强烈建议用户：

    a.  从数据库导入数据时，善用筛选功能过滤出自己要的数据

    b.  从外部文件导入数据时，通过"导出外部文件的工具"预筛选过滤出自己要的数据

## 3.3 可视化图表配置

![](https://gitee.com/liliangjie123/easyVisualization/raw/master/information/img/3.3.png)

### 3.3.1 术语解释

#### 3.3.1.1 图例与分类（轴）标签

若图例选择为unknownPercent，分类（轴）标签选择为id

![](https://gitee.com/liliangjie123/easyVisualization/raw/master/information/img/3.3.1.1.png)

-   图例

    -   对于柱状图、折线图、面积图、散点图、组合图，图例即y轴的数据

    -   对于直线图，图例即x轴的数据

    -   对于饼状图，图例即饼状图各个扇形的数据

-   分类（轴）标签

    -   对于柱状图、折线图、面积图、面积图、直线图、组合图，分类（轴）标签即x轴的数据

    -   对于直线图，图例即y轴的数据

    -   对于饼状图，分类（轴）标签即饼状图各个扇形的数据的名称

    -   对于雷达图，分类（轴）标签即雷达图各个数据的名称

-   **说明：**
    -   分类轴的数据需要保证无重复。日常生活中进行平面直角坐标系绘图时，如果同一个x对应了两个y，那么这样做出来的图也是会有问题的，因此请避免分类轴的数据无重复

    -   推荐使用数据库表中的无重复自增主键作为分类轴

    -   或者从数据库表中筛选数据时，使用分组语句保证无重复

    -   或者从在筛选数据源时，删除分类轴中重复的数据


#### 3.3.1.2 维度

雷达图特有的配置，即雷达图的各个指标

![图形用户界面, 应用程序, 表格 描述已自动生成](https://gitee.com/liliangjie123/easyVisualization/raw/master/information/img/3.3.1.2.png)

### 3.3.2 选择可视化图表类型

![图形用户界面, 应用程序, 表格 描述已自动生成](https://gitee.com/liliangjie123/easyVisualization/raw/master/information/img/3.3.2.png)

### 3.3.3 柱状图

**说明：**

-   对于每一个数据源，配置数据项时，只需要关注"图例"、"分类（轴）"或"维度"，不需要选择具体展示某几条数据

-   如果需要展示具体某几条数据，可以在"筛选数据源"时删除那些不需要的数据

#### 3.3.3.1 添加数据项

![](https://gitee.com/liliangjie123/easyVisualization/raw/master/information/img/3.3.3.1.png)

#### 3.3.3.2 配置数据项

![](https://gitee.com/liliangjie123/easyVisualization/raw/master/information/img/3.3.3.2.png)

**说明：**

-   一个数据项只能选择一个数据源，如果需要从多个数据源中选择数据进行交叉比较，需要新建一个数据项

-   每个数据源只能同时被一个数据项选择

#### **3.3.3.3 删除数据项**

![](https://gitee.com/liliangjie123/easyVisualization/raw/master/information/img/3.3.3.3.png)

#### 3.3.3.4 绘制可视化图表

![](https://gitee.com/liliangjie123/easyVisualization/raw/master/information/img/3.3.3.4.png)

**说明：**

至少需要一个数据项才能绘制可视化图表

![](https://gitee.com/liliangjie123/easyVisualization/raw/master/information/img/3.3.3.4_1.png)

### 3.3.4 折线图

同3.3.3

### 3.3.5 面积图

同3.3.3

### 3.3.6 散点图

同3.3.3

### 3.3.7 条形图

同3.3.3

### 3.3.8 组合图

#### 3.3.8.1 添加数据项

同3.3.3.1

#### 3.3.8.2 配置数据项

![图形用户界面, 网站 描述已自动生成](https://gitee.com/liliangjie123/easyVisualization/raw/master/information/img/3.3.8.2.png)

**说明：**

-   一个数据项只能选择一个数据源，如果需要从多个数据源中选择数据进行交叉比较，需要新建一个数据项

-   每个数据源只能同时被一个数据项选择

#### 3.3.8.3 **删除数据项**

同3.3.3.3

#### 3.3.8.4 绘制可视化图表

同3.3.3.4

![图表 描述已自动生成](https://gitee.com/liliangjie123/easyVisualization/raw/master/information/img/3.3.8.4.png)

### 3.3.9 饼状图

#### 3.3.9.1 添加数据项

同3.3.3.1

**说明：**饼状图只能有一个数据项

#### 3.3.9.2 配置数据项

![](https://gitee.com/liliangjie123/easyVisualization/raw/master/information/img/3.3.9.2.png)

#### 3.3.9.3 删除数据项

同3.3.3.3

#### 3.3.9.4 绘制可视化图表

同3.3.3.4

![](https://gitee.com/liliangjie123/easyVisualization/raw/master/information/img/3.3.9.4.png)

### 3.3.10 雷达图

#### 3.3.10.1 添加数据项

同3.3.3.1

**说明：**雷达图只能有一个数据项

#### 3.3.10.2 配置数据项

![](https://gitee.com/liliangjie123/easyVisualization/raw/master/information/img/3.3.10.2.png)

**说明：**维度只有选择三个及以上才能体现雷达图的效果（一个维度是一个点，两个维度是一条线）

#### 3.3.10.3 删除数据项

同3.3.3.3

#### 3.3.10.4 绘制可视化图表

同3.3.3.4

![](https://gitee.com/liliangjie123/easyVisualization/raw/master/information/img/3.3.10.4.png)

### 3.3.11 3D散点图

![](https://gitee.com/liliangjie123/easyVisualization/raw/master/information/img/3.3.11.png)

## 3.4 可视化图表详细设置

### 3.4.1 柱状图

#### 3.4.1.1 选择数据范围

![](https://gitee.com/liliangjie123/easyVisualization/raw/master/information/img/3.4.1.1.png)
**说明：**

-   如果有多个数据项，数据条数取决于数据条数最多的那个数据项

-   默认先加载数据源的前10条数据

-   **特别注意：虽然理论上可以全量加载数据，但是根据工具运行环境的性能差异，工具在大数据可视化时体现的性能不同，强烈不建议同时加载大量数据**

#### **3.4.1.2 标题设置**

![](https://gitee.com/liliangjie123/easyVisualization/raw/master/information/img/3.4.1.2.png)

#### 3.4.1.3 图例设置

![](https://gitee.com/liliangjie123/easyVisualization/raw/master/information/img/3.4.1.3.png)

#### 3.4.1.4 绘图网格设置

![图表, 条形图 描述已自动生成](https://gitee.com/liliangjie123/easyVisualization/raw/master/information/img/3.4.1.4.png)

**说明：**

-   网格左右侧距离的单位是%，上下侧距离的单位是px

-   根据用户显示器分辨率不同，距离调整会有偏差，如：网格下侧距离设置为0，可能图表横轴会超出屏幕范围

#### **3.4.1.5 横轴设置**

![](https://gitee.com/liliangjie123/easyVisualization/raw/master/information/img/3.4.1.5.png)

**说明：**

-   当横轴数据太多，标签展示不全时，可以采取

    a.  增加标签间隔

![](https://gitee.com/liliangjie123/easyVisualization/raw/master/information/img/3.4.1.5_a.png)

​    b.  旋转标签

![](https://gitee.com/liliangjie123/easyVisualization/raw/master/information/img/3.4.1.5_b.png)

#### **3.4.1.6 纵轴设置**

同3.4.1.5

#### **3.4.1.7 其他设置**

![](https://gitee.com/liliangjie123/easyVisualization/raw/master/information/img/3.4.1.7.png)

#### **3.4.1.8 保存图表等其他功能**

![](https://gitee.com/liliangjie123/easyVisualization/raw/master/information/img/3.4.1.8.png)

**说明：**

下载的图片格式为.jpg

### 3.4.2 折线图

同3.4.1

### 3.4.3 面积图

同3.4.1

### 3.4.4 散点图

同3.4.1

### 3.4.5 条形图

同3.4.1

### 3.4.6 组合图

同3.4.1

### 3.4.7 饼状图

本节未提到的设置均与3.4.1相同

#### 3.4.7.1 图例设置

![](https://gitee.com/liliangjie123/easyVisualization/raw/master/information/img/3.4.7.1.png)

**说明：**

-   饼状图会自动为图例设置颜色，不能自己设置图例颜色

-   饼状图的图例只有9种颜色

    a.  当有10条数据的时候，第10条数据和第1条数据颜色会相同，依次类推。

    b.  为了区分，可以打开"南丁格尔图"选项。

#### **3.4.7.2 饼图设置**

**说明：**饼图没有横轴、纵轴设置、其他设置

![](https://gitee.com/liliangjie123/easyVisualization/raw/master/information/img/3.4.7.2.png)

##### **3.4.7.2.1 南丁格尔图**

![](https://gitee.com/liliangjie123/easyVisualization/raw/master/information/img/3.3.7.2.1.png)

### 3.4.8 雷达图

#### 3.4.8.1 图例设置

![](https://gitee.com/liliangjie123/easyVisualization/raw/master/information/img/3.4.8.1.png)

**说明：**

雷达图图例不能指定颜色

#### **3.4.8.2 雷达图设置**

![](https://gitee.com/liliangjie123/easyVisualization/raw/master/information/img/3.4.8.2.png)

### 3.4.9 3D散点图

同3.4.1

**说明：**3D散点图只支持如下图所示的个性化定制功能

![](https://gitee.com/liliangjie123/easyVisualization/raw/master/information/img/3.4.9.png)

# 四 删除Data Show GUI

1.退出浏览器并在终端关闭DataShowGUI进程(ctrl + c 终止进程)

2.删除DataShowGUI.jar

3.删除运行目录下的data-source目录和data-source-edit目录
