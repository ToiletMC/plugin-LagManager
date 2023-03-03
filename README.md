# LagManager

这是一个滞后提醒器，当检测到服务器滞后时，会通过提醒的方式识图让玩家自觉维护服务器稳定性。

### 📐 工作原理

通过挂钩 Spark，获取服务器最近一分钟的平均 mspt。
每30秒检测一次，当提醒过一次之后，7.5 分钟内都不会再提醒。

### 📖 使用教程

命令-执行一次检测：/lagmanager test  
命令-发送强制提醒：/lagmanager force  
命令-显示debug信息：/lagmanager debug
