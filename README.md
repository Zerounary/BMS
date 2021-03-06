#BMS基础权限开发平台
	CodeGenerator自动生成Entity、Mapper、Service、Controller让开发更简单
	
## 模块介绍
* bms-common  公共服务模块
* bms-admin   后台管理模块
* bms-api     对外接口模块

## 内置功能

1. 系统基础管理
   - 1.1 用户管理 
   - 1.2 角色管理 
   - 1.3 资源管理
   - 1.4 部门管理 
   - 1.5 定时任务 
   
2. 系统监控管理
   - 2.1 druid监控 
   
3. 日志管理
   - 3.1 用户登录日志 
   
4. CMS管理
   - 4.1 新闻管理（富文本ckeditor，上传图片bootstrap fileinput）

## API服务
  Api模块采用Swagger2做接口文档说明,实用jjwt做接口安全认证
   

## 技术选型

1、后端

* 核心框架：Spring Boot
* 安全框架：Apache Shiro
* 持久层框架：Mybatis + Mybatis plus
* 模板引擎：thymeleaf
* 数据库连接池：Alibaba Druid
* 缓存框架：Ehcache
* 日志管理：Log4j2
* 工具类：feilong-core
* 接口文档:Swagger2
* 接口安全:jjwt

2、前端

* JS框架：jQuery
* UI框架：Amaze UI
* 客户端验证：Jquery-validation
* 树结构控件：JsTree
* 弹出层：Layer
* 富文本: ckeditor
* 上传文件 : bootstrap fileinput
* 表格插件：DLShouWen Grid（新版）

3、鸣谢
1.[webside](http://git.oschina.net/wjggwm/webside)
2.[mybatis-plus](http://git.oschina.net/baomidou/mybatis-plus)
3.[feilong-core](https://github.com/venusdrogon/feilong-core)

## QQ讨论群：593460183

## 后台界面

![登录](http://git.oschina.net/uploads/images/2016/1230/150126_2acd13ce_14904.jpeg "登录")
![用户管理](https://git.oschina.net/uploads/images/2017/0518/104840_a0bb1bb5_14904.jpeg "用户管理")
![角色管理](https://git.oschina.net/uploads/images/2017/0518/104915_ccdcbf0c_14904.jpeg "角色管理")
![资源管理](https://git.oschina.net/uploads/images/2017/0518/105001_6aa3d607_14904.jpeg "资源管理")
![部门管理](https://git.oschina.net/uploads/images/2017/0518/105023_6855288d_14904.jpeg "部门管理")
![定时任务](https://git.oschina.net/uploads/images/2017/0518/105048_91ea7a4a_14904.jpeg "定时任务")
![新增新闻](http://git.oschina.net/uploads/images/2017/0110/122046_1e2829a1_14904.jpeg "")
![新闻列表](http://git.oschina.net/uploads/images/2017/0110/122111_4b376f17_14904.jpeg "")
![踢出用户](https://git.oschina.net/uploads/images/2017/0518/105225_fd5aeed6_14904.jpeg "踢出用户")
![api列表](https://git.oschina.net/uploads/images/2017/0421/111723_e10e8f71_14904.jpeg "")
![无权限访问](https://git.oschina.net/uploads/images/2017/0421/111744_f5cd058e_14904.jpeg "")