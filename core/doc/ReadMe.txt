
[Actor]

1. Actor can do anything to itself.

2. If an actor want to conmunicate with another actor, it must use the future pattern. 
	1: actorA call actorB's method.
	2: actorB return a Future to actorA. 
	3: actorA and a FutureListener to the Future, wait the result in async way.