package Battleship

import Main.GameState

case class HumanPlayer(name: String, ships: List[Ship] = List(), grid: Grid = Grid.createGrid()) extends Player {

    /**
      * Function to know if a player isAlive
      * @return false if the player has no more ship alive that means the sum of all size of ships are equal to the number of touched cell in the grid
      */
    override def isAlive: Boolean = {
        val numberLifePoint: Int = ships.map(_.typeShip.size).sum
        this.grid.cells.flatten.count(cell => cell.typeCell == TypeCell.TOUCHED) == numberLifePoint
    }

    /**
      * Function that shoot on a cell of the opponent
      * @param cell : Cell: cell to shoot
      * @param gameState: GameState: State of the game
      * @return a new game state where grid and player state updated
      */
    override def shot(cell: Cell, gameState: GameState): GameState = {
        // TODO: IMPLEMENT
        GameState(gameState.player1, gameState.player2)
    }

    override def createShips(typeShips: List[TypeShip], f1:() => Int, f2:() => Int, f3:() => Int): HumanPlayer = {
        if (typeShips.isEmpty) {
            HumanPlayer(this.name, this.ships, this.grid)
        } else {
            val firstTypeShip: TypeShip = typeShips.head
            println(s"You have to place the ship ${firstTypeShip.name}, it has a size of ${firstTypeShip.size} cells.")
            //Get input of the user to create the cell of origin and then create a temporary ship until check
            val letter = f1()
            val number = f2()
            val direction = f3()
            val cell = Cell(letter, number, TypeCell.OCCUPIED)
            val tempShip : Ship = Ship.createShip(firstTypeShip, cell, direction)

            //Check if the ship is collapsing another ship or outside the board
            this.grid.checkPosition(tempShip) match {
                case true => {
                    val newGrid: Grid = this.grid.placeShip(tempShip)
                    val newListShips: List[Ship] = tempShip :: this.ships
                    val newPlayer: HumanPlayer = HumanPlayer(this.name, newListShips, newGrid)
                    val newTypeShips: List[TypeShip] = typeShips.tail
                    println(tempShip)
                    newPlayer.createShips(newTypeShips, f1, f2, f3)
                }
                case false =>
                    println("Your ship is not well positioned on the grid. Please try again.\n")
                    print(tempShip)
                    this.createShips(typeShips, f1, f2, f3)
            }
        }
    }
}
