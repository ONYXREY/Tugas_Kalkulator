package com.kevin.tugas_kalkulator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

    private lateinit var etNum1: EditText
    private lateinit var etNum2: EditText
    private lateinit var tvResult: TextView

    // Simpan history
    private val historyAdd = ArrayList<String>()
    private val historySub = ArrayList<String>()
    private val historyMul = ArrayList<String>()
    private val historyDiv = ArrayList<String>()

    // Simpan hasil terakhir untuk mengecek apakah kurang dari 0
    private var lastResultAdd = 0.0
    private var lastResultSub = 0.0
    private var lastResultMul = 0.0
    private var lastResultDiv = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etNum1 = findViewById(R.id.etNum1)
        etNum2 = findViewById(R.id.etNum2)
        tvResult = findViewById(R.id.tvResult)

        // Tombol Operasi
        findViewById<Button>(R.id.btnAdd).setOnClickListener { processCalculation("+") }
        findViewById<Button>(R.id.btnSub).setOnClickListener { processCalculation("-") }
        findViewById<Button>(R.id.btnMul).setOnClickListener { processCalculation("*") }
        findViewById<Button>(R.id.btnDiv).setOnClickListener { processCalculation("/") }

        // Tombol Buka History (Mengirimkan Fragment, List, Hasil Terakhir, Judul, dan Warna)
        // Orange = #FFA500, Pink = #FFC0CB, Hijau = #81C784, Biru = #64B5F6
        findViewById<Button>(R.id.btnHistAdd).setOnClickListener {
            showFragment(AddFragment(), historyAdd, lastResultAdd, "History Penjumlahan", "#FFA500")
        }
        findViewById<Button>(R.id.btnHistSub).setOnClickListener {
            showFragment(SubFragment(), historySub, lastResultSub, "History Pengurangan", "#FFC0CB")
        }
        findViewById<Button>(R.id.btnHistMul).setOnClickListener {
            showFragment(MulFragment(), historyMul, lastResultMul, "History Perkalian", "#81C784")
        }
        findViewById<Button>(R.id.btnHistDiv).setOnClickListener {
            showFragment(DivFragment(), historyDiv, lastResultDiv, "History Pembagian", "#64B5F6")
        }
    }

    private fun processCalculation(operator: String) {
        val num1Str = etNum1.text.toString()
        val num2Str = etNum2.text.toString()

        if (num1Str.isEmpty() || num2Str.isEmpty()) {
            Toast.makeText(this, "Masukkan kedua angka!", Toast.LENGTH_SHORT).show()
            return
        }

        val num1 = num1Str.toDouble()
        val num2 = num2Str.toDouble()
        var result = 0.0

        when (operator) {
            "+" -> {
                result = num1 + num2
                lastResultAdd = result
                historyAdd.add("$num1 + $num2 = $result")
            }
            "-" -> {
                result = num1 - num2
                lastResultSub = result
                historySub.add("$num1 - $num2 = $result")
            }
            "*" -> {
                result = num1 * num2
                lastResultMul = result
                historyMul.add("$num1 * $num2 = $result")
            }
            "/" -> {
                if (num2 == 0.0) {
                    Toast.makeText(this, "Tidak bisa dibagi nol!", Toast.LENGTH_SHORT).show()
                    return
                }
                result = num1 / num2
                lastResultDiv = result
                historyDiv.add("$num1 / $num2 = $result")
            }
        }

        // Tampilkan hasil langsung ke layar (bukan ke fragment)
        tvResult.text = "Hasil: $result"
    }

    private fun showFragment(fragment: Fragment, historyList: ArrayList<String>, lastResult: Double, title: String, colorHex: String) {
        val bundle = Bundle().apply {
            putStringArrayList("HISTORY_LIST", historyList)
            putBoolean("IS_NEGATIVE", lastResult < 0)
            putString("TITLE", title)
            putString("COLOR", colorHex) // Kirim warna ke fragment
        }
        fragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}