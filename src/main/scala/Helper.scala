import scala.io.StdIn

object Helper {


    /**
      * Function asking to the user to enter a letter to choose which column to shoot and check if the user entered a correct parameter
      * @return the user input
      */
    def chooseLetter(): Int = {
        println ("Where do you want to shoot ? You can say a letter between A and J")

        val letter = StdIn.readLine().toUpperCase()
        letter match {
            case "A" => 1
            case "B" => 2
            case "C" => 3
            case "D" => 4
            case "E" => 5
            case "F" => 6
            case "G" => 7
            case "H" => 8
            case "I" => 9
            case "J" => 10

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
            case "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9" | "10" =>
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
