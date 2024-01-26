package ger.tag.nodetree.data.data_base

import androidx.room.*
import ger.tag.nodetree.data.data_base.NodeArg.NODE_ID
import ger.tag.nodetree.data.data_base.NodeArg.NODE_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface NodeDao {

    @Query("SELECT * FROM $NODE_TABLE")
    fun getNodes(): Flow<List<NodeDbModel>>

    @Query("SELECT * FROM $NODE_TABLE WHERE $NODE_ID=:nodeId")
    fun getNode(nodeId: Long): Flow<NodeDbModel?>

    @Query("SELECT * FROM $NODE_TABLE WHERE ${NodeArg.PARENT_ID}=:nodeId")
    fun getChildes(nodeId: Long): Flow<List<NodeDbModel>>

    @Query("SELECT * FROM $NODE_TABLE WHERE ${NodeArg.PARENT_ID}=:nodeId")
    fun getChildesOnes(nodeId: Long): List<NodeDbModel>

    @Delete
    suspend fun deleteChildes(list: List<NodeDbModel>)

    @Insert
    suspend fun insertNode(nodeDbModel: NodeDbModel): Long

    @Update
    suspend fun updateNode(nodeDbModel: NodeDbModel)

    @Transaction
    suspend fun deleteNode(nodeDbModel: NodeDbModel) {
        val deletes = mutableListOf<NodeDbModel>()
        deletes.add(nodeDbModel)

        _getChildes(deletes, nodeDbModel.nodeId)
        deleteChildes(deletes)
    }

    private fun _getChildes(deletes: MutableList<NodeDbModel>, nodeId: Long) {
        val list = getChildesOnes(nodeId)
        deletes.addAll(list)
        list.forEach {
            _getChildes(deletes, it.nodeId)
        }
    }
}

























