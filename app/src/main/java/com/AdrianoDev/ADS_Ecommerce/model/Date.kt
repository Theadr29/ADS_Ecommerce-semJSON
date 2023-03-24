package com.AdrianoDev.ADS_Ecommerce.model

import androidx.room.TypeConverter
import java.util.*



// Essa classe não é usada, eu não apaguei para não da erro no banco de dados
// Essa classe não é usada, eu não apaguei para não da erro no banco de dados
// Essa classe não é usada, eu não apaguei para não da erro no banco de dados


class DateConverter {

    @TypeConverter
    fun toDate(dateLong: Long?) : Date? {
        return if (dateLong != null) Date(dateLong) else null
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

}