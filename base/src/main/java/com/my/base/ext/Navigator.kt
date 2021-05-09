package com.my.base.ext

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.FragmentActivity
import com.my.base.util.ContextUtils
import java.io.Serializable

/**
 * 跳转封装
 * @author caishuzhan
 */
object NavigatorExt {
    /**
     * @param ctx    可以Activity或者Application的Context,如不指定则默认是Application Context
     * @param params 传给Activity参数, 如"key" to value的键值对形式
     * @param flags  打开Activity的flag
     */
    inline fun <reified T : FragmentActivity> goto(
        ctx: Context = ContextUtils.getContext().applicationContext,
        vararg params: Pair<String, Any?>,
        flags: Int = 0,
        bundle: Bundle? = null
    ) {
        val composedFlags =
            if (ctx == ContextUtils.getContext().applicationContext) (FLAG_ACTIVITY_NEW_TASK or flags) else flags
        val intent =
            ctx.intentFor<T>(*params).apply {
                setFlags(composedFlags)
                bundle?.let {
                    putExtras(it)
                }
            }
        ctx.startActivity(intent)
    }
}

object Internals {

    @JvmStatic
    fun <T> createIntent(
        ctx: Context,
        clazz: Class<out T>,
        params: Array<out Pair<String, Any?>>
    ): Intent {
        val intent = Intent(ctx, clazz)
        if (params.isNotEmpty()) fillIntentArguments(intent, params)
        return intent
    }

    @JvmStatic
    private fun fillIntentArguments(intent: Intent, params: Array<out Pair<String, Any?>>) {
        params.forEach {
            val value = it.second
            when (value) {
                null -> intent.putExtra(it.first, null as Serializable?)
                is Int -> intent.putExtra(it.first, value)
                is Long -> intent.putExtra(it.first, value)
                is CharSequence -> intent.putExtra(it.first, value)
                is String -> intent.putExtra(it.first, value)
                is Float -> intent.putExtra(it.first, value)
                is Double -> intent.putExtra(it.first, value)
                is Char -> intent.putExtra(it.first, value)
                is Short -> intent.putExtra(it.first, value)
                is Boolean -> intent.putExtra(it.first, value)
                is Bundle -> intent.putExtra(it.first, value)
                is IntArray -> intent.putExtra(it.first, value)
                is LongArray -> intent.putExtra(it.first, value)
                is FloatArray -> intent.putExtra(it.first, value)
                is DoubleArray -> intent.putExtra(it.first, value)
                is CharArray -> intent.putExtra(it.first, value)
                is ShortArray -> intent.putExtra(it.first, value)
                is BooleanArray -> intent.putExtra(it.first, value)
                is Array<*> -> when {
                    value.isArrayOf<CharSequence>() -> intent.putExtra(it.first, value)
                    value.isArrayOf<String>() -> intent.putExtra(it.first, value)
                    value.isArrayOf<Parcelable>() -> intent.putExtra(it.first, value)
                    value.isArrayOf<Serializable>() -> intent.putExtra(it.first, value)
                    else -> throw RuntimeException("Intent extra ${it.first} has wrong type ${value.javaClass.name}")
                }
                is Parcelable -> intent.putExtra(it.first, value)
                is Serializable -> intent.putExtra(it.first, value)
                else -> throw RuntimeException("Intent extra ${it.first} has wrong type ${value.javaClass.name}")
            }
        }
    }
}

inline fun <reified T : Any> Context.intentFor(vararg params: Pair<String, Any?>): Intent =
    Internals.createIntent(this, T::class.java, params)

inline fun <reified T : FragmentActivity> FragmentActivity.navigateTo(
    vararg params: Pair<String, Any?>,
    bundle: Bundle? = null
) {
    NavigatorExt.goto<T>(this, *params, bundle = bundle)
}