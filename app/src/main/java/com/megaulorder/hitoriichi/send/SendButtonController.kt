package com.megaulorder.hitoriichi.send

import androidx.lifecycle.LifecycleCoroutineScope
import com.megaulorder.hitoriichi.edittext.EmojiEditTextWidget
import com.megaulorder.hitoriichi.mvi.Event
import com.megaulorder.hitoriichi.mvi.emit
import kotlinx.coroutines.flow.MutableSharedFlow

class SendButtonController(
	private val widget: SendButtonWidget,
	private val editTextWidget: EmojiEditTextWidget,
	coroutineScope: LifecycleCoroutineScope,
	eventsFlow: MutableSharedFlow<Event>
) {

	init {
		widget.setOnClickListener {
			eventsFlow.emit(
				coroutineScope,
				Event.SendButtonClicked(editTextWidget.getText())
			)
			editTextWidget.clear()
		}
	}
}