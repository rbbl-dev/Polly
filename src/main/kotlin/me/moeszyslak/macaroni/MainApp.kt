package me.moeszyslak.macaroni

import com.gitlab.kordlib.kordx.emoji.Emojis
import me.jakejmattson.discordkt.api.dsl.bot
import me.jakejmattson.discordkt.api.extensions.*
import java.awt.Color

suspend fun main(args: Array<String>) {
    val token = args.firstOrNull()
    require(token != null) { "Expected the bot token as a command line argument!" }

    bot(token) {
        prefix {
            "+"
        }

        configure {
            allowMentionPrefix = true
            generateCommandDocs = true
            showStartupLog = true
            requiresGuild = true
            commandReaction = Emojis.eyes
            theme = Color(0x00BFFF)
        }

        mentionEmbed {
            title = "Hello World"
            color = it.discord.configuration.theme

            author {
                with(it.author) {
                    icon = avatar.url
                    name = tag
                    url = profileLink
                }
            }

            thumbnail {
                url = api.getSelf().avatar.url
            }

            footer {
                val versions = it.discord.versions
                text = "${versions.library} - ${versions.kord} - ${versions.kotlin}"
            }

            addField("Prefix", it.prefix())
        }

        permissions {
            true
        }
    }
}