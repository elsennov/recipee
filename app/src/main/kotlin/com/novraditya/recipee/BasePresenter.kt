package com.novraditya.recipee

import com.novraditya.recipee.api.error.*
import io.reactivex.Observable
import io.reactivex.functions.Function

/**
 * Created by elsennovraditya on 12/13/16.
 */

open class BasePresenter {

    /**
     *
     *
     * @param observable
     */
    inline fun <T> handleRequestError(observable: Observable<T>,
                                      crossinline resultEmptyCheckingFunc: (obj: T?) -> Boolean): Observable<T> {
        return observable
            .flatMap {
                if (resultEmptyCheckingFunc(it)) {
                    Observable.error<T>(ResultEmptyError(Throwable()))
                } else {
                    Observable.just(it)
                }
            }
            .onErrorResumeNext(Function<Throwable, Observable<out T>> {
                if (it is ResultEmptyError) {
                    Observable.error(it)
                } else {
                    if (it.isRequestErrorNoInternet()) {
                        Observable.error(NoInternetError(it))
                    } else if (it.isRequestErrorNetwork()) {
                        Observable.error(NetworkError(it))
                    } else {
                        Observable.error(UnknownError(it))
                    }
                }
            })
    }

}
