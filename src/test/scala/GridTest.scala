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

    test("Check good cells of a ship. That means position"){
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

    test("Place ship on the grid"){
        val grid: Grid = Grid.createGrid()

        val typeShip: TypeShip = Config.TYPESHIP.head
        val cell: Cell = Cell(2,2, TypeCell.OCCUPIED)
        val direction: Int = 2

        val ship: Ship = Ship.createShip(typeShip, cell, direction)
        val newGrid: Grid = grid.placeShip(ship)

        val listOfOccupiedCells: List[Cell] = newGrid.cells.flatten.filter(cell => {
            cell.typeCell == TypeCell.OCCUPIED
        })

        assert(listOfOccupiedCells == ship.cells)
    }

    test("Check ship overlapping another ship exactly on the same cells"){
        val grid: Grid = Grid.createGrid()

        val typeShip: TypeShip = Config.TYPESHIP.head
        val cell: Cell = Cell(2,2, TypeCell.OCCUPIED)
        val direction: Int = 2

        val ship: Ship = Ship.createShip(typeShip, cell, direction)
        val newGrid: Grid = grid.placeShip(ship)

        val ship2: Ship = Ship.createShip(typeShip, cell, direction)

        assert(!newGrid.checkPosition(ship2))
    }

    test("Check ship overlapping another ship on one cell"){
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

    test("Check ship at the border of the grid"){
        val grid: Grid = Grid.createGrid()

        val typeShip: TypeShip = Config.TYPESHIP.head
        val cell1: Cell = Cell(9,9, TypeCell.OCCUPIED)
        val top: Int = 1
        val ship: Ship = Ship.createShip(typeShip, cell1, top)

        assert(grid.checkPosition(ship))
    }

    test("Shots on the grid") {
        val grid: Grid = Grid.createGrid()

        val typeShip: TypeShip = Config.TYPESHIP.head
        val cell1: Cell = Cell(2,2, TypeCell.OCCUPIED)
        val right: Int = 2
        val ship: Ship = Ship.createShip(typeShip, cell1, right)
        val newGrid: Grid = grid.placeShip(ship)

        // CASE TypeCell.OCCUPIED
        val shot1: Cell = Cell(2,2, TypeCell.UNKNOWN)
        val gridAfterFirstShot: Grid = newGrid.shot(shot1)
        assert(gridAfterFirstShot.cells.flatten.count(cell => cell.typeCell == TypeCell.TOUCHED) == 1)

        // CASE TypeCell.UNKNOWN
        val shot2: Cell = Cell(5,5, TypeCell.UNKNOWN)
        val gridAfterSecondShot: Grid = gridAfterFirstShot.shot(shot2)
        assert(gridAfterSecondShot.cells.flatten.count(cell => cell.typeCell == TypeCell.WATER) == 1)

        // CASE TypeCell.WATER
        val shot3: Cell = Cell(5,5, TypeCell.UNKNOWN)
        val gridAfterThirdShot: Grid = gridAfterSecondShot.shot(shot3)
        assert(gridAfterThirdShot.cells.flatten.count(cell => cell.typeCell == TypeCell.TOUCHED) == 1)

        // CASE TypeCell.TOUCHED
        val shot4: Cell = Cell(2,2, TypeCell.UNKNOWN)
        val gridAfterForthShot: Grid = newGrid.shot(shot1)
        assert(gridAfterForthShot.cells.flatten.count(cell => cell.typeCell == TypeCell.TOUCHED) == 1)
    }

    test("Check cell that return the type of a cell "){
        val grid: Grid = Grid.createGrid()
        val cell1: Cell = Cell(5,5, TypeCell.UNKNOWN)
        val cell2: Cell = Cell(4,4, TypeCell.WATER)
        val cell3: Cell = Cell(3,3, TypeCell.TOUCHED)
        val cell4: Cell = Cell(2,2, TypeCell.OCCUPIED)

        val grid1: Grid = Grid(grid.cells.updated(cell2.x, grid.cells(cell2.x).updated(cell2.y, cell2)))
        val grid2: Grid = Grid(grid1.cells.updated(cell3.x, grid1.cells(cell3.x).updated(cell3.y, cell3)))
        val grid3: Grid = Grid(grid2.cells.updated(cell4.x, grid2.cells(cell4.x).updated(cell4.y, cell4)))

        assert(cell1.typeCell == grid3.checkCell(cell1))
        assert(cell2.typeCell == grid3.checkCell(cell2))
        assert(cell3.typeCell == grid3.checkCell(cell3))
        assert(cell4.typeCell == grid3.checkCell(cell4))
    }

}
