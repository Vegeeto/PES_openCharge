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

    @Captor
    private ArgumentCaptor<PointsRepository.CreatePointCallback> repositoryCallbackCaptor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        sut = new PointsCreateUseCaseImpl(mockThreadExecutor, mockMainThread, mockPointsRepository, mockCallback);
    }

    @Test
    public void test_run_callRepository() {
        //Given
        double lat = 1.0;
        double lon = 1.0;
        String town = "townTest";
        String street = "streetTest";
        String number = "numberTest";
        String accessType = "accessTypeTest";
        String connectorType = "connectorTypeTest";
        String schedule = "scheduleTest";

        //When
        sut.setPointParameters(lat,lon,town,street,number,accessType,connectorType,schedule);
        sut.run();

        //Then
        //verify(mockPointsRepository).createPoint(any(Point.class),any(PointsRepository.CreatePointCallback.class));
    }

    @Test
    public void testRun_onPointsRetrievedFromRepository_returnPointsFromRepoToMainThread() {
        //Given
        double lat = 1.0;
        double lon = 1.0;
        String town = "townTest";
        String street = "streetTest";
        String number = "numberTest";
        String accessType = "accessTypeTest";
        String connectorType = "connectorTypeTest";
        String schedule = "scheduleTest";
        sut.setPointParameters(lat,lon,town,street,number,accessType,connectorType,schedule);
        sut.run();
        //verify(mockPointsRepository).createPoint(any(Point.class),repositoryCallbackCaptor.capture());

        //When
        String id = "5";

        repositoryCallbackCaptor.getValue().onPointCreated(id);

        //Then
        verify(mockMainThread).post(mainThreadRunnableCaptor.capture());

        mainThreadRunnableCaptor.getValue().run();
        verify(mockCallback).onPointCreated(id);
    }
}
