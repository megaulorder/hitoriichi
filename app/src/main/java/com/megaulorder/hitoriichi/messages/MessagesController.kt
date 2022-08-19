package com.megaulorder.hitoriichi.messages

import androidx.lifecycle.LifecycleCoroutineScope
import com.megaulorder.hitoriichi.mvi.Effect
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect

class MessagesController(
	private val widget: MessagesWidget,
	coroutineScope: LifecycleCoroutineScope,
	effectsFlow: MutableSharedFlow<Effect>
) {

	init {
		coroutineScope.launchWhenResumed {
			effectsFlow.collect {
				if (it is Effect.SendMessage) {
					widget.add(it.message)
				}
			}
		}
	}
}
