package Main

import Battleship._
import Helpers._

import scala.util.Random
import java.io.{BufferedWriter, FileWriter}

import Battleship.Players.AIs.{AIEasyPlayer, AIHardPlayer, AIMediumPlayer}
import Battleship.Players._

// player1 is the current player and player2 is the opponent, it is swapped at each end of turn
case class GameState(player1: Player, player2: Player)

object Game extends App {

    val r = new Random
    Display.show("Hello, you are now in the battleship application!")

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
                Display.show(s"The winner is ${winner.name} ! Congratulations !")
            case "2" =>
                val creatingEasyAI: Player = AIEasyPlayer(random = r)
                val easyAI: Player = creatingEasyAI.updateInformation()

                val creatingMediumAI: Player = AIMediumPlayer(random = r)
                val mediumAI: Player = creatingMediumAI.updateInformation()

                val creatingHardAI: Player = AIHardPlayer(random = r)
                val hardAI: Player = creatingHardAI.updateInformation()

                val numberWinWeakAIAgainstMedium: Int = playGamesBetweenAI(easyAI, mediumAI, Config.NB_FIGHTS_AI, 0)
                val numberWinWeakAIAgainstHard: Int = playGamesBetweenAI(easyAI, hardAI, Config.NB_FIGHTS_AI, 0)
                val numberWinMediumAIAgainstHard: Int = playGamesBetweenAI(mediumAI, hardAI, Config.NB_FIGHTS_AI, 0)

                val content: String = s"AI Name; score; AI Name2; score2\n" +
                    s"AI Level Beginner; $numberWinWeakAIAgainstMedium; Level Medium; ${Config.NB_FIGHTS_AI-numberWinWeakAIAgainstMedium}\n" +
                    s"AI Level Beginner; $numberWinWeakAIAgainstHard;  Level Hard; ${Config.NB_FIGHTS_AI - numberWinWeakAIAgainstHard}\n" +
                    s"AI Level Medium; $numberWinMediumAIAgainstHard; Level Hard; ${Config.NB_FIGHTS_AI - numberWinMediumAIAgainstHard}\n"

                writeToFile("./ai_proof.csv", content)

                Display.show(content)
                Display.show("You can find these information on the file ai_proof.csv")

            case _ =>
                Display.show("See you soon at the battleship application!")
                return
        }

        Display.show("What do you want to do next?")
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

    /**
      * Function that play a certain number of games between two AI and keep in memory the number of win of the first AI
      * @param AI1: Player: the first AI
      * @param AI2: Player: the second AI
      * @param nbGameToPlay: Int: The number of game to play
      * @param nbWinFirstAI: Int: The number of game win by the first AI
      * @return the number of game win by the first AI
      */
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

            val winner: Player = playGame(gameState)
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
        if (gameState.player1.isHuman()){
            Display.show(s"It's the turn of ${gameState.player1.name}.")

            // Display grids
            Display.show("    Your grid\n")
            // TODO: CHANGE TO ConsoleDisplay.show
            Display.showGridShips(gameState.player1.grid) //Display ships and cells shot

            Display.show("")
            Display.show("    Grid of your shots\n")

            // TODO: CHANGE TO ConsoleDisplay.show
            Display.showGridShots(gameState.player2.grid) //With the grid of the player2 we only show cells shot
        }

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
