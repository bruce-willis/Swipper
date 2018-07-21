package combruce_willis.github.swipper.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import combruce_willis.github.swipper.R
import combruce_willis.github.swipper.ui.game.GameFragment

class MainActivity : AppCompatActivity(), LauncherFragment.OnScreenOpenListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val maxScore = this.getSharedPreferences("app_prefs", Context.MODE_PRIVATE).getInt("max_score", 0)
        if (maxScore == 0) this
                .getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                .edit()
                .putInt("max_score", 0)
                .apply()
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
                .replace(R.id.main_container, fragment)
                .addToBackStack(null)
                .commit()
    }
}
