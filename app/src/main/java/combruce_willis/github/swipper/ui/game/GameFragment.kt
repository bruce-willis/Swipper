package combruce_willis.github.swipper.ui.game

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.afollestad.materialdialogs.DialogAction
import com.afollestad.materialdialogs.MaterialDialog
import com.yuyakaido.android.cardstackview.CardStackView
import com.yuyakaido.android.cardstackview.SwipeDirection
import combruce_willis.github.swipper.R
import combruce_willis.github.swipper.R.id.cv_squares
import combruce_willis.github.swipper.data.Square
import combruce_willis.github.swipper.data.SquaresRepository
import kotlinx.android.synthetic.main.fragment_game.*

class GameFragment : Fragment(), GameFragmentView {

    private var listener: OnFragmentInteractionListener? = null
    private lateinit var presenter: GameFragmentPresenter
    private lateinit var repository: SquaresRepository
    private lateinit var adapter : CardAdapter

    private var currentTime = INITIAL_TIME
    private var maxScore = 0
    private var score = 0

    companion object {
       private const val SQUARES_STACK_SIZE = 50
       private const val EXTRA_TIME = 1
       private const val PENALTY = 2
       private const val INITIAL_TIME = 5
       fun newInstance() = GameFragment()
    }

    private var squares = mutableListOf<Square>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
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
//        cv_squares.setCardEventListener(object : CardStackView.CardEventListener {
//            override fun onCardDragging(percentX: Float, percentY: Float) {
//
//            }
//
//            override fun onCardReversed() {
//            }
//
//            override fun onCardMovedToOrigin() {
//            }
//
//            override fun onCardClicked(index: Int) {
//            }
//
//            override fun onCardSwiped(direction: SwipeDirection?) {
//
//            }
//
//
//        })
//        adapter = CardAdapter(context!!, object : TouchCallback {
//            override fun onItemTapped(square: Square) {
//
//            }
//
//        })
//        cv_squares.setAdapter(adapter)
        repository = SquaresRepository()
        presenter = GameFragmentPresenter(this, repository)
    }

    override fun onItemsReceived(squares: MutableList<Square>?) {
        Log.i("TAG","ON ITEMS RECEIVED ${squares?.size}")
        adapter.updateDataSet(squares)
    }

    override fun updateCurrentTime() {
        if (currentTime == 0) {
            presenter.onGameOver()
        } else {
            currentTime -= 1
            tv_time.text = currentTime.toString()
        }
    }

    override fun onCorrectSwipe() {
        currentTime += EXTRA_TIME
        score += 0
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
                    presenter.requestNewSquares(100)
                    presenter.setUpTimerTask()
                    currentTime = INITIAL_TIME
                    score = 0
                }
                .show()
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
    }
}
