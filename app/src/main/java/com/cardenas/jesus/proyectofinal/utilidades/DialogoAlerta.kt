package com.cardenas.jesus.proyectofinal.utilidades

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class DialogoAlerta(var mensaje : String) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var builder = AlertDialog.Builder(context!!)
        builder.setMessage(mensaje)
            .setTitle("Error")
            .setPositiveButton("Ok"){dialog, _ ->
                dialog.cancel()
            }
        return builder.create()
    }
}