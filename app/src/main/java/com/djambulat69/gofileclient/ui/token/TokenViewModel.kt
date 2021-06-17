package com.djambulat69.gofileclient.ui.token

import androidx.lifecycle.ViewModel
import com.djambulat69.gofileclient.network.GoFileApiServiceHelper
import com.djambulat69.gofileclient.network.TokenInterceptor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject

class TokenViewModel : ViewModel() {

    private val apiServiceHelper = GoFileApiServiceHelper()

    private val disposable = CompositeDisposable()
    private val intentsDisposable = CompositeDisposable()

    private val stateSubject = BehaviorSubject.create<TokenState>()
    private var state: TokenState?
        get() = stateSubject.value
        set(newState) {
            stateSubject.onNext(newState)
        }

    var view: TokenView? = null

    init {
        subscribeOnViewState()
    }

    override fun onCleared() {
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
        if (!intentsDisposable.isDisposed) {
            intentsDisposable.dispose()
        }
    }

    fun bind(view: TokenView) {
        this.view = view
        subscribeOnLoadTokenIntent(view.loadTokenIntent())
        renderCurrentState()
    }

    fun unbind() {
        this.view = null
        intentsDisposable.clear()
    }

    private fun renderCurrentState() {
        state?.let {
            view?.render(it)
        }
    }

    private fun subscribeOnLoadTokenIntent(loadTokenIntent: Observable<String>) {
        disposable.add(
            loadTokenIntent
                .doOnNext { token -> TokenInterceptor.token = token }
                .observeOn(Schedulers.io())
                .flatMap {
                    apiServiceHelper.getAccountDetails()
                        .toObservable()
                        .map { response -> TokenState(details = response.accountDetails) }
                        .startWithItem(TokenState(isLoading = true))
                        .onErrorReturn { e -> TokenState(error = e) }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { state ->
                    if (state.details != null) {
                        view?.makeAction(TokenAction.SaveDetailsAndStartMainActivity(state.details))
                    }
                }
                .subscribe { newState ->
                    state = newState
                }
        )
    }

    private fun subscribeOnViewState() {
        stateSubject
            .subscribe { view?.render(it) }
    }
}
