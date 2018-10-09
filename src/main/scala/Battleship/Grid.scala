package Battleship

import scala.annotation.tailrec

/*
Coordinate System

	    1
	A   |------------> x that is represented from A to J
		|
		|
		|
	    |
        ↓
 */

case class Grid(cells: List[List[Cell]] = List(List())) {
    /**
      * Function checking if a ship has good positions.
      * That means, positions that are not overlapping a ship already on the grid or cells are not outside the grid.
      * @param tempShip: Ship: the ship to check
      * @return true if the ship has good positions, otherwise false
      */
    def checkPosition(tempShip: Ship): Boolean = {
        // For each cell of the ship we check if the cell is outside (that means not between 1 and 10)
        // or if the type of the cell of the grid is occupied else true
        tempShip.cells.count(cell => {
            if (cell.x < Config.GRID_SIZE && cell.x >= 0 && cell.y < Config.GRID_SIZE && cell.y >= 0) {
                this.cells(cell.x)(cell.y).typeCell match {
                    case TypeCell.OCCUPIED => false
                    case _ => true
                }
            } else false
        }) == tempShip.cells.length
    }

    /**
      * Function positioning the ship on the grid and returning the grid updated
      * @param ship: Ship: the ship to position
      * @return the grid with the ship positioned
      */
    def placeShip(ship: Ship): Grid = {

        /**
          * Function that position cells on the grid
          * @param cells: List[Cell]: List of cells to position
          * @param grid: Grid: The grid where to position cells
          * @return the grid with cells positioned
          */
        @tailrec
        def placeCellOnGrid(cells: List[Cell], grid: Grid) : Grid = {
            if (cells.isEmpty) grid
            else {
                val cell: Cell = cells.head
                placeCellOnGrid(cells.tail, Grid(grid.cells.updated(cell.x, grid.cells(cell.x).updated(cell.y, cell))))
            }
        }

        placeCellOnGrid(ship.cells, this)

    }

    /**
      * Function updating the grid by marking the cell shot
      * @param cell: Cell: the cell that the player wants to shoot
      * @return the new grid with the cell updated according to each case
      */
    def shot(cell: Cell): Grid = {
        this.cells(cell.x)(cell.y).typeCell match {
            case TypeCell.TOUCHED => this
            case TypeCell.OCCUPIED =>
                val newCell = Cell(cell.x, cell.y, TypeCell.TOUCHED)
                Grid(this.cells.updated(cell.x, this.cells(cell.x).updated(cell.y, newCell)))
            case TypeCell.UNKNOWN =>
                val newCell = Cell(cell.x, cell.y, TypeCell.WATER)
                Grid(this.cells.updated(cell.x, this.cells(cell.x).updated(cell.y, newCell)))
            case TypeCell.WATER => this
        }
    }

    /**
      * Function returning the type of the cell of the grid
      * @param cell: Cell: the cell to retrieve the type on the grid
      * @return the type of the cell on the grid
      */
    def checkCell(cell: Cell): TypeCell.Value = {
        this.cells(cell.x)(cell.y).typeCell
    }

    /**
      * Function that list cells touched
      * @return the list of cells touched
      */
    def listTouchedCell(): List[Cell] = {
        this.cells.flatten.filter(cell => cell.typeCell == TypeCell.TOUCHED)
    }

}

object Grid {
    /**
      * Function creating a Battleship.Grid initialising all cells
      * @return grid initialised
      */
    def createGrid() : Grid = {

        /**
          * Function that create all cells of the grid
          * @param x: Int : It represents the row where create cells
          * @return all cells of the grid (that means a List of ( List of Cell )
          */
        @tailrec
        def createCells(x: Int, listCells: List[List[Cell]]) : List[List[Cell]] = {
            if (x> Config.GRID_SIZE-1) listCells
            else {
                createCells(x+1,  listCells :+ createColumn(x,0, List()))
            }
        }

        /**
          * Function creating all cells for a column.
          * @param x : Int : It represents the row where create cells
          * @param y : Int : It represents the index of the column
          * @param cells: List[Cell]: Accumulate the creation of the column
          * @return a column of cell initialised : a list of cell
          */
        @tailrec
        def createColumn(x: Int, y: Int, cells: List[Cell]): List[Cell] = {
            if (y > Config.GRID_SIZE-1) cells
            else {
                createColumn(x, y+1, cells :+ Cell(x,y,TypeCell.UNKNOWN) )
            }
        }

        Grid(createCells(0, List()))
    }
}