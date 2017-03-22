package com.opencharge.opencharge.domain.executor;

import com.opencharge.opencharge.domain.use_cases.base.AbstractUseCase;

/**
 * This executor is responsible for running usecase on background threads.
 * <p/>
 */
public interface Executor {

    /**
     * This method should call the usecase's run method and thus start the usecase. This should be called
     * on a background thread as usecases might do lengthy operations.
     *
     * @param useCase The use case to run.
     */
    void execute(final AbstractUseCase useCase);
}
