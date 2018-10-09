package Battleship

import scala.annotation.tailrec

case class TypeShip(name: String, size: Int){}

case class Ship(typeShip: TypeShip, cells: List[Cell]) {

    /**
      * Function that mark a cell of the ship as TOUCHED
      * @param cell: Cell: the cell to mark
      * @return the ship updated with the cell marked as TOUCHED
      */
    def hit(cell: Cell): Ship = {
        val index: Int = this.cells.indexWhere(cellBoat => cell.x == cellBoat.x && cell.y == cellBoat.y)
        val newCells: List[Cell] = this.cells.updated(index, cell.copy(typeCell = TypeCell.TOUCHED))
        this.copy(cells = newCells)
    }

    /**
      * Function that count the number of cells of type TOUCHED
      * @return the number of TOUCHED cells
      */
    def numberCellsTouched(): Int = {
        this.cells.count(cell => cell.typeCell == TypeCell.TOUCHED)
    }

    /**
      * Function that says if a ship is sunk or not
      * @return true if the number of cells TOUCHED of the ship is equal to the size of the type ship
      */
    def isSunk: Boolean = {
         this.numberCellsTouched() == this.typeShip.size
    }

}


object Ship {
    /**
      * Function creating a ship
      * @param typeShip: TypeShip : Type of the ship
      * @param cell: Cell: Origin cell of the ship
      * @param direction: Int: Direction in which oriented the ship
      * @return a Ship created
      */
    def createShip(typeShip: TypeShip, cell: Cell, direction: Int) : Ship = {
        val cells: List[Cell] = createListCellsFor(typeShip, cell, direction, List(cell))
        Ship(typeShip, cells)
    }

    /**
      * Function creating a list of cell beginning after the first cell and positioned toward the good direction
      * @param typeShip: TypeShip : Type of the ship
      * @param cell: Cell: Cell where we find the next cell
      * @param direction: Int: Direction in which oriented the ship
      * @return List of cells after positioning toward the direction wanted
      */
    @tailrec
    private def createListCellsFor(typeShip: TypeShip, cell: Cell, direction: Int, cells: List[Cell]) : List[Cell] = {
        if (typeShip.size == 1)
            cells
        else {
            val newTypeShip: TypeShip = typeShip.copy(size = typeShip.size-1)
            val newCell: Cell = direction match {
                case 1 => cell.copy(y = cell.y - 1)
                case 2 => cell.copy(x = cell.x + 1)
                case 3 => cell.copy(y = cell.y + 1)
                case _ => cell.copy(x = cell.x - 1)
            }
            createListCellsFor(newTypeShip, newCell, direction, cells :+ newCell)
        }
    }
}