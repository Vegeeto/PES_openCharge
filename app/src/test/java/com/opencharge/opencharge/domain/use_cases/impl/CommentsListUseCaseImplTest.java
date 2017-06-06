package com.opencharge.opencharge.domain.use_cases.impl;

import com.opencharge.opencharge.domain.Entities.Comment;
import com.opencharge.opencharge.domain.executor.Executor;
import com.opencharge.opencharge.domain.executor.MainThread;
import com.opencharge.opencharge.domain.repository.CommentsRepository;
import com.opencharge.opencharge.domain.use_cases.CommentsListUseCase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by Oriol on 4/5/2017.
 */

public class CommentsListUseCaseImplTest {

    CommentsListUseCaseImpl sut;

    //Collaborators
    String id;

    @Mock
    Executor mockThreadExecutor;

    @Mock
    MainThread mockMainThread;

    @Mock
    CommentsRepository mockCommentsRepository;

    @Mock
    CommentsListUseCase.Callback mockCallback;

    @Captor
    private ArgumentCaptor<Runnable> mainThreadRunnableCaptor;

    @Captor
    private ArgumentCaptor<CommentsRepository.GetCommentsCallback> repositoryCallbackCaptor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        sut = new CommentsListUseCaseImpl(mockThreadExecutor, mockMainThread, mockCommentsRepository, mockCallback);
    }

    @Test
    public void test_run_callRepository() {
        //Given
        String comments = "comments list";
        id = "1";

        //When
        sut.run();

        //Then
        verify(mockCommentsRepository).getComments(id, any(CommentsRepository.GetCommentsCallback.class));
    }

    @Test
    public void testRun_onCommentsRetrievedFromRepository_returnCommentsFromRepoToMainThread() {
        //Given
        sut.run();
        verify(mockCommentsRepository).getComments(id, repositoryCallbackCaptor.capture());

        //When
        Comment comment = new Comment();
        comment.user = "Oriol";
        comment.text = "hola";

        Comment[] comments = new Comment[1];
        comments[0] = comment;

        repositoryCallbackCaptor.getValue().onCommentsRetrieved(comments);

        //Then
        verify(mockMainThread).post(mainThreadRunnableCaptor.capture());

        mainThreadRunnableCaptor.getValue().run();
        verify(mockCallback).onCommentsRetrieved(comments);
    }
}
