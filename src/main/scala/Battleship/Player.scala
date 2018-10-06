package Battleship

import Main.GameState

import scala.util.Random

trait Player {
    val name: String
    val ships: List[Ship]
    val grid: Grid
    val random: Random

    /**
      * Function to know if a player isAlive
      * @return false if the player has no more ship alive that means the sum of all size of ships are equal to the number of touched cell in the grid
      */
    def isAlive: Boolean = {
        val numberLifePoint: Int = ships.map(_.typeShip.size).sum
        this.grid.cells.flatten.count(cell => cell.typeCell == TypeCell.TOUCHED) != numberLifePoint
    }

    def createShips(typeShips: List[TypeShip], f1:() => Int, f2:() => Int, f3:() => Int): Player

    /**
      * Function that shoot on a cell of the opponent (that means marked the cell shot on the grid and if needed on its list of ships)
      * @param cell : Cell: cell to shoot
      * @param gameState: GameState: State of the game
      * @return a new game state where grid and player state updated
      */
    def shot(cell: Cell, gameState: GameState): GameState = {
        // Check shot and then update the ship hitted
        val resultShot: TypeCell.Value = gameState.player2.grid.checkCell(cell)

        // If the cell targeted is OCCUPIED or TOUCHED then we mark the cell has TOUCHED on the ship
        val newShips: List[Ship] = resultShot match {
            case TypeCell.OCCUPIED | TypeCell.TOUCHED =>
                gameState.player2.ships.map(ship => {
                    // Check if contains in the ship the cell with the type OCCUPIED or TOUCHED and then hit else return the ship
                    if (ship.cells.contains(cell.copy(typeCell = TypeCell.OCCUPIED)) || ship.cells.contains(cell.copy(typeCell = TypeCell.TOUCHED))) {
                        val tempShip: Ship = ship.hit(cell)
                        println("You hitted a ship !\n")
                        if (tempShip.isSunk()) {
                            println(s"Well done ! The ${tempShip.typeShip.name} is sunk.\n")
                        }
                        tempShip
                    } else ship
                })
            case _ =>
                println("Plouf ! You missed your shot\n")
                gameState.player2.ships
        }

        val player2AfterFirstShot: Player = gameState.player2.copyShips(ships = newShips)
        //Update the grid according to the shot on the grid
        val newGridPlayer2 = player2AfterFirstShot.grid.shot(cell)

        // Update the player shooted
        val newPlayer2: Player = player2AfterFirstShot.copyShipsAndGrid(player2AfterFirstShot.ships, newGridPlayer2)
        GameState(newPlayer2, gameState.player1)
    }
    def copyShips(ships: List[Ship]): Player
    def copyShipsAndGrid(ships: List[Ship], grid: Grid): Player
    def updateInformation(): Player
    def getInfoForShot(): Cell
}