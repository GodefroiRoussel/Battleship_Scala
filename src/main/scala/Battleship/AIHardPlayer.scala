package Battleship

import Main.GameState

import scala.util.Random

case class AIHardPlayer(name: String = "Hard", ships: List[Ship] = List(), grid: Grid = Grid.createGrid(), random: Random = new Random) extends Battleship.AI {

    override def createShips(typeShips: List[TypeShip], f1:() => Int, f2:() => Int, f3:() => Int): Player = {
        if (typeShips.isEmpty) {
            AIHardPlayer(this.name, this.ships, this.grid, this.random)
        } else {
            val firstTypeShip: TypeShip = typeShips.head
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
                    val newPlayer: AI = AIHardPlayer(this.name, newListShips, newGrid, this.random)
                    val newTypeShips: List[TypeShip] = typeShips.tail
                    newPlayer.createShips(newTypeShips, f1, f2, f3)
                case false =>
                    this.createShips(typeShips, f1, f2, f3)
            }
        }
    }

    override def copyShips(ships: List[Ship]): Player = {
        AIHardPlayer(this.name, ships, this.grid, this.random)
    }

    override def copyShipsAndGrid(ships: List[Ship], grid: Grid): Player = {
        AIHardPlayer(this.name, ships, grid, this.random)
    }

    /**
      * The hard AI choose a random cell just in case.
      * If the opponent of the AI has touched cells then the AI list the good cells next to touched cells.
      * That means, only keep cells inside the grid and non-touched cells. Then if exists at least one "good" cell
      * then select the first cell of this list. While another cell is not shot, the AI shot on the left then on the right, then on the top and end with the bottom.
      * @param opponentPlayer: Player: the opponent player
      * @return the cell to shoot
      */
    override def getInfoForShot(opponentPlayer: Player): Cell = {
        val randomCell: Cell = Cell(this.random.nextInt(10), this.random.nextInt(10), TypeCell.UNKNOWN)

        val grid: Grid = opponentPlayer.grid
        val listTouchedCell: List[Cell] = grid.listTouchedCell()
        if(listTouchedCell.nonEmpty) {
            // List cells next to touched cells and only keep cells that are in the grid and not already discovered (i.e. TOUCHED or WATER)
            val listGoodCellsNextToTouchedCells: List[Cell] = listTouchedCell.flatten(cell => {
                val wrongCell: Cell = Cell(-1,-1, TypeCell.UNKNOWN)
                val cellLeft: Cell = cell.x match {
                    case 0 => wrongCell
                    case _ => grid.cells(cell.x-1)(cell.y)
                }
                val cellRight: Cell = cell.x match {
                    case 9 => wrongCell
                    case _ => grid.cells(cell.x+1)(cell.y)
                }
                val cellTop: Cell = cell.y match {
                    case 0 => wrongCell
                    case _ => grid.cells(cell.x)(cell.y-1)
                }
                val cellBottom: Cell = cell.y match {
                    case 9 => wrongCell
                    case _ => grid.cells(cell.x)(cell.y+1)
                }
                List(cellLeft, cellRight, cellTop, cellBottom)
            }).filter(cell => cell.x < 10 && cell.y < 10 && cell.x >= 0 && cell.y >= 0 && (cell.typeCell == TypeCell.UNKNOWN || cell.typeCell == TypeCell.OCCUPIED))

            if (listGoodCellsNextToTouchedCells.nonEmpty){
                return listGoodCellsNextToTouchedCells.head
            } else randomCell
        }

        if (opponentPlayer.grid.checkCell(randomCell) == TypeCell.TOUCHED || opponentPlayer.grid.checkCell(randomCell) == TypeCell.WATER){
            this.getInfoForShot(opponentPlayer)
        } else {
            randomCell
        }
    }
}
