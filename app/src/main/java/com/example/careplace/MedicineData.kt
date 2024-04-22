package com.example.careplace

import java.util.Date

data class MedicineData(
    val medicineName: String ?= "",
    val numberOfDoses: String?= "",
    val dateForTaking: String ?= "",
    val medicineid : String ?= null

)
