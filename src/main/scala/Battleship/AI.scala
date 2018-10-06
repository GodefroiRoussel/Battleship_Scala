package Battleship

trait AI extends Player{

    override def updateInformation(): Player = {
        this.createShips(Config.TYPESHIP,
            () => random.nextInt(10),
            () => random.nextInt(10),
            () => random.nextInt(4))
    }
}
