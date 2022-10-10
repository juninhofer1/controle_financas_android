package com.buffalo.controlefinancas.util.activity

import android.os.Bundle
import android.app.Activity
import android.content.Context
import android.content.Intent
import java.io.Serializable

class ActivityUtil private constructor(aBuilder: Builder) {
    private val mContextActivity: Context
    private val mClazz: Class<*>?
    private val mBundle: Bundle?
    private val mOnActivityResult: Boolean
    private val mRequestCode: Int

    class Builder(val mContextActivity: Activity, aClazz: Class<*>) {
        var mClazz: Class<*>? = null
        var mBundle: Bundle? = null
        var mOnActivityResult = false
        var mRequestCode = 0
        private fun initBuilder(aClazz: Class<*>) {
            mBundle = Bundle()
            mClazz = aClazz
        }

        fun putBundleInt(aKey: String?, aValue: Int): Builder {
            mBundle!!.putInt(aKey, aValue)
            return this
        }

        fun putBundleLong(aKey: String?, aValue: Long): Builder {
            mBundle!!.putLong(aKey, aValue)
            return this
        }

        fun putBundleString(aKey: String?, aValue: String?): Builder {
            mBundle!!.putString(aKey, aValue)
            return this
        }

        fun putBundleSerializable(aKey: String?, aValue: Serializable?): Builder {
            mBundle!!.putSerializable(aKey, aValue)
            return this
        }

        fun putBundleDouble(aKey: String?, aValue: Double): Builder {
            mBundle!!.putDouble(aKey, aValue)
            return this
        }

        fun putBundleString(aKey: String?, aValue: Float): Builder {
            mBundle!!.putFloat(aKey, aValue)
            return this
        }

        fun onActivityResult(aRequestCode: Int): Builder {
            mOnActivityResult = true
            mRequestCode = aRequestCode
            return this
        }

        fun build(): ActivityUtil {
            return ActivityUtil(this)
        }

        init {
            initBuilder(aClazz)
        }
    }

    private fun show() {
        val lIntent = Intent(mContextActivity, mClazz)
        lIntent.putExtras(mBundle!!)
        if (mOnActivityResult) if (mContextActivity is Activity) mContextActivity.startActivityForResult(
            lIntent,
            mRequestCode
        ) else mContextActivity.startActivity(lIntent) else mContextActivity.startActivity(lIntent)
    }

    init {
        mContextActivity = aBuilder.mContextActivity
        mBundle = aBuilder.mBundle
        mClazz = aBuilder.mClazz
        mRequestCode = aBuilder.mRequestCode
        mOnActivityResult = aBuilder.mOnActivityResult
        show()
    }
}