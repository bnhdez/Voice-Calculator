package com.example.voicecalculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.voicecalculator.databinding.ActivityResultadoFinalBinding
import net.objecthunter.exp4j.ExpressionBuilder

class ResultadoFinal : AppCompatActivity() {

    private lateinit var binding3: ActivityResultadoFinalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding3 = ActivityResultadoFinalBinding.inflate(layoutInflater)
        setContentView(binding3.root)

        // Obtener el resultado del cálculo o del texto editado de las actividades anteriores
        val receivedTextCalculo = intent.getStringExtra("texto_calculo")
        val receivedTextEditando = intent.getStringExtra("texto_editando")

        // Mostrar el resultado en el TextView correspondiente
        if (receivedTextCalculo != null) {
            binding3.texto2.text = receivedTextCalculo
        } else if (receivedTextEditando != null) {
            binding3.texto2.text = receivedTextEditando
        }

        // Configurar el botón Refresh para regresar a la actividad principal (MainActivity)
        binding3.refresh.setOnClickListener{
            openMain()
        }

        // Configurar el botón Restart para regresar a la actividad principal (MainActivity)
        binding3.restart.setOnClickListener {
            openMain()
        }

    }

    // Función para abrir la actividad principal (MainActivity)
    private fun openMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}