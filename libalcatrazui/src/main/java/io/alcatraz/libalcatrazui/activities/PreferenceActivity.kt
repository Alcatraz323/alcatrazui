package io.alcatraz.libalcatrazui.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.AdapterView
import androidx.appcompat.widget.Toolbar
import io.alcatraz.libalcatrazui.R
import io.alcatraz.libalcatrazui.adapters.PreferenceListAdapter
import io.alcatraz.libalcatrazui.beans.PreferenceHeader
import kotlinx.android.synthetic.main.activity_preference.*


abstract class PreferenceActivity : CompatWithPipeActivity() {
    private lateinit var adapter: PreferenceListAdapter

    abstract fun prepareHeader(): MutableList<PreferenceHeader>

    fun getToolbar(): Toolbar {
        return preference_act_toolbar
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preference)
        initViews()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        sendBroadcast(Intent().setAction("lib_alc_update_preferences"))
    }

    private fun initViews() {
        setSupportActionBar(preference_act_toolbar)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        adapter = PreferenceListAdapter(this, prepareHeader())
        preference_act_list.adapter = adapter

        preference_act_list.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, _, i, _ ->
                val intent = Intent(this@PreferenceActivity, PreferenceInnerActivity::class.java)
                intent.putExtra(
                    PreferenceInnerActivity.PREFERENCE_TRANSFER_HEADER,
                    adapterView.getItemAtPosition(i) as PreferenceHeader
                )
                this@PreferenceActivity.startActivity(intent)
            }
    }
}
