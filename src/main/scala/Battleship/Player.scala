package Battleship

import Main.GameState

trait Player {
    val name: String
    val ships: List[Ship]
    val grid: Grid

    def isAlive: Boolean
    def createShips(typeShips: List[TypeShip], f1:() => Int, f2:() => Int, f3:() => Int): Player
    def shot(cell: Cell, gameState: GameState): GameState
    def copy(name: String, ships: List[Ship], grid: Grid): Player

}
