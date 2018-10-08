# Battleship_Scala

This project is a school project in the course: Functional Programing. 
During this course we learned functional programing paradigms and implemented these 
paradigms in Scala.

So this project is the only mark for this course and the purpose is to develop a battleship 
game in Scala using functional programing. The specifications of the game can be found below
in the section **Specifications**.

## Requirements

This project needs at least scala version 2.12.6 and sbt version 1.2.3

You can find a tutorial to install scala on their [official website](https://www.scala-lang.org/download/).

For sbt, you can also go on their [official website](https://www.scala-sbt.org/download.html).

## Installation
Once you have all requirements, you can clone this github repository with the command below:
```
git clone https://github.com/GodefroiRoussel/Battleship_Scala.git 
```

Go then to the source folder: 
```
cd Battleship_Scala
```

Now you can compile, run and test my code.

To compile it, use: 
```sbtshell
sbt compile
```

To test the code, use: 
```sbtshell
sbt test
```

To play to the application, use the command below and follow game instructions:
```sbtshell
sbt run
```

I hope you enjoyed your game.

## Specifications

###Players
This game must have several play modes:
* Human vs Human
* Human vs AI

A human player should be able to see his/her grids.
On one grid the player arranges ships and records the shots by the opponent.
On the other grid, the player records their own shots.
The human should also able to choose between several AIs (see next part).

At the end of a game, players should switch (the starting player is not the same in each game).

###AI
The game should have 3 level of AIs:
* Level Beginner: the AI shoots a square randomly (it can hit the same square several times),
* Level Medium: should be better than AI Level Beginner,
* Level Hard: should be better than AI Level Medium.

The programme should gives the proof of this assertion. 
In other words, you have to provide a programme that will run:
* 100 times AI Level Beginner vs Level Medium
* 100 times AI Level Beginner vs Level Hard
* 100 times AI Medium vs Level Hard

Remember that at the beginning of each new game between two AI, 
you should switch the starting AI. 
At the end, this programme should write in a CSV file named *ai_proof.csv*:
```
AI Name; score; AI Name2; score2
AI Level Beginner; X1; Level Medium; Y1
AI Level Beginner;X2;Level Hard;Y2
AI Medium;X3;Level Hard;Y3
```
Where Xi and Yi are the scores of AI1 and AI2

## License

This project is licensed under the MIT License - see the [LICENSE.md](https://github.com/GodefroiRoussel/Battleship_Scala/blob/master/LICENSE) file for details.

## Author

[Godefroi Roussel](https://github.com/GodefroiRoussel)