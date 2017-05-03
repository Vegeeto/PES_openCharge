package com.opencharge.opencharge.domain.use_cases;

import com.opencharge.opencharge.domain.Entities.Comment;

/**
 * Created by Oriol on 3/5/2017.
 */

public interface CommentsListUseCase {
    interface Callback {
        void onPointsRetrieved(Comment[] comments);
    }
}
