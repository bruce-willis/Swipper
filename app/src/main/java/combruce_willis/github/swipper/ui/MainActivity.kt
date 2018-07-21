package combruce_willis.github.swipper.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import combruce_willis.github.swipper.R

class MainActivity : AppCompatActivity(), LauncherFragment.OnScreenOpenListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        changeFragment(LauncherFragment.newInstance())
    }

    override fun onGameScreenOpen() {
        changeFragment(GameFragment.newInstance())
    }

    override fun onTutorialScreenOpen() {
        changeFragment(TutorialFragment.newInstance())
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit()
    }
}
