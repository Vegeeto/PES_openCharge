package com.opencharge.opencharge.domain.parsers.impl;

import com.opencharge.opencharge.domain.Entities.Reserve;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by ferran on 3/6/17.
 */

public class FirebaseReserveParserTest {
    FirebaseReserveParser sut;

    //Collaborators
    Map<String, Object> map;
    Reserve reserve;
    String key;

    @Before
    public void setUp() {
        setUpCollaborators();
        sut = new FirebaseReserveParser();
    }

    private void setUpCollaborators() {
        key = "reserve1ID";

        map = new HashMap<>();
        map.put(FirebaseReserveParser.DAY_KEY, "23/11/2017");
        map.put(FirebaseReserveParser.START_HOUR_KEY, "08:30");
        map.put(FirebaseReserveParser.END_HOUR_KEY, "14:05");
        map.put(FirebaseReserveParser.SUPPLIER_FINISH, false);
        map.put(FirebaseReserveParser.CONSUMER_FINISH, true);
        map.put(FirebaseReserveParser.POINT_ID, "PointId");
        map.put(FirebaseReserveParser.CONSUMER_USER_ID, "ConsumerUserId");
        map.put(FirebaseReserveParser.SUPPLIER_USER_ID, "ConsumerUserId");
        map.put(FirebaseReserveParser.STATE, Reserve.CREATED);
        map.put(FirebaseReserveParser.CAN_CONFIRM, true);

        Date day = new Date("11/23/2017");
        Date startHour = new Date("01/01/1970 08:30");
        Date endHour = new Date("01/01/1970 14:05");
        reserve = new Reserve(day, startHour, endHour);
        reserve.markAsFinishedByConsumer();
        reserve.setPointId("PointId");
        reserve.setConsumerUserId("ConsumerUserId");
        reserve.setSupplierUserId("ConsumerUserId");
        reserve.setCanConfirm(true);
    }

    @Test
    public void test_parseFromMap_createReserveWithCorrectParams() {
        //When
        Reserve parsedReserve = sut.parseFromMap(key, map);

        //Then
        assertEquals("Wrong parsed Id", key, parsedReserve.getId());
        assertEquals("Wrong parsed day", reserve.getDay(), parsedReserve.getDay());
        assertEquals("Wrong parsed startHour", reserve.getStartHour(), parsedReserve.getStartHour());
        assertEquals("Wrong parsed SupplierFinish", reserve.isMarkedAsFinishedBySupplier(), parsedReserve.isMarkedAsFinishedBySupplier());
        assertEquals("Wrong parsed Finish", reserve.isMarkedAsFinishedByConsumer(), parsedReserve.isMarkedAsFinishedByConsumer());
        assertEquals("Wrong parsed pointId", reserve.getPointId(), parsedReserve.getPointId());
        assertEquals("Wrong parsed consumerUserId", reserve.getConsumerUserId(), parsedReserve.getConsumerUserId());
        assertEquals("Wrong parsed supplierUserId", reserve.getSupplierUserId(), parsedReserve.getSupplierUserId());
        assertEquals("Wrong parsed state", reserve.getState(), parsedReserve.getState());
        assertEquals("Wrong parsed CanConfirm", reserve.getCanConfirm(), parsedReserve.getCanConfirm());
    }

    //<editor-fold desc="Parse tests">
    @Test
    public void testMapWithOwnerFinishToTrue_parseFromMap_createReserveWithCorrectParams() {
        //Given
        map.put(FirebaseReserveParser.SUPPLIER_FINISH, true);

        //When
        Reserve parsedReserve = sut.parseFromMap(key, map);

        //Then
        assertEquals("Wrong parsed ownerFinish", true, parsedReserve.isMarkedAsFinishedBySupplier());
    }

    @Test
    public void testMapWithUserFinishToFalse_parseFromMap_createReserveWithCorrectParams() {
        //Given
        map.put(FirebaseReserveParser.CONSUMER_FINISH, false);

        //When
        Reserve parsedReserve = sut.parseFromMap(key, map);

        //Then
        assertEquals("Wrong parsed ownerFinish", false, parsedReserve.isMarkedAsFinishedBySupplier());
    }

    @Test
    public void testMapWithAcceptedState_parseFromMap_createReserveWithCorrectParams() {
        //Given
        map.put(FirebaseReserveParser.STATE, Reserve.ACCEPTED);

        //When
        Reserve parsedReserve = sut.parseFromMap(key, map);

        //Then
        assertEquals("Wrong parsed state", Reserve.ACCEPTED, parsedReserve.getState());
    }

    @Test
    public void testMapWithRejectedState_parseFromMap_createReserveWithCorrectParams() {
        //Given
        map.put(FirebaseReserveParser.STATE, Reserve.REJECTED);

        //When
        Reserve parsedReserve = sut.parseFromMap(key, map);

        //Then
        assertEquals("Wrong parsed state", Reserve.REJECTED, parsedReserve.getState());
    }
    //</editor-fold>

    @Test
    public void test_serializeReserve_createMapWithCorrectParams() {
        //When
        Map<String, Object> serializedReserve = sut.serializeReserve(reserve);

        //Then
        assertEquals("Wrong serialized day", map.get(FirebaseReserveParser.DAY_KEY), serializedReserve.get(FirebaseReserveParser.DAY_KEY));
        assertEquals("Wrong serialized startHour", map.get(FirebaseReserveParser.START_HOUR_KEY), serializedReserve.get(FirebaseReserveParser.START_HOUR_KEY));
        assertEquals("Wrong serialized supplierFinish", map.get(FirebaseReserveParser.SUPPLIER_FINISH), serializedReserve.get(FirebaseReserveParser.SUPPLIER_FINISH));
        assertEquals("Wrong serialized consumerFinish", map.get(FirebaseReserveParser.CONSUMER_FINISH), serializedReserve.get(FirebaseReserveParser.CONSUMER_FINISH));
        assertEquals("Wrong serialized pointId", map.get(FirebaseReserveParser.POINT_ID), serializedReserve.get(FirebaseReserveParser.POINT_ID));
        assertEquals("Wrong serialized consumerUserId", map.get(FirebaseReserveParser.CONSUMER_USER_ID), serializedReserve.get(FirebaseReserveParser.CONSUMER_USER_ID));
        assertEquals("Wrong serialized supplierUserId", map.get(FirebaseReserveParser.SUPPLIER_USER_ID), serializedReserve.get(FirebaseReserveParser.SUPPLIER_USER_ID));
        assertEquals("Wrong serialized state", map.get(FirebaseReserveParser.STATE), serializedReserve.get(FirebaseReserveParser.STATE));
        assertEquals("Wrong serialized CanConfirm", map.get(FirebaseReserveParser.CAN_CONFIRM), serializedReserve.get(FirebaseReserveParser.CAN_CONFIRM));
    }
}
