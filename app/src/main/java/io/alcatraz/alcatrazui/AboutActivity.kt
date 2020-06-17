package io.alcatraz.alcatrazui

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import io.alcatraz.libalcatrazui.activities.AboutActivity
import io.alcatraz.libalcatrazui.beans.QueryElement

class AboutActivity : AboutActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getToolbar().setBackgroundColor(getColor(R.color.colorPrimary))
        }
    }

    override fun getAuthor(): String {
        return "Alcatraz"
    }

    override fun getDetailedAuthor(): String {
        return "Coder :Alcatraz\nSomething else..."
    }

    override fun getOpenSourceUrl(): String {
        return "https://github.com/Alcatraz323/alcatrazui"
    }

    override fun getEasterUrl(): String {
        return "https://github.com/Alcatraz323/alcatrazui"
    }

    override fun getOpenSourceProjects(): MutableList<QueryElement> {
        return mutableListOf(
            QueryElement(
                "Alcatraz323",
                "audiohq_md2",
                "https://github.com/Alcatraz323/audiohq_md2",
                "MIT",
                "A tool to control android application volume individually, TRUE adjusting not auto adjust script, for native and more information check the website"
            ),
            QueryElement(
                "Alcatraz323",
                "afkprotect",
                "https://github.com/Alcatraz323/afkprotect",
                "Apache 2.0",
                "A tool to control android application volume individually, TRUE adjusting not auto adjust script, for native and more information check the website"
            )
        )
    }

    override fun getAppName(): String {
        return "AlcatrazUI"
    }

    override fun onVersionClick() {
        super.onVersionClick()
    }

    override fun getAppIcon(): Drawable? {
        return getDrawable(R.mipmap.ic_launcher)
    }

    override fun getEasterMorse(): IntArray? {
        return null
    }
}