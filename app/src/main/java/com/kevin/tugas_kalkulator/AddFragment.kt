package com.kevin.tugas_kalkulator

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment

class AddFragment : Fragment(R.layout.fragment_history) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rootLayout = view.findViewById<LinearLayout>(R.id.fragmentRoot)
        val tvTitle = view.findViewById<TextView>(R.id.tvHistoryTitle)
        val tvContent = view.findViewById<TextView>(R.id.tvHistoryContent)

        // Ambil data dari Bundle
        val historyList = arguments?.getStringArrayList("HISTORY_LIST") ?: arrayListOf()
        val isNegative = arguments?.getBoolean("IS_NEGATIVE") ?: false
        val title = arguments?.getString("TITLE") ?: "History"
        val baseColor = arguments?.getString("COLOR") ?: "#FFFFFF" // Putih sebagai default

        // Render teks
        tvTitle.text = title
        tvContent.text = historyList.joinToString(separator = "\n")

        // Ketentuan 5: Ubah background jadi merah jika hasil < 0,
        // jika tidak, gunakan warna spesifik operasi tersebut.
        if (isNegative) {
            rootLayout.setBackgroundColor(Color.parseColor("#FF4C4C")) // Merah
        } else {
            rootLayout.setBackgroundColor(Color.parseColor(baseColor)) // Orange/Pink/Hijau/Biru
        }
    }
}