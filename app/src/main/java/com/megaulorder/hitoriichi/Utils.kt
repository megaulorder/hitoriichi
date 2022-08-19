package com.megaulorder.hitoriichi

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.Rect
import android.util.TypedValue
import kotlin.math.roundToInt

object Utils {
	fun getWidth(activity: Activity): Int {
		val rect = windowVisibleDisplayFrame(activity)
		return if (getOrientation(activity) == Configuration.ORIENTATION_PORTRAIT) rect.right else getScreenWidth(
			activity
		)
	}

	fun getHeight(activity: Activity): Int {
		return windowVisibleDisplayFrame(activity).bottom
	}

	private fun windowVisibleDisplayFrame(context: Activity): Rect {
		val result = Rect()
		context.window.decorView.getWindowVisibleDisplayFrame(result)
		return result
	}

	fun dpToPx(context: Context, dp: Float): Int {
		return (
			TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, dp,
				context.resources.displayMetrics,
			) + 0.5f
			).roundToInt()
	}

	private fun getOrientation(context: Context): Int {
		return context.resources.configuration.orientation
	}

	private fun getScreenWidth(context: Activity): Int {
		return dpToPx(context, context.resources.configuration.screenWidthDp.toFloat())
	}
}
