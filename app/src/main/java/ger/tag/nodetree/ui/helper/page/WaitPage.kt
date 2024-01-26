package ger.tag.nodetree.ui.helper.page

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun WaitPage(
    text: String? = null,
    r: Int? = null
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        Arrangement.Center, Alignment.CenterHorizontally
    ) {
        val txt = when {
            text != null -> { text }
            r != null -> { stringResource(id = r) }
            else -> { "" }
        }

        Text(text = txt)
        Box(Modifier.height(8.dp))
        CircularProgressIndicator()
    }
}