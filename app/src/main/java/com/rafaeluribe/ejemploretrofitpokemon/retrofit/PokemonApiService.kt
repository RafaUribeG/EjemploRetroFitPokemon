package com.rafaeluribe.ejemploretrofitpokemon.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApiService {

    @GET("{nombre}")
    fun obtenerPokemon(@Path("nombre") nombre: String): Call<Pokemon>
}