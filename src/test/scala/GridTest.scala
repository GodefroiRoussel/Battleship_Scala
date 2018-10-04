import Battleship._

import org.scalatest._

class GridTest extends FunSuite with DiagrammedAssertions {
    test("Creating the grid with only UNKNOWN cell. That implies createCells and createColumn tested throught createGrid") {
        val grid: Grid = Grid.createGrid()
        // For each row count each list that satisfy the predicate length of the column == 10
        val numberListCellOccupied: Int = grid.cells.count(row => {
            // For a column count each cell that satisfy the predicate cell.typeCell == TypeCell.UNKNOWN
            row.count(cell => {
                cell.typeCell == TypeCell.UNKNOWN
            }) == 10
        })

        // If the number of ListCellOccupied equal 10 then the test is good
        assert(numberListCellOccupied == 10)
    }

    test("Check good cells of a ship"){
        val grid: Grid = Grid.createGrid()

        val typeShip: TypeShip = Config.TYPESHIP.head
        val cell: Cell = Cell(2,2, TypeCell.OCCUPIED)
        val direction: Int = 2

        val ship: Ship = Ship.createShip(typeShip, cell, direction)

        assert(grid.checkPosition(ship))
    }

    test("Check bad cells of a ship"){
        val grid: Grid = Grid.createGrid()

        val typeShip: TypeShip = Config.TYPESHIP.head
        val cell: Cell = Cell(8,6, TypeCell.OCCUPIED)
        val direction: Int = 2

        val ship: Ship = Ship.createShip(typeShip, cell, direction)

        assert(!grid.checkPosition(ship))
    }

    test("Place Ship on the grid"){
        val grid: Grid = Grid.createGrid()

        val typeShip: TypeShip = Config.TYPESHIP.head
        val cell: Cell = Cell(2,2, TypeCell.OCCUPIED)
        val direction: Int = 2

        val ship: Ship = Ship.createShip(typeShip, cell, direction)
        val newGrid: Grid = grid.placeShip(ship)

        val listOfOccupiedCells: List[Cell] = newGrid.cells.flatMap(cell => cell).filter(cell => {
            cell.typeCell == TypeCell.OCCUPIED
        })

        assert(listOfOccupiedCells == ship.cells)
    }

    test("Check ship collapsing another ship exactly on the same cells"){
        val grid: Grid = Grid.createGrid()

        val typeShip: TypeShip = Config.TYPESHIP.head
        val cell: Cell = Cell(2,2, TypeCell.OCCUPIED)
        val direction: Int = 2

        val ship: Ship = Ship.createShip(typeShip, cell, direction)
        val newGrid: Grid = grid.placeShip(ship)

        val ship2: Ship = Ship.createShip(typeShip, cell, direction)

        assert(!newGrid.checkPosition(ship2))
    }

    test("Check ship collapsing another ship on one cell"){
        val grid: Grid = Grid.createGrid()

        val typeShip: TypeShip = Config.TYPESHIP.head
        val cell1: Cell = Cell(2,2, TypeCell.OCCUPIED)
        val right: Int = 2
        val ship: Ship = Ship.createShip(typeShip, cell1, right)
        val newGrid: Grid = grid.placeShip(ship)

        val cell2: Cell = Cell(3,6, TypeCell.OCCUPIED)
        val top: Int = 1
        val ship2: Ship = Ship.createShip(typeShip, cell2, top)

        assert(!newGrid.checkPosition(ship2))
    }

    test("Check ship near to another ship of one cell"){
        val grid: Grid = Grid.createGrid()

        val typeShip: TypeShip = Config.TYPESHIP.head
        val cell1: Cell = Cell(2,2, TypeCell.OCCUPIED)
        val right: Int = 2
        val ship: Ship = Ship.createShip(typeShip, cell1, right)
        val newGrid: Grid = grid.placeShip(ship)

        val cell2: Cell = Cell(3,7, TypeCell.OCCUPIED)
        val top: Int = 1
        val ship2: Ship = Ship.createShip(typeShip, cell2, top)

        assert(newGrid.checkPosition(ship2))
    }
}
