package com.opencharge.opencharge.domain.use_cases.base;

/**
 * Created by ferran on 15/3/17.
 */

public interface UseCase {
    /**
     * This is the main method that starts an interactor. It will make sure that the interactor operation is done on a
     * background thread.
     */
    void execute();
}
