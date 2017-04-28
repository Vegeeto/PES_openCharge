package com.opencharge.opencharge.domain.use_cases;

import com.opencharge.opencharge.domain.use_cases.base.UseCase;

import java.util.Date;

/**
 * Created by DmnT on 26/04/2017.
 */

public interface AddCommentUseCase extends UseCase {
    interface Callback {
        void onCommentAdded(String id);
    }

    public void setCommentParameters( String point_id, String autor, String text, Date data);
}
