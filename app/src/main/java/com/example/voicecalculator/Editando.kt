package com.example.voicecalculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.voicecalculator.databinding.ActivityEditandoBinding
import net.objecthunter.exp4j.ExpressionBuilder

class Editando : AppCompatActivity() {

    private lateinit var binding2: ActivityEditandoBinding
    private var resultado: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding2 = ActivityEditandoBinding.inflate(layoutInflater)
        setContentView(binding2.root)

        // Obtener el texto recibido desde MainActivity y establecerlo en el campo de texto
        val receivedText = intent.getStringExtra("text")
        binding2.texto.setText(receivedText)

        // Configuración del evento onClickListener para el botón AC
        binding2.btnAC.setOnClickListener{ binding2.texto.text = "" }

        // Configuración del evento onClickListener para el botón +/- (negativo/positivo)
        binding2.btnmasmenos.setOnClickListener{ binding2.texto.text = "-${binding2.texto.text}" }

        // Configuración del evento onClickListener para el botón %
        binding2.btnporcent.setOnClickListener{
            val num = binding2.texto.text.toString().toDouble()
            val porcent:Double = num / 100
            binding2.texto.text = "${porcent}"

        }

        // Configuración del evento onClickListener para el botón /
        binding2.btnentre.setOnClickListener{ binding2.texto.text = "${binding2.texto.text} / " }

        // Configuración del evento onClickListener para los botones numéricos
        binding2.btn7.setOnClickListener{ numeroPresionado("7") }
        binding2.btn8.setOnClickListener{ numeroPresionado("8") }
        binding2.btn9.setOnClickListener{ numeroPresionado("9") }
        binding2.btnmult.setOnClickListener{ binding2.texto.text = "${binding2.texto.text} * " }
        binding2.btn4.setOnClickListener{ numeroPresionado("4") }
        binding2.btn5.setOnClickListener{ numeroPresionado("5") }
        binding2.btn6.setOnClickListener{ numeroPresionado("6") }
        binding2.btnmenos.setOnClickListener{ binding2.texto.text = "${binding2.texto.text} - " }
        binding2.btn1.setOnClickListener{ numeroPresionado("1") }
        binding2.btn2.setOnClickListener{ numeroPresionado("2") }
        binding2.btn3.setOnClickListener{ numeroPresionado("3") }
        binding2.btnmas.setOnClickListener{ binding2.texto.text = "${binding2.texto.text} + " }
        binding2.btn0.setOnClickListener{ numeroPresionado("0") }
        binding2.btnpunto.setOnClickListener{ numeroPresionado(".") }

        binding2.btnigual.setOnClickListener{
            val expresion = binding2.texto.text
            resultado = ExpressionBuilder(expresion as String?).build().evaluate().toString()

            openResultado_e()// Abre la actividad de resultado final desde Editando
        }

        binding2.btnCalcular1.setOnClickListener{
            val expresion = binding2.texto.text
            resultado = ExpressionBuilder(expresion as String?).build().evaluate().toString()

            openResultado_e()// Abre la actividad de resultado final desde Editando
        }

    }

    // Función para abrir la actividad de resultado final desde Editando
    private fun openResultado_e() {
        val intentooo = Intent(this, ResultadoFinal::class.java)
        intentooo.putExtra("texto_editando", resultado) // Utiliza una clave diferente para el resultado de Editando
        startActivity(intentooo)
    }

    // Función para agregar el número presionado al campo de texto
    private fun numeroPresionado(digito: String){
        binding2.texto.text = "${binding2.texto.text}$digito"
    }

}