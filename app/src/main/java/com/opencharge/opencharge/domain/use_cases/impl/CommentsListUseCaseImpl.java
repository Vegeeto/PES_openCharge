package com.opencharge.opencharge.domain.use_cases.impl;

import com.opencharge.opencharge.domain.Entities.Comment;
import com.opencharge.opencharge.domain.executor.Executor;
import com.opencharge.opencharge.domain.executor.MainThread;
import com.opencharge.opencharge.domain.repository.CommentsRepository;
import com.opencharge.opencharge.domain.use_cases.base.AbstractUseCase;
import com.opencharge.opencharge.domain.use_cases.CommentsListUseCase;


/**
 * Created by Oriol on 3/5/2017.
 */

public class CommentsListUseCaseImpl extends AbstractUseCase implements CommentsListUseCase {

    private String pointId;
    private CommentsListUseCase.Callback callback;
    private CommentsRepository commentsRepository;

    public CommentsListUseCaseImpl(Executor threadExecutor,
                                 MainThread mainThread,
                                 CommentsRepository commentsRepository,
                                 Callback callback) {
        super(threadExecutor, mainThread);

        this.commentsRepository = commentsRepository;
        this.callback = callback;
    }

    @Override
    public void setPointId(String pointId) {
        this.pointId = pointId;
    }

    @Override
    public void run() {
        commentsRepository.getComments(this.pointId, new CommentsRepository.GetCommentsCallback() {
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
                callback.onCommentsRetrieved(comments);
            }
        });
    }
}
