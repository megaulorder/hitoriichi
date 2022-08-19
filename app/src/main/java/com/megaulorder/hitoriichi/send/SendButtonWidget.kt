package com.megaulorder.hitoriichi.send

import android.widget.ImageButton
import com.megaulorder.hitoriichi.ButtonWidget

class SendButtonWidget(private val view: ImageButton) : ButtonWidget {

	override fun setOnClickListener(listener: () -> Unit) {
		view.setOnClickListener { listener.invoke() }
	}
}
