package io.alcatraz.libalcatrazui.activities

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Vibrator
import android.view.MenuItem
import android.widget.AdapterView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import io.alcatraz.libalcatrazui.Easter
import io.alcatraz.libalcatrazui.R
import io.alcatraz.libalcatrazui.adapters.AuthorAdapter
import io.alcatraz.libalcatrazui.adapters.QueryElementAdapter
import io.alcatraz.libalcatrazui.beans.AuthorElement
import io.alcatraz.libalcatrazui.beans.QueryElement
import io.alcatraz.libalcatrazui.databinding.DialogOpsBinding
import io.alcatraz.libalcatrazui.utils.PackageCtlUtils
import kotlinx.android.synthetic.main.activity_about.*
import java.util.*

abstract class AboutActivity : CompatWithPipeActivity() {
    private var imgs: MutableList<Int> = ArrayList()
    private lateinit var data: MutableList<AuthorElement>
    private lateinit var vibrator: Vibrator
    private lateinit var easter: Easter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        initialize()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    abstract fun getAuthor(): String

    abstract fun getDetailedAuthor(): String

    abstract fun getOpenSourceUrl(): String

    abstract fun getEasterUrl(): String

    abstract fun getOpenSourceProjects(): MutableList<QueryElement>

    abstract fun getAppName(): String

    abstract fun getAppIcon(): Drawable?

    abstract fun getEasterMorse(): IntArray?

    open fun onVersionClick() {}

    open fun onVersionLongClick() {}

    fun getToolbar(): Toolbar {
        return about_toolbar
    }

    private fun initialize() {
        easter = Easter(this, getEasterUrl())
        easter.setMorse(getEasterMorse() ?: Easter.defaultArr)
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        initData()
        initViews()
    }

    private fun initViews() {
        setSupportActionBar(about_toolbar)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val authorAdapter = AuthorAdapter(this, data, imgs)
        about_listview.adapter = authorAdapter
        about_listview.onItemClickListener = AdapterView.OnItemClickListener { _, view, i, _ ->
            when (i) {
                0 -> {
                    if (vibrator.hasVibrator()) {
                        view.post {
                            onVersionClick()
                        }
                    }
                    easter.shortClick()
                }
                1 -> showDetailDev()
                2 -> startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(getOpenSourceUrl())
                    )
                )
                3 -> showOpenSourceProjectsDialog()
            }
        }

        about_listview.onItemLongClickListener =
            AdapterView.OnItemLongClickListener { _, view, i, _ ->
                if (i == 0) {
                    view.post {
                        onVersionLongClick()
                    }
                }
                true
            }

        about_app_icon.setImageDrawable(getAppIcon())
        about_app_name.text = getAppName()
    }

    private fun showOpenSourceProjectsDialog() {
        val ospBindings: DialogOpsBinding = DialogOpsBinding.inflate(layoutInflater)
        AlertDialog.Builder(this)
            .setTitle(R.string.au_osp)
            .setView(ospBindings.root)
            .setNegativeButton(R.string.ad_nb3, null).show()
        val data = getOpenSourceProjects()
        val adapter = QueryElementAdapter(this, data)
        ospBindings.opsRecycler.layoutManager = LinearLayoutManager(this)
        ospBindings.opsRecycler.itemAnimator = DefaultItemAnimator()
        ospBindings.opsRecycler.adapter = adapter
    }

    private fun showDetailDev() {
        AlertDialog.Builder(this)
            .setTitle(R.string.au_l_2)
            .setMessage(getDetailedAuthor())
            .setPositiveButton(R.string.ad_pb, null)
            .show()
    }

    private fun initData() {
        imgs.add(R.drawable.ic_info_outline_black_24dp)
        imgs.add(R.drawable.ic_account_circle_black_24dp)
        imgs.add(R.drawable.ic_code_black_24dp)
        imgs.add(R.drawable.ic_open_in_new_black_24dp)
        data = mutableListOf(
            AuthorElement(getString(R.string.au_l_1), PackageCtlUtils.getVersionName(this)!!),
            AuthorElement(getString(R.string.au_l_2), getAuthor()),
            AuthorElement(getString(R.string.au_l_3), ""),
            AuthorElement(getString(R.string.au_l_4), getString(R.string.au_l_4_1))
        )

    }
}