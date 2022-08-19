package com.megaulorder.hitoriichi.messages

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.megaulorder.hitoriichi.R

class MessagesAdapter : RecyclerView.Adapter<MessagesViewHolder>() {
	private val messages = mutableListOf<String>()

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessagesViewHolder {
		val layoutInflater = LayoutInflater.from(parent.context)
		return MessagesViewHolder(layoutInflater.inflate(R.layout.message_item, parent, false))
	}

	override fun onBindViewHolder(chatViewHolder: MessagesViewHolder, position: Int) {
		val text = messages[position]
		chatViewHolder.textView.text = text
	}

	override fun getItemCount() = messages.size

	fun add(text: String) {
		messages.add(text)
		notifyItemInserted(messages.size - 1)
	}
}

class MessagesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
	val textView: TextView = view.findViewById(R.id.message)
}
