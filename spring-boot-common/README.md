# spring-boot-common

## office tool

- apache.poi word
    - apache.poi生成word文件
        > 示例：_/apachePoiWord/exportWord_
        - 文本
        - 表格(支持行、列合并)
        - 图片
        - 加水印
            ````
            // 方式一
            WordUtil.setWordWaterMark(doc, "机密", "#d8d8d8");
            // 方式二
            WordUtil.makeFullWaterMarkByWordArt(doc, "机密", "#888888", "0.7pt","-30");
            ````
    - apache.poi word 转 pdf
        > 示例：_/apachePoiWord/word2pdf_ 仅docx格式 
    - poi-tl 根据模板生成word
        - 文本
        - 图片
        - 表格
        > 示例：_/apachePoiWord/exportTlTemplateWorld_
        poi-tl 基于Apache POI的Word模板引擎，较简单
    
    - apache.poi excel
        - 导出excel-Map(支持下拉、合并单元格)：_/apachePoiExcel/exportMap_
        - 导出excel-根据对象导出：_/apachePoiExcel/exportVo_
        - 导出excel-导出模板：_/apachePoiExcel/exportModel_
        - 导出excel-多sheet页：_/apachePoiExcel/exportManySheet_
        - 导入Excel数据为JSON格式：_/office/importToJSON_
        - 导入Excel数据为指定对象：_/office/importToVo_
        - 导入多Sheet页：_/office/importManySheet_

    >       参考：<https://blog.csdn.net/sunnyzyq/article/details/121994504>  
          maven依赖:
          ````
          <!-- 文件上传 -->
          <dependency>
              <groupId>org.apache.httpcomponents</groupId>
              <artifactId>httpmime</artifactId>
              <version>4.5.7</version>
          </dependency>
          <!-- POI -->
          <dependency>
              <groupId>org.apache.poi</groupId>
              <artifactId>poi-ooxml</artifactId>
              <version>3.16</version>
          </dependency>
          ````
- easy poi 
    - word
        - 文本
        - 表格
        - 图片
        > 示例：_/easyPoiWord/exportWordTemplate_  

        > 参考:<https://blog.csdn.net/weixin_45692705/article/details/123914941>
    - excel
        - 导入：_/easyPoiExcel/importExcelClass_
            > 文件示例：templates/easy_poi_import_user.xlsx
        - 导出：_/easyPoiExcel/exportExcelClass_
            > 支持图片导出
        > 参考：<https://blog.csdn.net/blueberrya/article/details/123008148>

- easyexcel
  ````
    <!--easyexcel-->
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>easyexcel</artifactId>
        <version>3.2.0</version>
    </dependency>
    ````

    - 导出多级表头excel
    > 方式一：  
    动态表头  
    示例：_/easyExcel/exportExcelMultistageHeader_  
    参考：<https://blog.csdn.net/qq_43301206/article/details/131844772>  
    方式二：  
    或者使用注解实现  
    @ExcelProperty(value = {"表头一","表头二"})  
    参考：<https://blog.csdn.net/lol19950605/article/details/135776897>  
    该文档还支持：批注、公式、合并单元格、自定义样式、超链接、设置密码等

    - 导出下拉
    > 示例：_/easyExcel/exportExcelSelect_
  
    > 参考：<https://blog.csdn.net/weixin_42653892/article/details/122618138>

    - 导出级联下拉
    > 示例：_/easyExcel/exportExcelCascadeSelect_

    > 参考：<https://www.cnblogs.com/lcy2020/p/18494495>

    - 导出图片
    > 示例：_/easyExcel/exportExcelImage_

    > 参考：<https://blog.csdn.net/xhmico/article/details/137724425>

    - 根据模板导出
    > 示例：_/easyExcel/exportExcelTemplate_

- 图片压缩
    > 示例：_/image/compressImage_



