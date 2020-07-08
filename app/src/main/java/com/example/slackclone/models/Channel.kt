package com.example.slackclone.models

/**
Created by rawandsaeed on 7/1/20
 */
class Channel(val name: String, val descrption: String, val id: String) {
    override fun toString(): String {
        return "#$name"
    }
}