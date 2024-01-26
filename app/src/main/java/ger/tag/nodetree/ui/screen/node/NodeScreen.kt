@file:OptIn(ExperimentalMaterial3Api::class)

package ger.tag.nodetree.ui.screen.node

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ger.tag.nodetree.R
import ger.tag.nodetree.ui.screen.node.NodeHelper.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import ger.tag.nodetree.ui.helper.page.ErrorPage
import ger.tag.nodetree.ui.helper.page.WaitPage
import kotlinx.coroutines.flow.firstOrNull

@Composable
fun NodeScreen(
    viewModel: NodeViewModel = hiltViewModel(),
    onNav: (nav: Nav) -> Unit,
) {
    Log.e("C", "NodeScreen")
    LaunchedEffect(Unit) {
        when (val nav = viewModel.nav.firstOrNull()) {
            is Nav.ToChild -> { onNav(Nav.ToChild(nav.childId)) }
            is Nav.ToParent -> { onNav(Nav.ToParent(nav.parentId)) }
            null -> {}
        }
    }

    val state = viewModel.state.collectAsState().value

    Scaffold(
        topBar = {
            NodeToolBar(viewModel, state.node)
        },
        floatingActionButton = {
            if(state.enabledAction) {
                val hc = state.node.hashCode()
                FloatingActionButton(
                    onClick = { viewModel.addChild() },
                ) {
                    Icon(Icons.Filled.Add, stringResource(id = R.string.add_node))
                }
            }
        }
    ) { innerPadding ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (val page = state.p) {
                Page.Nodes -> {
                    BackHandler {
                        val parentId = state.node.parentId
                        if(parentId != null) {
                            viewModel.saveCurrentNodeId(state.node.parentId,
                                Nav.ToParent(state.node.parentId))
                        }
                    }
                    NodesPage(childes = state.childes, viewModel = viewModel)
                }
                is Page.Error ->  {
                    var error = stringResource(id = R.string.error)
                    val msg = page.e.message
                    if(msg != null) { error += ": $msg" }
                    ErrorPage(txt = error) {
                        viewModel.getNode()
                    }
                }
                is Page.Wait -> {
                    var result = stringResource(id = R.string.load_data)
                    if(page.txtR != null) result = stringResource(id = page.txtR)
                    if(page.txt != null) result = page.txt
                    WaitPage(result)
                }
                null -> {}
            }
        }
    }
}














