package com.buffalo.controlefinancas.database

import com.buffalo.controlefinancas.model.ColorExpense
import com.buffalo.controlefinancas.util.BaseDao
import io.realm.Realm
import io.realm.Sort

object ColorExpenseDAO : BaseDao<Int, ColorExpense> {

    override fun save(aObject: ColorExpense) : Int {
        var lId = 1
        val realm = Realm.getDefaultInstance()
        try {
            val maxId = realm.where(ColorExpense::class.java).max("_id")
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

    override fun update(aObject: ColorExpense) : Int {
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

    override fun loadAll() : MutableList<ColorExpense>? {
        var list : MutableList<ColorExpense>? = null
        val realm = Realm.getDefaultInstance()
        try {
            val realInfos = realm.where(ColorExpense::class.java).sort("_id", Sort.ASCENDING).findAll()
            list = realm.copyFromRealm(realInfos)
        } catch (ex : Exception) {
            ex.printStackTrace()
        } finally {
            realm.close()
        }
        return list
    }

    fun count() : Int {
        var total = 0
        val realm = Realm.getDefaultInstance()
        try {
            val realInfos = realm.where(ColorExpense::class.java).sort("_id", Sort.ASCENDING).findAll()
            total = realInfos.size
        } catch (ex : Exception) {
            ex.printStackTrace()
        } finally {
            realm.close()
        }
        return total
    }

    override fun deleteById(id : Int) {
        val realm = Realm.getDefaultInstance()
        try {
            val monitoring = realm.where(ColorExpense::class.java).equalTo("_id", id).findFirst()
            realm.beginTransaction()
            monitoring?.deleteFromRealm()
            realm.commitTransaction()
        } catch (ex : Exception) {
            ex.printStackTrace()
        } finally {
            realm.close()
        }
    }

    override fun loadById(id: Int): ColorExpense? {
        TODO("Not yet implemented")
    }

    fun loadAllNotUsed() : MutableList<ColorExpense>? {
        var list : MutableList<ColorExpense>? = null
        val realm = Realm.getDefaultInstance()
        try {
            val realInfos = realm.where(ColorExpense::class.java).equalTo("isItUsed", false).sort("_id", Sort.ASCENDING).findAll()
            list = realm.copyFromRealm(realInfos)
        } catch (ex : Exception) {
            ex.printStackTrace()
        } finally {
            realm.close()
        }
        return list
    }
}