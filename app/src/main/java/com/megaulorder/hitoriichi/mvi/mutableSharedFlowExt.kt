package com.megaulorder.hitoriichi.mvi

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

fun <T> FlowCollector<T>.emit(coroutineScope: CoroutineScope, event: T) {
	coroutineScope.launch { emit(event) }
}
