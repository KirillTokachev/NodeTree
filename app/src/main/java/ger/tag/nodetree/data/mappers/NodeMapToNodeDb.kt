package ger.tag.nodetree.data.mappers

import ger.tag.nodetree.data.data_base.NodeDbModel
import ger.tag.nodetree.model.models.Node
import javax.inject.Inject

class NodeMapToNodeDb @Inject constructor() : (Node) -> NodeDbModel {

    override fun invoke(value: Node) =
        with(value) {
            NodeDbModel(
                nodeId = id,
                name = name,
                parentId = parentId
            )
        }
}