package com.buffalo.controlefinancas.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.io.Serializable

open class ColorExpense (
    @PrimaryKey
    var _id : Int? = 0,
    var hexadecimal : String? = null,
    var isItUsed: Boolean? = false
) : RealmObject(), Serializable