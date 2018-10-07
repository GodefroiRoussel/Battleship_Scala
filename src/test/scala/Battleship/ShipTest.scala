package Battleship

import org.scalatest._

class ShipTest extends FunSuite with DiagrammedAssertions {
    test("Creating all ships implies also testing createListCellsFor") {
        val typeShipList: List[TypeShip] = Config.TYPESHIP

        val cell1: Cell = Cell(2, 2,TypeCell.OCCUPIED)
        val cell2: Cell = Cell(3, 6,TypeCell.OCCUPIED)
        val cell3: Cell = Cell(7, 7,TypeCell.OCCUPIED)
        val cell4: Cell = Cell(9, 4,TypeCell.OCCUPIED)
        val cell5: Cell = Cell(6, 9,TypeCell.OCCUPIED)

        val top: Int = 1 //That means going to the top
        val right: Int = 2 //That means going to the right
        val bottom: Int = 3 //That means going to the bottom
        val left: Int = 4 //That means going to the left

        val listCellsShip1: List[Cell] =
            List(
                Cell(2,2,TypeCell.OCCUPIED),
                Cell(3,2,TypeCell.OCCUPIED),
                Cell(4,2,TypeCell.OCCUPIED),
                Cell(5,2,TypeCell.OCCUPIED),
                Cell(6,2,TypeCell.OCCUPIED)
            )

        val listCellsShip2: List[Cell] =
            List(
                Cell(3,6,TypeCell.OCCUPIED),
                Cell(3,5,TypeCell.OCCUPIED),
                Cell(3,4,TypeCell.OCCUPIED),
                Cell(3,3,TypeCell.OCCUPIED)
            )

        val listCellsShip3: List[Cell] =
            List(
                Cell(7,7,TypeCell.OCCUPIED),
                Cell(6,7,TypeCell.OCCUPIED),
                Cell(5,7,TypeCell.OCCUPIED)
            )

        val listCellsShip4: List[Cell] =
            List(
                Cell(9,4,TypeCell.OCCUPIED),
                Cell(8,4,TypeCell.OCCUPIED),
                Cell(7,4,TypeCell.OCCUPIED)
            )

        val listCellsShip5: List[Cell] =
            List(
                Cell(6,9,TypeCell.OCCUPIED),
                Cell(7,9,TypeCell.OCCUPIED)
            )

        val ship1: Ship = Ship.createShip(typeShipList.head, cell1, right)
        val ship2: Ship = Ship.createShip(typeShipList(1), cell2, top)
        val ship3: Ship = Ship.createShip(typeShipList(2), cell3, left)
        val ship4: Ship = Ship.createShip(typeShipList(3), cell4, left)
        val ship5: Ship = Ship.createShip(typeShipList(4), cell5, right)

        assert(ship1.typeShip == typeShipList.head && ship1.cells == listCellsShip1)
        assert(ship2.typeShip == typeShipList(1) && ship2.cells == listCellsShip2)
        assert(ship3.typeShip == typeShipList(2) && ship3.cells == listCellsShip3)
        assert(ship4.typeShip == typeShipList(3) && ship4.cells == listCellsShip4)
        assert(ship5.typeShip == typeShipList(4) && ship5.cells == listCellsShip5)
    }

    test("A hit on a ship"){
        val typeShip: TypeShip = Config.TYPESHIP.head
        val cell: Cell = Cell(2,2, TypeCell.OCCUPIED)
        val right: Int = 2
        val ship: Ship = Ship.createShip(typeShip, cell, right)

        val listCell: List[Cell] = List(
            Cell(2,2,TypeCell.TOUCHED),
            Cell(3,2,TypeCell.OCCUPIED),
            Cell(4,2,TypeCell.OCCUPIED),
            Cell(5,2,TypeCell.OCCUPIED),
            Cell(6,2,TypeCell.OCCUPIED)
        )

        val shipHitted: Ship = ship.hit(cell)
        val shipTest: Ship = Ship(typeShip, listCell)
        assert(shipTest == shipHitted)
    }

    test("Count the number of touched cell in a ship"){
        val typeShip: TypeShip = Config.TYPESHIP.head
        val listCell: List[Cell] = List(
            Cell(2,2,TypeCell.TOUCHED),
            Cell(3,2,TypeCell.TOUCHED),
            Cell(4,2,TypeCell.TOUCHED),
            Cell(5,2,TypeCell.TOUCHED),
            Cell(6,2,TypeCell.OCCUPIED)
        )
        val ship: Ship = Ship(typeShip, listCell)
        assert(ship.numberCellsTouched()==4)
    }

    test("A ship is not sunk then a ship is sunk"){
        val typeShip: TypeShip = Config.TYPESHIP.head
        val listCell: List[Cell] = List(
            Cell(2,2,TypeCell.TOUCHED),
            Cell(3,2,TypeCell.TOUCHED),
            Cell(4,2,TypeCell.TOUCHED),
            Cell(5,2,TypeCell.TOUCHED),
            Cell(6,2,TypeCell.OCCUPIED)
        )
        val ship: Ship = Ship(typeShip, listCell)
        assert(!ship.isSunk())

        val shipHitted: Ship = ship.hit(Cell(6,2,TypeCell.OCCUPIED))
        assert(shipHitted.isSunk())
    }
}