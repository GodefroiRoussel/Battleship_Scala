import scala.io.StdIn

object Helper {

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

        StdIn.readLine()
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

        val opponent: String = StdIn.readLine()
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
      * Function asking to the user to enter a letter to choose which column to shoot and check if the user entered a correct parameter
      * @return the user input
      */
    def chooseLetter(): Int = {
        println ("Where do you want to shoot ? You can say a letter between A and J")

        val letter = StdIn.readLine().toUpperCase()
        letter match {
            case "A" => 0
            case "B" => 1
            case "C" => 2
            case "D" => 3
            case "E" => 4
            case "F" => 5
            case "G" => 6
            case "H" => 7
            case "I" => 8
            case "J" => 9

            case _ =>
                println("You choosed a wrong parameter. Please try again.")
                chooseLetter()
        }
    }

    /**
      * Function asking to the user to enter a number to choose which row to shoot and check if the user entered a correct parameter
      * @return the user input
      */
    def chooseNumber(): Int = {
        println("Where do you want to shoot ? You can say a number between 1 and 10")

        val number = StdIn.readLine()
        number match {
            case "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9" =>
                number.toInt
            case _ =>
                println("You choosed a wrong parameter. Please try again.")
                chooseNumber()
        }
    }

    /**
      * Function asking to the user to enter a number to choose which direction to position the ship and check if the user entered a correct parameter
      * @return the user input
      */
    def chooseDirection(): Int = {
        println("Where do you want to shoot ? You can say a number between 1 and 4. " +
            "Press 1 to position your ship toward the top." +
            "Press 2 to position your ship toward the right." +
            "Press 3 to position your ship toward the bottom." +
            "Press 4 to position your ship toward the left.")

        val number = StdIn.readLine()
        number match {
            case "1" | "2" | "3" | "4" =>
                number.toInt
            case _ =>
                println("You choosed a wrong parameter. Please try again.")
                chooseNumber()
        }
    }
}
