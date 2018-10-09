package Battleship.Players.AIs

import Battleship.Players.Player
import Battleship._

import scala.util.Random

case class AIEasyPlayer(name: String = "Easy AI", ships: List[Ship] = List(), grid: Grid = Grid.createGrid(), random: Random = new Random) extends AI {

    override def createShips(typeShips: List[TypeShip], f1:() => Int, f2:() => Int, f3:() => Int): Player = {
        if (typeShips.isEmpty) {
            AIEasyPlayer(this.name, this.ships, this.grid, this.random)
        } else {
            val firstTypeShip: TypeShip = typeShips.head

            //Get input of the user to create the cell of origin and then create a temporary ship until check
            val letter = f1()
            val number = f2()
            val direction = f3()+1
            val cell = Cell(letter, number, TypeCell.OCCUPIED)
            val tempShip : Ship = Ship.createShip(firstTypeShip, cell, direction)

            //Check if the ship is overlapping another ship or outside the board
            if (this.grid.checkPosition(tempShip)) {
                val newGrid: Grid = this.grid.placeShip(tempShip)
                val newListShips: List[Ship] = tempShip :: this.ships
                val newPlayer: AI = AIEasyPlayer(this.name, newListShips, newGrid, this.random)
                val newTypeShips: List[TypeShip] = typeShips.tail
                newPlayer.createShips(newTypeShips, f1, f2, f3)
            } else {
                this.createShips(typeShips, f1, f2, f3)
            }
        }
    }

    override def copyShips(newShips: List[Ship]): Player = {
        AIEasyPlayer(this.name, ships = newShips, this.grid, this.random)
    }

    override def copyShipsAndGrid(ships: List[Ship], grid: Grid): Player = {
        AIEasyPlayer(this.name, ships, grid, this.random)
    }

    /**
      * This function return a random cell in the grid and do not take care of the environment of the opponent player
      * @param opponentPlayer: Player: the opponent player
      * @return the cell to shoot
      */
    override def getInfoForShot(opponentPlayer: Player): Cell = {
        Cell(this.random.nextInt(Config.GRID_SIZE), this.random.nextInt(Config.GRID_SIZE), TypeCell.UNKNOWN)
    }
}
