case class TypeShip(name: String, size: Int){}

case class Ship(typeShip: TypeShip, cells: List[Cell]) {

}

object Ship {
    def createShip(typeShip: TypeShip, cell: Cell, direction: Int) : Ship = {
        val newTypeShip = typeShip.copy(size = typeShip.size-1)
        val cells = cell :: createListCellsFor(typeShip, cell, direction)
        Ship(typeShip, cells)
    }

    /**
      * Function creating a list of cell beginning after the first cell
      * @param typeShip
      * @param cell
      * @param direction
      * @return
      */
    private def createListCellsFor(typeShip: TypeShip, cell: Cell, direction: Int) : List[Cell] = {
        if (typeShip.size == 1)
            Nil
        else {
            val newTypeShip = typeShip.copy(size = typeShip.size-1)
            val newCell = direction match {
                case 1 => cell.copy(y = cell.y - 1)
                case 2 => cell.copy(x = cell.x + 1)
                case 3 => cell.copy(y = cell.y + 1)
                case _ => cell.copy(x = cell.x - 1)
            }
            newCell :: createListCellsFor(newTypeShip, newCell, direction)
        }
    }
}