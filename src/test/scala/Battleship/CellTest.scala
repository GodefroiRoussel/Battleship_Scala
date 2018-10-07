package Battleship

import org.scalatest._

class CellTest extends FunSuite with DiagrammedAssertions {
    test("Creating cells with all enumerations possible") {
        val cell1: Cell = Cell(0, 0, TypeCell.OCCUPIED)
        val cell2: Cell = Cell(0, 0, TypeCell.UNKNOWN)
        val cell3: Cell = Cell(0, 0, TypeCell.WATER)
        val cell4: Cell = Cell(0, 0, TypeCell.TOUCHED)

        assert(cell1.typeCell == TypeCell.OCCUPIED)
        assert(cell2.typeCell == TypeCell.UNKNOWN)
        assert(cell3.typeCell == TypeCell.WATER)
        assert(cell4.typeCell == TypeCell.TOUCHED)
    }
}
