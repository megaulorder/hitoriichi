package com.megaulorder.hitoriichi.mvi

import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect

class Reducer(
	private val coroutineScope: LifecycleCoroutineScope,
	private val eventFlow: MutableSharedFlow<Event>,
	private val effectFlow: MutableSharedFlow<Effect>
) {
	init {
		coroutineScope.launchWhenResumed { eventFlow.collect(::handleEvent) }
	}

	private fun handleEvent(event: Event) {
		when (event) {
			is Event.ToggleEmojiButtonClicked -> {
				emitEffect(Effect.ShowKeyboardButton)
				emitEffect(Effect.ShowEmojiKeyboard)
			}
			is Event.ToggleKeyboardButtonClicked -> {
				emitEffect(Effect.HideEmojiKeyboard)
				emitEffect(Effect.ShowEmojiButton)

			}
			is Event.InputFieldClicked -> {
				emitEffect(Effect.HideEmojiKeyboard)
				emitEffect(Effect.ShowEmojiButton)
			}
			is Event.EmojiClicked -> emitEffect(Effect.TypeEmoji(event.emoji))
			is Event.EmojiDeleteButtonClicked -> emitEffect(Effect.RemoveEmoji)
			is Event.SendButtonClicked -> emitEffect(Effect.SendMessage(event.message))
		}
	}

	private fun emitEffect(effect: Effect) {
		effectFlow.emit(coroutineScope, effect)
	}
}
