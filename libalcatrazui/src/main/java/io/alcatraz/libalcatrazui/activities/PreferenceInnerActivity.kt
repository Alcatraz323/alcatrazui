package io.alcatraz.libalcatrazui.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import io.alcatraz.libalcatrazui.R
import io.alcatraz.libalcatrazui.beans.PreferenceHeader
import kotlinx.android.synthetic.main.activity_preference_inner.*

abstract class PreferenceInnerActivity : CompatWithPipeActivity() {

    abstract fun getFragment(title: String): Fragment

    fun getToolbar(): Toolbar {
        return preference_act_toolbar
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preference_inner)

        val intent = intent
        val header = intent.getParcelableExtra<PreferenceHeader>(PREFERENCE_TRANSFER_HEADER)
        preference_act_toolbar.title = header?.title
        setSupportActionBar(preference_act_toolbar)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.preference_act_fragment_container, getFragment(header?.title ?: return))
            .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val PREFERENCE_TRANSFER_HEADER = "PREFERENCE_HEADER"
    }
}
