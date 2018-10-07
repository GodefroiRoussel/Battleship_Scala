package Battleship

import Main.GameState

import scala.util.Random

case class AIMediumPlayer(name: String = "Medium", ships: List[Ship] = List(), grid: Grid = Grid.createGrid(), random: Random = new Random) extends Battleship.AI {

    override def createShips(typeShips: List[TypeShip], f1:() => Int, f2:() => Int, f3:() => Int): Player = {
        if (typeShips.isEmpty) {
            AIMediumPlayer(this.name, this.ships, this.grid, this.random)
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
                    val newPlayer: AI = AIMediumPlayer(this.name, newListShips, newGrid, this.random)
                    val newTypeShips: List[TypeShip] = typeShips.tail
                    newPlayer.createShips(newTypeShips, f1, f2, f3)
                case false =>
                    println("Your ship is not well positioned on the grid. Please try again.\n")
                    this.createShips(typeShips, f1, f2, f3)
            }
        }
    }

    override def copyShips(ships: List[Ship]): Player = {
        AIMediumPlayer(this.name, ships, this.grid, this.random)
    }

    override def copyShipsAndGrid(ships: List[Ship], grid: Grid): Player = {
        AIMediumPlayer(this.name, ships, grid, this.random)
    }

    override def getInfoForShot(opponentPlayer: Player): Cell = {
        val cell: Cell = Cell(this.random.nextInt(10), this.random.nextInt(10), TypeCell.UNKNOWN)
        if (opponentPlayer.grid.checkCell(cell) == TypeCell.TOUCHED || opponentPlayer.grid.checkCell(cell) == TypeCell.WATER){
            this.getInfoForShot(opponentPlayer)
        } else {
            cell
        }
    }

}
