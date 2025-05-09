# spring-boot-my
常用小功能

## 定义全局异常处理类

- 使用@ControllerAdvice注解接收全局信息
- 可以在@ExceptionHandler注解中指定处理的异常类（可添加多个）

> 示例：GlobalExceptionControllerAdvice

## 公式计算

- 自定义计算（速度较快，含公式格式校验方法） [FormulaUtil.java](src%2Fmain%2Fjava%2Fcom%2Fexample%2Fspringbootmy%2Fcommon%2Futils%2FFormulaUtil.java)
- Jexl [CalcUtils.java](src%2Fmain%2Fjava%2Fcom%2Fexample%2Fspringbootmy%2Fcommon%2Futils%2FCalcUtils.java)
- hutool ExpressionUtil : 示例：hutoolExpr [SpringBootMyApplicationTests.java](src%2Ftest%2Fjava%2Fcom%2Fexample%2Fspringbootmy%2FSpringBootMyApplicationTests.java)

> 参考：<https://blog.csdn.net/weixin_52801742/article/details/118736283>

