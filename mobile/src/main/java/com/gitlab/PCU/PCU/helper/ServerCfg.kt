package com.gitlab.PCU.PCU.helper

import android.content.SharedPreferences
import android.util.ArrayMap
import org.json.JSONException
import org.json.JSONObject
import org.json.JSONTokener

/**
 * Created by tim on 18.01.17.
 */
object ServerCfg {

    fun read(sharedPreferences: SharedPreferences, serverVariable: Defaults.ServerVariable, serverID: String): ServerSettingsStore {
        val json = sharedPreferences.getString("servers", "{}")
        val jsonObject: JSONObject
        try {
            jsonObject = JSONObject(JSONTokener(json))

            if (jsonObject.has(Defaults.ServerStatic.SERVER_JSON_OBJECT_NAME)) {
                val servers = jsonObject.getJSONObject(Defaults.ServerStatic.SERVER_JSON_OBJECT_NAME)
                if (!servers.has(serverID)) {
                    val server = JSONObject()
                    servers.put(serverID, server)

                }

                val server = servers.getJSONObject(serverID)

                val name: String
                if (server.has("name")) {
                    name = server.getString("name")
                } else {
                    name = serverVariable.NAME
                }

                val ip: IP
                if (server.has("ip_a") && server.has("ip_b") && server.has("ip_c") && server.has("ip_d")) {
                    ip = IP(server.getInt("ip_a"), server.getInt("ip_b"), server.getInt("ip_c"), server.getInt("ip_d"))
                } else {
                    ip = Defaults.ServerStatic.IP
                }

                val desc: String
                if (server.has("desc")) {
                    desc = server.getString("desc")
                } else {
                    desc = serverVariable.DESC
                }

                return ServerSettingsStore(serverID, name, ip, desc)

            } else {
                jsonObject.put(Defaults.ServerStatic.SERVER_JSON_OBJECT_NAME, JSONObject().put(serverID, JSONObject()))
                return serverVariable.newServerSettingsStore()
            }
        } catch (ignored: JSONException) {
            return serverVariable.newServerSettingsStore()
        }

    }

    fun write(sharedPreferences: SharedPreferences, serverSettingsStore: ServerSettingsStore) {
        val json: String = sharedPreferences.getString("servers", "{}")
        val serverID: String = serverSettingsStore.id
        val jsonObject: JSONObject
        try {
            jsonObject = JSONObject(JSONTokener(json))


            if (jsonObject.has(Defaults.ServerStatic.SERVER_JSON_OBJECT_NAME)) {
                val servers = jsonObject.getJSONObject(Defaults.ServerStatic.SERVER_JSON_OBJECT_NAME)
                if (!servers.has(serverID)) {
                    val server = JSONObject()
                    servers.put(serverID, server)

                }

                val server = servers.getJSONObject(serverID)
                server.put("name", serverSettingsStore.name)
                val ip = serverSettingsStore.ip.int
                server.put("ip_a", ip[0])
                server.put("ip_b", ip[1])
                server.put("ip_c", ip[2])
                server.put("ip_d", ip[3])
                server.put("desc", serverSettingsStore.desc)
            } else {
                jsonObject.put(Defaults.ServerStatic.SERVER_JSON_OBJECT_NAME, JSONObject().put(serverID, JSONObject()))
            }
            sharedPreferences.edit().putString("servers", jsonObject.toString()).apply()
        } catch (ignored: JSONException) {
        }

    }

    fun list(sharedPreferences: SharedPreferences): ArrayMap<String, Pair<String, String>> {
        val json = sharedPreferences.getString("servers", "{}")
        val jsonObject: JSONObject
        try {
            jsonObject = JSONObject(JSONTokener(json))

            if (jsonObject.has(Defaults.ServerStatic.SERVER_JSON_OBJECT_NAME)) {
                val servers = jsonObject.getJSONObject(Defaults.ServerStatic.SERVER_JSON_OBJECT_NAME)
                val iterator = servers.keys()
                val stringArrayList = ArrayMap<String, Pair<String, String>>()
                while (iterator.hasNext()) {
                    val name = iterator.next()
                    try {
                        val server = servers.getJSONObject(name)
                        stringArrayList.put(name, Pair(server.getString("name"), server.getString("desc")))
                    } catch (ignored: JSONException) {
                    }

                }
                return stringArrayList
            }
        } catch (ignored: JSONException) {
        }

        return ArrayMap()
    }

    fun delete(sharedPreferences: SharedPreferences, serverID: String) {
        val json = sharedPreferences.getString("servers", "{}")
        val jsonObject: JSONObject
        try {
            jsonObject = JSONObject(JSONTokener(json))


            if (jsonObject.has(Defaults.ServerStatic.SERVER_JSON_OBJECT_NAME)) {
                val servers = jsonObject.getJSONObject(Defaults.ServerStatic.SERVER_JSON_OBJECT_NAME)
                if (servers.has(serverID)) {
                    servers.remove(serverID)
                }
            } else {
                jsonObject.put(Defaults.ServerStatic.SERVER_JSON_OBJECT_NAME, JSONObject().put(serverID, JSONObject()))
            }
            sharedPreferences.edit().putString("servers", jsonObject.toString()).apply()
        } catch (ignored: JSONException) {
        }

    }
}
