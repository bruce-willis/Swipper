package combruce_willis.github.swipper.data

import java.util.*

class SquaresRepository {

    private val randomAction = Random()

    fun getSquares(quantity : Int): MutableList<Square> {
        return MutableList(quantity) {createRandomSquare()}
    }

    private fun createRandomSquare(): Square {
        return when (randomAction.nextInt(3)) {
            0 -> Square(Action.LEFT)
            1 -> Square(Action.RIGHT)
            else -> {
                Square(Action.TAP)
            }
        }
    }

}