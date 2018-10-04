package Battleship

object TypeCell extends Enumeration {
    val WATER, OCCUPIED, TOUCHED, UNKNOWN = Value
}

case class Cell(x: Int, y: Int, typeCell: TypeCell.Value) {}
