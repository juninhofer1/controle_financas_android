package com.buffalo.controlefinancas.database

import android.util.Log
import com.buffalo.controlefinancas.model.Expense
import com.buffalo.controlefinancas.model.ExpenseType
import com.buffalo.controlefinancas.model.FilterExpenseType
import com.buffalo.controlefinancas.util.BaseDao
import io.realm.Realm
import io.realm.Sort
import java.util.*

object ExpenseDao : BaseDao<Long, Expense> {

    override fun save(aObject: Expense) : Long {
        var lId = 1L
        val realm = Realm.getDefaultInstance()
        try {
            val maxId = realm.where(Expense::class.java).max("_id")
            val nextId = if (maxId == null) 1 else maxId.toLong() + 1
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


    override fun update(aObject: Expense) : Long {
        var lId = 1L
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

    override fun loadAll() : MutableList<Expense>? {
        var list : MutableList<Expense>? = null
        val realm = Realm.getDefaultInstance()
        try {
            val realInfos = realm.where(Expense::class.java).sort("_id", Sort.ASCENDING).findAll()
            list = realm.copyFromRealm(realInfos)
        } catch (ex : Exception) {
            ex.printStackTrace()
        } finally {
            realm.close()
        }
        return list
    }

    override fun deleteById(id : Long) {
        val realm = Realm.getDefaultInstance()
        try {
            val monitoring = realm.where(Expense::class.java).equalTo("_id", id).findFirst()
            realm.beginTransaction()
            monitoring?.deleteFromRealm()
            realm.commitTransaction()
        } catch (ex : Exception) {
            ex.printStackTrace()
        } finally {
            realm.close()
        }
    }

    override fun loadById(id: Long): Expense? {
        TODO("Not yet implemented")
    }

    fun loadByDate(startDate: Date, finalDate : Date): List<Expense>? {
        var list : MutableList<Expense>? = null
        val realm = Realm.getDefaultInstance()
        try {
            val realInfos = realm.where(Expense::class.java)
                .greaterThanOrEqualTo("dataRegiter",startDate)
                .lessThan("dataRegiter",finalDate)
                .sort("dataRegiter", Sort.ASCENDING)
                .findAll()
            list = realm.copyFromRealm(realInfos)
        } catch (ex : Exception) {
            ex.printStackTrace()
        } finally {
            realm.close()
        }
        return list
    }

    fun loadByDateExpenseTypeShort(startDate: Date, finalDate : Date): List<Expense>? {
        var list : MutableList<Expense>? = null
        val realm = Realm.getDefaultInstance()
        try {
            val realInfos = realm.where(Expense::class.java)
                .greaterThanOrEqualTo("dataRegiter",startDate)
                .lessThan("dataRegiter",finalDate)
                .beginGroup()
                .sort("expenseType", Sort.ASCENDING)
                .endGroup()
                .findAll()
            list = realm.copyFromRealm(realInfos)
        } catch (ex : Exception) {
            ex.printStackTrace()
        } finally {
            realm.close()
        }
        return list
    }



    fun totalExpenses(startDate: Date, finalDate : Date): Double {
        val realm = Realm.getDefaultInstance()
        try {
            val value = realm.where(Expense::class.java)
                .greaterThanOrEqualTo("dataRegiter",startDate)
                .lessThan("dataRegiter",finalDate)
                .sort("dataRegiter", Sort.ASCENDING)
                .sum("amount")
            return value.toDouble()
        } catch (ex : Exception) {
            ex.printStackTrace()
        } finally {
            realm.close()
        }
        return 0.0
    }
}