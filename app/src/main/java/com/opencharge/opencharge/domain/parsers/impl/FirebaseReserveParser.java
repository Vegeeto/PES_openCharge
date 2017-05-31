package com.opencharge.opencharge.domain.parsers.impl;

import com.opencharge.opencharge.domain.Entities.Point;
import com.opencharge.opencharge.domain.Entities.Reserve;
import com.opencharge.opencharge.domain.parsers.ReserveParser;
import com.opencharge.opencharge.domain.parsers.ServiceParser;

import java.util.Map;

/**
 * Created by Oriol on 17/5/2017.
 */

public class FirebaseReserveParser extends AbstractParser implements ReserveParser {

    public static final String DAY_KEY = "day";
    public static final String START_HOUR_KEY = "startHour";
    public static final String END_HOUR_KEY = "endHour";
    public static final String OWNER_FINISH = "ownerFinish";
    public static final String SERVICE_ID = "servideId";
    public static final String STATE = "state";
    public static final String USER_FINISH = "userFinish";
    public static final String USER_ID = "userId";

    //TODO: finish this class => ORIOL

    @Override
    public Reserve parseFromMap(String key, Map<String, Object> map) {
        Reserve reserve = new Reserve(key);

        reserve.setDay(parseDateKeyFromMap(DAY_KEY, map));
        reserve.setStartHour(parseDateKeyFromMap(START_HOUR_KEY, map));
        reserve.setEndHour(parseDateKeyFromMap(END_HOUR_KEY, map));
        reserve.setOwnerFinish(parseBooleanKeyFromMap(OWNER_FINISH, map));
        reserve.setServiceId(parseStringKeyFromMap(SERVICE_ID, map));
        reserve.setUserFinish(parseBooleanKeyFromMap(USER_FINISH, map));
        reserve.setUserId(parseStringKeyFromMap(USER_ID, map));
        String stateFirebase = parseStringKeyFromMap(STATE, map);
        if (stateFirebase.equals("Creada")) {
            reserve.setState(Reserve.CREATED);
        }
        if (stateFirebase.equals("Rebutjada")) {
            reserve.setState(Reserve.REJECTED);
        }
        if (stateFirebase.equals("Acceptada")) {
            reserve.setState(Reserve.ACCEPTED);
        }
        return reserve;
    }

}
