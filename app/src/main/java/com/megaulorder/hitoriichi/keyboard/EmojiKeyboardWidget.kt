package com.megaulorder.hitoriichi.keyboard

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupWindow
import androidx.core.view.ViewCompat
import com.megaulorder.hitoriichi.R
import com.megaulorder.hitoriichi.Utils

private const val MIN_KEYBOARD_HEIGHT = 50
private const val APPLY_WINDOW_INSETS_DURATION = 250
private const val EMOJI_TAG = "emoji"

class EmojiKeyboardPopupWidget(
	private val layout: EmojiKeyboardLayout,
	private val editText: EditText,
	internal val activity: Activity,
) {
	private val rootView: View = activity.findViewById<View>(android.R.id.content).rootView
	private val popupWindow: PopupWindow = PopupWindow(activity)

	private var popupWindowHeight: Int = 0
	private var systemKeyboardHeight: Int = 0
	private var delay: Int = 0
	private var isToOpen: Boolean = false

	init {
		popupWindow.contentView = layout
		popupWindow.inputMethodMode = PopupWindow.INPUT_METHOD_NOT_NEEDED
		popupWindow.setBackgroundDrawable(BitmapDrawable(activity.resources, null as Bitmap?))

		if (rootView.parent != null) {
			start()
		}

		rootView.addOnAttachStateChangeListener(PopupOnAttachStateChangeListener(this))
	}

	fun start() {
		activity.window.decorView.setOnApplyWindowInsetsListener(
			PopUpOnApplyWindowInsetsListener(this)
		)
	}

	fun stop() {
		popupWindow.dismiss()
		activity.window.decorView.setOnApplyWindowInsetsListener(null)
		popupWindow.setOnDismissListener(null)
	}

	fun show() {
		if (!popupWindow.isShowing) {
			isToOpen = true
			start()
			ViewCompat.requestApplyInsets(activity.window.decorView)
			val inputMethodManager =
				activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

			inputMethodManager.showSoftInput(editText, InputMethodManager.RESULT_UNCHANGED_SHOWN)
//			showAtLocation()
		}
	}

	private fun showAtLocation() {
		isToOpen = false

		editText.postDelayed(
			{
				popupWindow.showAtLocation(
					rootView, Gravity.NO_GRAVITY, 0,
					Utils.getHeight(activity) + popupWindowHeight,
				)
			},
			delay.toLong(),
		)
	}

	fun hide() {
		popupWindow.dismiss()
	}

	fun setWidthAndHeight(keyboardHeight: Int) {
		if (popupWindowHeight > 0 && popupWindow.height != popupWindowHeight) {
			popupWindow.height = popupWindowHeight
		} else if (popupWindowHeight == 0 && popupWindow.height != keyboardHeight) {
			popupWindow.height = keyboardHeight
		}
		delay = if (systemKeyboardHeight != keyboardHeight) {
			systemKeyboardHeight = keyboardHeight
			APPLY_WINDOW_INSETS_DURATION
		} else {
			0
		}

		val width = Utils.getWidth(activity)
		if (popupWindow.width != width) {
			popupWindow.width = width
		}

		if (isToOpen) {
			showAtLocation()
		}
	}

	fun setButtonOnClickListener(listener: (emoji: String) -> Unit) {
		getButtonsByTag(layout, EMOJI_TAG).forEach { button ->
			button.setOnClickListener { listener.invoke(button.text.toString()) }
		}
	}

	fun setDeleteButtonOnClickListener(listener: () -> Unit) {
		layout.findViewById<ImageView>(R.id.keyboard_backspace)
			.setOnClickListener { listener.invoke() }
	}

	private fun getButtonsByTag(root: ViewGroup, tag: String): ArrayList<Button> {
		val views = ArrayList<Button>()
		val childCount = root.childCount
		for (i in 0 until childCount) {
			val child = root.getChildAt(i)
			if (child is ViewGroup) {
				views.addAll(getButtonsByTag(child, tag))
			}
			val childTag = child.tag
			if (childTag != null && childTag == tag) {
				views.add(child as Button)
			}
		}
		return views
	}
}

class PopupOnAttachStateChangeListener(private val emojiPopup: EmojiKeyboardPopupWidget) :
	View.OnAttachStateChangeListener {

	override fun onViewAttachedToWindow(v: View) {
		emojiPopup.start()
	}

	override fun onViewDetachedFromWindow(v: View) {
		emojiPopup.stop()
		v.removeOnAttachStateChangeListener(this)
	}
}

class PopUpOnApplyWindowInsetsListener(private val emojiPopup: EmojiKeyboardPopupWidget) :
	View.OnApplyWindowInsetsListener {
	private var previousOffset = 0

	override fun onApplyWindowInsets(v: View, insets: WindowInsets): WindowInsets {
		val systemWindowInsetBottom = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
			insets.getInsets(WindowInsets.Type.ime()).bottom
		} else {
			insets.systemWindowInsetBottom
		}

		val stableInsetBottom = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
			insets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars()).bottom
		} else {
			insets.stableInsetBottom
		}

		val offset = if (systemWindowInsetBottom < stableInsetBottom) {
			systemWindowInsetBottom
		} else {
			systemWindowInsetBottom - stableInsetBottom
		}

		if (offset != previousOffset || offset == 0) {
			previousOffset = offset
			if (offset > Utils.dpToPx(emojiPopup.activity, MIN_KEYBOARD_HEIGHT.toFloat())) {
				emojiPopup.setWidthAndHeight(offset)
			} else {
				emojiPopup.hide()
			}
		}
		return emojiPopup.activity.window.decorView.onApplyWindowInsets(insets)
	}
}
