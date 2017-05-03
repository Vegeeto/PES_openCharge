package com.opencharge.opencharge.domain.use_cases.impl;

import com.opencharge.opencharge.domain.Entities.Comment;
import com.opencharge.opencharge.domain.executor.Executor;
import com.opencharge.opencharge.domain.executor.MainThread;
import com.opencharge.opencharge.domain.repository.CommentsRepository;
import com.opencharge.opencharge.domain.use_cases.CommentsListUseCase;
import com.opencharge.opencharge.domain.use_cases.base.AbstractUseCase;


/**
 * Created by Oriol on 3/5/2017.
 */

public class CommentsListUseCaseImpl extends AbstractUseCase implements CommentsListUseCase {
    private CommentsListUseCase.Callback callback;
    private CommentsRepository commentsRepository;

    public CommentsListUseCaseImpl(Executor threadExecutor,
                                 MainThread mainThread,
                                 CommentsRepository commentsRepository,
                                 CommentsListUseCase.Callback callback) {
        super(threadExecutor, mainThread);

        this.commentsRepository = commentsRepository;
        this.callback = callback;
    }

    @Override
    public void run() {
        commentsRepository.getComments(new CommentsRepository.GetCommentsCallback() {
            @Override
            public void onCommentsRetrieved(Comment[] comments) {
                postComments(comments);
            }

            @Override
            public void onError() {

            }
        });
    }

    private void postComments(final Comment[] comments) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                //callback.onCommentsRetrieved(comments);
            }
        });
    }
}
