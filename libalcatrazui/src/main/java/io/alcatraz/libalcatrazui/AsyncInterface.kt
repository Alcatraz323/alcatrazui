package io.alcatraz.libalcatrazui

interface AsyncInterface<T> {
    fun onDone(result: T)
}
