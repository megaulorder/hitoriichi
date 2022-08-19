package com.megaulorder.hitoriichi.keyboard

import androidx.lifecycle.LifecycleCoroutineScope
import com.megaulorder.hitoriichi.edittext.EmojiEditTextWidget
import com.megaulorder.hitoriichi.keyboard.emoji.EmojiKeyboardPopupWidget
import com.megaulorder.hitoriichi.mvi.Effect
import com.megaulorder.hitoriichi.mvi.Event
import com.megaulorder.hitoriichi.mvi.emit
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect

class EmojiKeyboardController(
	private val edittextWidget: EmojiEditTextWidget,
	private val emojiKeyboardWidget: EmojiKeyboardPopupWidget,
	private val coroutineScope: LifecycleCoroutineScope,
	private val eventsFlow: MutableSharedFlow<Event>,
	private val effectsFlow: MutableSharedFlow<Effect>
) {

	init {
		coroutineScope.launchWhenResumed { effectsFlow.collect(::handleEffect) }

		emojiKeyboardWidget.setButtonOnClickListener {
			eventsFlow.emit(coroutineScope, Event.EmojiClicked(it))
		}

		emojiKeyboardWidget.setDeleteButtonOnClickListener {
			eventsFlow.emit(coroutineScope, Event.EmojiDeleteButtonClicked)
		}
	}

	private fun handleEffect(effect: Effect) {
		if (effect is Effect.HideEmojiKeyboard) {
			emojiKeyboardWidget.hide()
		}
		if (effect is Effect.ShowEmojiKeyboard) {
			edittextWidget.requestFocus()
			emojiKeyboardWidget.show()
		}
	}
}
