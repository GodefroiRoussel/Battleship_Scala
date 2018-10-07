package Helpers

import Battleship.{Cell, Grid, TypeCell}

import scala.annotation.tailrec

object Display {
    def show(s: String) : Unit = {
        println(s)
    }

    def showSameLine(s: String): Unit = {
        print(s)
    }

    /**
      * Function that display the grid with ships and shots
      */
    def showGridShips(grid: Grid): Unit = {

        /**
          * Function that show a cell with the right character according to the type of the cell
          * @param cell: Cell: the cell to show
          */
        def showCellGridShip(cell: Cell): Unit = {
            cell.typeCell match {
                case TypeCell.WATER => Display.showSameLine(Console.BLUE+"□ "+Console.RESET)
                case TypeCell.TOUCHED => Display.showSameLine(Console.RED+"● "+Console.RESET)
                case TypeCell.UNKNOWN => Display.showSameLine(Console.BLACK +"□ "+Console.RESET)
                case TypeCell.OCCUPIED => Display.showSameLine(Console.WHITE+"ο "+Console.RESET)
            }
        }

        Display.show("  A B C D E F G H I J")
        showGridTR(0, 0, grid, showCellGridShip)
        Display.show("")
    }

    /**
      *
      * Function that show the grid with ships and shots of the opponent on your grid for
      * @param x: Int: Number of the row
      * @param y: Int; Number of the column
      * @param grid: Grid: The grid to display
      * @param f1: Cell => Unit: The function that display a cell according to the context
      */
    @tailrec
    def showGridTR(x: Int, y: Int, grid: Grid, f1: Cell => Unit) : Unit = {
        if(x == 0) Display.showSameLine(y+" ")

        if (x < 10) {
            f1(grid.cells(x)(y))
            showGridTR(x+1, y, grid, f1)
        }
        else if (y < 9) {
            Display.show("")
            showGridTR(0, y+1, grid, f1)
        }
    }

    /**
      * Function that show the grid with shots only
      */
    def showGridShots(grid: Grid): Unit = {
        /**
          * Function that show a cell with the right character according to the type of the cell
          * @param cell: Cell: the cell to show
          */
        def showCellGridShot(cell: Cell): Unit = {
            cell.typeCell match {
                case TypeCell.WATER => Display.showSameLine(Console.BLUE+"□ "+Console.RESET)
                case TypeCell.TOUCHED => Display.showSameLine(Console.RED+"● "+Console.RESET)
                case TypeCell.UNKNOWN | TypeCell.OCCUPIED => Display.showSameLine(Console.BLACK +"□ "+Console.RESET)
            }
        }

        Display.show("  A B C D E F G H I J")
        showGridTR(0, 0, grid, showCellGridShot)
        Display.show("")
    }

}
