package ger.tag.nodetree.ui.screen.node

import ger.tag.nodetree.data.data_base.NodeDbModel
import ger.tag.nodetree.model.models.Node
import ger.tag.nodetree.ui.helper.BaseVMNav
import ger.tag.nodetree.ui.helper.BaseVMState

class NodeHelper {
    sealed class Page {
        class Wait(val txtR: Int? = null, val txt: String? = null): Page()
        class Error(val e: Throwable): Page()
        object Nodes: Page()
    }

    data class State(
        val p: Page? = null,

        val node: Node = Node(0, "ROOT", null),
        val childes: List<Node> = listOf(),
        val enabledAction: Boolean = true
    ): BaseVMState

    sealed class Nav: BaseVMNav {
        class ToChild(val childId: Long): Nav()
        class ToParent(val parentId: Long): Nav()
    }
}