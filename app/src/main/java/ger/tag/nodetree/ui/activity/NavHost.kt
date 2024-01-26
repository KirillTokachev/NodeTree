package ger.tag.nodetree.ui.activity

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ger.tag.nodetree.ui.activity.Navigation.Args.NODE_ID
import ger.tag.nodetree.ui.activity.Navigation.Routes.NODE
import ger.tag.nodetree.ui.screen.node.NodeHelper
import ger.tag.nodetree.ui.screen.node.NodeScreen

@Composable
fun NavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = "$NODE/{$NODE_ID}"
    ) {
        composable(
            route = "$NODE/{$NODE_ID}",
            arguments = listOf(
                navArgument(NODE_ID) {
                    type = NavType.LongType
                    defaultValue = 1L
                }
            )
        ) { bse ->
            NodeScreen(
                onNav = { nav ->
                    when(nav) {
                        is NodeHelper.Nav.ToChild -> { navController.navigate("$NODE/${nav.childId}") }
                        is NodeHelper.Nav.ToParent -> {
                            navController.navigate("$NODE/${nav.parentId}") {
                                popUpTo("$NODE/${nav.parentId}") { inclusive = true }
                            }
                        }
                    }
                }
            )
        }
    }
}

object Navigation {
    object Args {
        const val NODE_ID = "NodeId"
    }

    object Routes {
        const val NODE = "Node"
    }
}
















