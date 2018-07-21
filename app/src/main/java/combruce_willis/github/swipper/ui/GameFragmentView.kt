package combruce_willis.github.swipper.ui

import combruce_willis.github.swipper.data.Square

interface GameFragmentView {
    fun onItemsRecieved(squares : List<Square>)
    fun updateCurrentTime()
    fun onCorrectSwipe()
    fun onWrongSwipe(penalty : Int)
}