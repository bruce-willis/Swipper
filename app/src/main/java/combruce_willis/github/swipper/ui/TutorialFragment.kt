package combruce_willis.github.swipper.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import combruce_willis.github.swipper.R

class TutorialFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tutorial, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = TutorialFragment()
    }
}
