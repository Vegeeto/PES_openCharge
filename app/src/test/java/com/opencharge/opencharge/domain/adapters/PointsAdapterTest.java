package com.opencharge.opencharge.domain.adapters;


import android.graphics.Point;

import com.opencharge.opencharge.domain.parsers.impl.FirebasePointsParser;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;


/**
 * Created by Oriol on 20/4/2017.
 */

public class PointsAdapterTest {

    PointsAdapterTest sut;

    @Before
    public void setUp() {
        //setUpCollaborators();
        sut = new PointsAdapterTest();
    }

}
