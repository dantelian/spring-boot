# spring-boot-common

## office tool

- apache.poi wold
    - apache.poi生成wold文件
        - 文本
        - 表格(支持行、列合并)
        - 图片
            > 示例：OfficeWordService>buildApacheWord
        - 加水印
            > WordUtil.setWordWaterMark(doc, "机密", "#d8d8d8");  
            WordUtil.makeFullWaterMarkByWordArt(doc, "机密", "#888888", "0.7pt","-30");
        - apache.poi word 转 pdf
            > /office/word2pdf 仅docx格式 
    - poi-tl 根据模板生成wold
        - 文本
        - 图片
        - 表格
        > 示例：OfficeWordService>buildApacheWord  
        poi-tl 基于Apache POI的Word模板引擎，较简单
    
- apache.poi excel
    - 导入Excel数据为JSON格式：_/office/importToJSON_
    - 导入Excel数据为指定对象：_/office/importToVo_
    - 导入多Sheet页：_/office/importManySheet_
    - 导出excel：_/office/export_
    - 导出excel模板：_/office/exportModel_
    - 导出excel-根据对象导出：_/office/exportVo_
    - 导出多sheet页Excel：_/office/exportManySheet_

    > 参考：<https://blog.csdn.net/sunnyzyq/article/details/121994504>  
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
    - wold
        - 文本
        - 表格
        - 图片
        > 参考:<https://blog.csdn.net/weixin_45692705/article/details/123914941>
    - excel
        - 导入：/office/importEasyPoiExcel  
            > 文件示例：templates/easy_poi_import_user.xlsx
        - 导出：/office/exportEasyPoiExcel
            > 支持图片导出
        > 参考：<https://blog.csdn.net/blueberrya/article/details/123008148>

