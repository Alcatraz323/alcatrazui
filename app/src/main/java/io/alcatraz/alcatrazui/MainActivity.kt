package io.alcatraz.alcatrazui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import io.alcatraz.libalcatrazui.LogBuff
import io.alcatraz.libalcatrazui.activities.CompatWithPipeActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : CompatWithPipeActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        LogBuff.E("Test")
        LogBuff.D("Test")
        LogBuff.I("Test")
        LogBuff.W("Test")
        LogBuff.log("Test")
        LogBuff.addDivider()

        overlay_interactive_1.setShowOverlay(true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_setup -> startActivity(Intent(this,SetupActivity::class.java))
            R.id.action_about -> startTransition(Intent(this,AboutActivity::class.java))
            R.id.action_log -> startActivity(Intent(this,LogActivity::class.java))
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }
}
