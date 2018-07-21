package combruce_willis.github.swipper.ui.game

import android.content.Context
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.afollestad.materialdialogs.DialogAction
import com.afollestad.materialdialogs.MaterialDialog
import com.mindorks.placeholderview.SwipeDecor
import com.mindorks.placeholderview.SwipePlaceHolderView.SWIPE_TYPE_HORIZONTAL
import com.yuyakaido.android.cardstackview.CardStackView
import com.yuyakaido.android.cardstackview.SwipeDirection
import combruce_willis.github.swipper.R
import combruce_willis.github.swipper.data.Action
import combruce_willis.github.swipper.data.Square
import combruce_willis.github.swipper.data.SquaresRepository
import combruce_willis.github.swipper.ui.view.Card
import kotlinx.android.synthetic.main.fragment_game.*

class GameFragment : Fragment(), GameFragmentView {

    private var listener: OnFragmentInteractionListener? = null
    private lateinit var presenter: GameFragmentPresenter
    private lateinit var repository: SquaresRepository

    private var currentTime = INITIAL_TIME
    private var maxScore = 0
    private var score = 0

    companion object {
       private const val SQUARES_STACK_SIZE = 25
       private const val EXTRA_TIME = 1
       private const val PENALTY = 2
       private const val INITIAL_TIME = 5
       fun newInstance() = GameFragment()
    }

    private var squares = mutableListOf<Square>()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            //throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }

        maxScore = context
                .getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                .getInt("max_score", 0)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeView.builder
                .setSwipeType(SWIPE_TYPE_HORIZONTAL)
                .setSwipeHorizontalThreshold(1)
                .setWidthSwipeDistFactor(0.1f)
                .setSwipeDecor(SwipeDecor()
                        .setSwipeAnimFactor(0.1f)
                        .setSwipeAnimTime(0)
                        .setSwipeMaxChangeAngle(0f)
                        .isAnimateScale(true))
        repository = SquaresRepository()
        presenter = GameFragmentPresenter(this, repository)
        presenter.requestNewSquares(SQUARES_STACK_SIZE)
    }



    override fun onItemsReceived(squares: MutableList<Square>?) {
        squares?.forEach { swipeView.addView(Card(it, object : GameFragment.TouchCallback {
            override fun onItemTapped(square: Square) {
                if (it.action == Action.TAP)
                    presenter.onCorrectSwipe() else presenter.onWrongSwipe(PENALTY)
                swipeView.doSwipe(true)
            }

            override fun onItemSwipedLeft(square: Square) {
                if (it.action == Action.LEFT)
                    presenter.onCorrectSwipe() else presenter.onWrongSwipe(PENALTY)
            }

            override fun onItemSwipedRight(square: Square) {
                if (it.action == Action.RIGHT)
                    presenter.onCorrectSwipe() else presenter.onWrongSwipe(PENALTY)
            }

            override fun onItemSwipedUp(square: Square) {
                if (it.action == Action.UP)
                    presenter.onCorrectSwipe() else presenter.onWrongSwipe(PENALTY)
            }

            override fun onItemSwipedDown(square: Square) {
                if (it.action == Action.DOWN)
                    presenter.onCorrectSwipe() else presenter.onWrongSwipe(PENALTY)
            }

        }) )}
    }

    override fun updateCurrentTime() {
        if (currentTime <= 0) {
            presenter.onGameOver()
        } else {
            currentTime -= 1
            tv_time.text = currentTime.toString()
        }
    }

    override fun onCorrectSwipe() {
        currentTime += EXTRA_TIME
        score += 1
        tv_score.text = "Счет: " + score
        if (score > maxScore) maxScore = score
    }

    override fun onWrongSwipe(penalty: Int) {
        currentTime -= penalty
    }

    override fun onGameOver() {
        MaterialDialog.Builder(context!!)
                .title(R.string.game_over)
                .content("Вы проиграли! :( Ваш счет: $score")
                .positiveText("Пропробовать снова")
                .onPositive { dialog, which ->
                    score = 0
                    presenter.requestNewSquares(SQUARES_STACK_SIZE)
                    presenter.setUpTimerTask()
                    currentTime = INITIAL_TIME
                    score = 0
                }
                .show()
        activity?.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)?.edit()?.putInt("max_score", maxScore)?.apply()
    }

    override fun onResume() {
        super.onResume()
        presenter.onAttach(this)
        presenter.setUpTimerTask()
    }


    override fun onDetach() {
        super.onDetach()
        listener = null
        presenter.onDetach()
    }

    private interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    interface TouchCallback {
        fun onItemTapped(square : Square)
        fun onItemSwipedLeft(square: Square)
        fun onItemSwipedRight(square: Square)
        fun onItemSwipedUp(square: Square)
        fun onItemSwipedDown(square: Square)
    }
}
