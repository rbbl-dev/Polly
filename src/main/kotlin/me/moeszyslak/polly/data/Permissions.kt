package me.moeszyslak.polly.data

import dev.kord.core.any
import me.jakejmattson.discordkt.dsl.PermissionContext
import me.jakejmattson.discordkt.dsl.PermissionSet

@Suppress("unused")
enum class Permissions : PermissionSet {
    BOT_OWNER {
        override suspend fun hasPermission(context: PermissionContext) =
            context.discord.getInjectionObjects<Configuration>().botOwner == context.user.id.value.toLong()
    },
    GUILD_OWNER {
        override suspend fun hasPermission(context: PermissionContext) =
            context.getMember()?.isOwner() ?: false

    },
    STAFF {
        override suspend fun hasPermission(context: PermissionContext): Boolean {
            val guild = context.guild ?: return false
            val member = context.user.asMember(guild.id)
            val configuration = context.discord.getInjectionObjects<Configuration>()
            return member.roles.any { it.id.value == configuration[guild.id.value]!!.staffRole }
        }
    },
    NONE {
        override suspend fun hasPermission(context: PermissionContext) = true
    }
}