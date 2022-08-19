package com.megaulorder.hitoriichi.edittext

import androidx.lifecycle.LifecycleCoroutineScope
import com.megaulorder.hitoriichi.mvi.Effect
import com.megaulorder.hitoriichi.mvi.Event
import com.megaulorder.hitoriichi.mvi.emit
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect

class EmojiEditTextController(
	private val widget: EmojiEditTextWidget,
	private val coroutineScope: LifecycleCoroutineScope,
	private val eventsFlow: MutableSharedFlow<Event>,
	private val effectsFlow: MutableSharedFlow<Effect>,
) {

	init {
		widget.setOnClickListener {
			eventsFlow.emit(coroutineScope, Event.InputFieldClicked)
		}

		coroutineScope.launchWhenResumed { effectsFlow.collect { handleEffect(it) } }
	}

	private fun handleEffect(effect: Effect) {
		if (effect is Effect.TypeEmoji) {
			widget.type(effect.emoji)
		}
		if (effect is Effect.RemoveEmoji) {
			widget.removeLastSymbol()
		}
	}
}
