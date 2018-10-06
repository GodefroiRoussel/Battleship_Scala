package Battleship

import Helper.Helper
import Main.GameState

import scala.io.StdIn
import scala.util.Random

case class HumanPlayer(name: String = "", ships: List[Ship] = List(), grid: Grid = Grid.createGrid(), random: Random = new Random) extends Player {
    /**
      * Function creating ships for a player. The recursion ends when the typeShips parameter is empty.
      * @param typeShips: List[TypeShip]: The list of type ship to create
      * @param f1: function for choosing the letter
      * @param f2: function for choosing the number
      * @param f3: function for choosing the direction
      * @return a player with all his ships positioned on the grid
      */
    override def createShips(typeShips: List[TypeShip], f1:() => Int, f2:() => Int, f3:() => Int): Player = {
        if (typeShips.isEmpty) {
            HumanPlayer(this.name, this.ships, this.grid)
        } else {
            val firstTypeShip: TypeShip = typeShips.head
            println(s"You have to place the ship ${firstTypeShip.name}, it has a size of ${firstTypeShip.size} cells.")
            //Get input of the user to create the cell of origin and then create a temporary ship until check
            val letter = f1()
            val number = f2()
            val direction = f3()
            val cell = Cell(letter, number, TypeCell.OCCUPIED)
            val tempShip : Ship = Ship.createShip(firstTypeShip, cell, direction)

            //Check if the ship is overlapping another ship or outside the board
            this.grid.checkPosition(tempShip) match {
                case true =>
                    val newGrid: Grid = this.grid.placeShip(tempShip)
                    val newListShips: List[Ship] = tempShip :: this.ships
                    val newPlayer: HumanPlayer = HumanPlayer(this.name, newListShips, newGrid)
                    val newTypeShips: List[TypeShip] = typeShips.tail
                    newPlayer.createShips(newTypeShips, f1, f2, f3)
                case false =>
                    println("Your ship is not well positioned on the grid. Please try again.\n")
                    this.createShips(typeShips, f1, f2, f3)
            }
        }
    }

    /**
      * Function that copy a Human player by updating his ships
      * @param newShips: List[Ship]: List of ships that has to be updated
      * @return a new player with his ships updated
      */
    override def copyShips(newShips: List[Ship]): Player = {
        HumanPlayer(this.name, ships = newShips, this.grid)
    }

    /**
      * Function that copy a Human player by updating his ships and his grid
      * @param ships: List[Ship]: List of ships that has to be updated
      * @param grid: Grid: Grid that has to be updated
      * @return a new player with his ships and his grid updated
      */
    override def copyShipsAndGrid(ships: List[Ship], grid: Grid): Player = {
        HumanPlayer(this.name, ships, grid)
    }

    override def updateInformation(): Player = {
        println(s"What will be the name of player ${this.name}?")
        val namePlayer: String = StdIn.readLine()
        if (!namePlayer.isEmpty){
            val player: HumanPlayer = HumanPlayer(namePlayer)

            player.createShips(Config.TYPESHIP,
                () => Helper.chooseLetter(Config.TEXT_POSITIONING_SHIP),
                () => Helper.chooseNumber(Config.TEXT_POSITIONING_SHIP),
                () => Helper.chooseDirection())
        } else {
            println("You must choose a name, maybe in a next version you will be in high scores !")
            this.updateInformation()
        }
    }

    override def getInfoForShot(): Cell = {
        // User inputs for the shot
        val letter = Helper.chooseLetter(Config.TEXT_SHOOT)
        val number = Helper.chooseNumber(Config.TEXT_SHOOT)

        // Shot and return the new game state after the shot
        Cell(letter, number, TypeCell.UNKNOWN)
    }

}
