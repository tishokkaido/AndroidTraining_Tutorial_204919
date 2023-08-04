package com.example.exercise01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // インスタンス取得
        val textView = findViewById<TextView>(R.id.text)
        val button = findViewById<Button>(R.id.button_1)

        // クリック処理の追加
        button.setOnClickListener {
            // TextViewがすでに選択＝クリックされているか
            if (it.isSelected) {
                textView.text = "Hello World!"
            } else {
                textView.text = "Button is Clicked!"
            }
            // 選択状態を反転させる
            it.isSelected = !it.isSelected
        }

    }
}