package com.megaulorder.hitoriichi.messages

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MessagesWidget(private val list: RecyclerView) {

	private val adapter = MessagesAdapter()

	init {
		list.adapter = adapter
		list.layoutManager = LinearLayoutManager(list.context, RecyclerView.VERTICAL, false)
	}

	fun add(message: String) {
		adapter.add(message)
		list.scrollToPosition(adapter.itemCount - 1)
	}
}
