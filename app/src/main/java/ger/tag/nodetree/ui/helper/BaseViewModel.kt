package ger.tag.nodetree.ui.helper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

interface BaseVMState
interface BaseVMNav

abstract class BaseViewModel<State: BaseVMState, Nav: BaseVMNav>: ViewModel() {
    abstract fun initState(): State

    private val initialState by lazy { initState() }
    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<State> = _state

    fun setState(state: State.()-> State) {
        _state.value = _state.value.state()
    }

    private val _nav: Channel<Nav> = Channel(Channel.CONFLATED)
    val nav = _nav.receiveAsFlow()

    fun setNav(nav: Nav) {
        viewModelScope.launch {
            _nav.send(nav)
        }
    }
}