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


----------
Stone
===
stone是一个游戏服务器引擎。

**1. 为什么要搞这样一个游戏引擎？**

首先我自己到现在为止从事游戏行业已经6年多的时间了。这期间3/5的时间从事网游（包括大型MMO端游，MMO页游，SLG页游，手游等）服务器的开发，2/5的时间从事客户端的开发。在做服务器的过程中，我见到了也自己搭建过各种各样架构的服务器。那一个好的游戏服务器的设计是什么样的呢？我的理解它是这样的：

- 易用性。就是说引擎的设计要和互联网产品的设计一样，都很容易让用户上手，有很好的用户体验。那引擎的用户就是coder了，如何能设计足够良好以及简单的api以及架构，让即使是小白coder也不要望而却步，并且可以在使用过程中读到诗一样的代码那就更好了。

- 扩展性。这里分为两个方面，一方面是引擎本身的结构方面，就是说引擎要留给用户足够合理以及灵活的接口供用户进行业务的扩展等。另一方面就是就性能方面来说server引擎要方便scale up 纵向扩展，也要方便scale out 横向扩展。当然两者都能满足那就再好不过了。

- 并发性。这算是server引擎最重要的特性之一了，它会直接影响到引擎的承载能力，响应性等等。但是当前的Thread结构在处理共享状态和可变状态的时候都需要和锁Lock配合使用，Lock的机制是为了解决资源访问的同步，但是同步和并发本身就是水火不容的，更甚是这种结构本身就很复杂，需要coder有很好的并发问题处理功底，稍微不慎就会有死锁等等各种问题，如何处理？有没有更好的处理模型？

- 吞吐量。这个名词的解释可以看操作系统的从前往后的发展，核心解决的是引擎能如何有效的把物理服务器的资源利用起来，它跟引擎的并发性密切相关。

- 响应性。它说的是引擎处理请求的速度能有多快，高响应性可以提供良好的游戏体验。


那是否可以写一个可以解决上述所有问题的游戏服务器殷勤呢？Stone就是为了这个目的开始码起的。



**2. 高并发？**




**3. scale out？scale up?**




**4. 扩展性？易用性？**



**4. 数据层设计？**






