package com.roselia

import com.roselia.functions.utils.DiscordRpc

fun main() {
    val presence = DiscordRpc.RichPresence().apply {
        state = ""
        details = "Roselia oynuyor"
        startTimestamp = System.currentTimeMillis() / 1000
        largeImageKey = "roselialogo"
        largeImageText = "Roselia Gaming Software"
    }

    DiscordRpc.initialize("APPLICATION_ID", null, true, null)
    DiscordRpc.updatePresence(presence)
    println("initialized discord rpc")

    while (true) {
        DiscordRpc.runCallbacks()
        Thread.sleep(2000)
    }
}