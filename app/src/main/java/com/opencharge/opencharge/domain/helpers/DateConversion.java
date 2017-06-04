package com.opencharge.opencharge.domain.helpers;

import java.util.Date;

/**
 * Created by Oriol on 4/5/2017.
 */

public interface DateConversion {
    Date ConvertStringToDate(String dateString);

    Date ConvertStringToTime(String dateString);

    String ConvertLongToString(long time);

    String ConvertDateToString(Date date);

    String ConvertHourAndMinutesToString(int hour, int minute);
}
