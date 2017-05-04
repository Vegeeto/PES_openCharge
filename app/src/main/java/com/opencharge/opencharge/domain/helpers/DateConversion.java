package com.opencharge.opencharge.domain.helpers;

import java.util.Date;

/**
 * Created by Oriol on 4/5/2017.
 */

public interface DateConversion {

    long DateToLong(Date date);

    Date longToDate(long time);

}
