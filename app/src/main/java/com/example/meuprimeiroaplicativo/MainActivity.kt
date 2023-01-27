package com.example.meuprimeiroaplicativo

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText: EditText = findViewById(R.id.edit_number)
        val txtResult: TextView = findViewById(R.id.txt_result)
        val btnGenerate: Button = findViewById(R.id.btn_generate)

        preferences =getSharedPreferences("db", Context.MODE_PRIVATE)
        val result = preferences.getString("result", null)
        result?.let {
            txtResult.text = "Ultima aposta: $it"
        }

        btnGenerate.setOnClickListener {
            val text = editText.text.toString()

            numberGenerator(text, txtResult)
        }

    }

    private fun numberGenerator(text: String, txtResult: TextView) {
        if (text.isEmpty()) {
            Toast.makeText(this, "Informe um numero entre 6 e 15", Toast.LENGTH_SHORT).show()
            return
        }
        val qtd = text.toInt()
        if (qtd < 6 || qtd > 15) {
            Toast.makeText(this, "Informe um numero entre 6 e 15", Toast.LENGTH_SHORT).show()
            return
        }

        val numbers = mutableSetOf<Int>()
        val random = Random()

        while (true) {
            val number = random.nextInt(60)
            numbers.add(number + 1)

            if (numbers.size == qtd) {
                break
            }
        }

        txtResult.text = numbers.joinToString(" - ")

        preferences.edit().apply {
            putString("result", txtResult.text.toString())
            apply()
        }
    }
}