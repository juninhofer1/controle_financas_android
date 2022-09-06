package com.buffalo.controlefinancas.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.io.Serializable
import java.util.*

open class Expense (
    @PrimaryKey
    var _id : Long? = 0,
    var descricao : String? = null,
    var amount : Double? = null,
    var literage : Double? = null,
    var dataRegiter : Date? = null,
    var numberTicket : String? = null,
    var state : String? = null,
    var city : String? = null,
    var expenseType : ExpenseType? = null
) : RealmObject(), Serializable