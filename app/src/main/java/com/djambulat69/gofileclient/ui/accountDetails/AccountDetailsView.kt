package com.djambulat69.gofileclient.ui.accountDetails

import io.reactivex.rxjava3.core.Observable

interface AccountDetailsView {
    fun render(state: AccountDetailsState)
    fun loadDetailsIntent(): Observable<Unit>
}
