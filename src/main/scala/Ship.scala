import scala.annotation.tailrec

case class TypeShip(name: String, size: Int){}

case class Ship(typeShip: TypeShip, cells: List[Cell]) {

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
        val newTypeShip = typeShip.copy(size = typeShip.size-1)
        val cells = createListCellsFor(typeShip, cell, direction, List(cell))
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
            val newTypeShip = typeShip.copy(size = typeShip.size-1)
            val newCell = direction match {
                case 1 => cell.copy(y = cell.y - 1)
                case 2 => cell.copy(x = cell.x + 1)
                case 3 => cell.copy(y = cell.y + 1)
                case _ => cell.copy(x = cell.x - 1)
            }
            createListCellsFor(newTypeShip, newCell, direction, cells :+ newCell)
        }
    }
}