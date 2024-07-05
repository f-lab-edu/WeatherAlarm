package com.ysw.data

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
    fun fromUri(uri: Uri?): String? {
        return uri?.toString()
    }

    @TypeConverter
    fun toUri(uri: String?): Uri? {
        return uri?.let {
            Uri.parse(it)
        }
    }

    @TypeConverter
    fun fromStringList(list: List<String>?): String? {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun toStringList(listString: String?): List<String>? {
        return listString?.let {
            val listType = object : TypeToken<List<String>>() {}.type
            Gson().fromJson(it, listType)
        }
    }

    @TypeConverter
    fun fromStringUriMap(map: Map<String, Uri>?): String? {
        return Gson().toJson(map)
    }

    @TypeConverter
    fun toStringUriMap(mapString: String?): Map<String, Uri>? {
        return mapString?.let {
            val mapType = object : TypeToken<Map<String, Uri>>() {}.type
            Gson().fromJson(it, mapType)
        }
    }

}
