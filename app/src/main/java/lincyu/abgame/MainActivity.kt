package lincyu.abgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {

    lateinit var tv_result: TextView
    lateinit var et_input: EditText
    lateinit var btn_calculate: Button
    lateinit var show_ans: Button
    lateinit var again: Button
    lateinit var history_text: TextView

    var answer: IntArray = intArrayOf(0, 0, 0, 0)
    var shistory = StringBuilder()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_result = findViewById(R.id.tv_result)
        et_input = findViewById(R.id.et_input)
        btn_calculate = findViewById(R.id.btn_calculate)
        show_ans = findViewById(R.id.show_ans)
        again = findViewById(R.id.again)
        history_text = findViewById((R.id.history_textView))

        btn_calculate.setOnClickListener { buttonClicked() }
        show_ans.setOnClickListener { show_ans_buttonClicked() }
        again.setOnClickListener { again_buttonClicked() }

        generateAnswer()

    }

    private fun buttonClicked() {

        var input_str: String = et_input.text.toString()
        et_input.setText("")

        if (input_str.length != 4) {
            Toast.makeText(this,"ERROR",Toast.LENGTH_LONG).show()
            return
        }

        tv_result.setText(compare(input_str))
    }

    private fun show_ans_buttonClicked() {

        var sans: String =
            "" + answer[0].toString() + answer[1].toString() + answer[2].toString() + answer[3].toString()

        tv_result.setText(sans)

        return
    }

    private fun again_buttonClicked() {

        generateAnswer()
        shistory.clear()
        history_text.setText(shistory)
        return
    }

    private fun generateAnswer() {

        for (i in 0..3) {
            var breakflag: Boolean = true
            do {
                breakflag = true
                answer[i] = (Math.random() * 10).toInt()
                for (j in 0..(i - 1)) {
                    if (answer[i] == answer[j]) {
                        breakflag = false
                        break
                    }
                }
            } while (!breakflag)
        }
    }

    private fun compare(input_str: String): String {

        var guess: Int = 0
        try {
            guess = input_str.toInt()
        } catch (e: NumberFormatException) {
            return getString(R.string.input_error)
        }
        var guessarray: IntArray = intArrayOf(0, 0, 0, 0)
        var x: Int = 10000
        for (i in 0..3) {
            guessarray[i] = (guess % x) / (x / 10)
            x = x / 10
        }
        var a: Int = 0
        var b: Int = 0

        for (i in 0..3) {
            if (guessarray[i] == answer[i]) {
                a += 1
            }

            for (j in 0..3) {
                if (i == j) continue
                if (guessarray[i] == answer[j]) {
                    b = b + 1
                }
            }
        }
        if (shistory.isEmpty()){
            shistory.append(input_str + " " +a.toString() + "A" + b.toString() + "B")
        }
        else{
            shistory.append('\n' + input_str + " " + a.toString() + "A" + b.toString() + "B")
        }
        history_text.setText(shistory)
        return a.toString() + "A" + b.toString() + "B"
    }

}





