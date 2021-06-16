package com.djambulat69.gofileclient.ui.accountDetails

import com.djambulat69.gofileclient.network.AccountDetails

data class AccountDetailsState(
    val isLoading: Boolean = false,
    val details: AccountDetails? = null,
    val error: Throwable? = null
)
