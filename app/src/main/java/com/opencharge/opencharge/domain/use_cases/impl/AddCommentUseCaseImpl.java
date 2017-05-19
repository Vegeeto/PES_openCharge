package com.opencharge.opencharge.domain.use_cases.impl;


import android.util.Log;

import com.opencharge.opencharge.domain.Entities.Comment;
import com.opencharge.opencharge.domain.Entities.FirebaseComment;
import com.opencharge.opencharge.domain.Factories.CommentFactory;
import com.opencharge.opencharge.domain.executor.Executor;
import com.opencharge.opencharge.domain.executor.MainThread;
import com.opencharge.opencharge.domain.repository.CommentsRepository;
import com.opencharge.opencharge.domain.use_cases.AddCommentUseCase;
import com.opencharge.opencharge.domain.use_cases.base.AbstractUseCase;

import java.util.Date;

/**
 * Created by DmnT on 26/04/2017.
 */

public class AddCommentUseCaseImpl extends AbstractUseCase implements AddCommentUseCase {
    private AddCommentUseCase.Callback callback;
    private CommentsRepository commentsRepository;
    private String point_id;
    private String autor;
    private String text;
    private String data;

    public AddCommentUseCaseImpl(Executor threadExecutor,
                                   MainThread mainThread,
                                   CommentsRepository commentsRepository,
                                   AddCommentUseCase.Callback callback) {
        super(threadExecutor, mainThread);

        this.commentsRepository = commentsRepository;
        this.callback = callback;
    }
    @Override
    public void setCommentParameters(String point_id, String autor, String text, String data){
        this.point_id = point_id;
        this.autor = autor;
        this.text = text;
        this.data = data;
    }

    @Override
    public void run() {
        System.out.println("Enter AddComment.run()");
        final Comment comment = CommentFactory.getInstance().createNewComment(autor, text, data);
        System.out.println("Created Comment: "+ comment.toString());
        final FirebaseComment firebaseComment = CommentFactory.getInstance().commentToFirebaseComment(comment);
        commentsRepository.createComment(point_id, firebaseComment, new CommentsRepository.CreateCommentCallback(){
            @Override
            public void onCommentCreated(String id) {
                CommentFactory.getInstance().setCommentId(comment, id);
                postComment(id);
            }

            @Override
            public void onError() {
                Log.e("ERROR: ", "on create comment");
            }
        });
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