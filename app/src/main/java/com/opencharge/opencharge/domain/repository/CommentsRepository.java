package com.opencharge.opencharge.domain.repository;

import com.opencharge.opencharge.domain.Entities.Comment;

/**
 * Created by DmnT on 26/04/2017.
 */

public interface CommentsRepository {

    interface GetCommentsCallback {
        void onCommentsRetrieved(Comment[] comments);
        void onError();
    }

    interface GetCommentByIdCallback {
        void onCommentRetrieved(Comment comment);
        void onError();
    }

    interface CreateCommentCallback {
        void onCommentCreated(String id);
        void onError();
    }


    void createComment(String point_id, Comment comment, final CreateCommentCallback callback);

    void getComments(String point_id, final GetCommentsCallback callback);

}
