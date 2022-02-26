package com.mabel.utils;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @project: message-gateway
 * @description:
 * @author: Mabel.Chen
 * @create: 2022-02-26
 **/
public class ZonedDateUtil {

    public static final String DATE_TIME_FORMATTER = "yyyy-MM-dd HH:mm:ss";
    public static final String ASIA_SHANGHAI_ZONE_ID = ZoneId.SHORT_IDS.get("CTT");

    public static ZonedDateTime convertDate(Date date, String zoneId) {
        return ZonedDateTime.ofInstant(date.toInstant(), ZoneId.of(zoneId));
    }

    public static String formatDateWithZone(Date date, String zoneId) {
        ZonedDateTime zonedDateTime = convertDate(date, zoneId);
        return zonedDateTime.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER));
    }

    public static String formatDate(Date date) {
        ZonedDateTime zonedDateTime = convertDate(date, ASIA_SHANGHAI_ZONE_ID);
        return zonedDateTime.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER));
    }
}