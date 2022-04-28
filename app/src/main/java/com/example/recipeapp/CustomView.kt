package com.example.recipeapp

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast

class CustomView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
): LinearLayout(context, attributeSet, defStyle) {

    init {
        // Viewを設定
        inflate(context, R.layout.favorite_button, this)

        findViewById<ImageButton>(R.id.favorite_button)?.setOnClickListener {
            // TODO お気に入りボタン押下時の処理
            Toast.makeText(context, "お気に入りに登録しました", Toast.LENGTH_SHORT).show()
            findViewById<ImageButton>(R.id.favorite_button).setImageResource(R.drawable.ic_star_orange)

            Toast.makeText(context, "お気に入りから削除しました", Toast.LENGTH_SHORT).show()
            findViewById<ImageButton>(R.id.favorite_button).setImageResource(R.drawable.ic_star_border_orange)
        }
    }
}