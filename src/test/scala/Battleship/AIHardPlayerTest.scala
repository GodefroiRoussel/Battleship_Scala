package Battleship

import Battleship.Players.AIs.AIHardPlayer
import Battleship.Players.Player
import org.scalatest._

import scala.util.Random

class AIHardPlayerTest extends FunSuite with DiagrammedAssertions {
    test("Copy ships of hard AI"){
        val r: Random = new Random

        val ship: Ship = Ship.createShip(Config.TYPESHIP.head, Cell(4,4, TypeCell.OCCUPIED), 3)
        val ship2: Ship = Ship.createShip(Config.TYPESHIP.head, Cell(5,5, TypeCell.OCCUPIED), 1)

        val hardPlayerToCopy: Player = AIHardPlayer(ships = List(ship), random = r)
        val newShips: List[Ship] = List(ship,ship2)

        val hardPlayerShouldBeAfterCopy: Player = AIHardPlayer(ships = newShips, random = r)
        val playerAfterCopy: Player = hardPlayerToCopy.copyShips(newShips)

        assert(hardPlayerShouldBeAfterCopy == playerAfterCopy)
    }

    test("Copy ships and grid of hard AI"){
        val r: Random = new Random

        val ship: Ship = Ship.createShip(Config.TYPESHIP.head, Cell(4,4, TypeCell.OCCUPIED), 3)
        val ship2: Ship = Ship.createShip(Config.TYPESHIP.head, Cell(5,5, TypeCell.OCCUPIED), 1)

        val hardPlayerToCopy: Player = AIHardPlayer(ships = List(ship), random = r)

        val newGrid: Grid = hardPlayerToCopy.grid.placeShip(ship)

        val newShips: List[Ship] = List(ship,ship2)

        val hardPlayerShouldBeAfterCopy: Player = AIHardPlayer(ships = newShips, grid = newGrid, random = r)
        val playerAfterCopy: Player = hardPlayerToCopy.copyShipsAndGrid(newShips, newGrid)

        assert(hardPlayerShouldBeAfterCopy == playerAfterCopy)
    }

    test("Intelligence of hard AI when ship placed in the middle of the grid"){
        // Creating the environment for tests
        val r: Random = new Random

        val ship: Ship = Ship.createShip(Config.TYPESHIP.head, Cell(5,5, TypeCell.OCCUPIED), 3)

        val hardAI: Player = AIHardPlayer(name = "Shooter", random = r)
        val creatingOpponent:Player = AIHardPlayer(ships = List(ship), random = r)
        val gridOpponentInstantiate: Grid = creatingOpponent.grid.placeShip(ship)
        val opponent: Player = creatingOpponent.copyShipsAndGrid(List(ship), gridOpponentInstantiate)
        val gameState: GameState = GameState(hardAI, opponent)

        // First try
        val gameState1: GameState = hardAI.shot(Cell(5,5, TypeCell.UNKNOWN), gameState)
        val gameStateSwap1: GameState = GameState(gameState1.player2, gameState1.player1)

        // Begin of testing the Intelligence of hard AI
        val firstCellToShot: Cell = gameStateSwap1.player1.getInfoForShot(gameStateSwap1.player2)
        val firstCellThatShouldBeShooted: Cell = Cell(4, 5, TypeCell.UNKNOWN)

        // Second try
        val gameState2: GameState = gameStateSwap1.player1.shot(firstCellToShot, gameStateSwap1)
        val gameStateSwap2: GameState = GameState(gameState2.player2, gameState2.player1)

        val secondCellToShot: Cell = gameStateSwap2.player1.getInfoForShot(gameStateSwap2.player2)
        val secondCellThatShouldBeShooted: Cell = Cell(6, 5, TypeCell.UNKNOWN)
        assert(secondCellToShot == secondCellThatShouldBeShooted)

        // Third try
        val gameState3: GameState = gameStateSwap2.player1.shot(secondCellToShot, gameStateSwap2)
        val gameStateSwap3: GameState = GameState(gameState3.player2, gameState3.player1)

        val thirdCellToShot: Cell = gameStateSwap3.player1.getInfoForShot(gameStateSwap3.player2)
        val thirdCellThatShouldBeShooted: Cell = Cell(5, 4, TypeCell.UNKNOWN)
        assert(thirdCellToShot == thirdCellThatShouldBeShooted)

        // Last try
        val gameState4: GameState = gameStateSwap3.player1.shot(thirdCellToShot, gameStateSwap3)
        val gameStateSwap4: GameState = GameState(gameState4.player2, gameState4.player1)

        val lastCellToShot: Cell = gameStateSwap4.player1.getInfoForShot(gameStateSwap4.player2)
        val lastCellThatShouldBeShooted: Cell = Cell(5, 6, TypeCell.OCCUPIED)
        assert(lastCellToShot == lastCellThatShouldBeShooted)
    }

    test("Intelligence of hard AI when ship placed in the left and top border of the grid."){
        // Creating the environment for tests
        val r: Random = new Random

        val ship: Ship = Ship.createShip(Config.TYPESHIP.head, Cell(0,0, TypeCell.OCCUPIED), 3)

        val hardAI: Player = AIHardPlayer(name = "Shooter", random = r)
        val creatingOpponent:Player = AIHardPlayer(ships = List(ship), random = r)
        val gridOpponentInstantiate: Grid = creatingOpponent.grid.placeShip(ship)
        val opponent: Player = creatingOpponent.copyShipsAndGrid(List(ship), gridOpponentInstantiate)
        val gameState: GameState = GameState(hardAI, opponent)

        // First try
        val gameState1: GameState = hardAI.shot(Cell(0,0, TypeCell.UNKNOWN), gameState)
        val gameStateSwap1: GameState = GameState(gameState1.player2, gameState1.player1)

        // Begin of testing the Intelligence of hard AI
        val firstCellToShot: Cell = gameStateSwap1.player1.getInfoForShot(gameStateSwap1.player2)
        val firstCellThatShouldBeShooted: Cell = Cell(1, 0, TypeCell.UNKNOWN)

        // Second try
        val gameState2: GameState = gameStateSwap1.player1.shot(firstCellToShot, gameStateSwap1)
        val gameStateSwap2: GameState = GameState(gameState2.player2, gameState2.player1)

        val secondCellToShot: Cell = gameStateSwap2.player1.getInfoForShot(gameStateSwap2.player2)
        val secondCellThatShouldBeShooted: Cell = Cell(0, 1, TypeCell.OCCUPIED)
        assert(secondCellToShot == secondCellThatShouldBeShooted)

    }

    test("Intelligence of hard AI when ship placed in the right and bottom border of the grid."){
        // Creating the environment for tests
        val r: Random = new Random

        val ship: Ship = Ship.createShip(Config.TYPESHIP.head, Cell(9,9, TypeCell.OCCUPIED), 1)

        val hardAI: Player = AIHardPlayer(name = "Shooter", random = r)
        val creatingOpponent:Player = AIHardPlayer(ships = List(ship), random = r)
        val gridOpponentInstantiate: Grid = creatingOpponent.grid.placeShip(ship)
        val opponent: Player = creatingOpponent.copyShipsAndGrid(List(ship), gridOpponentInstantiate)
        val gameState: GameState = GameState(hardAI, opponent)

        // First try
        val gameState1: GameState = hardAI.shot(Cell(9,9, TypeCell.UNKNOWN), gameState)
        val gameStateSwap1: GameState = GameState(gameState1.player2, gameState1.player1)

        // Begin of testing the Intelligence of hard AI
        val firstCellToShot: Cell = gameStateSwap1.player1.getInfoForShot(gameStateSwap1.player2)
        val firstCellThatShouldBeShooted: Cell = Cell(8, 9, TypeCell.UNKNOWN)

        // Second try
        val gameState2: GameState = gameStateSwap1.player1.shot(firstCellToShot, gameStateSwap1)
        val gameStateSwap2: GameState = GameState(gameState2.player2, gameState2.player1)

        val secondCellToShot: Cell = gameStateSwap2.player1.getInfoForShot(gameStateSwap2.player2)
        val secondCellThatShouldBeShooted: Cell = Cell(9, 8, TypeCell.OCCUPIED)
        assert(secondCellToShot == secondCellThatShouldBeShooted)

    }
}
