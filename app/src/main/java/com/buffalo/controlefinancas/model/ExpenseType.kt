package com.buffalo.controlefinancas.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.io.Serializable

open class ExpenseType (
    @PrimaryKey
    var _id : Int? = 0,
    var descricao : String? = null,
    var color : String? = null
) : RealmObject(), Serializable