package com.opencharge.opencharge.domain.use_cases.impl;

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
import static org.mockito.Mockito.when;

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
    private ArgumentCaptor<Runnable> runnableCaptor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        sut = new PointsListUseCaseImpl(mockThreadExecutor, mockMainThread, mockPointsRepository, mockCallback);
    }

    @Test
    public void test_run_returnPointsFromRepository() {
        //Given
        String points = "points list";
        when(mockPointsRepository.getPoints()).thenReturn(points);

        //When
        sut.run();

        //Then
        verify(mockPointsRepository).getPoints();
        verify(mockMainThread).post(runnableCaptor.capture());

        runnableCaptor.getValue().run();
        verify(mockCallback).onPointsRetrieved(points);
    }
}
