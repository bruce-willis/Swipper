package combruce_willis.github.swipper.ui.game

import combruce_willis.github.swipper.data.Square

interface GameFragmentView {
    fun onItemsReceived(squares : MutableList<Square>?)
    fun updateCurrentTime()
    fun onCorrectSwipe()
    fun onWrongSwipe(penalty : Int)
    fun onGameOver()
}