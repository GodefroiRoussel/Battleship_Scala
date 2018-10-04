case class HumanPlayer(name: String, ships: List[Ship] = List(), grid: Grid = Grid.createGrid()) extends Player {

    /**
      * Function to know if a player isAlive
      * @return a false if the player has no more ship, else true
      */
    override def isAlive: Boolean = {
        // TODO: Implement
        true
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

    override def createShips(typeShips: List[TypeShip], player: Player, f1:() => Int, f2:() => Int, f3:() => Int): HumanPlayer = {
        if (typeShips.isEmpty) {
            HumanPlayer(player.name, player.ships, player.grid)
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
            player.grid.checkPosition(tempShip) match {
                case true => {
                    val newGrid: Grid = player.grid.placeShip(tempShip)
                    val newListShips: List[Ship] = tempShip :: player.ships
                    val newPlayer: HumanPlayer = HumanPlayer(player.name, newListShips, newGrid)
                    val newTypeShips: List[TypeShip] = typeShips.tail
                    println(tempShip)
                    createShips(newTypeShips, newPlayer, f1, f2, f3)
                }
                case false =>
                    println("Your ship is not well positioned on the grid. Please try again.")
                    createShips(typeShips, player, f1, f2, f3)
            }

            println(s"Lettre : ${letter} + Number : ${number} + Direction : ${direction}")
            println(tempShip)
            HumanPlayer(player.name, player.ships, player.grid)

        }
    }
}
