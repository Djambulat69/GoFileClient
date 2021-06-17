package com.djambulat69.gofileclient.ui.token

import com.djambulat69.gofileclient.network.AccountDetails

sealed class TokenAction {
    class SaveDetailsAndStartMainActivity(val details: AccountDetails) : TokenAction()
}
