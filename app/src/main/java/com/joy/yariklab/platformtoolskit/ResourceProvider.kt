package com.joy.yariklab.platformtoolskit

import android.content.Context
import android.content.res.Resources
import androidx.annotation.ArrayRes
import androidx.annotation.BoolRes
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.IntegerRes
import androidx.annotation.Px
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import java.util.Locale

/**
 * gateway to get at app resources
 */
interface ResourceProvider {

    val packageName: String

    fun getString(@StringRes res: Int): String

    fun getString(@StringRes res: Int, vararg args: Any): String

    fun getStringArray(@ArrayRes res: Int): List<String>

    fun getInteger(@IntegerRes resId: Int): Int

    fun getIntegerArray(@ArrayRes resId: Int): List<Int>

    fun getBoolean(@BoolRes resId: Int): Boolean

    fun getColor(@ColorRes res: Int): Int

    @Px
    fun getDimensionPixelSize(@DimenRes dimenRes: Int): Int

    fun getLocale(): Locale
}

class ResourceProviderImpl(
    private val context: Context
) : ResourceProvider {

    private val resources: Resources
        get() = context.resources

    override val packageName: String
        get() = context.packageName

    override fun getString(res: Int) = resources.getString(res)

    override fun getString(res: Int, vararg args: Any) =
        resources.getString(res, *args)

    override fun getStringArray(res: Int) =
        resources.getStringArray(res).asList()

    override fun getInteger(resId: Int) = resources.getInteger(resId)

    override fun getIntegerArray(resId: Int) =
        resources.getIntArray(resId).toTypedArray().toList()

    override fun getBoolean(resId: Int) = resources.getBoolean(resId)

    override fun getColor(res: Int) = ContextCompat.getColor(context, res)

    override fun getDimensionPixelSize(dimenRes: Int) = resources.getDimensionPixelSize(dimenRes)

    override fun getLocale(): Locale = resources.configuration.locales.get(0)
}