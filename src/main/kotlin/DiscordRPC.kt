package com.roselia.functions.utils

import com.sun.jna.*
import com.sun.jna.ptr.PointerByReference
import java.io.File

object DiscordRpc {
    interface DiscordRPC : Library {
        fun Discord_Initialize(applicationId: String, handlers: Pointer?, autoRegister: Boolean, optionalSteamId: String?)
        fun Discord_RunCallbacks()
        fun Discord_Shutdown()
        fun Discord_UpdatePresence(presence: RichPresence)

        companion object {
            val INSTANCE: DiscordRPC by lazy {
                Native.load(
                    "C:\\RoseliaGamingSoftware\\discord-rpc.dll",
                    DiscordRPC::class.java
                ) as DiscordRPC
            }
        }
    }

    @JvmStatic
    fun initialize(applicationId: String, handlers: Pointer?, autoRegister: Boolean, optionalSteamId: String?) {
        DiscordRPC.INSTANCE.Discord_Initialize(applicationId, handlers, autoRegister, optionalSteamId)
    }

    @JvmStatic
    fun runCallbacks() {
        DiscordRPC.INSTANCE.Discord_RunCallbacks()
    }

    @JvmStatic
    fun shutdown() {
        DiscordRPC.INSTANCE.Discord_Shutdown()
    }

    @JvmStatic
    fun updatePresence(presence: RichPresence) {
        DiscordRPC.INSTANCE.Discord_UpdatePresence(presence)
    }

    class RichPresence : Structure() {
        @JvmField var state: String? = null
        @JvmField var details: String? = null
        @JvmField var startTimestamp: Long = 0
        @JvmField var endTimestamp: Long = 0
        @JvmField var largeImageKey: String? = null
        @JvmField var largeImageText: String? = null
        @JvmField var smallImageKey: String? = null
        @JvmField var smallImageText: String? = null
        @JvmField var partyId: String? = null
        @JvmField var partySize: Int = 0
        @JvmField var partyMax: Int = 0
        @JvmField var matchSecret: String? = null
        @JvmField var joinSecret: String? = null
        @JvmField var spectateSecret: String? = null
        @JvmField var instance: Boolean = false

        override fun getFieldOrder(): List<String> {
            return listOf(
                "state", "details", "startTimestamp", "endTimestamp",
                "largeImageKey", "largeImageText", "smallImageKey", "smallImageText",
                "partyId", "partySize", "partyMax", "matchSecret",
                "joinSecret", "spectateSecret", "instance"
            )
        }
    }

    private fun getLibraryPath(libName: String): String {
        val resource = DiscordRpc::class.java.classLoader.getResource(libName)
            ?: throw IllegalStateException("DLL bulunamadı: $libName")
        val file = File(resource.toURI())
        return file.absolutePath
    }
}