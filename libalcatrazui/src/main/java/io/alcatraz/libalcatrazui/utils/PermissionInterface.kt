package io.alcatraz.libalcatrazui.utils

interface PermissionInterface {
    fun onResult(requestCode: Int, permissions: Array<String>, granted: IntArray)
}
