package combruce_willis.github.swipper.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import combruce_willis.github.swipper.R
import kotlinx.android.synthetic.main.fragment_launcher.*

class LauncherFragment : Fragment() {
    private var listener: OnScreenOpenListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_launcher, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        play.setOnClickListener { listener?.onGameScreenOpen() }
        tutorial.setOnClickListener { listener?.onTutorialScreenOpen() }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnScreenOpenListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnScreenOpenListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnScreenOpenListener {
        fun onGameScreenOpen()

        fun onTutorialScreenOpen()
    }

    companion object {
        @JvmStatic
        fun newInstance() = LauncherFragment()
    }
}
