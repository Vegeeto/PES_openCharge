package com.opencharge.opencharge.domain.use_cases.impl;

/**
 * Created by DmnT on 26/04/2017.
 */

import com.opencharge.opencharge.domain.Entities.Comment;
import com.opencharge.opencharge.domain.executor.Executor;
import com.opencharge.opencharge.domain.executor.MainThread;
import com.opencharge.opencharge.domain.repository.CommentsRepository;
import com.opencharge.opencharge.domain.repository.PointsRepository;
import com.opencharge.opencharge.domain.use_cases.AddCommentUseCase;
import com.opencharge.opencharge.domain.use_cases.PointsListUseCase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

public class AddCommentUseCaseImplTest {

    AddCommentUseCaseImpl sut;

    //Collaborators
    @Mock
    Executor mockThreadExecutor;

    @Mock
    MainThread mockMainThread;

    @Mock
    CommentsRepository mockCommentsRepository;

    @Mock
    AddCommentUseCase.Callback mockCallback;

    @Captor
    private ArgumentCaptor<Runnable> mainThreadRunnableCaptor;

    @Captor
    private ArgumentCaptor<CommentsRepository.CreateCommentCallback> repositoryCallbackCaptor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        sut = new AddCommentUseCaseImpl(mockThreadExecutor, mockMainThread, mockCommentsRepository, mockCallback);
    }

    @Test
    public void test_run_callRepository() {
        //Given
        String point_id = "1";
        String autor = "Tester";
        String text = "Això és un comentari";
        Date data = new java.util.Date();

        //When
        sut.setCommentParameters(point_id, autor, text,data);
        sut.run();

        //Then
        verify(mockCommentsRepository).createComment(any(String.class),any(Comment.class),any(CommentsRepository.CreateCommentCallback.class));
    }
}