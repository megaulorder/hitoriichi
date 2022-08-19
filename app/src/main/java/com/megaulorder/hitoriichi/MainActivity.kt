package com.megaulorder.hitoriichi

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import com.megaulorder.hitoriichi.edittext.EmojiEditTextController
import com.megaulorder.hitoriichi.edittext.EmojiEditTextWidget
import com.megaulorder.hitoriichi.keyboard.EmojiKeyboardLayout
import com.megaulorder.hitoriichi.keyboard.EmojiKeyboardController
import com.megaulorder.hitoriichi.keyboard.emoji.EmojiKeyboardPopupWidget
import com.megaulorder.hitoriichi.toggle.ToggleButtonController
import com.megaulorder.hitoriichi.toggle.ToggleButtonWidget
import com.megaulorder.hitoriichi.messages.MessagesController
import com.megaulorder.hitoriichi.messages.MessagesWidget
import com.megaulorder.hitoriichi.mvi.Effect
import com.megaulorder.hitoriichi.mvi.Event
import com.megaulorder.hitoriichi.mvi.Reducer
import com.megaulorder.hitoriichi.send.SendButtonController
import com.megaulorder.hitoriichi.send.SendButtonWidget
import kotlinx.coroutines.flow.MutableSharedFlow

class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		val eventsFlow = MutableSharedFlow<Event>()
		val effectsFlow = MutableSharedFlow<Effect>()
		val reducer = Reducer(lifecycle.coroutineScope, eventsFlow, effectsFlow)

		val keyboardButtonController =
			ToggleButtonController(
				widget = ToggleButtonWidget(findViewById(R.id.chat_emoji)),
				coroutineScope = lifecycle.coroutineScope,
				eventsFlow = eventsFlow,
				effectsFlow = effectsFlow
			)

		val editText = findViewById<EditText>(R.id.chat_edit_text)
		val editTextWidget = EmojiEditTextWidget(editText)
		val editTextController =
			EmojiEditTextController(
				editTextWidget,
				lifecycle.coroutineScope,
				eventsFlow,
				effectsFlow
			)

		val emojiKeyboardWidget =
			EmojiKeyboardPopupWidget(
				EmojiKeyboardLayout(this),
				editText,
				this,
				findViewById(R.id.rootview),
			)

		val emojiKeyboardController =
			EmojiKeyboardController(
				edittextWidget = editTextWidget,
				emojiKeyboardWidget = emojiKeyboardWidget,
				coroutineScope = lifecycle.coroutineScope,
				eventsFlow = eventsFlow,
				effectsFlow = effectsFlow
			)

		val sendButtonController = SendButtonController(
			widget = SendButtonWidget(findViewById(R.id.chat_send)),
			editTextWidget = editTextWidget,
			coroutineScope = lifecycle.coroutineScope,
			eventsFlow = eventsFlow
		)

		val messagesController = MessagesController(
			MessagesWidget(findViewById(R.id.messages)),
			lifecycle.coroutineScope,
			effectsFlow
		)
	}
}
