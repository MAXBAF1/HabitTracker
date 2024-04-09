package com.example.habitstracker.utils

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import java.io.Serializable

inline fun <reified T : Serializable> Fragment.getSerializable(key: String): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arguments?.getSerializable(key, T::class.java)
    } else {
        arguments?.getSerializable(key) as T?
    }
}

fun NavController.navigateTo(destinationId: Int, bundle: Bundle) {

}

/*
fun Drawable.toBitmap(): Bitmap? {
    if (this is BitmapDrawable) {
        return this.bitmap
    } else {
        val constantState = this.constantState ?: return null
        val drawable = constantState.newDrawable().mutate()
        val bitmap: Bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }
}
*/
