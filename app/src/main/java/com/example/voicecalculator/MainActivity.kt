package com.example.voicecalculator

import net.objecthunter.exp4j.ExpressionBuilder
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.voicecalculator.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private var text: String? = null // Variable para almacenar el texto reconocido por voz
    private var resultado: String? = null // Variable para almacenar el resultado del cálculo
    private lateinit var binding: ActivityMainBinding // Objeto para acceder a los elementos de la interfaz
    private var microphoneButtonPressed = false // Variable para controlar si se pulsó el botón de microfono
    private val speechRecognizerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val data: Intent? = result.data
            val recognizedText = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            text = recognizedText?.get(0) // Obtiene el texto reconocido y lo asigna a la variable text
            binding.indicacion.text = text // Muestra el texto reconocido en la vista indicacion
        } else if (result.resultCode == RESULT_CANCELED){
            microphoneButtonPressed = false
            // Se canceló el reconocimiento de voz
            Toast.makeText(applicationContext, "Reconocimiento de voz cancelado", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configuración del evento onClickListener para el botón de micrófono
        binding.microfono.setOnClickListener {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Diga su operación aritmética")

            try {
                speechRecognizerLauncher.launch(intent) // Inicia la actividad de reconocimiento de voz
            } catch (exp: Exception) {
                Toast.makeText(applicationContext, "Recognition not supported", Toast.LENGTH_SHORT).show()
            }

            binding.btnEditar.visibility = View.VISIBLE // Hace visible el botón de editar
            microphoneButtonPressed = true // Se ha pulsado el botón de microfono

        }

        // Configuración del evento onClickListener para el botón de editar
        binding.btnEditar.setOnClickListener{
            openEditar()
        }

        // Configuración del evento onClickListener para el botón de calcular
        binding.btnCalcular.setOnClickListener{
            if (!microphoneButtonPressed) {
                // Mostrar Toast si se pulsa el botón Calcular sin pulsar previamente el botón de microfono
                Toast.makeText(this, "Debe pulsar el botón de microfono y decir su operación al microfono antes de calcular", Toast.LENGTH_LONG).show()
            } else {
                resultado = ExpressionBuilder(text).build().evaluate().toString()// Evalúa la expresión matemática y guarda el resultado en la variable resultado
                openResultado()
            }
        }

    }
    // Función para abrir la actividad de resultado final
    private fun openResultado() {
        val intent = Intent(this, ResultadoFinal::class.java)
        intent.putExtra("texto_calculo", resultado) // Utiliza una clave diferente para el resultado del cálculo
        startActivity(intent)
    }
    // Función para abrir la actividad de edición
    private fun openEditar() {
        val intent = Intent(this, Editando::class.java)
        intent.putExtra("text", text) // Envía el texto reconocido a la actividad de edición
        startActivity(intent)
    }
}