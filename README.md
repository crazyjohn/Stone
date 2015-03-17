Stone
=====

a game server engine


### The engine's structure ###

- actor。the actor model implements by java language, instead of thread and lock。but now i use akka to do this thing。
- aspct。the engine's aspect, use aspectj language。
- bot。the robot framework, use scala language。
- core。the engine's common lib, class, and utils。
- db。the engine's data layer。
- game。the engine's business logic part。
- proto。protocol between client and server, use google protobuf。
- resource。the template data resources。
- test。the engine test cases。


### The progres of development ###
- you can create account, login, create role, select role and enter scene。




### TODO ###
- template engine: mabe i will use velocity。
- db human sub entity。
- data layer use cache。
- routing logic。
- balance。
- robot pressure test, and make the test doc。
- all the framework use akka，use remote and cluster。