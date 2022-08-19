package com.megaulorder.hitoriichi.keyboard

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import com.megaulorder.hitoriichi.R
import com.megaulorder.hitoriichi.Utils

private const val BUTTON_SIZE: Float = 48f
private const val PADDING: Float = 12f
private const val EMOJI_TAG = "emoji"

class EmojiKeyboardLayout(context: Context, attrs: AttributeSet? = null) :
	LinearLayout(context, attrs) {

	private val buttonSize = Utils.dpToPx(context, BUTTON_SIZE)
	private val padding = Utils.dpToPx(context, PADDING)

	init {
		orientation = VERTICAL
		gravity = Gravity.CENTER
		setBackgroundColor(resources.getColor(R.color.gray))

		addEmoji("💩")
		addEmoji("🤡")
		addEmoji("🗿")
		addEmoji("🍕")
		addBackspaceButton()
	}

	private fun addEmoji(emoji: String) {
		val button = Button(context)
		button.tag = EMOJI_TAG
		val layoutParams = LayoutParams(buttonSize, buttonSize)
		button.text = emoji
		button.textSize = 18f
		addView(button, layoutParams)
	}

	private fun addBackspaceButton() {
		val button = ImageButton(context)
		button.id = R.id.keyboard_backspace
		val layoutParams = LayoutParams(buttonSize, buttonSize)
		button.scaleType = ImageView.ScaleType.CENTER_CROP
		button.setImageResource(R.drawable.ic_backspace)
		val outValue = TypedValue()
		context.theme.resolveAttribute(
			android.R.attr.selectableItemBackgroundBorderless,
			outValue,
			true
		)
		button.setBackgroundResource(outValue.resourceId)
		button.setPadding(padding, padding, padding, padding)
		addView(button, layoutParams)
	}
}
