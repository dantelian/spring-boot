# spring-boot-elasticsearch-jdk17

## 创建索引

> /es/createIndex

### mapping

_基本数据类型_

| 类型                                   | 解释                             |
|--------------------------------------|--------------------------------|
| text                                 | 字符串类型，会被分词并索引                  |
| keyword                              | 字符串类型，不会被分词，需要精确匹配             |
| long,integer,short,byte              | 整数                             |
| double,float,half_float,scaled_float | 浮点数                            |
| date                                 | 日期类型，日期和时间，可以支持多种格式的日期字符串或时间戳  |
| boolean                              | 布尔类型，true 或 false              |
| binary                               | 二进制类型，任意二进制数据，通常作为Base64编码的字符串 |
| range                                | 范围类型，用于存储范围值，如日期范围或数值范文        |
| geo_point,geo_shape                  | 地理类型：分别用于存储地理坐标和地理形状数据         |



````json
{
  "properties": {
    "id": {
      "type": "keyword"
    },
    "name": {
      "type": "text"
    },
    "gender": {
      "type": "text"
    },
    "age": {
      "type": "integer"
    },
    "birthday": {
      "type": "date",
      "format": "yyyy-MM-dd"
    }
  }
}
````










