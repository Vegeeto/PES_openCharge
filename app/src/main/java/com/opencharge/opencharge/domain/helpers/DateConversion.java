package com.opencharge.opencharge.domain.helpers;

import java.util.Date;

/**
 * Created by Oriol on 4/5/2017.
 */

public interface DateConversion {

    long DateToLong(Date date);

    Date longToDate(long time);

    Date StringToDate(String time);

    String ConvertLongToDateFormat(long time);

    Date ConvertStringToDate(String dateString);

    String ConvertDateToString(Date date);

    Date ConvertStringToTime(String dateString);

    String ConvertIntToTimeString(int hour, int minute);

}
