package com.opencharge.opencharge.domain.parsers;

import java.util.Date;
import java.util.Map;

/**
 * Created by Oriol on 4/5/2017.
 */

public interface Parser {

    String parseStringKeyFromMap(String key, Map<String, Object> map);

    double parseDoubleKeyFromMap(String key, Map<String, Object> map);

    Date parseDateKeyFromMap(String key, Map<String, Object> map);

}
