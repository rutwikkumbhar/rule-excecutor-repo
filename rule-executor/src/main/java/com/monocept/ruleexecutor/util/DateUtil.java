package com.monocept.ruleexecutor.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static String nudgeCurrentTimestamp() {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        LocalDateTime localDateTime = timestamp.toLocalDateTime();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        return localDateTime.format(formatter);
    }
}
