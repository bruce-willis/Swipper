package combruce_willis.github.swipper.ui.game

import combruce_willis.github.swipper.data.Square
import combruce_willis.github.swipper.data.SquaresRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class GameFragmentPresenter(_view : GameFragmentView?,
                            private val repository : SquaresRepository) {

    private lateinit var timerDisposable : Disposable

    private var view = _view

    fun onAttach(_view: GameFragmentView?) {
        view = _view
    }

    fun requestNewSquares(quantity : Int): MutableList<Square> {
        val squares = repository.getSquares(quantity)
        view?.onItemsReceived(squares)
        return squares
    }

    fun setUpTimerTask() {
        timerDisposable = Observable.interval(1, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
                .subscribe { view?.updateCurrentTime() }

    }

    fun onGameOver() {
        timerDisposable.dispose()
        view?.onGameOver()
    }

    fun onWrongSwipe() {
        view?.onWrongSwipe()
    }

    fun onCorrectSwipe() {
        view?.onCorrectSwipe()
    }

    fun removeAndAdd() {
        view?.removeAndAdd()
    }

    fun onDetach() {
        view = null
        if (!timerDisposable.isDisposed) timerDisposable.dispose()
    }

}