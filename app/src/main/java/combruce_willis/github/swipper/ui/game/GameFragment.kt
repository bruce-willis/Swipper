package combruce_willis.github.swipper.ui.game

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import combruce_willis.github.swipper.R
import combruce_willis.github.swipper.data.Square
import combruce_willis.github.swipper.data.SquaresRepository
import combruce_willis.github.swipper.ui.Card
import kotlinx.android.synthetic.main.fragment_game.*

class GameFragment : Fragment(), GameFragmentView {

    private var listener: OnFragmentInteractionListener? = null
    private lateinit var presenter: GameFragmentPresenter
    private lateinit var repository: SquaresRepository

    private var currentTime = INITIAL_TIME
    private var maxScore = 0

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
        repository = SquaresRepository()
        presenter = GameFragmentPresenter(this, repository)
        squares = presenter.requestNewSquares(SQUARES_STACK_SIZE)

//        swipeView!!.builder
//                .setDisplayViewCount(4)


        for (square in squares) {
            swipeView!!.addView(Card(square, presenter))
        }
    }


    override fun onItemsRecieved(squares: List<Square>) {

    }

    override fun updateCurrentTime() {
        currentTime -= 1
    }

    override fun onCorrectSwipe() {
        currentTime += EXTRA_TIME
    }

    override fun onWrongSwipe(penalty: Int) {
        currentTime -= penalty
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
}
