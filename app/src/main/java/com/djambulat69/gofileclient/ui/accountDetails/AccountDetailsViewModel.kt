package com.djambulat69.gofileclient.ui.accountDetails

import androidx.lifecycle.ViewModel
import com.djambulat69.gofileclient.network.GoFileApiServiceHelper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject

class AccountDetailsViewModel : ViewModel() {

    private val apiServiceHelper = GoFileApiServiceHelper()

    private val state = BehaviorSubject.create<AccountDetailsState>()

    private val disposable = CompositeDisposable()
    private val intentsDisposable = CompositeDisposable()

    private var view: AccountDetailsView? = null

    init {
        subscribeOnViewState()
        loadDetails()
    }

    override fun onCleared() {
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
        if (!intentsDisposable.isDisposed) {
            intentsDisposable.dispose()
        }
    }

    fun bind(view: AccountDetailsView) {
        this.view = view
        subscribeOnLoadDetailsIntent(view.loadDetailsIntent())
        view.render(state.value)
    }

    fun unbind() {
        this.view = null
        intentsDisposable.clear()
    }

    private fun subscribeOnLoadDetailsIntent(loadDetailsIntent: Observable<Unit>) {
        intentsDisposable.add(
            loadDetailsIntent
                .observeOn(Schedulers.io())
                .flatMap { loadDetailsState() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { newState ->
                    updateState(newState)
                }
        )
    }

    private fun loadDetails() {
        disposable.add(
            loadDetailsState()
                .subscribe { updateState(it) }
        )
    }

    private fun loadDetailsState(): Observable<AccountDetailsState> {
        return apiServiceHelper.getAccountDetails()
            .toObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { response ->
                AccountDetailsState(details = response.accountDetails)
            }
            .onErrorReturn { e ->
                AccountDetailsState(error = e)
            }
            .startWithItem(AccountDetailsState(isLoading = true))
    }

    private fun updateState(newState: AccountDetailsState) {
        state.onNext(newState)
    }

    private fun subscribeOnViewState() {
        disposable.add(
            state
                .subscribe { view?.render(it) }
        )
    }

}
