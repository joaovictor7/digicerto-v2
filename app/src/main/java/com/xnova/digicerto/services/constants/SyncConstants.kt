package com.xnova.digicerto.services.constants

class SyncConstants {
    object FTP {
        const val FILE_NAME = "DIGICERTO.txt"
        const val SPLIT_DELIMITER = ";"

        object KEYS {
            const val RECORD_TYPE = 0

            object PRODUCER {
                const val MINIMUM_DATA = 8
                const val CODE = 1
                const val NAME = 2
                const val FARM_CODE = 3
                const val FARM_NAME = 4
                const val AVG_VOLUME = 5
                const val TANK_TYPE = 6
                const val TANK_CODE = 7
                const val NOTE = 8
            }

            object VEHICLE {
                const val MINIMUM_DATA = 3
                const val CODE = 1
                const val PLATE = 2
                const val FIRST_COMPARTMENT = 3
            }

            object ROUTE {
                const val MINIMUM_DATA = 3
                const val MINIMUM_DATA_WITH_PRODUCER = 6
                const val CODE = 1
                const val NAME = 2
                const val SEQUENCE = 3
                const val PRODUCER_CODE = 4
                const val PRODUCER_FARM_CODE = 5
            }

            object OCCURRENCE {
                const val MINIMUM_DATA = 4
                const val CODE = 1
                const val TYPE = 2
                const val DESCRIPTION = 3
            }

            object DRIVER {
                const val MINIMUM_DATA = 3
                const val CODE = 1
                const val NAME = 2
            }
        }
    }

    object WS
}