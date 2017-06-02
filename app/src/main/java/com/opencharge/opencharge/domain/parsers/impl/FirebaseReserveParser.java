package com.opencharge.opencharge.domain.parsers.impl;

import com.opencharge.opencharge.domain.Entities.Reserve;
import com.opencharge.opencharge.domain.parsers.ReserveParser;

import java.util.Date;
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
    public static final String CONSUMER_USER_ID = "consumeruserId";
    public static final String SUPPLIER_USER_ID = "supplieruserId";
    public static final String CANCONFIRM = "canconfirm";

    @Override
    public Reserve parseFromMap(String key, Map<String, Object> map) {
        Date day = parseDateStringKeyFromMap(DAY_KEY, map);
        Date startDate = parseTimeKeyFromMap(START_HOUR_KEY, map);
        Date finishDate = parseTimeKeyFromMap(END_HOUR_KEY, map);

        Reserve reserve = new Reserve(day, startDate, finishDate);
        reserve.setId(key);

        boolean ownerFinish = parseBooleanKeyFromMap(OWNER_FINISH, map);
        if (ownerFinish) {
            reserve.markAsFinishedByOwner();
        }

        boolean userFinish = parseBooleanKeyFromMap(USER_FINISH, map);
        if (userFinish) {
            reserve.markAsFinishedByUser();
        }

        boolean canConfirm = parseBooleanKeyFromMap(CANCONFIRM, map);
        reserve.setCanConfirm(canConfirm);

        reserve.setConsumerUserId(parseStringKeyFromMap(CONSUMER_USER_ID, map));
        reserve.setSupplierUserId(parseStringKeyFromMap(SUPPLIER_USER_ID, map));
        reserve.setServiceId(parseStringKeyFromMap(SERVICE_ID, map));

        String stateFirebase = parseStringKeyFromMap(STATE, map);
        if (stateFirebase.equals(Reserve.ACCEPTED)) {
            reserve.accept();
        }
        if (stateFirebase.equals(Reserve.REJECTED)) {
            reserve.reject();
        }
        return reserve;
    }

}
