package com.opencharge.opencharge.domain.use_cases.impl;

import com.opencharge.opencharge.domain.Entities.Points;
import com.opencharge.opencharge.domain.executor.Executor;
import com.opencharge.opencharge.domain.executor.MainThread;
import com.opencharge.opencharge.domain.repository.PointsRepository;
import com.opencharge.opencharge.domain.use_cases.PointsListUseCase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by ferran on 15/3/17.
 */

public class PointsListUseCaseImplTest {

    PointsListUseCaseImpl sut;

    //Collaborators
    @Mock
    Executor mockThreadExecutor;

    @Mock
    MainThread mockMainThread;

    @Mock
    PointsRepository mockPointsRepository;

    @Mock
    PointsListUseCase.Callback mockCallback;

    @Captor
    private ArgumentCaptor<Runnable> mainThreadRunnableCaptor;

    @Captor
    private ArgumentCaptor<PointsRepository.GetPointsCallback> repositoryCallbackCaptor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        sut = new PointsListUseCaseImpl(mockThreadExecutor, mockMainThread, mockPointsRepository, mockCallback);
    }

    @Test
    public void test_run_callRepository() {
        //Given
        String points = "points list";

        //When
        sut.run();

        //Then
        verify(mockPointsRepository).getPoints(any(PointsRepository.GetPointsCallback.class));
    }

    @Test
    public void testRun_onPointsRetrievedFromRepository_returnPointsFromRepoToMainThread() {
        //Given
        sut.run();
        verify(mockPointsRepository).getPoints(repositoryCallbackCaptor.capture());

        //When
        Points point = new Points();
        point.lat = 1.1f;
        point.lon = 1.2f;

        Points[] points = new Points[1];
        points[0] = point;

        repositoryCallbackCaptor.getValue().onPointsRetrieved(points);

        //Then
        verify(mockMainThread).post(mainThreadRunnableCaptor.capture());

        mainThreadRunnableCaptor.getValue().run();
        verify(mockCallback).onPointsRetrieved(points);
    }
}
