package god.treeview.utils

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast

fun String.showToast(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}

// 通过反射调用ActivityThread中的currentApplication方法来得到全局Application对象
fun getApplication(): Application? {
    var application: Application? = null
    try {
        val method =
            Class.forName("android.app.ActivityThread")
                .getDeclaredMethod("currentApplication")
        application = method.invoke(null, null) as Application
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return application
}

var DEBUG_UTILS = true
fun String.V() {
    if (DEBUG_UTILS) {
        Log.v("程序员", this)
    }
}

fun String.D() {
    if (DEBUG_UTILS) {
        Log.d("程序员", this)
    }
}

fun String.I() {
    if (DEBUG_UTILS) {
        Log.i("程序员", this)
    }
}

fun String.W() {
    if (DEBUG_UTILS) {
        Log.w("程序员", this)
    }
}

fun String.E() {
    if (DEBUG_UTILS) {
        Log.e("程序员", this)
    }
}