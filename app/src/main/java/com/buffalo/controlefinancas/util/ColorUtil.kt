package com.buffalo.controlefinancas.util

import android.graphics.Color
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import com.buffalo.controlefinancas.database.ColorExpenseDAO
import com.buffalo.controlefinancas.model.ColorExpense

object ColorUtil {

    const val MEDIA_COLOR = 230

    fun dominatColor(hexdecimal: String): Int {
        var color  = Color.parseColor(hexdecimal)
        val red = color.red
        val green = color.green
        val blue = color.blue
        return (red + green + blue) / 3
    }

//    fun getDominantColor(image: Bitmap?): Int{
//        val bitmap: Bitmap? = image
//
//        var redColors = 0
//        var greenColors = 0
//        var blueColors = 0
//        var pixelCount = 0
//
//        for (y in 0 until bitmap!!.height) {
//            for (x in 0 until bitmap.width) {
//                val c = bitmap.getPixel(x, y)
//                pixelCount++
//                redColors += Color.red(c)
//                greenColors += Color.green(c)
//                blueColors += Color.blue(c)
//            }
//        }
//
//        val red = redColors / pixelCount
//        val green = greenColors / pixelCount
//        val blue = blueColors / pixelCount
//
//        return (red + green + blue) / 3
//    }


    fun createList() {
        ColorExpenseDAO.save(ColorExpense(1, "#6A5ACD", false))
        ColorExpenseDAO.save(ColorExpense(2, "#836FFF", false))
        ColorExpenseDAO.save(ColorExpense(3, "#6959CD", false))
        ColorExpenseDAO.save(ColorExpense(4, "#483D8B", false))
        ColorExpenseDAO.save(ColorExpense(5, "#191970", false))
        ColorExpenseDAO.save(ColorExpense(6, "#000080", false))
        ColorExpenseDAO.save(ColorExpense(7, "#0000CD", false))
        ColorExpenseDAO.save(ColorExpense(8, "#0000FF", false))
        ColorExpenseDAO.save(ColorExpense(9, "#6495ED", false))
        ColorExpenseDAO.save(ColorExpense(10, "#4169E1", false))
        ColorExpenseDAO.save(ColorExpense(11, "#1E90FF", false))
        ColorExpenseDAO.save(ColorExpense(12, "#87CEFA", false))
        ColorExpenseDAO.save(ColorExpense(13, "#87CEEB", false))
        ColorExpenseDAO.save(ColorExpense(14, "#ADD8E6", false))
        ColorExpenseDAO.save(ColorExpense(15, "#4682B4", false))
        ColorExpenseDAO.save(ColorExpense(16, "#B0C4DE", false))
        ColorExpenseDAO.save(ColorExpense(17, "#708090", false))

        ColorExpenseDAO.save(ColorExpense(18, "#98FB98", false))
        ColorExpenseDAO.save(ColorExpense(19, "#00FFFF", false))
        ColorExpenseDAO.save(ColorExpense(20, "#00CED1", false))
        ColorExpenseDAO.save(ColorExpense(21, "#40E0D0", false))
        ColorExpenseDAO.save(ColorExpense(22, "#7FFFD4", false))
        ColorExpenseDAO.save(ColorExpense(23, "#66CDAA", false))
        ColorExpenseDAO.save(ColorExpense(24, "#00FF00", false))
        ColorExpenseDAO.save(ColorExpense(25, "#6B8E23", false))

        ColorExpenseDAO.save(ColorExpense(26, "#808000", false))
        ColorExpenseDAO.save(ColorExpense(27, "#006400", false))
        ColorExpenseDAO.save(ColorExpense(28, "#A0522D", false))
        ColorExpenseDAO.save(ColorExpense(29, "#BC8F8F", false))
        ColorExpenseDAO.save(ColorExpense(30, "#CD853F", false))
        ColorExpenseDAO.save(ColorExpense(31, "#D2691E", false))
        ColorExpenseDAO.save(ColorExpense(32, "#F4A460", false))

        ColorExpenseDAO.save(ColorExpense(33, "#EE82EE", false))
        ColorExpenseDAO.save(ColorExpense(34, "#DA70D6", false))
        ColorExpenseDAO.save(ColorExpense(35, "#DDA0DD", false))

        ColorExpenseDAO.save(ColorExpense(36, "#FFE4E1", false))
        ColorExpenseDAO.save(ColorExpense(37, "#FFF0F5", false))
        ColorExpenseDAO.save(ColorExpense(38, "#E6E6FA", false))
        ColorExpenseDAO.save(ColorExpense(39, "#D8BFD8", false))
        ColorExpenseDAO.save(ColorExpense(40, "#F0FFFF", false))
        ColorExpenseDAO.save(ColorExpense(41, "#E0FFFF", false))
        ColorExpenseDAO.save(ColorExpense(42, "#B0E0E6", false))

        ColorExpenseDAO.save(ColorExpense(43, "#E0FFFF", false))
        ColorExpenseDAO.save(ColorExpense(44, "#F0FFF0", false))
        ColorExpenseDAO.save(ColorExpense(45, "#F5FFFA", false))
    }
}