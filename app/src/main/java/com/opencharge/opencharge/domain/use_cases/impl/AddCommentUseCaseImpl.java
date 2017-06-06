package com.opencharge.opencharge.domain.use_cases.impl;


import android.content.Context;
import android.util.Log;

import com.opencharge.opencharge.domain.Entities.Comment;
import com.opencharge.opencharge.domain.Entities.User;
import com.opencharge.opencharge.domain.Factories.CommentFactory;
import com.opencharge.opencharge.domain.executor.Executor;
import com.opencharge.opencharge.domain.executor.MainThread;
import com.opencharge.opencharge.domain.repository.CommentsRepository;
import com.opencharge.opencharge.domain.use_cases.AddCommentUseCase;
import com.opencharge.opencharge.domain.use_cases.GetCurrentUserUseCase;
import com.opencharge.opencharge.domain.use_cases.base.AbstractUseCase;
import com.opencharge.opencharge.presentation.locators.UseCasesLocator;

/**
 * Created by DmnT on 26/04/2017.
 */

public class AddCommentUseCaseImpl extends AbstractUseCase implements AddCommentUseCase {

    private AddCommentUseCase.Callback callback;
    private CommentsRepository commentsRepository;
    private Context context;

    private String point_id;
    private String text;
    private String data;

    public AddCommentUseCaseImpl(Executor threadExecutor,
                                   MainThread mainThread,
                                   CommentsRepository commentsRepository,
                                   Context context,
                                   AddCommentUseCase.Callback callback) {
        super(threadExecutor, mainThread);
        this.commentsRepository = commentsRepository;
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void setCommentParameters(String point_id, String text, String data){
        this.point_id = point_id;
        this.text = text;
        this.data = data;
    }

    @Override
    public void run() {
        UseCasesLocator useCasesLocator = UseCasesLocator.getInstance();
        GetCurrentUserUseCase getCurrentUserUseCase = useCasesLocator.getGetCurrentUserUseCase(context, new GetCurrentUserUseCase.Callback() {
            @Override
            public void onCurrentUserRetrieved(final User currentUser) {
                final Comment comment = CommentFactory.getInstance().createNewComment(currentUser.getId(), text, data);
                //System.out.println("Created Comment: "+ comment.toString());
                commentsRepository.createComment(point_id, comment, new CommentsRepository.CreateCommentCallback(){
                    @Override
                    public void onCommentCreated(String id) {
                        comment.setId(id);
                        postComment(id);
                    }

                    @Override
                    public void onError() {
                        Log.e("ERROR: ", "on create comment");
                    }
                });
            }
        });
        getCurrentUserUseCase.execute();
    }

    private void postComment(final String id) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onCommentAdded(id);
            }
        });
    }
}