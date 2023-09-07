package com.hartz.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private var tvInput:TextView? = null
    private var lastNumeric:Boolean = false
    private var lastDot:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvInput = findViewById(R.id.tvInput)
    }
    fun onDigit(view: View){
//        Toast.makeText(this, "Button clicked", Toast.LENGTH_SHORT).show()
        tvInput?.append((view as Button).text)
        lastNumeric = true
//        lastDot = false
    }

    fun onClear(view: View){
        tvInput?.text = ""
        lastNumeric = false
        lastDot= false
    }


    fun onDecimalPoint(view: View){
        if(lastNumeric && !lastDot){
            tvInput?.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    private fun isOperatorAdded(value:String):Boolean{
        return if(value.startsWith("-")){
            false
        }
        else{
            (value.contains("/")
                    || value.contains("*")
                    || value.contains("+")
                    || value.contains("-")
                    )
        }

    }

    fun onEqual(view: View){
        if(lastNumeric){
            var tvValue = tvInput?.text.toString()
            var prefix = ""
            try {
                if(tvValue.startsWith("-")){
                    prefix = "-"
                    tvValue = tvValue.substring(1);
                }
                if (tvValue.contains("-")){
                    val splitValue = tvValue.split("-")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    var result = one.toDouble() - two.toDouble()
                    tvInput?.text = removeZeroAfterDot(result.toString())
                }
                else if (tvValue.contains("+")){
                    val splitValue = tvValue.split("+")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    var result = one.toDouble() + two.toDouble()
                    tvInput?.text = removeZeroAfterDot(result.toString())
                }
                else if (tvValue.contains("*")){
                    val splitValue = tvValue.split("*")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    var result = one.toDouble() * two.toDouble()
                    tvInput?.text = removeZeroAfterDot(result.toString())
                }
                else if (tvValue.contains("/")){
                    val splitValue = tvValue.split("/")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    var result = one.toDouble() / two.toDouble()
                    tvInput?.text = removeZeroAfterDot(result.toString())
                }

            }catch (e:ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterDot(result: String):String{
        var value = result
        if(value.contains(".0")){
            value = result.substring(0,result.length -2)
        }
        return value
    }

    fun onOperator(view: View){
        tvInput?.text?.let {
            if(lastNumeric && !isOperatorAdded(it.toString())){
                tvInput?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }
    }
}