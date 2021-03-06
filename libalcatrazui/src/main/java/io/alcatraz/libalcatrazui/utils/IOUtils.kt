package io.alcatraz.libalcatrazui.utils

import io.alcatraz.libalcatrazui.LogBuff
import java.io.File

object IOUtils {
    fun write(dir: String, content: String) {
        val file = File(dir)
        if (!file.exists()) {
            try {
                file.parentFile?.mkdirs()
                file.createNewFile()
            } catch (ignored: Exception) {
            }
        }
        file.writeText(content)
    }

    fun read(dir: String, rm: ReadMonitor?): String {
        var content = ""
        val file = File(dir)
        if (!file.exists()) {
            try {
                file.parentFile?.mkdirs()
                file.createNewFile()
                return content
            } catch (e: Exception) {
                LogBuff.E(e.message ?: "")
            }
        }
        file.forEachLine {
            content += "\n"
            content += it
            rm?.onLine(it)
        }
        return content
    }

    fun renameFile(oldPath: String, newPath: String) {
        val oleFile = File(oldPath)
        val newFile = File(newPath)
        oleFile.renameTo(newFile)
    }

    fun delete(path: String): Boolean {
        val file = File(path)
        if (!file.exists()) {
            return false
        }
        if (file.isFile) {
            return file.delete()
        }
        val files = file.listFiles()
        for (f in files ?: return false) {
            if (f.isFile) {
                if (!f.delete()) {
                    return false
                }
            } else {
                if (!delete(f.absolutePath)) {
                    return false
                }
            }
        }
        return file.delete()
    }

    interface ReadMonitor {
        fun onLine(line: String)

        fun callFinish()
    }
}