package com.buffalo.controlefinancas

import android.app.Application
import com.buffalo.controlefinancas.database.ColorExpenseDAO
import com.buffalo.controlefinancas.util.ColorUtil
import com.buffalo.controlefinancas.util.ExpenseUtil
import io.realm.Realm
import io.realm.RealmConfiguration

class MainApplication : Application() {

    private var mRealmConfiguration: RealmConfiguration? = null

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        Realm.setDefaultConfiguration(getRealmConfiguration())

        createFirstType()
        createColorsDatabase()
    }

    fun getRealmConfiguration(): RealmConfiguration? {
        if (mRealmConfiguration == null) {
            mRealmConfiguration = RealmConfiguration.Builder()
                .name(getRealmName())
                .schemaVersion(getSchemaVersion())
                .deleteRealmIfMigrationNeeded()
                .build()
        }
        return mRealmConfiguration
    }

    fun createFirstType() {
        ExpenseUtil.createExpenseDefault()
    }

    fun createColorsDatabase() {
        if(ColorExpenseDAO.count() == 0) {
            ColorUtil.createList()
        }
    }

    fun getRealmName(): String {
        return Realm.DEFAULT_REALM_NAME
    }

    fun getSchemaVersion(): Long {
        return BuildConfig.VERSION_CODE.toLong()
    }
}