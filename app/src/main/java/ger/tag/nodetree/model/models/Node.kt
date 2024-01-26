package ger.tag.nodetree.model.models

data class Node(
    val id: Long,
    val name: String,
    val parentId: Long?,
)