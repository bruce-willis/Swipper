package combruce_willis.github.swipper.ui.game

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import combruce_willis.github.swipper.R
import combruce_willis.github.swipper.data.Square
import android.view.LayoutInflater
import combruce_willis.github.swipper.data.Action


class CardAdapter(context: Context, _callback : GameFragment.TouchCallback) : ArrayAdapter<Square>(context, 0) {

    private var squares = mutableListOf<Square>()
    private val callback = _callback

    override fun getView(position: Int, contentView: View?, parent: ViewGroup): View {
        val holder : ViewHolder
        if (contentView == null) {
            val inflater = LayoutInflater.from(context)
            val contentView = inflater.inflate(R.layout.item_square, parent, false)
            holder = ViewHolder(contentView)
            contentView.tag = holder
        } else holder = contentView.tag as ViewHolder


        when (squares[position].action) {
            Action.UP -> holder.actionIv.setImageResource(R.drawable.ic_arrow_up_24dp)
            Action.DOWN -> holder.actionIv.setImageResource(R.drawable.ic_arrow_down_black_24dp)
            Action.LEFT -> holder.actionIv.setImageResource(R.drawable.ic_arrow_back_black_24dp)
            Action.RIGHT -> holder.actionIv.setImageResource(R.drawable.ic_arrow_right_black_24dp)
            Action.TAP -> holder.actionIv.setImageResource(R.drawable.ic_arrow_up_24dp)
        }


        holder.actionIv.setOnClickListener { callback.onItemTapped(squares[position]) }
        contentView!!.tag = holder


        return contentView!!
    }

    fun updateDataSet(squares : MutableList<Square>?) {
        if (squares != null) {
            this.squares.clear()
            this.squares.addAll(squares)
            notifyDataSetChanged()
        }
    }

    inner class ViewHolder(view: View) {
        var actionIv : ImageView = view.findViewById(R.id.iv_action)
    }

}
