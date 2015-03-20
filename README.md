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

首先我自己到现在为止从事游戏行业已经6年多的时间了。这期间3/5的时间从事游戏服务器的开发，2/5的时间从事游戏客户端的开发。在做服务器的过程中，我见到了也自己搭建过各种各样架构的服务器。那一个好的游戏服务器的设计是什么样的呢？我的理解它是这样的：

- 易用性。就是说引擎的设计要和互联网产品的设计一样，都很容易让用户上手，有很好的用户体验。那引擎的用户就是coder了，如何能设计足够良好以及简单的api以及架构，让即使是小白coder也不要望而却步，并且可以在使用过程中读到诗一样的代码那就更好了。

- 扩展性。这里分为两个方面，一方面是引擎本身的结构方面，就是说引擎要留给用户足够合理以及灵活的接口供用户进行业务的扩展等。另一方面就是就性能方面来说server引擎要方便scale up 纵向扩展，也要方便scale out 横向扩展。当然两者都能满足那就再好不过了。

- 并发性。这算是server引擎最重要的特性之一了，它会直接影响到引擎的承载能力，响应性等等。但是当前的Thread结构在处理共享状态和可变状态的时候都需要和锁Lock配合使用，Lock的机制是为了解决资源访问的同步，但是同步和并发本身就是水火不容的，更甚是这种结构本身就很复杂，需要coder有很好的并发问题处理功底，稍微不慎就会有死锁等等各种问题，如何处理？有没有更好的处理模型？

- 吞吐量。这个名词的解释可以看操作系统的从前往后的发展，核心解决的是引擎能如何有效的把物理服务器的资源利用起来，它跟引擎的并发性密切相关。

- 响应性。它说的是引擎处理请求的速度能有多快，高响应性可以提供良好的游戏体验。


那是否可以写一个可以解决上述所有问题的游戏服务器引擎呢？Stone就是为了这个目的开始码起的。



**2. 高并发？**

首先是为什么需要高并发？因为单个线程的处理能力毕竟是有限的，拿游戏服务器来说一个非io请求的处理时间在1ms的级别，一个io请求的处理时间在100ms级别，那么随着单服游戏玩家的增加，单线程结构的吞吐量就会下降，响应时间变长，极端情况下消息队列里消息无限堆积，fullgc频繁，但是内存无法回收，最终OOM导致服务器crash。

那我们就要把单线程的结构换成多线程的结构来提高服务器的请求处理能力。最早做MMO的时候会在设计上把游戏世界分为多个场景这样的设计，所以在后端就会使用线程作为载体然后一个或者多个场景的处理分摊到指定的场景线程这种设计。再往后做SLG页游的时候，游戏设计上已经淡化了场景的概念，所以更倾向于把服务器的业务处理设计为多个责任节点，比如根据游戏玩家的状态机：GAMING - BATTLING,等把处理节点分为游戏中处理节点和战斗处理节点，而每个处理节点会有一个或者多个线程来分担处理，这其中由于db io请求的处理比较耗时，所以也会分一个数据处理节点出来，进行数据请求的异步处理，然后进一步回调结果通知给请求节点等。

上述的架构思想，基本可以解决目前网游服务器的需求，但是有2个问题是，如果使用把玩家分摊到多个游戏线程里，1需要解决跨线程的玩家如何安全的交互？2需要解决玩家和游戏中的一些全局管理器的安全交互？

解决上头两个问题最好的办法是在框架级别封装多线程的复杂性，不要让coder过多操心并发这类复杂的事情，但是如果你要做一个高承载量的东西，这个往往很难做到，无疑还是要和Thread以及Lock这类并发工具打交道，所以这就无疑为程序的鲁棒性添加了隐患。

我转而去尝试其他的并发模型，比如Actor, STM。


**3. Actor model?**
Actor模型并Erlang采用广泛应用于通信领域，获得了很大的成功。jvm上的多范型语言scala也把Actor模型内置到自己的语言体系中。

那究竟什么是Actor呢？啰嗦一点我先从并发的一些设计模式说起，以下是一些并发的设计模式：

1. single thread execution。这其实是最简单暴力的处理方式，字面意思就是单一时刻资源只有一个线程在处理。实现方式就是所有共享资源的方法都是用synchronized来标识。缺点也很明显就是毫无并发性可言，如果某个资源竞争很激烈，就会成为热点，有很大的性能问题。
2. immutable
3. guarder suspenson
4. balking
5. producer comsumer
6. thread per msg
7. worker thread
8. future
9. read write lock
10. two phase termination
11. thread specific storage
12. active object



**4. scale out？scale up?**




**5. 扩展性？易用性？**



**6. 数据层设计？**






