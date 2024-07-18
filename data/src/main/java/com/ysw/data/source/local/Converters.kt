package com.ysw.data.source.local

import android.net.Uri
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.LocalTime
import java.time.format.DateTimeFormatter


/**
 * AlarmConverters
 * Room에 적용할 Converters
 *
 * @constructor Create empty Converters
 */
internal class AlarmConverters {

    private val gson = Gson()

    @TypeConverter
    fun fromLocalTime(time: LocalTime?): String? {
        return time?.format(DateTimeFormatter.ofPattern("HH:mm"))
    }

    @TypeConverter
    fun toLocalTime(time: String?): LocalTime? {
        return time?.let {
            LocalTime.parse(it, DateTimeFormatter.ofPattern("HH:mm"))
        }
    }

    @TypeConverter
    fun fromStringList(list: List<String>?): String? {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toStringList(listString: String?): List<String>? {
        return listString?.let {
            val listType = object : TypeToken<List<String>>() {}.type
            gson.fromJson(it, listType)
        }
    }

    @TypeConverter
    fun fromStringUriMap(map: Map<String, Uri>?): String? {
        val stringUriMap = map?.mapValues { it.value.toString() }
        return gson.toJson(stringUriMap)
    }

    @TypeConverter
    fun toStringUriMap(mapString: String?): Map<String, Uri>? {
        return mapString?.let {
            val mapType = object : TypeToken<Map<String, String>>() {}.type
            val stringToUriMap: Map<String, String> = gson.fromJson(it, mapType)
            stringToUriMap.mapValues { Uri.parse(it.value) }
        }
    }

}
