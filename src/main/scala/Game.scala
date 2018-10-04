import scala.io.StdIn

// player1 is the current player and player2 is the opponent, it is swap at each end of turn
case class GameState(player1: Player, player2: Player)

object Game extends App {

    println("Hello, you are now in the battleship application!")

    main()

    def main() : Unit = {
        val mode = Helper.chooseMode()
        mode match {
            case "1" =>
                val opponent = Helper.chooseOpponent()
                val player2: Player = opponent match {
                    case "1" =>
                        // Case Human
                        println("What will be the name of your opponent?")
                        val opponentName = StdIn.readLine()
                        val player2WithoutShip = HumanPlayer(opponentName)

                        player2WithoutShip.createShips(
                            Config.typeShip,
                            player2WithoutShip,
                            () => Helper.chooseLetter(),
                            () => Helper.chooseNumber(),
                            () => Helper.chooseDirection()
                        )
                    case _ =>
                        HumanPlayer("AI")
                        // Case AI
                        /*
                        val level = chooseLevel()
                        level match {
                            case "1" =>
                                // TODO: Create an easy AI
                                AIPlayer("easy")
                            case "2" =>
                                // TODO: Create a medium AI
                                AIPlayer("medium")
                            case _ =>
                                // TODO: Create a hard AI
                                AIPlayer("hard")
                        }
                        */
                }
                println("What will be your name?")
                val namePlayer1: String = StdIn.readLine()
                val player1WithoutShip: HumanPlayer = HumanPlayer(namePlayer1)
                //TODO: Rec Set Ships for humans and/or AI
                val player1: HumanPlayer = player1WithoutShip.createShips(Config.typeShip,
                    player1WithoutShip,
                    () => Helper.chooseLetter(),
                    () => Helper.chooseNumber(),
                    () => Helper.chooseDirection()
                )

                //TODO: Rec Play Turn
                val gameState = GameState(player1, player2)
                val winner: Player = playGame(gameState)
                println(s"The winner is ${winner.name} ! Congratulations !")
            case "2" =>
                //TODO: Look how to write into a file and to play against other AI
                println("I wrote a 2")
            case _ =>
                println("See you soon at the battleship application!")
                return
        }

        println("What do you want to do next?")
        main()
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
        println(s"It's the turn of $gameState.player1.")
        /*
        // Display grids
        gameState.player1.displayGridShips(gameState.player1.grid) //Display ships and cells shot
        println("\n \n \n")
        gameState.player1.displayGridShots(gameState.player2.grid) //With the grid of the player2 we only display cells shot
        */
        // User inputs for the shot
        val letter = Helper.chooseLetter()
        val number = Helper.chooseNumber()

        // Shot and return the new game state after the shot
        val cell = Cell(letter, number, TypeCell.UNKNOWN)
        gameState.player1.shot(cell, gameState) // this new game state swap player 1 and player 2 and
    }
}
