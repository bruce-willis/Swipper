package combruce_willis.github.swipper.ui

import android.graphics.Color
import android.os.CountDownTimer
import android.support.v7.widget.CardView
import android.widget.ImageView
import com.mindorks.placeholderview.annotations.*
import com.mindorks.placeholderview.annotations.swipe.SwipeIn
import com.mindorks.placeholderview.annotations.swipe.SwipeOut
import combruce_willis.github.swipper.R
import combruce_willis.github.swipper.data.Action
import combruce_willis.github.swipper.data.Square
import combruce_willis.github.swipper.ui.game.GameFragmentPresenter

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
                //Action.DOWN -> Color.CYAN
                //Action.UP -> Color.BLUE
                    Action.RIGHT -> Color.GREEN
                    else -> Color.YELLOW
                }
        )
        actionImage.setImageResource(
                when (square.action) {
                    Action.LEFT -> R.drawable.ic_arrow_back_black_24dp
                //Action.DOWN -> R.drawable.ic_arrow_downward_black_24dp
                //Action.UP -> R.drawable.ic_arrow_upward_black_24dp
                    Action.RIGHT -> R.drawable.ic_arrow_forward_black_24dp
                    else -> R.drawable.ic_fingerprint_black_24dp
                }
        )
    }

    @Click(R.id.swipeCard)
    fun longClicked() {
        if (square.action == Action.TAP) {
            presenter.onCorrectSwipe()
        } else presenter.onWrongSwipe()
        presenter.removeAndAdd()
    }

    @SwipeOut
    fun onSwipedOut() {
        if (square.action == Action.LEFT) {
            presenter.onCorrectSwipe()
        } else presenter.onWrongSwipe()
    }

    @SwipeIn
    fun onSwipeIn() {
        if (square.action == Action.RIGHT) {
            presenter.onCorrectSwipe()
        } else presenter.onWrongSwipe()
    }
}