package com.xnova.digicerto.services.constants

class DatabaseConstants private constructor() {
    companion object {
        const val INITIAL_INSERTION_QUERY =
            "insert into Settings " +
            "(Travel_Type, Travel_ReadBarcode, Travel_WildcardProducer, Travel_Transhipment, " +
            " Collect_CollectiveTankProduction, Collect_ReadSample, Collect_AcidMilk, " +
            " Travel_TypeTravelCodeReading, Travel_PlatformMeasure, Travel_MilkDensityType, " +
            " Travel_AwaitDischarge, Collect_TemperaturePicture, Travel_Odometer, Printer_UsePrinter, " +
            " Printer_SecondVia, Printer_CancelledCollections, Printer_PrintType, Printer_PrintScale, " +
            " Printer_PrintCompartments, Printer_PrintSample, Printer_PrintTemperature, " +
            " Printer_PrintOccurrence, Printer_PrintAlizarol)" +
            "values" +
            "('Undefined', 0, 0, 0, 'Typed', 'None', 0, 'None', 'None', 'Default', 0, 0, 0, 0, 'Optional', 0, " +
            " 'Complete', 1, 1, 1, 1, 1, 1)"
    }
}