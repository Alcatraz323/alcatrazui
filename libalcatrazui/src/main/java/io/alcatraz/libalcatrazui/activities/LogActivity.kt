package io.alcatraz.libalcatrazui.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import io.alcatraz.libalcatrazui.LogBuff
import io.alcatraz.libalcatrazui.R

import kotlinx.android.synthetic.main.activity_log.*

open class LogActivity : CompatWithPipeActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log)
        initViews()
        initData()
    }

    fun getToolbar(): Toolbar {
        return log_toolbar
    }

    private fun initViews() {
        setSupportActionBar(log_toolbar)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun initData() {
        log_console_box.text = LogBuff.finalLog
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = MenuInflater(this)
        menuInflater.inflate(R.menu.activity_log_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.menu_log_refresh -> initData()
        }
        return super.onOptionsItemSelected(item)
    }
}
