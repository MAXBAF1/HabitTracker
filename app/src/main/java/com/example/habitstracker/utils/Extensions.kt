package com.example.habitstracker.utils

import android.os.Build
import androidx.fragment.app.Fragment
import com.example.habitstracker.constance.Constance
import java.io.Serializable

inline fun <reified T : Serializable> Fragment.getSerializable(key: String): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arguments?.getSerializable(key, T::class.java)
    } else {
        arguments?.getSerializable(key) as T?
    }
}