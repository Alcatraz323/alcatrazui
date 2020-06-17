package io.alcatraz.libalcatrazui.utils

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build.VERSION
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.drawerlayout.widget.DrawerLayout

object StatusBarUtil {
    const val DEFAULT_STATUS_BAR_ALPHA = 70
    private fun addTranslucentView(var0: Activity, var1: Int) {
        val var2 = var0.findViewById<View>(android.R.id.content) as ViewGroup
        if (var2.childCount > 1) {
            var2.removeViewAt(1)
        }
        var2.addView(createTranslucentStatusBarView(var0, var1))
    }

    private fun calculateStatusColor(var0: Int, var1: Int): Int {
        val var2 = 1.toFloat() - var1.toFloat() / 255.0f
        val var3 = 255 and var0 shr 16
        val var4 = 255 and var0 shr 8
        val var5 = var0 and 255
        val var6 = (0.5 + (var2 * var3.toFloat()).toDouble()).toInt()
        val var7 = (0.5 + (var2 * var4.toFloat()).toDouble()).toInt()
        return (0.5 + (var2 * var5.toFloat()).toDouble()).toInt() or -16777216 or (var6 shl 16) or (var7 shl 8)
    }

    private fun createStatusBarView(var0: Activity, var1: Int): View {
        val var2 = View(var0)
        var2.layoutParams = LinearLayout.LayoutParams(-1, getStatusBarHeight(var0))
        var2.setBackgroundColor(var1)
        return var2
    }

    private fun createStatusBarView(var0: Activity, var1: Int, var2: Int): View {
        val var3 = View(var0)
        var3.layoutParams = LinearLayout.LayoutParams(-1, getStatusBarHeight(var0))
        var3.setBackgroundColor(calculateStatusColor(var1, var2))
        return var3
    }

    private fun createTranslucentStatusBarView(var0: Activity, var1: Int): View {
        val var2 = View(var0)
        var2.layoutParams = LinearLayout.LayoutParams(-1, getStatusBarHeight(var0))
        var2.setBackgroundColor(Color.argb(var1, 0, 0, 0))
        return var2
    }

    private fun getStatusBarHeight(var0: Context): Int {
        val var1 = var0.resources.getIdentifier("status_bar_height", "dimen", "android")
        return var0.resources.getDimensionPixelSize(var1)
    }

    fun setColor(var0: Activity, var1: Int) {
        setColor(var0, var1, 70)
    }

    fun setColor(var0: Activity, var1: Int, var2: Int) {
        if (VERSION.SDK_INT >= 21) {
            var0.window.addFlags(-2147483648)
            var0.window.clearFlags(67108864)
            var0.window.statusBarColor = calculateStatusColor(var1, var2)
        } else if (VERSION.SDK_INT >= 19) {
            var0.window.addFlags(67108864)
            val var3 = createStatusBarView(var0, var1, var2)
            (var0.window.decorView as ViewGroup).addView(var3)
            setRootView(var0)
            return
        }
    }

    fun setColorDiff(var0: Activity, var1: Int) {
        if (VERSION.SDK_INT >= 19) {
            var0.window.addFlags(67108864)
            val var2 = createStatusBarView(var0, var1)
            (var0.window.decorView as ViewGroup).addView(var2)
            setRootView(var0)
        }
    }

    fun setColorForDrawerLayout(var0: Activity, var1: DrawerLayout, var2: Int) {
        setColorForDrawerLayout(var0, var1, var2, 70)
    }

    fun setColorForDrawerLayout(
        var0: Activity,
        var1: DrawerLayout,
        var2: Int,
        var3: Int
    ) {
        if (VERSION.SDK_INT >= 19) {
            if (VERSION.SDK_INT >= 21) {
                var0.window.addFlags(-2147483648)
                var0.window.clearFlags(67108864)
                var0.window.statusBarColor = 0
            } else {
                var0.window.addFlags(67108864)
            }
            val var4 = createStatusBarView(var0, var2)
            val var5 = var1.getChildAt(0) as ViewGroup
            var5.addView(var4, 0)
            if (var5 !is LinearLayout && var5.getChildAt(1) != null) {
                var5.getChildAt(1).setPadding(0, getStatusBarHeight(var0), 0, 0)
            }
            val var6 = var1.getChildAt(1) as ViewGroup
            var1.setFitsSystemWindows(false)
            var5.fitsSystemWindows = false
            var5.clipToPadding = true
            var6.fitsSystemWindows = false
            addTranslucentView(var0, var3)
        }
    }

    fun setColorForDrawerLayoutDiff(var0: Activity, var1: DrawerLayout, var2: Int) {
        if (VERSION.SDK_INT >= 19) {
            var0.window.addFlags(67108864)
            val var3 = createStatusBarView(var0, var2)
            val var4 = var1.getChildAt(0) as ViewGroup
            var4.addView(var3, 0)
            if (var4 !is LinearLayout && var4.getChildAt(1) != null) {
                var4.getChildAt(1).setPadding(0, getStatusBarHeight(var0), 0, 0)
            }
            val var5 = var1.getChildAt(1) as ViewGroup
            var1.fitsSystemWindows = false
            var4.fitsSystemWindows = false
            var4.clipToPadding = true
            var5.fitsSystemWindows = false
        }
    }

    fun setColorNoTranslucent(var0: Activity, var1: Int) {
        setColor(var0, var1, 0)
    }

    fun setColorNoTranslucentForDrawerLayout(
        var0: Activity,
        var1: DrawerLayout,
        var2: Int
    ) {
        setColorForDrawerLayout(var0, var1, var2, 0)
    }

    private fun setRootView(var0: Activity) {
        val var1 =
            (var0.findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup
        var1.fitsSystemWindows = true
        var1.clipToPadding = true
    }

    fun setTranslucent(var0: Activity) {
        setTranslucent(var0, 70)
    }

    fun setTranslucent(var0: Activity, var1: Int) {
        if (VERSION.SDK_INT >= 19) {
            setTransparent(var0)
            addTranslucentView(var0, var1)
        }
    }

    fun setTranslucentDiff(var0: Activity) {
        if (VERSION.SDK_INT >= 19) {
            var0.window.addFlags(67108864)
            setRootView(var0)
        }
    }

    fun setTranslucentForDrawerLayout(var0: Activity, var1: DrawerLayout) {
        setTranslucentForDrawerLayout(var0, var1, 70)
    }

    fun setTranslucentForDrawerLayout(var0: Activity, var1: DrawerLayout, var2: Int) {
        if (VERSION.SDK_INT >= 19) {
            setTransparentForDrawerLayout(var0, var1)
            addTranslucentView(var0, var2)
        }
    }

    fun setTranslucentForDrawerLayoutDiff(var0: Activity, var1: DrawerLayout) {
        if (VERSION.SDK_INT >= 19) {
            var0.window.addFlags(67108864)
            val var2 = var1.getChildAt(0) as ViewGroup
            var2.fitsSystemWindows = true
            var2.clipToPadding = true
            (var1.getChildAt(1) as ViewGroup).fitsSystemWindows = false
            var1.setFitsSystemWindows(false)
        }
    }

    fun setTransparent(var0: Activity) {
        if (VERSION.SDK_INT >= 19) {
            transparentStatusBar(var0)
            setRootView(var0)
        }
    }

    fun setTransparentForDrawerLayout(var0: Activity, var1: DrawerLayout) {
        if (VERSION.SDK_INT >= 19) {
            if (VERSION.SDK_INT >= 21) {
                var0.window.addFlags(-2147483648)
                var0.window.clearFlags(67108864)
                var0.window.statusBarColor = 0
            } else {
                var0.window.addFlags(67108864)
            }
            val var2 = var1.getChildAt(0) as ViewGroup
            if (var2 !is LinearLayout && var2.getChildAt(1) != null) {
                var2.getChildAt(1).setPadding(0, getStatusBarHeight(var0), 0, 0)
            }
            val var3 = var1.getChildAt(1) as ViewGroup
            var1.setFitsSystemWindows(false)
            var2.fitsSystemWindows = false
            var2.clipToPadding = true
            var3.fitsSystemWindows = false
        }
    }

    @TargetApi(19)
    private fun transparentStatusBar(var0: Activity) {
        if (VERSION.SDK_INT >= 21) {
            var0.window.addFlags(-2147483648)
            var0.window.clearFlags(67108864)
            var0.window.addFlags(134217728)
            var0.window.statusBarColor = 0
        } else {
            var0.window.addFlags(67108864)
        }
    }
}