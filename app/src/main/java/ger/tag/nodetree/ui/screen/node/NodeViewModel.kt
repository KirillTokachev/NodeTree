package ger.tag.nodetree.ui.screen.node

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ger.tag.nodetree.data.repository.NodeRepositoryImpl
import ger.tag.nodetree.model.models.Node
import ger.tag.nodetree.ui.activity.Navigation
import ger.tag.nodetree.ui.helper.BaseViewModel
import ger.tag.nodetree.ui.screen.node.NodeHelper.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import javax.inject.Inject

@HiltViewModel
class NodeViewModel @Inject constructor(
    savedState: SavedStateHandle,
    private val nodeRepositoryImpl: NodeRepositoryImpl
): BaseViewModel<State, Nav>() {

    private val nodeId: Long = checkNotNull(savedState[Navigation.Args.NODE_ID])
    private var job: Job? = null

    private val exception = CoroutineExceptionHandler { _, e ->
        setState { copy(p = Page.Error(e = e), enabledAction = true) }
    }

    override fun initState() = State()

    init {
        Log.d("NODE_ID", "$nodeId")
        if(nodeId == 1L) {
            viewModelScope.launch(exception) {
                val nodeId = nodeRepositoryImpl.getLastNodeId()
                if(nodeId != 1L) setNav(Nav.ToChild(nodeId))
            }
        }
        getNode()
    }

    fun saveCurrentNodeId(nodeId: Long, nav: Nav) {
        viewModelScope.launch(exception) {
            nodeRepositoryImpl.saveCurrentNode(nodeId)
            setNav(nav)
        }
    }


    fun getNode() {
        setState { copy(p = Page.Wait()) }
        job?.cancel()
        job = viewModelScope.launch(exception) {
            launch {
                nodeRepositoryImpl.getNode(nodeId).collect {
                    setState { copy(p = Page.Nodes, node = it) }
                    Log.d("GET NODE", "$it")
                }
            }
            launch {
                nodeRepositoryImpl.getChildes(nodeId).collect {
                    setState { copy(childes = it) }
                }
            }
        }
    }

    private fun getHash(nodeName: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashArray = digest.digest(nodeName.toByteArray(StandardCharsets.UTF_8))
        val hexStr = StringBuilder()

        for(i in hashArray.size-20 until hashArray.size) {
            val hex = Integer.toHexString(0xff and hashArray[i].toInt())
            if(hex.length == 1) hexStr.append('0')
            hexStr.append(hex)
        }
        return hexStr.toString()
    }

    fun addChild() {
        setState { copy(enabledAction = false) }
        viewModelScope.launch(exception) {
            delay(250)
            val nodeHash = state.value.node.hashCode() + state.value.childes.hashCode()
            val node = Node(0L, getHash(nodeHash.toString()),
                state.value.node.id)
            nodeRepositoryImpl.addChild(node)
            setState { copy(enabledAction = true) }
        }
    }

    fun deleteNodeAndChildes(node: Node) {
        setState { copy(enabledAction = false) }
        viewModelScope.launch(exception) {
            delay(250)
            nodeRepositoryImpl.deleteChildes(node)
            setState { copy(enabledAction = true) }
        }
    }

}














