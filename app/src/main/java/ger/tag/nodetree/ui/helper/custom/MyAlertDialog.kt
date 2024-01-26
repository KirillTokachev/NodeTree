package ger.tag.nodetree.ui.helper.custom

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import ger.tag.nodetree.R

@Composable
fun MyAlertDialog(
    openDialog: MutableState<Boolean>,
    title: String = "",
    txt: String,
    okTxt: String = "OK",
    cancelTxt: String = stringResource(id = R.string.cancel).uppercase(),
    onOk: ()->Unit,
    onCancel: ()->Unit = { }
) {

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = { Text(title) },
            text = {
                Text(text = txt)
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        onOk()
                    }
                ) {
                    Text(okTxt)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        onCancel()
                    }
                ) {
                    Text(cancelTxt)
                }
            }
        )
    }
}