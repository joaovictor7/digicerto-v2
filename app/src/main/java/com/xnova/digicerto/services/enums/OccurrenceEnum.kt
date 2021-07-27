package com.xnova.digicerto.services.enums

enum class OccurrenceType(val id: String) {
    Collect("C"), NotCollect("N");

    companion object {
        fun existId(id: String): Boolean {
            return enumValues<OccurrenceType>().any { it.id == id }
        }

        fun getById(id: String): OccurrenceType {
            return OccurrenceType.values().find { it.id == id }!!
        }
    }
}