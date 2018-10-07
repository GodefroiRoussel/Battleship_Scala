package Main

import Battleship._
import Helper._

import scala.util.Random
import scala.io.StdIn

// player1 is the current player and player2 is the opponent, it is swapped at each end of turn
case class GameState(player1: Player, player2: Player)

object Game extends App {

    val r = new Random
    println("Hello, you are now in the battleship application!")

    main(r)

    /**
      * Function containing the body of our application.
      * We choose the mode needed then we play the mode (i.e. player vs player, player vs AI or AI vs AI)
      * @param r: Random
      */
    def main(r: Random) : Unit = {
        val mode = Helper.chooseMode()
        mode match {
            case "1" =>
                val player1Empty: Player = HumanPlayer("1")

                // Choose play mode
                val opponent = Helper.chooseOpponent()
                val player2Empty: Player = opponent match {
                    case "1" =>
                        // Case Human
                        HumanPlayer("2")
                    case _ =>
                        // Case AI
                        val level = Helper.chooseLevel()
                        level match {
                            case "1" =>
                                AIEasyPlayer(random = r)
                            case "2" =>
                                AIMediumPlayer(random = r)
                            case _ =>
                                AIHardPlayer(random = r)
                        }
                }
                val player1: Player = player1Empty.updateInformation()
                val player2: Player = player2Empty.updateInformation()

                val gameState = GameState(player1, player2)
                val winner: Player = playGame(gameState)
                println(s"The winner is ${winner.name} ! Congratulations !")
            case "2" =>
                //TODO: Look how to write into a file and to play against other AI
                /*
                val easyAI: AIEasyPlayer = AIEasyPlayer()
                val mediumAI: AIMediumPlayer = AIMediumPlayer()
                val hardAI: AIHardPlayer = AIHardPlayer()
                val winWeakAI: Int = playGameBetweenAI()*/
                println("I wrote a 2")
            case _ =>
                println("See you soon at the battleship application!")
                return
        }

        println("What do you want to do next?")
        main(r)
    }

    /**
      * Function to play a game. It plays a turn if the current player is alive (that means that the other player is alive too because he played just before)
      * @param gameState : state of the game
      * @return the winner of the game
      */
    def playGame(gameState: GameState): Player = {
        // If the current player is alive we play a turn
        if (gameState.player1.isAlive){
            val newGameState = playTurn(gameState)
            playGame(newGameState)
        } else {
            // Return the opponent player because it is the only one alive and he won the game
            gameState.player2
        }
    }

    /**
      * Function to play a turn. That means the current player enter coordinates, shot on this cell and return the new game state
      * @param gameState: state of the game
      * @return the new game state at the end of the turn (after the shot)
      */
    def playTurn(gameState: GameState): GameState = {
        println(s"It's the turn of ${gameState.player1.name}.")

        // Display grids
        println("    Your grid\n")
        gameState.player1.grid.displayGridShips() //Display ships and cells shot
        println()
        println("    Grid of your shots\n")
        gameState.player2.grid.displayGridShots() //With the grid of the player2 we only display cells shot

        val cell: Cell = gameState.player1.getInfoForShot(gameState.player2)
        gameState.player1.shot(cell, gameState) // this new game state swap player 1 and player 2 and
    }
}
