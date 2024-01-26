package ger.tag.nodetree.data.data_base

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


object NodeArg {
    const val NODE_TABLE = "node_table"
    const val NODE_ID = "node_id"
    const val NAME = "name"
    const val PARENT_ID = "parent_id"
}

@Entity(tableName = NodeArg.NODE_TABLE)
data class NodeDbModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = NodeArg.NODE_ID)
    val nodeId: Long,
    @ColumnInfo(name = NodeArg.NAME)
    val name: String,
    @ColumnInfo(name = NodeArg.PARENT_ID)
    val parentId: Long?,
)
















