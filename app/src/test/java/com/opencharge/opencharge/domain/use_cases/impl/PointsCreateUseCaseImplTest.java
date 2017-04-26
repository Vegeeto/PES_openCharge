package com.opencharge.opencharge.domain.use_cases.impl;

/**
 * Created by Crjs on 26/04/2017.
 */
import com.opencharge.opencharge.domain.Entities.Point;
import com.opencharge.opencharge.domain.executor.Executor;
import com.opencharge.opencharge.domain.executor.MainThread;
import com.opencharge.opencharge.domain.repository.PointsRepository;
import com.opencharge.opencharge.domain.use_cases.PointsCreateUseCase;
import com.opencharge.opencharge.domain.use_cases.PointsListUseCase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

public class PointsCreateUseCaseImplTest {

    PointsCreateUseCaseImpl sut;
    private double lat = 1.0;
    private double lon = 1.0;
    private String town = "townTest";
    private String street = "streetTest";
    private String number = "numberTest";
    private String accessType = "accessTypeTest";
    private String connectorType = "connectorTypeTest";
    private String schedule = "scheduleTest";


    //Collaborators
    @Mock
    Executor mockThreadExecutor;

    @Mock
    MainThread mockMainThread;

    @Mock
    PointsRepository mockPointsRepository;

    @Mock
    PointsCreateUseCase.Callback mockCallback;

    @Captor
    private ArgumentCaptor<Runnable> mainThreadRunnableCaptor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        sut = new PointsCreateUseCaseImpl(mockThreadExecutor, mockMainThread, mockPointsRepository, mockCallback,
                lat,lon,town,street,number,accessType,connectorType,schedule);
    }

    @Test
    public void test_run_callRepository() {
        //When
        sut.run();

        //Then
        verify(mockPointsRepository).createPoint(any(Point.class));
    }
}
