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
        map.put(FirebaseReserveParser.OWNER_FINISH, false);
        map.put(FirebaseReserveParser.USER_FINISH, true);
        map.put(FirebaseReserveParser.SERVICE_ID, "ServiceId");
        map.put(FirebaseReserveParser.CONSUMER_USER_ID, "ConsumerUserId");
        map.put(FirebaseReserveParser.SUPPLIER_USER_ID, "ConsumerUserId");
        map.put(FirebaseReserveParser.STATE, Reserve.CREATED);
        map.put(FirebaseReserveParser.CANCONFIRM, true);

        Date day = new Date("11/23/2017");
        Date startHour = new Date("01/01/1970 08:30");
        Date endHour = new Date("01/01/1970 14:05");
        reserve = new Reserve(day, startHour, endHour);
        reserve.markAsFinishedBySupplier();
        reserve.setServiceId("ServiceId");
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
        assertEquals("Wrong parsed ownerFinish", reserve.isMarkedAsFinishedBySupplier(), parsedReserve.isMarkedAsFinishedBySupplier());
        assertEquals("Wrong parsed userFinish", reserve.isMarkedAsFinishedByConsumer(), parsedReserve.isMarkedAsFinishedByConsumer());
        assertEquals("Wrong parsed serviceId", reserve.getServiceId(), parsedReserve.getServiceId());
        assertEquals("Wrong parsed consumerUserId", reserve.getConsumerUserId(), parsedReserve.getConsumerUserId());
        assertEquals("Wrong parsed supplierUserId", reserve.getSupplierUserId(), parsedReserve.getSupplierUserId());
        assertEquals("Wrong parsed state", reserve.getState(), parsedReserve.getState());
        assertEquals("Wrong parsed CanConfirm", reserve.getCanConfirm(), parsedReserve.getCanConfirm());
    }

    @Test
    public void testMapWithOwnerFinishToTrue_parseFromMap_createReserveWithCorrectParams() {
        //Given
        map.put(FirebaseReserveParser.OWNER_FINISH, true);

        //When
        Reserve parsedReserve = sut.parseFromMap(key, map);

        //Then
        assertEquals("Wrong parsed ownerFinish", true, parsedReserve.isMarkedAsFinishedBySupplier());
    }

    @Test
    public void testMapWithUserFinishToFalse_parseFromMap_createReserveWithCorrectParams() {
        //Given
        map.put(FirebaseReserveParser.USER_FINISH, false);

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
}
