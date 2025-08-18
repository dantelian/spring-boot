# spring-boot-mybatis
支持功能：增删改查，分页，jpa

> 实体类字段转字符串返回 [Role.java](src%2Fmain%2Fjava%2Fcom%2Fexample%2Fspringbootmybatis%2Fentity%2FRole.java)
````java
// 方案一
@JsonFormat(shape =JsonFormat.Shape.STRING)

// 方案二
@JsonSerialize(using = ToStringSerializer.class)
````

## Mybatis-plus 多租户插件

[多租户]<https://blog.csdn.net/qq_55272229/article/details/145481171>
