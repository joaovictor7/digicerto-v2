package com.xnova.digicerto.services.enums.producer

enum class TankType(val id: String) {
    Individual("I"), Collective("C"), Associated("A");

    companion object {
        fun existId(id: String): Boolean {
            return enumValues<TankType>().any { it.id == id }
        }

        fun getById(id: String): TankType {
            return values().find { it.id == id }!!
        }
    }
}