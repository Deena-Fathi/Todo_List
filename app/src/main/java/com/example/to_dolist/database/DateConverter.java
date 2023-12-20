package com.example.to_dolist.database;

import androidx.annotation.Nullable;
import androidx.room.TypeConverter;

import java.util.Date;

/**
 * This class is used to convert dates to longs. <br>
 * This is needed because Room cannot store dates.
 */
public class DateConverter {

    @TypeConverter
    public static Date fromTimestamp(@Nullable Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long dateToTimestamp(@Nullable Date date) {
        return date == null ? null : date.getTime();
    }
}
