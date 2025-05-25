package com.example.spike.preferences

import android.content.Context
import com.example.spike.data.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class UserPreference(context: Context) {
    private val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveUser(user: User) {
        val users = getAllUsers().toMutableList()
        users.add(user)
        val json = gson.toJson(users)
        prefs.edit().putString("users", json).apply()
    }

    fun getAllUsers(): List<User> {
        val json = prefs.getString("users", "[]")
        val type = object : TypeToken<List<User>>() {}.type
        return gson.fromJson(json, type)
    }

    fun getUser(username: String): User? {
        val users = getAllUsers()
        return users.find { it.username.equals(username, ignoreCase = true) }
    }
}
