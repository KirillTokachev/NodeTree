package ger.tag.nodetree.data.source

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class NodeStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    companion object{
        val NODE_ID = longPreferencesKey("node_id")
    }

    suspend fun saveNodeId(nodeId: Long) {
        dataStore.edit { preferences->
            preferences[NODE_ID] = nodeId
        }
    }

    fun getNodeId(): Flow<Long> = dataStore.data.map { preferences  ->
        preferences[NODE_ID] ?: 1L
    }
}