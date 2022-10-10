package com.buffalo.controlefinancas.database

import com.buffalo.controlefinancas.model.ExpenseType
import com.buffalo.controlefinancas.util.BaseDao
import io.realm.Realm
import io.realm.Sort

object ExpenseTypeDAO : BaseDao<Int, ExpenseType> {

    override fun save(aObject: ExpenseType) : Int {
        var lId = 1
        val realm = Realm.getDefaultInstance()
        try {
            val maxId = realm.where(ExpenseType::class.java).max("_id")
            val nextId = if (maxId == null) 1 else maxId.toInt() + 1
            aObject._id = nextId
            realm.beginTransaction()
            val transaction = realm.copyToRealmOrUpdate(aObject)
            lId = transaction?._id!!
            realm.commitTransaction()
        } catch (ex : Exception) {
            ex.printStackTrace()
        } finally {
            realm.close()
        }
        return lId
    }

    override fun update(aObject: ExpenseType) : Int {
        var lId = 1
        val realm = Realm.getDefaultInstance()
        try {
            realm.beginTransaction()
            val transaction = realm.copyToRealmOrUpdate(aObject)
            lId = transaction?._id!!
            realm.commitTransaction()
        } catch (ex : Exception) {
            ex.printStackTrace()
        } finally {
            realm.close()

        }
        return lId
    }

    override fun loadAll() : MutableList<ExpenseType>? {
        var list : MutableList<ExpenseType>? = null
        val realm = Realm.getDefaultInstance()
        try {
            val realInfos = realm.where(ExpenseType::class.java).sort("_id", Sort.ASCENDING).findAll()
            list = realm.copyFromRealm(realInfos)
        } catch (ex : Exception) {
            ex.printStackTrace()
        } finally {
            realm.close()
        }
        return list
    }

    override fun deleteById(id : Int) {
        val realm = Realm.getDefaultInstance()
        try {
            val monitoring = realm.where(ExpenseType::class.java).equalTo("_id", id).findFirst()
            realm.beginTransaction()
            monitoring?.deleteFromRealm()
            realm.commitTransaction()
        } catch (ex : Exception) {
            ex.printStackTrace()
        } finally {
            realm.close()
        }
    }

    override fun loadById(id: Int): ExpenseType? {
        TODO("Not yet implemented")
    }
}