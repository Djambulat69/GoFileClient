package com.djambulat69.gofileclient.ui.token

import io.reactivex.rxjava3.core.Observable

interface TokenView {
    fun render(state: TokenState)
    fun makeAction(action: TokenAction)

    fun loadTokenIntent(): Observable<String>
}
