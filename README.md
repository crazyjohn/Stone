Stone
=====

a game server engine


### 结构介绍 ###

- actor。是基于java语言的actor框架。用来使用actor思想替换掉复杂易错的并发设计（synchronized，lock, thread等）。
- aspct。是引擎的切面部分，使用aspectj实现。
- bot。是基于scala语言的机器人框架。
- core。是引擎的最核心的部分，放一些通用的库。
- db。是引擎的数据层部分。
- game。是引擎的业务部分，所有的游戏业务都在这里。
- proto。是引擎的协议部分，使用google protobuf实现。
- resource。是引擎的资源，用来放多语言以及模版资源。
- test。是引擎的测试部分。