package combruce_willis.github.swipper.ui.view

import android.graphics.Color
import android.support.v7.widget.CardView
import android.widget.ImageView
import com.mindorks.placeholderview.annotations.*
import com.mindorks.placeholderview.annotations.swipe.SwipeIn
import com.mindorks.placeholderview.annotations.swipe.SwipeOut
import combruce_willis.github.swipper.R
import combruce_willis.github.swipper.data.Action
import combruce_willis.github.swipper.data.Square
import combruce_willis.github.swipper.ui.game.GameFragment
import combruce_willis.github.swipper.ui.game.GameFragmentPresenter

@Layout(R.layout.view_square)
class Card(private val square: Square, var callback : GameFragment.TouchCallback) {

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
                    Action.RIGHT -> R.drawable.ic_arrow_right_black_24dp
                    else -> R.drawable.ic_fingerprint_24dp
                }
        )
    }

    @Click(R.id.swipeCard)
    fun longClicked() {
        callback.onItemTapped(square)
    }

    @SwipeOut
    fun onSwipedOut() {
        callback.onItemSwipedLeft(square)
    }

    @SwipeIn
    fun onSwipeIn() {
        callback.onItemSwipedRight(square)
    }
}