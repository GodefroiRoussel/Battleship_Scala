package Battleship.Players.AIs

import Battleship.Config
import Battleship.Players.Player

trait AI extends Player{

    override def updateInformation(): Player = {
        this.createShips(Config.TYPESHIP,
            () => random.nextInt(10),
            () => random.nextInt(10),
            () => random.nextInt(4))
    }
}
