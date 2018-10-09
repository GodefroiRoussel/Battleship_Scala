package Battleship.Players

import Battleship._
import Helpers.{Display, Input}

import scala.io.StdIn
import scala.util.Random

case class HumanPlayer(name: String = "", ships: List[Ship] = List(), grid: Grid = Grid.createGrid(), random: Random = new Random) extends Player {

    override def createShips(typeShips: List[TypeShip], f1:() => Int, f2:() => Int, f3:() => Int): Player = {
        if (typeShips.isEmpty) {
            HumanPlayer(this.name, this.ships, this.grid)
        } else {
            val firstTypeShip: TypeShip = typeShips.head
            Display.show(s"You have to place the ship ${firstTypeShip.name}, it has a size of ${firstTypeShip.size} cells.")
            //Get input of the user to create the cell of origin and then create a temporary ship until check
            val letter = f1()
            val number = f2()
            val direction = f3()
            val cell = Cell(letter, number, TypeCell.OCCUPIED)
            val tempShip : Ship = Ship.createShip(firstTypeShip, cell, direction)

            //Check if the ship is overlapping another ship or is outside the board
            if (this.grid.checkPosition(tempShip)) {
                // We place the boat on the grid, we update the list of ships, we update the player with these new info and we call again the function with the new player
                val newGrid: Grid = this.grid.placeShip(tempShip)
                val newListShips: List[Ship] = tempShip :: this.ships
                val newPlayer: HumanPlayer = HumanPlayer(this.name, newListShips, newGrid)
                val newTypeShips: List[TypeShip] = typeShips.tail
                newPlayer.createShips(newTypeShips, f1, f2, f3)
            } else {
                Display.show("Your ship is not well positioned on the grid. Please try again.\n")
                this.createShips(typeShips, f1, f2, f3)
            }
        }
    }

    override def copyShips(newShips: List[Ship]): Player = {
        HumanPlayer(this.name, ships = newShips, this.grid)
    }

    override def copyShipsAndGrid(ships: List[Ship], grid: Grid): Player = {
        HumanPlayer(this.name, ships, grid)
    }

    override def updateInformation(): Player = {
        Display.show(s"What will be the name of player ${this.name}?")
        val namePlayer: String = StdIn.readLine()
        if (!namePlayer.isEmpty){
            val player: HumanPlayer = HumanPlayer(namePlayer)

            player.createShips(Config.TYPESHIP,
                () => Input.chooseLetter(Config.TEXT_POSITIONING_SHIP),
                () => Input.chooseNumber(Config.TEXT_POSITIONING_SHIP),
                () => Input.chooseDirection())
        } else {
            Display.show("You must choose a name, maybe in a next version you will be in high scores !")
            this.updateInformation()
        }
    }

    override def getInfoForShot(opponentPlayer: Player): Cell = {
        // User inputs for the shot
        val letter = Input.chooseLetter(Config.TEXT_SHOOT)
        val number = Input.chooseNumber(Config.TEXT_SHOOT)
        Cell(letter, number, TypeCell.UNKNOWN)
    }

    override def isHuman: Boolean = true

}
