trait Player {
    val name: String
    val ships: List[Ship]
    val grid: Grid

    def isAlive: Boolean
    def createShips(typeShips: List[TypeShip], player: Player, f1:() => Int, f2:() => Int, f3:() => Int): Player
    def shot(cell: Cell, gameState: GameState): GameState

}
