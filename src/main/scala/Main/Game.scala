package Main

import Battleship._
import Helper._

import scala.util.Random
import scala.io.StdIn
import java.io.{BufferedWriter, FileWriter}

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
                val gameState: GameState = initiateGame(player1Empty, player2Empty)

                val winner: Player = playGame(gameState)
                println(s"The winner is ${winner.name} ! Congratulations !")
            case "2" =>
                val creatingEasyAI: Player = AIEasyPlayer(random = r)
                val easyAI: Player = creatingEasyAI.updateInformation()

                val creatingMediumAI: Player = AIMediumPlayer(random = r)
                val mediumAI: Player = creatingMediumAI.updateInformation()

                val creatingHardAI: Player = AIHardPlayer(random = r)
                val hardAI: Player = creatingHardAI.updateInformation()

                val numberWinWeakAIAgainstMedium: Int = playGamesBetweenAI(easyAI, mediumAI, 100, 0)
                val numberWinWeakAIAgainstHard: Int = playGamesBetweenAI(easyAI, hardAI, 100, 0)
                val numberWinMediumAIAgainstHard: Int = playGamesBetweenAI(mediumAI, hardAI, 100, 0)

                val content: String = s"AI Name; score; AI Name2; score2\n" +
                    s"AI Level Beginner; $numberWinWeakAIAgainstMedium; Level Medium; ${100-numberWinWeakAIAgainstMedium}\n" +
                    s"AI Level Beginner; $numberWinWeakAIAgainstHard;  Level Hard; ${100 - numberWinWeakAIAgainstHard}\n" +
                    s"AI Level Medium; $numberWinMediumAIAgainstHard; Level Hard; ${100 - numberWinMediumAIAgainstHard}\n"

                writeToFile("./ai_proof.csv", content)

                println(content)
                println("You can find these information on the file ai_proof.csv")

            case _ =>
                println("See you soon at the battleship application!")
                return
        }

        println("What do you want to do next?")
        main(r)
    }

    /**
      * Function that ask to 2 players to update their information, that means create their ships
      * @param player1Empty: Player
      * @param player2Empty: Player
      * @return the game state to begin the game
      */
    def initiateGame(player1Empty: Player, player2Empty: Player): GameState = {
        val player1: Player = player1Empty.updateInformation()
        val player2: Player = player2Empty.updateInformation()

        GameState(player1, player2)
    }

    def playGamesBetweenAI(AI1: Player, AI2: Player, nbGameToPlay: Int, nbWinFirstAI: Int): Int ={
        if (nbGameToPlay == 0){
            nbWinFirstAI
        } else {
            // Switch each game the beginner of the game and place new ships
            val turn: Int = nbGameToPlay % 2
            val gameState: GameState = turn match {
                case 0 => initiateGame(AI1, AI2)
                case _ => initiateGame(AI2, AI1)
            }
            println(nbGameToPlay)
            val winner: Player = playGame(gameState)
            println(winner.name)
            if(winner.name == AI1.name){
                playGamesBetweenAI(AI1, AI2, nbGameToPlay-1, nbWinFirstAI+1)
            } else {
                playGamesBetweenAI(AI1, AI2, nbGameToPlay-1, nbWinFirstAI)
            }
        }
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
        //println(s"It's the turn of ${gameState.player1.name}.")

        // Display grids
        //println("    Your grid\n")
        //gameState.player1.grid.displayGridShips() //Display ships and cells shot
        //println()
        //println("    Grid of your shots\n")
        //gameState.player2.grid.displayGridShots() //With the grid of the player2 we only display cells shot

        val cell: Cell = gameState.player1.getInfoForShot(gameState.player2)
        gameState.player1.shot(cell, gameState) // this new game state swap player 1 and player 2 and
    }


    /**
      * Function that write into a file the content put as parameters
      * @param location: String: name and location of the file on the computer
      * @param content: String: Content to write into the file
      */
    def writeToFile(location: String, content: String): Unit = {
        val bw = new BufferedWriter(new FileWriter(location))
        bw.write(content)
        bw.flush()
        bw.close()
    }
}
