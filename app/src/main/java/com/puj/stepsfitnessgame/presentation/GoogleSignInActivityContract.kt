package com.puj.stepsfitnessgame.presentation

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class GoogleSignInActivityContract : ActivityResultContract<Unit, Int>() {

    override fun createIntent(context: Context, input: Unit): Intent {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        val gc = GoogleSignIn.getClient(context, gso)

        if(GoogleSignIn.getLastSignedInAccount(context) == null) {
            return gc.signInIntent
        }
        return Intent()
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Int {
        TODO()
    }
}