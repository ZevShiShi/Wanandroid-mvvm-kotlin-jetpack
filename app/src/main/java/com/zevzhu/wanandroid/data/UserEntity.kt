package com.zevzhu.wanandroid.data

data class UserEntity(
    val id: Int = 0,
    val admin: Boolean = false,
    val email: String? = null,
    val icon: String? = null,
    val nickname: String? = null,
    val password: String? = null,
    val publicName: String? = null,
    val token: String? = null,
    val type: Int = 0,
    val username: String? = null
){
    override fun toString(): String {
        return "UserEntity(id=$id, admin=$admin, email=$email, icon=$icon, nickname=$nickname, password=$password, publicName=$publicName, token=$token, type=$type, username=$username)"
    }
}