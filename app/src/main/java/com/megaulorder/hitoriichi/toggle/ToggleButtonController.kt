package com.megaulorder.hitoriichi.toggle

import androidx.lifecycle.LifecycleCoroutineScope
import com.megaulorder.hitoriichi.mvi.Effect
import com.megaulorder.hitoriichi.mvi.Event
import com.megaulorder.hitoriichi.mvi.emit
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect

class ToggleButtonController(
	private val widget: ToggleButtonWidget,
	private val coroutineScope: LifecycleCoroutineScope,
	private val eventsFlow: MutableSharedFlow<Event>,
	private val effectsFlow: MutableSharedFlow<Effect>
) {

	private var isEmojiIconShown: Boolean = true

	init {
		widget.setOnClickListener {
			if (isEmojiIconShown) {
				isEmojiIconShown = false
				widget.toggle(!isEmojiIconShown)
				eventsFlow.emit(coroutineScope, Event.ToggleEmojiButtonClicked)
			} else {
				isEmojiIconShown = true
				widget.toggle(!isEmojiIconShown)
				eventsFlow.emit(coroutineScope, Event.ToggleKeyboardButtonClicked)
			}
		}

		coroutineScope.launchWhenResumed {
			effectsFlow.collect {
				if (it is Effect.ShowEmojiButton) {
					widget.toggle(false)
					isEmojiIconShown = true
				}
			}
		}
	}
}
