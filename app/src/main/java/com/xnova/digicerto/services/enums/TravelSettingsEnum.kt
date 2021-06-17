package com.xnova.digicerto.services.enums

enum class RouteType(id: Int) {
    Defined(1), Undefined(2)
}

enum class CollectiveTankProduction(id: Int) {
    Typed(1), Picture(2)
}

enum class ReadSampleType(id: Int) {
    None(1), ProducerCode(2), AleatoryCode(3), ReadBarcode(4)
}

enum class TypeTravelCodeReading(id: Int){
    None(1), ReadBarcode(2), Manual(3)
}

enum class PlatformMeasure(id: Int){
    None(1), Weight(2), Volume(3)
}

enum class MilkDensityType(id: Int){
    Default(1), Measure(2)
}