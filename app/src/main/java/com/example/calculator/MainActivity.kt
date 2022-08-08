package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    var lastNumeric=false
    var lastDot=false
    private var tvInput:TextView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput=findViewById(R.id.tvInput)
    }
    fun onDigit(view: View){
        tvInput?.append((view as Button).text) //displays on the screen(tvInput)the button pressed
        lastNumeric=true
        lastDot=false
    }
    fun onClear(view: View){
        tvInput?.text=""
    }

    fun onDecimalPoint(view:View){
        if(lastNumeric && !lastDot){
            tvInput?.append(".")
            lastNumeric=false
            lastDot=true
    }
}
    fun onOperator(view: View){
        tvInput?.text?.let{ //checks if the tvInput actually exists and if it does convert it into string and pass it into the isOperatorAdded
            if(lastNumeric&&!isOperatorAdded(it.toString())){  //it contains the char sequence in tvInput
                tvInput?.append((view as Button).text)
                lastNumeric=false
                lastDot=false
            }
        }
    }

    private fun removeZero(result:String):String{
        var value=result
        if(result.contains(".0"))
            value=result.substring(0,result.length-2)
        return value
    }


    private fun isOperatorAdded(value:String):Boolean{
        return if (value.startsWith("-")||value.startsWith("+")||value.startsWith("*")||value.startsWith("/")){
            false
        }
        else {
            value.contains("/")
                    || value.contains("*")
                    || value.contains("+")
                    || value.contains("-")

        }
    }



    fun onEqual(view: View){
        if(lastNumeric){
            var tvValue=tvInput?.text.toString()
            var prefix=""
            try {
                if(tvValue.startsWith("-")){
                    prefix="-"
                    tvValue=tvValue.substring(1) //-99-1 -> 99-1
                }

                if(tvValue.contains("-")){
                    val splitValue=tvValue.split("-")
                        //eg 99-1
                    var one=splitValue[0] //99
                    var two=splitValue[1] //1


                    if(prefix.isNotEmpty()){
                        one=prefix+one //-99-1 is restored
                    }
                    tvInput?.text=removeZero((one.toDouble() - two.toDouble()).toString())
                } else if(tvValue.contains("+")){
                    val splitValue=tvValue.split("+")

                    var one=splitValue[0]
                    var two=splitValue[1]


                    if(prefix.isNotEmpty()){
                        one=prefix+one
                    }
                    tvInput?.text=removeZero((one.toDouble()+two.toDouble()).toString())
                }
                else if(tvValue.contains("/")){
                    val splitValue=tvValue.split("/")

                    var one=splitValue[0]
                    var two=splitValue[1]


                    if(prefix.isNotEmpty()){
                        one=prefix+one
                    }
                    tvInput?.text=removeZero((one.toDouble()/two.toDouble()).toString())
                } else if(tvValue.contains("x")){
                    val splitValue=tvValue.split("x")

                    var one=splitValue[0]
                    var two=splitValue[1]


                    if(prefix.isNotEmpty()){
                        one=prefix+one
                    }
                    tvInput?.text=removeZero((one.toDouble() * two.toDouble()).toString())
                }

            }catch (e:ArithmeticException){
                e.printStackTrace()
            }
        }
    }
}