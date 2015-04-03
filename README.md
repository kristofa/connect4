# Connect4 #

This little project implements the [Connect 4 board game](http://en.wikipedia.org/wiki/Connect_Four).
It has a human player and a computer player implementation. The computer player is implemented
using the [Minimax algorithm](http://en.wikipedia.org/wiki/Minimax) using [alpha-beta pruning](http://en.wikipedia.org/wiki/Alpha-beta_pruning).

It is also my first Scala project. I'm learning Scala mainly by reading [Scala for the impatient](http://www.horstmann.com/scala/index.html) and I wanted
to have a little project to try out what I read and to get a feel with the language, IDE support, testing,... That's how Connect4 was born.

I'll change the code as I progress and get smarter on Scala.
If you see how things can be done in a nicer way, better, more efficient, feel free to submit pull requests.

The project uses [sbt](http://www.scala-sbt.org/). So if you have sbt installed you should be able to execute:

`sbt compile` : Compiles application.
`sbt test:compile` : Compiles tests.
`sbt test` : Executes the tests.
`sbt run`  : Executes the application.
`sbt eclipse` : Create Eclipse project files.
`sbt gen-idea` : Create IntelliJ IDEA project files.
