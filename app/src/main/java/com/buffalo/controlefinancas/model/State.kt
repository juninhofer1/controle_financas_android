package com.buffalo.controlefinancas.model

import com.google.gson.annotations.SerializedName

class State {

    @SerializedName("sigla")
    var sigla : String? = ""
    @SerializedName("nome")
    var name : String? = ""
    @SerializedName("cidades")
    var cities : MutableList<String>? = null
}