package com.zs.base_library.utils

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import android.util.Base64
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.my.base.util.ContextUtils.getContext
import java.io.*
import java.util.*

/**
 * SharePreference封装
 *
 * @author zs
 */
object SPUtils {

    private const val PREF_NAME = "sp_cache"

    fun getBoolean(
        key: String,
        defaultValue: Boolean
    ): Boolean {
        val sp = getContext().getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        return sp.getBoolean(key, defaultValue)
    }

    fun putBoolean(ctx: Context, key: String, value: Boolean) {
        val sp = getContext().getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        sp.edit().putBoolean(key, value).apply()
    }

    fun getString(ctx: Context, key: String, defaultValue: String): String? {
        val sp = getContext().getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        return sp.getString(key, defaultValue)
    }

    fun getString(key: String, defaultValue: String): String? {
        val sp = getContext().getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        return sp.getString(key, defaultValue)
    }

    fun putString(ctx: Context, key: String, value: String) {
        val sp = getContext().getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        sp.edit().putString(key, value).apply()
    }

    fun putInt(ctx: Context, key: String, value: Int) {
        val sp = getContext().getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        sp.edit().putInt(key, value).apply()
    }

    fun getInt(ctx: Context, key: String, defaultValue: Int): Int {
        val sp = getContext().getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        return sp.getInt(key, defaultValue)
    }

    fun putLong(ctx: Context, key: String, value: Long) {
        val sp = getContext().getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        sp.edit().putLong(key, value).apply()
    }

    fun getLong(ctx: Context, key: String, defaultValue: Long): Long {
        val sp = getContext().getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        return sp.getLong(key, defaultValue)
    }

    fun putBoolean(key: String, value: Boolean) {
        val sp = getContext().getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        sp.edit().putBoolean(key, value).apply()
    }

    fun getString(key: String): String? {
        val sp = getContext().getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        var result: String? = null
        result = try {
            sp.getString(key, "")
        } catch (e: ClassCastException) {
            sp.getInt(key, -1).toString()
        }
        return result
    }

    fun putString(key: String, value: String) {
        val sp = getContext().getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        sp.edit().putString(key, value).apply()
    }

    fun putInt(key: String, value: Int) {
        val sp = getContext().getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        sp.edit().putInt(key, value).apply()
    }

    fun getInt(key: String, defaultValue: Int): Int {
        val sp = getContext().getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        return sp.getInt(key, defaultValue)
    }

    fun putLong(key: String, value: Long) {
        val sp = getContext().getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        sp.edit().putLong(key, value).apply()
    }

    fun getLong(key: String, defaultValue: Long): Long {
        val sp = getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sp.getLong(key, defaultValue)
    }

    fun putHashSet(key: String, value: HashSet<String>) {
        val sp = getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        sp.edit().putStringSet(key, value).apply()
    }

    fun getHashSet(key: String): MutableSet<String>? {
        val sp = getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sp.getStringSet(key, null)
    }

    fun removeKey(key: String): Boolean {
        val sp = getContext().getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        return sp.edit().remove(key).commit()
    }


    fun putObject(key: String, value: Any?) {
        if (value == null) {
            return
        }
        if (value !is Serializable) {
            return
        }
        var baos: ByteArrayOutputStream? = null
        var oos: ObjectOutputStream? = null
        try {
            val sp = getContext().getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
            )
            baos = ByteArrayOutputStream()
            oos = ObjectOutputStream(baos)
            oos.writeObject(value)
            val temp = String(Base64.encode(baos.toByteArray(), Base64.DEFAULT))
            sp.edit().putString(key, temp).apply()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (oos != null && baos != null) {
                CloseUtils.closeIO(oos, baos)
            }
        }

    }

    fun getObject(key: String): Any? {
        var `object`: Any? = null
        var bais: ByteArrayInputStream? = null
        var ois: ObjectInputStream? = null

        val sp = getContext().getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        val temp = sp.getString(key, "")
        if (!TextUtils.isEmpty(temp)) {
            try {
                bais = ByteArrayInputStream(Base64.decode(temp!!.toByteArray(), Base64.DEFAULT))
                ois = ObjectInputStream(bais)
                `object` = ois.readObject()
            } catch (ignored: Exception) {

            } finally {
                if (ois != null && bais != null) {
                    CloseUtils.closeIO(ois, bais)
                }
            }
        }
        return `object`
    }

    inline fun <reified T> putListData(key: String?, list: List<T>?): Boolean {
        if (list == null || list.size <= 0) return false
        var result: Boolean
        val clz = T::class.java
        val sp: SharedPreferences = getContext().getSharedPreferences(
            "sp_cache",
            Context.MODE_PRIVATE
        )
        val editor = sp.edit()
        val array = JsonArray()
        try {
            when (clz) {
                Boolean::class.java -> {
                    var i = 0
                    while (i < list.size) {
                        array.add(list[i] as Boolean)
                        i++
                    }
                }
                Long::class.java -> {
                    var i = 0
                    while (i < list.size) {
                        array.add(list[i] as Long)
                        i++
                    }
                }
                Float::class.java -> {
                    var i = 0
                    while (i < list.size) {
                        array.add(list[i] as Float)
                        i++
                    }
                }
                String::class.java -> {
                    var i = 0
                    while (i < list.size) {
                        array.add(list[i] as String)
                        i++
                    }
                }
                Integer::class.java -> {
                    var i = 0
                    while (i < list.size) {
                        array.add(list[i] as Int)
                        i++
                    }
                }
                else -> {
                    val gson = Gson()
                    var i = 0
                    while (i < list.size) {
                        val obj = gson.toJsonTree(list[i])
                        array.add(obj)
                        i++
                    }
                }
            }
            editor.putString(key, array.toString())
            result = true
        } catch (e: java.lang.Exception) {
            result = false
            e.printStackTrace()
        }
        editor.apply()
        return result
    }

    inline fun <reified T> getListData(key: String?): MutableList<T> {
        val sp: SharedPreferences = getContext().getSharedPreferences(
            "sp_cache",
            Context.MODE_PRIVATE
        )
        val json = sp.getString(key, "")
        if (json != "" && json!!.isNotEmpty()) {
            return MoshiUtils.listFromJson(json)
        }
        return mutableListOf()
    }

    fun <K, V> putHashMap(key: String?, map: Map<K, V>): Boolean {
        var result: Boolean
        val sp: SharedPreferences = getContext().getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        val editor = sp.edit()
        try {
            editor.putString(key, map.toJson())
            result = true
        } catch (e: java.lang.Exception) {
            result = false
            e.printStackTrace()
        }
        editor.apply()
        return result
    }

    inline fun <reified K, reified V> getHashMap(key: String): MutableMap<K, V> {
        val sp: SharedPreferences = getContext().getSharedPreferences(
            "sp_cache",
            Context.MODE_PRIVATE
        )
        val json = sp.getString(key, "")
        if (json != null && !json.isEmpty()) {
            return MoshiUtils.mapFromJson(json)
        }
        return mutableMapOf()
    }

    /**
     * 清除所有数据
     */
    fun clear() {
        val sp: SharedPreferences = getContext().getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        val editor = sp.edit()
        editor.clear().apply()
    }

    fun contains(key: String?): Boolean {
        val sp: SharedPreferences = getContext().getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        return sp.contains(key)
    }

    /**
     * 返回所有的键值对
     *
     * @return
     */
    fun getAll(): Map<String, *>? {
        val sp: SharedPreferences = getContext().getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        return sp.all
    }


}
