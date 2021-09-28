package com.xnova.digicerto.services.constants

class DatabaseConstants private constructor() {

    companion object {
        const val INITIAL_INSERTION_QUERY =
            "insert into Settings " +
            "(Travel_Type, " +
            " Travel_ProducerCodeReading, " +
            " Travel_WildcardProducer, " +
            " Travel_Transhipment, " +
            " Collect_CollectiveTankCollection, " +
            " Collect_RegisterSample, " +
            " Collect_AcidMilk, " +
            " Travel_TravelCodeReading, " +
            " Travel_PlatformMeasure, " +
            " Travel_MilkDensity, " +
            " Travel_AwaitDischarge, " +
            " Collect_TemperaturePicture, " +
            " Travel_OdometerRegister," +
            " Printer_UsePrinter, " +
            " Printer_Dupplicate, " +
            " Printer_CollectCancelled, " +
            " Printer_Layout, " +
            " Printer_PrintScale, " +
            " Printer_PrintCompartments, " +
            " Printer_PrintSample, " +
            " Printer_PrintTemperature, " +
            " Printer_PrintOccurrence, " +
            " Printer_PrintAlizarol," +
            " File_CollectCancelled) " +
            "values " +
            "('Undefined', " +
            "  0, " +
            "  0, " +
            "  0, " +
            "  'Typed', " +
            "  'None', " +
            "  0, " +
            "  'None', " +
            "  'None', " +
            "  'Default', " +
            "  0, " +
            "  0, " +
            "  'None', " +
            "  0, " +
            "  'Optional', " +
            "  0, " +
            "  'Complete', " +
            "  1, " +
            "  1, " +
            "  1, " +
            "  1, " +
            "  1, " +
            "  1, " +
            "  1)"
    }
}