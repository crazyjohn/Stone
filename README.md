Stone
=====

a game server engine


### The engine's structure ###

- actor。the actor model implements by java language, instead of thread and lock。
- aspct。the engine's aspect, use aspectj language。
- bot。the robot framework, use scala language。
- core。the engine's common lib, class, and utils。
- db。the engine's data layer。
- game。the engine's business logic part。
- proto。protocol between client and server, use goole protobuf。
- resource。the template data resources。
- test。the engine test case。


### The progres of development ###
- you can create account, login, create role, and enter scene。




### TODO ###

-- all the framework use akka，use remote and cluster。
-- data layer use cache。
-- routing logic。
-- balance。
-- db human sub entity。
-- robot test, and test doc。