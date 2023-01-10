# spring-boot-common

## office tool

- apache.poi wold
    - apache.poi生成wold文件
        - 文本
        - 表格(支持行、列合并)
        - 图片
        > 示例：OfficeWordService>buildApacheWord
    - poi-tl 根据模板生成wold
        - 支持文本
        - 图片
        - 表格
        > 示例：OfficeWordService>buildApacheWord  
        > poi-tl 基于Apache POI的Word模板引擎，较简单
        
- apache.poi excel
    - 导入Excel数据为JSON格式：_/office/importToJSON_
    - 导入Excel数据为指定对象：_/office/importToVo_
    - 导入多Sheet页：_/office/importManySheet_
    - 导出excel：_/office/export_
    - 导出excel模板：_/office/exportModel_
    - 导出excel-根据对象导出：_/office/exportVo_
    - 导出多sheet页Excel：_/office/exportManySheet_
        
        