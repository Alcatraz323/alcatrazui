package io.alcatraz.libalcatrazui.utils

import android.annotation.TargetApi
import android.app.Activity
import android.graphics.Color
import android.os.Build.VERSION
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout

object DrawerLayoutUtil {
    const val DEFAULT_ALPHA = 70
    fun immersive(var0: Toolbar, var1: Boolean, var2: Activity) {
        val var3 = var2.resources.getIdentifier("status_bar_height", "dimen", "android")
        val var4 = var2.resources.getDimensionPixelSize(var3)
        val var5: Int = var0.paddingTop
        val var6: Int = var0.paddingBottom
        val var7: Int = var0.paddingLeft
        val var8: Int = var0.paddingRight
        val var9: ViewGroup.LayoutParams = var0.layoutParams
        val var10 = var9.height
        if (VERSION.SDK_INT >= 19) {
            setTranslucentStatus(var2, true)
            val var11: Int
            val var12: Int
            if (var1) {
                var11 = var5 + var4
                var12 = var4 + var10
            } else {
                var11 = var5 - var4
                var12 = var10 - var4
            }
            var9.height = var12
            var0.setPadding(var7, var11, var8, var6)
        }
    }

    fun setImmersiveToolbarWithDrawer(
        var0: Toolbar,
        var1: DrawerLayout,
        var2: Activity,
        var3: View,
        var4: String?,
        var5: Int
    ) {
        setImmersiveToolbarWithDrawer(var0, var1, var2, var3, var4, var5, true, 70)
    }

    fun setImmersiveToolbarWithDrawer(
        var0: Toolbar,
        var1: DrawerLayout,
        var2: Activity,
        var3: View,
        var4: String?,
        var5: Int,
        var6: Boolean,
        var7: Int
    ) {
        val var8 = var2.resources.getIdentifier("status_bar_height", "dimen", "android")
        val var9 = var2.resources.getDimensionPixelSize(var8)
        if (var5 >= 19) {
            var3.layoutParams = LinearLayout.LayoutParams(-1, var9)
            var3.setBackgroundColor(Color.parseColor(var4))
        }
        var0.setBackgroundColor(Color.parseColor(var4))
        if (var6) {
            val var10 = ActionBarDrawerToggle(var2, var1, var0, 0, 0)
            var10.syncState()
            var1.setDrawerListener(var10)
        }
        StatusBarUtil.setTranslucentForDrawerLayout(var2, var1, var7)
    }

    @TargetApi(19)
    private fun setTranslucentStatus(var0: Activity, var1: Boolean) {
        val var2 = var0.window
        val var3 = var2.attributes
        if (var1) {
            var3.flags = var3.flags or 67108864
        } else {
            var3.flags = var3.flags and -67108865
        }
        var2.attributes = var3
    }
}