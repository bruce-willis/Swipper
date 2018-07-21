package combruce_willis.github.swipper.data

import java.util.*

class SquaresRepository {

    private val randomAction = Random()

    fun getSquares(quantity : Int): MutableList<Square> {
        val squares = mutableListOf<Square>()
        for (i in 1..quantity) {
            squares.add(createRandomSquare())
        }
        return squares
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