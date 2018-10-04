package Battleship

/*
Coordinate System

	    0
	A   |------------> x that is represented from A to J
		|
		|
		|
	    |
        ↓
 */

case class Grid(cells: List[List[Cell]] = List(List())) {
    /**
      * Function checking a ship has good positions.
      * That means, positions that are not collapsing a ship already on the grid or outside the grid.
      * @param tempShip: Ship: the ship to check
      * @return true if the ship has good positions, otherwise false
      */
    def checkPosition(tempShip: Ship): Boolean = {
        // For each cell of the ship we check if the cell is outside (that means not between 1 and 10)
        // or if the type of the cell of the grid is occupied else true
        tempShip.cells.count(cell => {
            if (cell.x < 10 && cell.x >= 0 && cell.y < 10 && cell.y >= 0) {
                this.cells(cell.x)(cell.y).typeCell match {
                    case TypeCell.OCCUPIED => false
                    case _ => true
                }
            } else false
        }) == tempShip.cells.length
    }

    /**
      * Function positioning the ship on the grid and returning the grid updated
      * @param ship : Ship: the ship to position
      * @return the grid with the ship positioned
      */
    def placeShip(ship: Ship): Grid = {

        def placeCellOnGrid(cells: List[Cell], grid: Grid) : Grid = {
            if (cells.isEmpty) grid
            else {
                val cell: Cell = cells.head
                placeCellOnGrid(cells.tail, Grid(grid.cells.updated(cell.x, grid.cells(cell.x).updated(cell.y, cell))))
            }
        }

        placeCellOnGrid(ship.cells, this)

    }



}

object Grid {
    /**
      * Function creating a Battleship.Grid initialising all cells
      * @return grid initialisated
      */
    def createGrid() : Grid = {

        /**
          * Function that create all cells of the grid
          * @param x: Int : It represents the row where create cells
          * @return all cells of the grid (that means a List of ( List of Cell )
          */
        def createCells(x: Int) : List[List[Cell]] = {
            if (x>9) Nil
            else {
                createColumn(x, 0) :: createCells(x+1)
            }
        }

        /**
          * Function creating all cells for a column.
          * @param x : Int : It represents the row where create cells
          * @param y : Int : It represents the index of the column
          * @return a column of cell initialisated : a list of cell
          */
        def createColumn(x: Int, y: Int): List[Cell] = {
            if (y > 9) Nil
            else {
                Cell(x,y,TypeCell.UNKNOWN) :: createColumn(x, y+1)
            }
        }

        Grid(createCells(0))
    }

}

