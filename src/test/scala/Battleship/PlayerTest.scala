package Battleship

import Battleship.Players.AIs.{AIEasyPlayer, AIHardPlayer, AIMediumPlayer}
import org.scalatest._
import Battleship.Players.{HumanPlayer, Player}

import scala.util.Random

class PlayerTest extends FunSuite with DiagrammedAssertions {
    test("Shot as a player that means testing to shot as a HumanPlayer but also as an AI"){
        val r: Random = new Random

        val ship: Ship = Ship.createShip(Config.TYPESHIP.head, Cell(4,4, TypeCell.OCCUPIED), 3)

        val newGrid: Grid = Grid.createGrid().placeShip(ship)
        val hardPlayer: Player = AIHardPlayer("Player", List(ship), newGrid, r)
        val humanPlayer: Player = HumanPlayer("Player", List(ship), newGrid, r)

        val cell1: Cell = Cell(4, 4, TypeCell.UNKNOWN)
        val cell2: Cell = Cell(0, 0, TypeCell.UNKNOWN)

        val gameState1: GameState = GameState(humanPlayer, hardPlayer)
        // Turn 1 Both players touch a ship
        val gameState2: GameState = gameState1.player1.shot(cell1, gameState1)
        val gameState3: GameState = gameState2.player1.shot(cell1, gameState2)

        assert(gameState3.player1.grid.listTouchedCell().length == 1)
        assert(gameState3.player2.grid.listTouchedCell().length == 1)
        // Turn 2 Both players miss
        val gameState4: GameState = gameState3.player1.shot(cell2, gameState3)
        val gameState5: GameState = gameState4.player1.shot(cell2, gameState4)

        assert(gameState5.player1.grid.listTouchedCell().length == 1)
        assert(gameState5.player2.grid.listTouchedCell().length == 1)

        // Turn 3 Both players shot on a cell already touched
        val gameState6: GameState = gameState5.player1.shot(cell1, gameState5)
        val gameState7: GameState = gameState6.player1.shot(cell1, gameState6)

        assert(gameState7.player1.grid.listTouchedCell().length == 1)
        assert(gameState7.player2.grid.listTouchedCell().length == 1)
    }

    test("Is a player alive"){
        val r: Random = new Random

        val ship: Ship = Ship.createShip(Config.TYPESHIP.head, Cell(4,4, TypeCell.OCCUPIED), 3)

        val newGrid: Grid = Grid.createGrid().placeShip(ship)
        val hardPlayer: Player = AIHardPlayer("Player", List(ship), newGrid, r)
        val humanPlayer: Player = HumanPlayer("Player", List(ship), newGrid, r)

        assert(hardPlayer.isAlive)
        assert(humanPlayer.isAlive)

        val cell1: Cell = Cell(4, 4, TypeCell.UNKNOWN)
        val cell2: Cell = Cell(4, 5, TypeCell.UNKNOWN)
        val cell3: Cell = Cell(4, 6, TypeCell.UNKNOWN)
        val cell4: Cell = Cell(4, 7, TypeCell.UNKNOWN)
        val cell5: Cell = Cell(4, 8, TypeCell.UNKNOWN)

        val gameState1: GameState = GameState(humanPlayer, hardPlayer)
        // Turn 1
        val gameState2: GameState = gameState1.player1.shot(cell1, gameState1)
        val gameState3: GameState = gameState2.player1.shot(cell1, gameState2)

        // Turn 2
        val gameState4: GameState = gameState3.player1.shot(cell2, gameState3)
        val gameState5: GameState = gameState4.player1.shot(cell2, gameState4)

        // Turn 3
        val gameState6: GameState = gameState5.player1.shot(cell3, gameState5)
        val gameState7: GameState = gameState6.player1.shot(cell3, gameState6)

        // Turn 4
        val gameState8: GameState = gameState7.player1.shot(cell4, gameState7)
        val gameState9: GameState = gameState8.player1.shot(cell4, gameState8)

        // Turn 5
        val gameState10: GameState = gameState9.player1.shot(cell5, gameState9)
        val gameState11: GameState = gameState10.player1.shot(cell5, gameState10)

        assert(!gameState11.player1.isAlive)
        assert(!gameState11.player2.isAlive)
    }

    test("Is a player human"){
        val humanPlayer: Player = HumanPlayer()
        val hardAIPlayer: Player = AIHardPlayer()
        val mediumAIPlayer: Player = AIMediumPlayer()
        val easyAIPlayer: Player = AIEasyPlayer()
        assert(humanPlayer.isHuman)
        assert(!hardAIPlayer.isHuman)
        assert(!mediumAIPlayer.isHuman)
        assert(!easyAIPlayer.isHuman)
    }

    test("Copy ships of Human Player"){
        val r: Random = new Random

        val ship: Ship = Ship.createShip(Config.TYPESHIP.head, Cell(4,4, TypeCell.OCCUPIED), 3)
        val ship2: Ship = Ship.createShip(Config.TYPESHIP.head, Cell(5,5, TypeCell.OCCUPIED), 1)

        val humanPlayerToCopy: Player = HumanPlayer(ships = List(ship), random = r)
        val newShips: List[Ship] = List(ship,ship2)

        val humanPlayerShouldBeAfterCopy: Player = HumanPlayer(ships = newShips, random = r)
        val playerAfterCopy: Player = humanPlayerToCopy.copyShips(newShips)

        assert(humanPlayerShouldBeAfterCopy == playerAfterCopy)
    }

    test("Copy ships and grid of Human Player"){
        val r: Random = new Random

        val ship: Ship = Ship.createShip(Config.TYPESHIP.head, Cell(4,4, TypeCell.OCCUPIED), 3)
        val ship2: Ship = Ship.createShip(Config.TYPESHIP.head, Cell(5,5, TypeCell.OCCUPIED), 1)

        val humanPlayerToCopy: Player = HumanPlayer(ships = List(ship), random = r)

        val newGrid: Grid = humanPlayerToCopy.grid.placeShip(ship)

        val newShips: List[Ship] = List(ship,ship2)

        val humanPlayerShouldBeAfterCopy: Player = HumanPlayer(ships = newShips, grid = newGrid, random = r)
        val playerAfterCopy: Player = humanPlayerToCopy.copyShipsAndGrid(newShips, newGrid)

        assert(humanPlayerShouldBeAfterCopy == playerAfterCopy)
    }
}
