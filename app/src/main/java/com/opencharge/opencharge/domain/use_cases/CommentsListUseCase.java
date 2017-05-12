package com.opencharge.opencharge.domain.use_cases;

import com.opencharge.opencharge.domain.Entities.Comment;
import com.opencharge.opencharge.domain.use_cases.base.UseCase;

/**
 * Created by Oriol on 3/5/2017.
 */

public interface CommentsListUseCase extends UseCase {
    interface Callback {
        void onCommentsRetrieved(Comment[] comments);
    }

    void setCommentId(String commentId);
}
