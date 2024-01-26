package ger.tag.nodetree.model.repository

import ger.tag.nodetree.model.models.Node
import kotlinx.coroutines.flow.Flow

interface NodeRepository {

    suspend fun getLastNodeId(): Long

    suspend fun saveCurrentNode(id: Long)

    suspend fun getNode(id: Long): Flow<Node>

    fun getChildes(id: Long): Flow<List<Node>>

    suspend fun addChild(node: Node)

    suspend fun deleteChildes(node: Node)
}