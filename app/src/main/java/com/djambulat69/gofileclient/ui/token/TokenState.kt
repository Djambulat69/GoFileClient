package com.djambulat69.gofileclient.ui.token

import com.djambulat69.gofileclient.network.AccountDetails

class TokenState(
    val isLoading: Boolean = false,
    val details: AccountDetails? = null,
    val error: Throwable? = null
)
