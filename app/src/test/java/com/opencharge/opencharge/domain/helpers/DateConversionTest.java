package com.opencharge.opencharge.domain.helpers;

import com.opencharge.opencharge.domain.helpers.impl.DateConversionImpl;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static junit.framework.Assert.assertEquals;


/**
 * Created by Oriol on 4/5/2017.
 */

public class DateConversionTest {

    private DateConversionImpl sut;

    @Before
    public void setUp() {
        sut = new DateConversionImpl();
    }

    @Test
    public void testRightTime_dateConversion_returnCorrectDate() {
        //Given

        //When
        Date date = sut.longToDate(System.currentTimeMillis());

        //Then
        assertEquals("Returned date is correct", date);
    }

    @Test
    public void testRightTime_dateConversion_returnCorrectLong() {
        //Given

        //When
        long time = sut.DateToLong(new Date(System.currentTimeMillis()));

        //Then
        assertEquals("Returned long is correct", time);
    }


}
