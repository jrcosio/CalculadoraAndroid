package com.jrblanco.calculadorajr

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var numeroDecimal:Boolean = false // Variable para saber si es un numero decimal (float o Double)
    private var operacion:String = "NO_OPERACION"
    private var numero1:Double = 0.0
    private var numero2:Double = 0.0
    private var nuevoNumero:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnBorrar.setOnClickListener { ResetCalculadora() }

        btn0.setOnClickListener { this.clickBotonesNum("0") }
        btn1.setOnClickListener { this.clickBotonesNum("1") }
        btn2.setOnClickListener { this.clickBotonesNum("2") }
        btn3.setOnClickListener { this.clickBotonesNum("3") }
        btn4.setOnClickListener { this.clickBotonesNum("4") }
        btn5.setOnClickListener { this.clickBotonesNum("5") }
        btn6.setOnClickListener { this.clickBotonesNum("6") }
        btn7.setOnClickListener { this.clickBotonesNum("7") }
        btn8.setOnClickListener { this.clickBotonesNum("8") }
        btn9.setOnClickListener { this.clickBotonesNum("9") }
        btnPunto.setOnClickListener { this.clickBotonesNum(".") }
        btnSigno.setOnClickListener {
            val resultado = "${tvPantalla.text.toString().toDouble() * (-1)}"
            if (resultado.toString().endsWith(".0")){
                tvPantalla.text = resultado.toString().replace(".0","")
            } else {
                tvPantalla.text = resultado.toString()
            }
        }

        btnPorcentaje.setOnClickListener {
            Toast.makeText(this, "Función sin hacer ;)", 5).show()
        }

        btnSumar.setOnClickListener { this.clickOperacion("SUMAR") }
        btnResta.setOnClickListener { this.clickOperacion("RESTAR") }
        btnMultiplicar.setOnClickListener { this.clickOperacion("MULTIPLICAR") }
        btnDividir.setOnClickListener { this.clickOperacion("DIVIDIR") }

        btnIgual.setOnClickListener { this.clickIgual() } // Evento del IGUAL

        //Al hacer click en la pantalla, se copia el numero al portapapeles y se manda un mensaje toast
        tvPantalla.setOnClickListener {
            Toast.makeText(this, "Copiado al portapapeles", 3).show()
        }
    }

    private fun ResetCalculadora(){
        // Método que inicializa todas la variables
        tvPantalla.text = ("0")
        this.numeroDecimal = false
        this.operacion = ""
        this.numero1 = 0.0
        this.numero2 = 0.0
        this.nuevoNumero = false
    }

    private fun clickOperacion(op:String){
        this.operacion = op
        this.numeroDecimal = false
        this.nuevoNumero = true
    }

    private fun clickIgual(){
        // Método que hace las operaciones al pulsar =
        var resultado: Double = 0.0

        when (this.operacion){
            "SUMAR" -> {resultado = this.numero1 + this.numero2}
            "RESTAR" -> {resultado = this.numero1 - this.numero2}
            "MULTIPLICAR" -> {resultado = this.numero1 * this.numero2}
            "DIVIDIR" -> {
                    if (this.numero2 != 0.0) {
                        resultado = this.numero1 / this.numero2
                    } else {
                        Toast.makeText(this, "Error: División entre CERO",5).show()
                        ResetCalculadora()
                    }
                }
        }

        this.numero1 = resultado
        this.nuevoNumero = true

        if (resultado.toString().endsWith(".0")){
            tvPantalla.text = resultado.toString().replace(".0","")
        } else {
            tvPantalla.text = resultado.toString()
        }
    }


    private fun clickBotonesNum(tecla:String){
        //Método que va añadiendo los números
        if (this.nuevoNumero) {
            tvPantalla.text = "0"
            this.nuevoNumero = false
        }

        if (!((tecla == ".") && (this.numeroDecimal))){
            if ((tecla == ".") && (tvPantalla.text == "0")) {
                tvPantalla.text = "0."
                this.numeroDecimal = true
            } else {
                if (tvPantalla.text == "0") {
                    tvPantalla.text = tecla
                } else {
                    if (tecla == ".") {
                        this.numeroDecimal = true
                        tvPantalla.text = "${tvPantalla.text}."
                    } else {
                        tvPantalla.text = "${tvPantalla.text}${tecla}" //Concatena los numero string
                    }
                }
            }
        }

        if (this.operacion == "NO_OPERACION") {
            this.numero1 = tvPantalla.text.toString().toDouble()
        } else {
            this.numero2 = tvPantalla.text.toString().toDouble()
        }
    }
}
