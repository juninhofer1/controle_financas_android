package com.buffalo.controlefinancas.model.props

enum class EOptions(var codigo: Int, var descricao: String) {
    SHOW(0, "Visualizar"),
    EDIT(1, "Editar"),
    DELETE(2, "Remover");

    companion object {
        fun getEnun(aCod: Int): EOptions {
            for (lSituacao in values()) {
                if (lSituacao.codigo == aCod) {
                    return lSituacao
                }
            }
            return SHOW
        }

        fun getEnun(descricao: String?): EOptions {
            for (lSituacao in values()) {
                if (lSituacao.descricao.equals(descricao, ignoreCase = true)) {
                    return lSituacao
                }
            }
            return SHOW
        }
    }

}