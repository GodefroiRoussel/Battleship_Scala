import scala.io.StdIn

// player1 is the current player and player2 is the opponent, it is swap at each end of turn
//case class GameState(player1: Player, player2: Player)

object Game extends App {

    println("Hello, you are now in the battleship application!")
    /*
    main()

    def main() : Unit = {
        val mode = chooseMode()
        mode match {
            case "1" =>
                val opponent = chooseOpponent()
                val player2: Player = opponent match {
                    case "1" =>
                        // Case Human
                        println("What will be the name of your opponent?")
                        val opponentName = StdIn.readLine()
                        val player2WithoutShip = HumanPlayer(name)
                        player2WithoutShip.createShips()
                    case _ =>
                        // Case AI
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
                }
                println("What will be your name?")
                val namePlayer1: String = StdIn.readLine()
                val player1WithoutShip: HumanPlayer = HumanPlayer(namePlayer1)
                //TODO: Rec Set Ships for humans and/or AI
                val player1: HumanPlayer = player1WithoutShip.createShips()

                //TODO: Rec Play Turn
                val gameState = GameState(player1, player2)
                val winner: Player = playGame(gameState)
                println(s"The winner is $winner.name ! Congratulations !")
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
      * Function asking to the user to enter a number to choose which mode to play and check if the user entered a correct parameter
      * "1" => To play the game either against a human or an AI
      * "2" => To test AI and make them fight together
      * Other => Will quit the program
      * @return the input of the user
      */
    def chooseMode(): String = {
        println ("Do you want to play to the battleship or do you want to see AI fights ?\n" +
            "Press 1 to play the game.\n" +
            "Press 2 to see AI fights.\n" +
            "Press any other key to quit the program.")

        scala.io.StdIn.readLine()
    }

    /**
      * Function asking to the user to enter a number to choose which game to play and check if the user entered a correct parameter
      * "1" => To play the game against the human
      * "2" => To play the game against an AI
      * Other => call again the chooseOpponent function untill the user enter a 1 or 2
      * @return the input of the user
      */
    def chooseOpponent(): String = {
        println ("Do you want to play against a human or against an AI?\n" +
            "Press 1 to play against a human.\n" +
            "Press 2 to play against an AI.\n")

        val opponent: String = scala.io.StdIn.readLine()
        opponent match {
            case "1" | "2" =>
                // Play against a Human or an AI
                opponent
            case _ =>
                // Choose an Opponent again
                println("You choosed a wrong parameter. Please try again.")
                chooseOpponent()
        }
    }

    /**
      * Function asking to the user to enter a number to choose the level of the AI and check if the user entered a correct parameter
      * "1" => To play against the easiest AI
      * "2" => To play the game against the medium AI
      * "3" => To play against the hardest AI
      * Other => Call again the chooseLevel function untill the user enter a 1, 2 or 3
      * @return the input of the user
      */
    def chooseLevel(): String = {
        println ("Which AI do you want to play against?\n" +
            "Press 1 to play against an easy AI.\n" +
            "Press 2 to play against a medium AI.\n" +
            "Press 3 to play against a hard AI.\n")

        val level = StdIn.readLine()
        level match {
            case "1" | "2" | "3" =>
                // Play against a Human or an AI
                level
            case _ =>
                // Choose an Opponent again
                println("You choosed a wrong parameter. Please try again.")
                chooseLevel()
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
        println(s"It's the turn of $gameState.player1.")

        // Display grids
        gameState.player1.displayGridShips(gameState.player1.grid) //Display ships and cells shot
        println("\n \n \n")
        gameState.player1.displayGridShots(gameState.player2.grid) //With the grid of the player2 we only display cells shot

        // User inputs for the shot
        val letter = chooseLetter()
        val number = chooseNumber()

        // Shot and return the new game state after the shot
        val cell = Cell(letter, number)
        gameState.player1.shot(cell, gameState) // this new game state swap player 1 and player 2
    }

    /**
      * Function asking to the user to enter a letter to choose which column to shoot and check if the user entered a correct parameter
      * @return the user input
      */
    def chooseLetter(): String = {
        println ("Where do you want to shoot ? You can say a letter between A and J")

        val letter = StdIn.readLine().toUpperCase()
        letter match {
            case "A" | "B" | "C" | "D" | "E" | "F" | "G" | "H" | "I" | "J" =>
                letter
            case _ =>
                println("You choosed a wrong parameter. Please try again.")
                chooseLetter()
        }
    }

    /**
      * Function asking to the user to enter a number to choose which row to shoot and check if the user entered a correct parameter
      * @return the user input
      */
    def chooseNumber(): String = {
        println("Where do you want to shoot ? You can say a number between 1 and 10")

        val number = StdIn.readLine()
        number match {
            case "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9" | "10" =>
                number
            case _ =>
                println("You choosed a wrong parameter. Please try again.")
                chooseNumber()
        }
    }
    */
}
