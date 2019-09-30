# 一、项目说明
此项目为基于[smart-cloud](https://github.com/smart-cloud/smart-cloud)的微服务示例项目。各模块说明如下：
## （一）模块说明
<table>
 	<tr>
 		<th>模块</th>
 		<th>子模块模块</th>
 		<th>说明</th>
 	</tr>
 	<tr>
 		<td rowspan=5>application-module（应用服务模块）</td>
 		<td>application-common-config</td>
 		<td>公共配置</td>
 	</tr>
 	<tr>
 		<td>basic-module</td>
 		<td>基础服务模块（如文件服务、权限服务、登陆服务等）</td>
 	</tr>
 	<tr>
 		<td>mall-module</td>
 		<td>商城服务模块</td>
 	</tr>
 	<tr>
 		<td>rpc-module</td>
 		<td>rpc调用模块</td>
 	</tr>
 	<tr>
 		<td>support-module</td>
 		<td>支撑服务模块（如注册中心、网关、监控等）</td>
 	</tr>
 	<tr>
 		<td>docs</td>
 		<td>-</td>
 		<td>相关文档、sql等</td>
 	</tr>
 	<tr>
 		<td rowspan=2>merge-module（服务合并模块）</td>
 		<td>merge-basic</td>
 		<td>基础服务合并</td>
 	</tr>
 	<tr>
 		<td>merge-mall</td>
 		<td>商城服务合并</td>
 	</tr>
 	<tr>
 		<td>smart-cloud-example-framework</td>
 		<td>-</td>
 		<td>公共部分封装</td>
 	</tr>
 	<tr>
 		<td>system-test</td>
 		<td>-</td>
 		<td>系统测试模块</td>
 	</tr>
</table>

## （二）服务说明
 <table>
 	<tr>
 		<th>子模块</th>
 		<th>服务</th>
 		<th>说明</th>
 		<th>端口号</th>
 	</tr>
 	<tr>
 		<td rowspan=2>basic-module</td>
 		<td>basic-service-user</td>
 		<td>用户服务</td>
 		<td>20031</td>
 	</tr>
 	<tr>
 		<td>basic-service-auth</td>
 		<td>权限服务</td>
 		<td>20041</td>
 	</tr>
 	<tr>
 		<td rowspan=2>mall-module</td>
 		<td>mall-service-order</td>
		<td>订单服务</td>
 		<td>20011</td>
 	</tr>
 	<tr>
 		<td>mall-service-product</td>
 		<td>商品服务</td>
 		<td>20021</td>
 	</tr>
 	<tr>
 		<td rowspan=2>rpc-module</td>
 		<td>basic-service-rpc</td>
 		<td>基础服务rpc模块</td>
 		<td>-</td>
 	</tr>
 	<tr>
 		<td>mall-service-rpc</td>
 		<td>商城服务rpc模块</td>
 		<td>-</td>
 	</tr>
 	<tr>
 		<td rowspan=3>support-module</td>
 		<td>support-service-eureka</td>
 		<td>注册中心</td>
 		<td>10001</td>
 	</tr>
 	<tr>
 		<td>support-service-admin</td>
 		<td>监控服务</td>
 		<td>10011</td>
 	</tr>
 	<tr>
 		<td>support-service-gateway</td>
 		<td>网关</td>
 		<td>80</td>
 	</tr>
</table>

# 二、环境搭建
- 更改hosts文件，添加如下内容（注册中心eureka会使用到）
```
  127.0.0.1       nodeA
```

- 安装[redis](https://github.com/microsoftarchive/redis/releases)，并启动
- 安装[mysql](https://www.mysql.com/downloads/)，执行/docs/sql下脚本
- 安装[seata](https://github.com/seata/seata/releases)服务端，并启动
- 服务启动（先启动[nacos](https://nacos.io/en-us/docs/quick-start.html)，然后依次启动mall下服务）

# 三、注意事项
- 针对**jasypt**加密，所有的需要合并的单体服务的**jasypt.encryptor.password**的值必须相同，否则会报错。
- 关于seata
```
1、seata目前不支持“allowMultiQueries=true”，一次执行多条sql会报错。
2、seata服务端以file方式存储；以db方式存储会报错。
```

- 服务构建
```
单体服务构建：clean install
合体服务构建：clean install -P merge
```