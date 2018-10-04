package Battleship

object Config {
    // CTE
    val TYPESHIP: List[TypeShip] = List(TypeShip("Carrier",5), TypeShip("Battleship",4), TypeShip("Cruiser",3), TypeShip("Submarine",3), TypeShip("Destroyer",2))

    val TEXT_POSITIONING_SHIP: String = "Where do you want to position your ship?"
    val TEXT_SHOOT: String = "Where do you want to shoot?"
}
