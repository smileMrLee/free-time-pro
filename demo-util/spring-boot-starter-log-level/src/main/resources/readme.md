### 自定义扩展listener
` 当前仅支持apolloListener，如需支持其他配置中心，请按如下步骤进行扩展。`

#### 定义listener
- 创建class XxxLogLevelListener extends AbstractLogLevelListener 
- 并实现listen() 方法，注册监听配置中心配置变化，实现convertLogLevelEvent() 方法将配置变化事件转化为通用LogLevelEvent事件。
#### 注册listener
- 以ApolloLogLevelListener 为例，将listener class名字注册到spring boot starter容器中LogLevelConfiguration；
` LogLevelConfiguration.registerListener(String listener, String clazzName) ` 其中，listener是监听器的别名，用于配置应用使用哪个监听器，匹配对应配置中心，clazzName则是自定义listener的类全名。

#### 配置
- 配置应用：log.level.dynamic.listener=apollo #(此处修改为你的自定义listener名称)

#### 验证
- 在控制台查看是否有输出"Dynamic log level listener use:apollo" 