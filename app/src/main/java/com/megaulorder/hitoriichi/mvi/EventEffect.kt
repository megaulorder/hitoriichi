package com.megaulorder.hitoriichi.mvi

sealed interface Event {

	object InputFieldClicked: Event

	object ToggleEmojiButtonClicked: Event

	object ToggleKeyboardButtonClicked: Event

	object EmojiDeleteButtonClicked: Event

	data class EmojiClicked(val emoji: String): Event

	data class SendButtonClicked (val message: String): Event
}

sealed interface Effect {

	object ShowKeyboardButton: Effect

	object ShowEmojiButton: Effect

	object ShowEmojiKeyboard: Effect

	object HideEmojiKeyboard: Effect

	object RemoveEmoji: Effect

	data class TypeEmoji(val emoji: String): Effect

	data class SendMessage(val message: String): Effect
}
