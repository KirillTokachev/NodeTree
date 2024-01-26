@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)

package ger.tag.nodetree.ui.screen.node

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ger.tag.nodetree.R
import ger.tag.nodetree.data.data_base.NodeDbModel
import ger.tag.nodetree.model.models.Node
import ger.tag.nodetree.ui.helper.custom.MyAlertDialog
import ger.tag.nodetree.ui.screen.node.NodeHelper.*

@Composable
fun NodesPage(
    childes: List<Node>,
    viewModel: NodeViewModel,
) {

    LazyColumn() {
        items(childes) { child->
            val expanded = remember { mutableStateOf(false) }
            ListItem(
                colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                headlineText = {
                    Text(text = child.name, maxLines = 1, overflow = TextOverflow.Ellipsis, fontSize = 13.sp)
                },
                modifier = Modifier
                    .combinedClickable(
                        onClick = {
                            viewModel.saveCurrentNodeId(child.id, Nav.ToChild(child.id))
                        },
                        onLongClick = { expanded.value = true }
                    )
            )
            Box(Modifier.padding(start = 8.dp)) {
                DropdownMenu(
                    expanded = expanded.value,
                    onDismissRequest = { expanded.value = false } ) {

                    val openDelete = remember { mutableStateOf(false) }
                    MyAlertDialog(
                        openDialog = openDelete,
                        txt = stringResource(id = R.string.are_you_shure) + " " +
                                stringResource(id = R.string.delete).lowercase(),
                        onOk = {
                            viewModel.deleteNodeAndChildes(child)
                            expanded.value = false
                        })
                    DropdownMenuItem(
                        leadingIcon = {
                            Icon(Icons.Filled.Delete, stringResource(id = R.string.delete))
                        },
                        text = {
                            Text(stringResource(id = R.string.delete))
                        },
                        onClick = {
                            openDelete.value = true
                        }
                    )
                }
            }
        }

        item {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )
        }
    }
}