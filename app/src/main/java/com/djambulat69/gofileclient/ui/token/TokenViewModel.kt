package com.djambulat69.gofileclient.ui.token

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.djambulat69.gofileclient.network.AccountDetails
import com.djambulat69.gofileclient.network.GoFileApiServiceHelper
import com.djambulat69.gofileclient.utils.Data
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class TokenViewModel : ViewModel() {

    private val apiServiceHelper = GoFileApiServiceHelper()
    private val disposable = CompositeDisposable()

    private val _accountDetails = MutableLiveData<Data<AccountDetails>>()
    val accountDetails: LiveData<Data<AccountDetails>> get() = _accountDetails

    override fun onCleared() {
        disposable.dispose()
    }

    fun getAccountDetails() {
        disposable.add(
            apiServiceHelper.getAccountDetails()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _accountDetails.value = Data.Loading() }
                .subscribe(
                    { response ->
                        _accountDetails.value = Data.Success(response.accountDetails)
                    },
                    { e ->
                        Log.d("tag", e.stackTraceToString())
                        _accountDetails.value = Data.Failure(e.message.orEmpty())
                    }
                )
        )
    }


}
