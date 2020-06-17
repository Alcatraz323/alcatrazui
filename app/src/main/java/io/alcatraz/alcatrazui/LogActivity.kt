package io.alcatraz.alcatrazui

import android.os.Build
import android.os.Bundle
import io.alcatraz.libalcatrazui.activities.LogActivity

class LogActivity : LogActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getToolbar().setBackgroundColor(getColor(R.color.colorPrimary))
        }
    }
}