package com.megaulorder.hitoriichi.edittext

import android.widget.EditText
import com.megaulorder.hitoriichi.EmojiRegexUtil

class EmojiEditTextWidget(private val view: EditText) {

	fun requestFocus() {
		view.requestFocus()
	}

	fun getText(): String = view.text.toString().trim()

	fun type(char: String) {
		view.text.append(char)
	}

	fun removeLastSymbol() {
		val index = view.selectionStart
		val str = view.text.toString().trim()

		if (str.length < 2) {
			view.text.delete(index - 1, index)
		} else if (index > 0) {
			val lastText = str.substring(index - 2, index)
			if (EmojiRegexUtil.checkEmoji(lastText)) {
				view.text.delete(index - 2, index)
			} else {
				view.text.delete(index - 1, index)
			}
		}
	}

	fun clear() {
		view.text.clear()
	}

	fun setOnClickListener(listener: () -> Unit) {
		view.setOnClickListener { listener.invoke() }
	}
}
