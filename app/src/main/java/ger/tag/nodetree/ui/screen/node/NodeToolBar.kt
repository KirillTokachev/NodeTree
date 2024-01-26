@file:OptIn(ExperimentalMaterial3Api::class)

package ger.tag.nodetree.ui.screen.node

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ger.tag.nodetree.R
import ger.tag.nodetree.data.data_base.NodeDbModel
import ger.tag.nodetree.model.models.Node
import ger.tag.nodetree.ui.screen.node.NodeHelper.*

@Composable
fun NodeToolBar(
    viewModel: NodeViewModel,
    node: Node,
) {

    TopAppBar(
        title = {
            Text(
                node.name,
                fontSize = 15.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            val parentId = node.parentId
            if(parentId != null) {
                IconButton(onClick = {
                    viewModel.saveCurrentNodeId(parentId, Nav.ToParent(parentId))

                }) {
                    Icon(Icons.Filled.ArrowBack, stringResource(id = R.string.back))
                }
            }
        }
    )
}