Stone
=====
a game server engine（
[**YO YO YO! Click me, biatch !**](https://github.com/crazyjohn/crazyjohn.github.io/issues/1)）




The engine's structure 
----------


- actor。the actor model implements by java language, instead of thread and lock。but now i use akka to do this thing。
- aspct。the engine's aspect, use aspectj language。
- bot。the robot framework, use scala language。
- core。the engine's common lib, class, and utils。
- db。the engine's data layer。
- game。the engine's business logic part。
- proto。protocol between client and server, use google protobuf。
- resource。the template data resources。
- test。the engine test cases。

now, what's the progress?
----------
- you can create account, login, create role, select role and enter scene。



TODO
----------
- template engine: mabe i will use velocity。
- db human sub entity。
- data layer use cache。
- routing logic。
- balance。
- robot pressure test, and make the test doc。
- all the framework use akka，use remote and cluster。


cn
----------
1. 模板层的设计，使用velocity或者freemaker之类的模板引擎，还有一种方案就是用scala去定义DSL，然后使用DSL来做这个事情？

2. 数据层的详细设计，子实体？缓存？落地策略？

3. 路由逻辑，以及负载均衡

4. 多服架构的逻辑，Gate + Login + Game，如何实现透明化的scale out?可以参考pomelo和skynet

5. robot压测文档