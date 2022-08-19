package com.megaulorder.hitoriichi.toggle

import android.widget.ImageButton
import com.megaulorder.hitoriichi.ButtonWidget
import com.megaulorder.hitoriichi.R

class ToggleButtonWidget(private val view: ImageButton) : ButtonWidget {

	override fun setOnClickListener(listener: () -> Unit) {
		view.setOnClickListener { listener.invoke() }
	}

	fun toggle(isEmojiKeyboardActive: Boolean) {
		if (isEmojiKeyboardActive) {
			view.setImageResource(R.drawable.ic_keyboard)
		} else {
			view.setImageResource(R.drawable.ic_emojis)
		}
	}
}
