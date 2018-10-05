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
        }
    }

    //TODO: TO REFACTOR WITH A HIGH ORDER FUNCTION

    /**
      * Function that display the grid with ships and shots
      */
    def displayGridShips(): Unit = {

        /**
          * Function that display a cell with the right caractere according to the type of the cell
          * @param cell: Cell: the cell to display
          */
        def displayCellGridShip(cell: Cell): Unit = {
            cell.typeCell match {
                case TypeCell.WATER => print(Console.BLUE+"□ "+Console.RESET)
                case TypeCell.TOUCHED => print(Console.RED+"● "+Console.RESET)
                case TypeCell.UNKNOWN => print(Console.BLACK +"□ "+Console.RESET)
                case TypeCell.OCCUPIED => print(Console.WHITE+"ο "+Console.RESET)
            }
        }

        /**
          * Function that display the grid with ships and shots of the opponent on your grid for
          * @param x: Int: Number of the row
          * @param y: Int; Number of the column
          */
        def displayGridShipsTR(x: Int, y: Int): Unit = {
            if(x == 0) print(y+" ")

            if (x < 10) {
                displayCellGridShip(this.cells(x)(y))
                displayGridShipsTR(x+1, y)
            }
            else if (y < 9) {
                println()
                displayGridShipsTR(0, y+1)
            }
        }

        println("  A B C D E F G H I J")
        displayGridShipsTR(0, 0)
        println()
    }

    /**
      * Function that display the grid with ships and shots
      */
    def displayGridShots(): Unit = {
        /**
          * Function that display a cell with the right caractere according to the type of the cell
          * @param cell: Cell: the cell to display
          */
        def displayCellGridShot(cell: Cell): Unit = {
            cell.typeCell match {
                case TypeCell.WATER => print(Console.BLUE+"□ "+Console.RESET)
                case TypeCell.TOUCHED => print(Console.RED+"● "+Console.RESET)
                case TypeCell.UNKNOWN | TypeCell.OCCUPIED => print(Console.BLACK +"□ "+Console.RESET)
            }
        }

        /**
          * Function that display the grid with shots that you did
          * @param x: Int: Number of the row
          * @param y: Int; Number of the column
          */
        def displayGridShipsTR(x: Int, y: Int): Unit = {
            if(x == 0) print(y+" ")

            if (x < 10) {
                displayCellGridShot(this.cells(x)(y))
                displayGridShipsTR(x+1, y)
            }
            else if (y < 9) {
                println()
                displayGridShipsTR(0, y+1)
            }
        }
        println("  A B C D E F G H I J")
        displayGridShipsTR(0, 0)
        println()
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

