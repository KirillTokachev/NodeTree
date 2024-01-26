package ger.tag.nodetree.data.mappers

import ger.tag.nodetree.data.data_base.NodeDbModel
import ger.tag.nodetree.model.models.Node
import javax.inject.Inject

class NodeDbMapToNode @Inject constructor() : (NodeDbModel) -> Node {

    override fun invoke(value: NodeDbModel) =
        with(value) {
            Node(
                id = nodeId,
                name = name,
                parentId = parentId
            )
        }
}