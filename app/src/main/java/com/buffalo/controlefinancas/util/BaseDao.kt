package com.buffalo.controlefinancas.util

interface BaseDao<TypeID, Table> {
    fun save(aObject: Table) : TypeID
    fun update(aObject: Table) : TypeID
    fun loadAll() : MutableList<Table>?
    fun loadById(id: TypeID) : Table?
    fun deleteById(id : TypeID)
}