package com.opencharge.opencharge.domain.repository;

import com.opencharge.opencharge.domain.Entities.Comment;

/**
 * Created by DmnT on 26/04/2017.
 */

public interface CommentsRepository {

    public interface GetCommentsCallback {
        public void onCommentsRetrieved(Comment[] comments);
        public void onError();
    }

    public interface GetCommentByIdCallback {
        public void onPointRetrieved(Comment comment);
        public void onError();
    }

    public interface CreateCommentCallback {
        public void onCommentCreated(String id);

        public void onError();
    }


    void createComment(String point_id, Comment point, final CreateCommentCallback callback);
    void getComments(String point_id, final GetCommentsCallback callback);
    //void getCommentById(String commentId, final GetCommentByIdCallback callback);
}
