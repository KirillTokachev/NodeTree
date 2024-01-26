package ger.tag.nodetree.data.repository

import ger.tag.nodetree.data.helper.DoNotFindNode
import ger.tag.nodetree.data.data_base.NodeDao
import ger.tag.nodetree.data.data_base.NodeDbModel
import ger.tag.nodetree.data.mappers.NodeDbMapToNode
import ger.tag.nodetree.data.mappers.NodeMapToNodeDb
import ger.tag.nodetree.data.source.NodeStore
import ger.tag.nodetree.model.models.Node
import ger.tag.nodetree.model.repository.NodeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NodeRepositoryImpl @Inject constructor(
    private val nodeDao: NodeDao,
    private val nodeStore: NodeStore,
    private val nodeMapToNodeDb: NodeMapToNodeDb,
    private val nodeDbMapToNode: NodeDbMapToNode
): NodeRepository {

    override suspend fun getLastNodeId(): Long {
        return nodeStore.getNodeId().firstOrNull() ?: throw NullPointerException()
    }

    override suspend fun saveCurrentNode(id: Long) {
        nodeStore.saveNodeId(id)
    }

    override suspend fun getNode(id: Long): Flow<Node> {
        return flow {
            nodeDao.getNode(id).collect { node->
                if(id == 1L && node == null) {
                    nodeDao.insertNode(NodeDbModel(1L, "ROOT", null))
                }
                else if(node == null) throw DoNotFindNode()
                else { emit(nodeDbMapToNode(node)) }
            }
        }
    }

    override fun getChildes(id: Long): Flow<List<Node>> {
        return nodeDao.getChildes(id).map {
            it.map(nodeDbMapToNode)
        }
    }

    override suspend fun addChild(node: Node) {
        nodeDao.insertNode(nodeMapToNodeDb(node))
    }

    override suspend fun deleteChildes(node: Node) {
        nodeDao.deleteNode(nodeMapToNodeDb(node))
    }
}






















