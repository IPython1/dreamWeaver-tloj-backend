# SpringBoot 项目初始模板

> 作者：[程序员鱼皮](https://github.com/liyupi)
> 仅分享于 [编程导航知识星球](https://yupi.icu)

基于 Java SpringBoot 的项目初始模板，整合了常用框架和主流业务的示例代码。

只需 1 分钟即可完成内容网站的后端！！！大家还可以在此基础上快速开发自己的项目。

[toc]
IPython
111111666666
## 模板特点

### 主流框架 & 特性

- Spring Boot 2.7.x（贼新）
- Spring MVC
- MyBatis + MyBatis Plus 数据访问（开启分页）
- Spring Boot 调试工具和项目处理器
- Spring AOP 切面编程
- Spring Scheduler 定时任务
- Spring 事务注解

### 数据存储

- MySQL 数据库
- Redis 内存数据库
- Elasticsearch 搜索引擎
- 腾讯云 COS 对象存储

### 工具类

- Easy Excel 表格处理
- Hutool 工具库
- Apache Commons Lang3 工具类
- Lombok 注解

### 业务特性

- 业务代码生成器（支持自动生成 Service、Controller、数据模型代码）
- Spring Session Redis 分布式登录
- 全局请求响应拦截器（记录日志）
- 全局异常处理器
- 自定义错误码
- 封装通用响应类
- Swagger + Knife4j 接口文档
- 自定义权限注解 + 全局校验
- 全局跨域处理
- 长整数丢失精度解决
- 多环境配置


## 业务功能

- 提供示例 SQL（用户、帖子、帖子点赞、帖子收藏表）
- 用户登录、注册、注销、更新、检索、权限管理
- 帖子创建、删除、编辑、更新、数据库检索、ES 灵活检索
- 帖子点赞、取消点赞
- 帖子收藏、取消收藏、检索已收藏帖子
- 帖子全量同步 ES、增量同步 ES 定时任务
- 支持微信开放平台登录
- 支持微信公众号订阅、收发消息、设置菜单
- 支持分业务的文件上传

### 单元测试

- JUnit5 单元测试
- 示例单元测试类

### 架构设计

- 合理分层


## 快速上手

> 所有需要修改的地方鱼皮都标记了 `todo`，便于大家找到修改的位置~

### MySQL 数据库

1）修改 `application.yml` 的数据库配置为你自己的：

```yml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/my_db
    username: root
    password: 123456
```

2）执行 `sql/create_table.sql` 中的数据库语句，自动创建库表

3）启动项目，访问 `http://localhost:8101/api/doc.html` 即可打开接口文档，不需要写前端就能在线调试接口了~

![](doc/swagger.png)

### Redis 分布式登录

1）修改 `application.yml` 的 Redis 配置为你自己的：

```yml
spring:
  redis:
    database: 1
    host: localhost
    port: 6379
    timeout: 5000
    password: 123456
```

2）修改 `application.yml` 中的 session 存储方式：

```yml
spring:
  session:
    store-type: redis
```

3）移除 `MainApplication` 类开头 `@SpringBootApplication` 注解内的 exclude 参数：

修改前：

```java
@SpringBootApplication(exclude = {RedisAutoConfiguration.class})
```

修改后：


```java
@SpringBootApplication
```

### Elasticsearch 搜索引擎

1）修改 `application.yml` 的 Elasticsearch 配置为你自己的：

```yml
spring:
  elasticsearch:
    uris: http://localhost:9200
    username: root
    password: 123456
```

2）复制 `sql/post_es_mapping.json` 文件中的内容，通过调用 Elasticsearch 的接口或者 Kibana Dev Tools 来创建索引（相当于数据库建表）

```
PUT post_v1
{
 参数见 sql/post_es_mapping.json 文件
}
```

这步不会操作的话需要补充下 Elasticsearch 的知识，或者自行百度一下~

3）开启同步任务，将数据库的帖子同步到 Elasticsearch

找到 job 目录下的 `FullSyncPostToEs` 和 `IncSyncPostToEs` 文件，取消掉 `@Component` 注解的注释，再次执行程序即可触发同步：

```java
// todo 取消注释开启任务
//@Component
```

### 业务代码生成器

支持自动生成 Service、Controller、数据模型代码，配合 MyBatisX 插件，可以快速开发增删改查等实用基础功能。

找到 `generate.CodeGenerator` 类，修改生成参数和生成路径，并且支持注释掉不需要的生成逻辑，然后运行即可。

```
// 指定生成参数
String packageName = "com.yupi.tloj";
String dataName = "用户评论";
String dataKey = "userComment";
String upperDataKey = "UserComment";
```

生成代码后，可以移动到实际项目中，并且按照 `// todo` 注释的提示来针对自己的业务需求进行修改。


/**
* 获取当前登录用户
*
* @param request
* @return
*/
* 
@Override
public User getLoginUser(HttpServletRequest request) {
    // 先判断是否已登录
    //会从 request 对象中提取会话信息，然后根据会话中的用户标识（如用户ID）查询数据库或缓存，获取用户的详细信息。
    //如果会话中存在用户标识，并且能够查询到对应的用户信息，则返回该用户对象；否则返回 null 或抛出异常。
    //通过这种方式，getLoginUser 方法能够从 request 中获取当前登录用户的状态。
    Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
    User currentUser = (User) userObj;
    if (currentUser == null || currentUser.getId() == null) {
    throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
    }
    // 从数据库查询（追求性能的话可以注释，直接走缓存）
    long userId = currentUser.getId();
    currentUser = this.getById(userId);
    if (currentUser == null) {
    throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
    }
    return currentUser;
}

alt+enter对着类名 会出现很多可以选择的操作比如 创建实现类，创建测试类等等


工厂模式：工厂模式是一种创建型设计模式，用于在不指定具体类的情况下创建对象。这里 CodeSandboxFactory 是一个工厂类，负责根据传入的 type 参数创建具体的 CodeSandbox 对象。
CodeSandbox 接口：这是一个定义了代码沙箱行为的接口。具体的实现类会根据 type 参数来决定。
newInstance(type) 方法：这个方法根据传入的 type 参数返回一个具体的 CodeSandbox 实现对象。
代理模式：代理模式是一种结构型设计模式，用于为其他对象提供一种代理以控制对这个对象的访问。这里的 CodeSandboxProxy 是一个代理类，它包装了 codeSandbox 对象，并在其上调用方法时添加了额外的功能，如日志记录。
CodeSandboxProxy 类：这个类实现了 CodeSandbox 接口，并在其方法中添加了日志记录等额外功能。
new CodeSandboxProxy(codeSandbox)：创建一个 CodeSandboxProxy 对象，并将之前创建的 codeSandbox 对象传递给它。这样，通过 codeSandbox 调用的方法实际上会调用 CodeSandboxProxy 中的方法，从而实现日志记录等功能。
总结
工厂模式 用于创建具体的 CodeSandbox 实现对象，使得代码更加灵活和可扩展。
代理模式 用于在不修改原有代码的情况下，为 CodeSandbox 对象添加额外的功能，如日志记录。

整个系统内部的异常 错误码：建议以500开头，例如：500001 表示参数错误，500002 表示用户未登录等。