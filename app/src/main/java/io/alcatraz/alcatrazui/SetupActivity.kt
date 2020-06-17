package io.alcatraz.alcatrazui

import android.graphics.Color
import android.os.Bundle
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import io.alcatraz.alcatrazui.databinding.Setup2Binding
import io.alcatraz.libalcatrazui.activities.SetupWizardBaseActivity
import io.alcatraz.libalcatrazui.beans.SetupPage

class SetupActivity : SetupWizardBaseActivity() {
    private lateinit var permissionBinding: Setup2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        permissionBinding = Setup2Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
    }

    override fun onSetupPageInit(pages: MutableList<SetupPage>) {
        val page = SetupPage("Welcome", R.layout.setup_1)
        pages.add(page)
        getPager().addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(i: Int, v: Float, i1: Int) {}
            override fun onPageSelected(i: Int) {
                when (i) {
                    1 -> onSelectSetup2()
                    else -> restoreState()
                }
            }

            override fun onPageScrollStateChanged(i: Int) {}

        })

    }

    override fun onUpdate(pages: MutableList<SetupPage>) {
        val permissionPage = SetupPage("Permissions", R.layout.setup_2)
        permissionPage.rootView = permissionBinding.root
        pages.add(permissionPage)
        val updatePage =
            SetupPage("Current Update", R.layout.setup_3)
        pages.add(updatePage)
    }

    override fun onFinishSetup() {
        finish()
    }

    override fun getVersionCode(): Int {
        return 1
    }

    override fun specialUpdateShow(): Boolean {
        return !checkAndUpdatePermissionStatus()
    }

    override fun onResume() {
        super.onResume()
        checkAndUpdatePermissionStatus()
    }

    private fun onSelectSetup2() {
        banNextStep()
        checkAndUpdatePermissionStatus()
    }

    private fun checkAndUpdatePermissionStatus(): Boolean {
        var passAllCriticalPermission = true
        permissionBinding.setup2FloatCheckIndicator.setImageResource(R.drawable.ic_close_red_24dp)
        permissionBinding.setup2FloatCheckState.text = "Denied"
        permissionBinding.setup2FloatCheckTitle.setTextColor(Color.RED)
        passAllCriticalPermission = false
        permissionBinding.setup2FloatCheck.setOnClickListener {
            permissionBinding.setup2FloatCheckIndicator.setImageResource(R.drawable.ic_check_green_24dp)
            permissionBinding.setup2FloatCheckState.text = "Granted"
            permissionBinding.setup2FloatCheckTitle.setTextColor(Color.GREEN)
            restoreState()
        }

        return passAllCriticalPermission
    }
}