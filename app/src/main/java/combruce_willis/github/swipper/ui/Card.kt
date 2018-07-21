package combruce_willis.github.swipper.ui

import android.graphics.Color
import android.support.v7.widget.CardView
import android.util.Log
import android.widget.ImageView
import com.mindorks.placeholderview.SwipeDirection
import com.mindorks.placeholderview.annotations.Layout
import com.mindorks.placeholderview.annotations.Resolve
import com.mindorks.placeholderview.annotations.View
import com.mindorks.placeholderview.annotations.swipe.*
import combruce_willis.github.swipper.R
import combruce_willis.github.swipper.data.Action
import combruce_willis.github.swipper.data.Square
import combruce_willis.github.swipper.ui.game.GameFragmentPresenter
import kotlin.math.sqrt

@Layout(R.layout.card_view)
class Card(private val square: Square, private val presenter: GameFragmentPresenter) {

    @View(R.id.swipeCard)
    lateinit var swipeCard: CardView

    @View(R.id.actionImage)
    lateinit var actionImage: ImageView

    @Resolve
    fun onResolved() {
        swipeCard.setCardBackgroundColor(
                when (square.action) {
                    Action.LEFT -> Color.RED
                    Action.DOWN -> Color.CYAN
                    Action.UP -> Color.BLUE
                    Action.RIGHT -> Color.GREEN
                    else -> Color.YELLOW
                }
        )
        actionImage.setImageResource(
                when (square.action) {
                    Action.LEFT -> R.drawable.ic_arrow_back_black_24dp
                    Action.DOWN -> R.drawable.ic_arrow_downward_black_24dp
                    Action.UP -> R.drawable.ic_arrow_upward_black_24dp
                    Action.RIGHT -> R.drawable.ic_arrow_forward_black_24dp
                    else -> R.drawable.ic_fingerprint_black_24dp
                }
        )
    }

    @SwipeOut
    fun onSwipedOut() {
        Log.d("DEBUG", "onSwipedOut")
    }

    @SwipeCancelState
    fun onSwipeCancelState() {
        Log.d("DEBUG", "onSwipeCancelState")
    }

    @SwipeIn
    fun onSwipeIn() {
        Log.d("DEBUG", "onSwipedIn")
    }

    @SwipeInState
    fun onSwipeInState() {
        Log.d("DEBUG", "onSwipeInState")
    }

    @SwipeOutState
    fun onSwipeOutState() {
        Log.d("DEBUG", "onSwipeOutState")
    }

    @SwipeOutDirectional
    fun onSwipeOutDirectional(direction: SwipeDirection) {
        Log.d("DEBUG", "SwipeOutDirectional " + direction.name)
    }


    @SwipeInDirectional
    fun onSwipeInDirectional(direction: SwipeDirection) {
        Log.d("DEBUG", "SwipeInDirectional " + direction.name)
    }

    @SwipingDirection
    fun onSwipingDirection(direction: SwipeDirection) {
        Log.d("DEBUG", "SwipingDirection " + direction.name)
    }


    @SwipeTouch
    fun onSwipeTouch(xStart: Float, yStart: Float, xCurrent: Float, yCurrent: Float) {
        Log.d("DEBUG", "onSwipeTouch "
                + " xStart : " + xStart
                + " yStart : " + yStart
                + " xCurrent : " + xCurrent
                + " yCurrent : " + yCurrent
        )
    }

}