import scala.io.StdIn

//case class GameState(player1: Player, player2: Player)

object Game extends App {

    println("Hello, you are now in the battleship application!")
    main()

    def main() : Unit = {
        val mode = chooseMode()
        mode match {
            case "1" =>
                val opponent = chooseOpponent()
                opponent match {
                    case "1" =>
                        // Case Human
                        println("I want to play against humans")
                        //TODO: Create human 2
                    case _ =>
                        // Case AI
                        println("I want to play against an AI now let's choose a difficulty")
                        val level = chooseLevel()
                        level match {
                            case "1" =>
                                // TODO: Create an easy AI
                                println("I will create an easy AI")
                            case "2" =>
                                // TODO: Create a medium AI
                                println("I will create a medium AI")
                            case _ =>
                                // TODO: Create an hard AI
                                println("I will create a hard AI")
                        }
                }
                //TODO: Create a human
                //TODO: Set Ships for humans and/or AI
                //TODO: Rec Play Turn
            case "2" =>
                //TODO: Look how to write into a file and to play against other AI
                println("I wrote a 2")
            case _ =>
                println("See you soon at the battleship application!")
                return
        }

        //TODO: Print a message for the winner
        main()
    }

    /**
      * Function asking to the user to enter a number to choose which mode to play
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
      * Function asking to the user to enter a number to choose which game to play
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
      * Function asking to the user to enter a number to choose the level of the AI
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
}
