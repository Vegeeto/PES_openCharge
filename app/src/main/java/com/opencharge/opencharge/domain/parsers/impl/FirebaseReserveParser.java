package com.opencharge.opencharge.domain.parsers.impl;

import com.opencharge.opencharge.domain.Entities.Reserve;
import com.opencharge.opencharge.domain.parsers.ReserveParser;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Oriol on 17/5/2017.
 */

public class FirebaseReserveParser extends AbstractParser implements ReserveParser {

    public static final String DAY_KEY = "day";
    public static final String START_HOUR_KEY = "startHour";
    public static final String END_HOUR_KEY = "endHour";
    public static final String STATE = "state";
    public static final String SUPPLIER_FINISH = "ownerFinish";
    public static final String CONSUMER_FINISH = "userFinish";
    public static final String POINT_ID = "pointId";
    public static final String CONSUMER_USER_ID = "consumeruserId";
    public static final String SUPPLIER_USER_ID = "supplieruserId";
    public static final String CAN_CONFIRM = "canconfirm";

    @Override
    public Reserve parseFromMap(String key, Map<String, Object> map) {
        Date day = parseDateStringKeyFromMap(DAY_KEY, map);
        Date startDate = parseTimeKeyFromMap(START_HOUR_KEY, map);
        Date finishDate = parseTimeKeyFromMap(END_HOUR_KEY, map);

        Reserve reserve = new Reserve(day, startDate, finishDate);
        reserve.setId(key);

        boolean ownerFinish = parseBooleanKeyFromMap(SUPPLIER_FINISH, map);
        if (ownerFinish) {
            reserve.markAsFinishedBySupplier();
        }

        boolean userFinish = parseBooleanKeyFromMap(CONSUMER_FINISH, map);
        if (userFinish) {
            reserve.markAsFinishedByConsumer();
        }

        boolean canConfirm = parseBooleanKeyFromMap(CAN_CONFIRM, map);
        reserve.setCanConfirm(canConfirm);

        reserve.setConsumerUserId(parseStringKeyFromMap(CONSUMER_USER_ID, map));
        reserve.setSupplierUserId(parseStringKeyFromMap(SUPPLIER_USER_ID, map));
        reserve.setPointId(parseStringKeyFromMap(POINT_ID, map));

        String stateFirebase = parseStringKeyFromMap(STATE, map);
        if (stateFirebase.equals(Reserve.ACCEPTED)) {
            reserve.accept();
        }
        if (stateFirebase.equals(Reserve.REJECTED)) {
            reserve.reject();
        }
        return reserve;
    }

    @Override
    public Map<String, Object> serializeReserve(Reserve reserve) {
        Map<String, Object> serializedReserve = new HashMap<>();

        String serializedDay = serializeDate(reserve.getDay());
        serializedReserve.put(DAY_KEY, serializedDay);

        String serializedStartHour = serializeTime(reserve.getStartHour());
        serializedReserve.put(START_HOUR_KEY, serializedStartHour);

        String serializedEndHour = serializeTime(reserve.getEndHour());
        serializedReserve.put(END_HOUR_KEY, serializedEndHour);

        serializedReserve.put(CONSUMER_FINISH, reserve.isMarkedAsFinishedByConsumer());
        serializedReserve.put(SUPPLIER_FINISH, reserve.isMarkedAsFinishedBySupplier());

        serializedReserve.put(CONSUMER_USER_ID, reserve.getConsumerUserId());
        serializedReserve.put(SUPPLIER_USER_ID, reserve.getSupplierUserId());
        serializedReserve.put(POINT_ID, reserve.getPointId());

        serializedReserve.put(STATE, reserve.getState());
        serializedReserve.put(CAN_CONFIRM, reserve.getState());

        return serializedReserve;
    }
}
