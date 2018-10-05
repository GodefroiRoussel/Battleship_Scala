package Battleship

import Main.GameState

case class HumanPlayer(name: String, ships: List[Ship] = List(), grid: Grid = Grid.createGrid()) extends Player {

    /**
      * Function to know if a player isAlive
      * @return false if the player has no more ship alive that means the sum of all size of ships are equal to the number of touched cell in the grid
      */
    override def isAlive: Boolean = {
        val numberLifePoint: Int = ships.map(_.typeShip.size).sum
        this.grid.cells.flatten.count(cell => cell.typeCell == TypeCell.TOUCHED) != numberLifePoint
    }

    /**
      * Function that shoot on a cell of the opponent (that means marked the cell shot on the grid and if needed on its list of ships)
      * @param cell : Cell: cell to shoot
      * @param gameState: GameState: State of the game
      * @return a new game state where grid and player state updated
      */
    override def shot(cell: Cell, gameState: GameState): GameState = {
        // Check shot and then update the ship hitted
        val resultShot: TypeCell.Value = gameState.player2.grid.checkCell(cell)

        // If the cell targeted is OCCUPIED or TOUCHED then we mark the cell has TOUCHED on the ship
        val newShips: List[Ship] = resultShot match {
            case TypeCell.OCCUPIED | TypeCell.TOUCHED =>
                gameState.player2.ships.map(ship => {
                    // Check if contains in the ship the cell with the type OCCUPIED or TOUCHED and then hit else return the ship
                    if(ship.cells.contains(cell.copy(typeCell = TypeCell.OCCUPIED)) || ship.cells.contains(cell.copy(typeCell = TypeCell.TOUCHED))) {
                        val tempShip: Ship = ship.hit(cell)
                        println("You hitted a ship !\n")
                        if(tempShip.isSunk()){
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
                case true =>
                    val newGrid: Grid = this.grid.placeShip(tempShip)
                    val newListShips: List[Ship] = tempShip :: this.ships
                    val newPlayer: HumanPlayer = HumanPlayer(this.name, newListShips, newGrid)
                    val newTypeShips: List[TypeShip] = typeShips.tail
                    newPlayer.createShips(newTypeShips, f1, f2, f3)
                case false =>
                    println("Your ship is not well positioned on the grid. Please try again.\n")
                    print(tempShip)
                    this.createShips(typeShips, f1, f2, f3)
            }
        }
    }

    override def copy(name: String, ships: List[Ship], grid: Grid): HumanPlayer = {
        HumanPlayer(name, ships, grid)
    }

    override def copyShips(newShips: List[Ship]): Player = {
        HumanPlayer(this.name, ships = newShips, this.grid)
    }

    override def copyShipsAndGrid(ships: List[Ship], grid: Grid): Player = {
        HumanPlayer(this.name, ships, grid)
    }
}
