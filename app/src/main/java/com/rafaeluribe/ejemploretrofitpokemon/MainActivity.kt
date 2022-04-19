package com.rafaeluribe.ejemploretrofitpokemon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.rafaeluribe.ejemploretrofitpokemon.databinding.ActivityMainBinding
import com.rafaeluribe.ejemploretrofitpokemon.retrofit.Pokemon
import com.rafaeluribe.ejemploretrofitpokemon.retrofit.PokemonApiService
import com.rafaeluribe.ejemploretrofitpokemon.retrofit.RestEngine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Call

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBuscar.setOnClickListener {
            if (binding.txtPokemon.text.toString() != ""){
                binding.progressBar.visibility = View.VISIBLE
                invocarAPI(binding.txtPokemon.text.toString())
            }
            else{
                Toast.makeText(applicationContext,
                    "Ingrese un pokemon",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun invocarAPI(pokemon: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val llamada: PokemonApiService = RestEngine.getRestEngine().create(PokemonApiService::class.java)
            val resultado: Call<Pokemon> = llamada.obtenerPokemon(pokemon)
            val p:Pokemon? = resultado.execute().body()

            if (p != null){
                runOnUiThread {
                    binding.txtBaseEsperience.text = p.base_experience.toString()
                    binding.txtid.text = p.id.toString()
                    binding.txtName.text = p.name
                    binding.txtSprite.text = "SPRITES: \n"
                    binding.txtSprite.append(p.sprites.back_default + "\n")
                    binding.txtSprite.append(p.sprites.back_female + "\n")
                    binding.txtSprite.append(p.sprites.back_shiny + "\n")
                    binding.txtSprite.append(p.sprites.back_shiny_female + "\n")
                    binding.txtSprite.append(p.sprites.front_default + "\n")
                    binding.txtSprite.append(p.sprites.front_female + "\n")
                    binding.txtSprite.append(p.sprites.front_shiny + "\n")
                    binding.txtSprite.append(p.sprites.front_shiny_female + "\n")

                    CoroutineScope(Dispatchers.IO).launch {
                        val x: Int = async {
                            construirImagenes(p)
                        }.await()
                    }

                    runOnUiThread { binding.progressBar.visibility = View.GONE }
                }
            }
            else{
                runOnUiThread { Toast.makeText(applicationContext,
                    "No se encontraron resultados...",
                    Toast.LENGTH_SHORT).show()
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    fun construirImagenes(p: Pokemon): Int{
        runOnUiThread {
            Glide.with(applicationContext)
                .load(p.sprites.back_default)
                .override(400, 400)
                .into(binding.imageView1)
        }

        runOnUiThread {
            Glide.with(applicationContext)
                .load(p.sprites.back_default)
                .override(400, 400)
                .into(binding.imageView2)
        }

        runOnUiThread {
            Glide.with(applicationContext)
                .load(p.sprites.back_default)
                .override(400, 400)
                .into(binding.imageView3)
        }

        runOnUiThread {
            Glide.with(applicationContext)
                .load(p.sprites.back_default)
                .override(400, 400)
                .into(binding.imageView4)
        }
        return 1
    }
}