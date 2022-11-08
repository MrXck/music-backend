## 环境

```
JDK 8
MySQL >= 5.6
redis
```

## 准备工作

```
修改 src\main\resources\application.yml 中的数据库配置
尽量不要修改端口如果要修改的话前端项目也需要修改 
同时CommonController中upload方法的返回值中的端口也需要修改

在MySQL中创建叫做 music 的数据库
然后导入 music.sql

修改utils下SMSUtils中对于腾讯云短信的配置
```
