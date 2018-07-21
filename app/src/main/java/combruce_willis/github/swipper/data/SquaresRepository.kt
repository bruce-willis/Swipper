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
        return when (randomAction.nextInt(5)) {
            0 -> Square(Action.LEFT)
            1 -> Square(Action.RIGHT)
            2 -> Square(Action.DOWN)
            3 -> Square(Action.UP)
            else -> {
                Square(Action.TAP)
            }
        }
    }

}