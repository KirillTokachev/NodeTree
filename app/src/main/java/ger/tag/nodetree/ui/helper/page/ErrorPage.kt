package ger.tag.nodetree.ui.helper.page

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ger.tag.nodetree.R

@Composable
fun ErrorPage(
    txt: String,
    onClick: ()->Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        Arrangement.Center, Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
            text = txt,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.error
        )
        Box(Modifier.height(8.dp))
        Button(onClick = { onClick() }) {
            Text(text = stringResource(id = R.string.try_again))
        }
    }
}