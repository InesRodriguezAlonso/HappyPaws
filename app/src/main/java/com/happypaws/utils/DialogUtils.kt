package com.happypaws.utils

import android.app.AlertDialog
import android.content.Context

class DialogUtils {
    companion object {
        fun showDialogAlert(context: Context, message: String) {
            AlertDialog.Builder(context)
                    .setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton(context.getString(android.R.string.ok)) { dialog, _ -> dialog.dismiss() }
                    .create()
                    .show()

        }
    }
}
