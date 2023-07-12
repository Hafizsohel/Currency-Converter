package com.example.currencyconverter

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import org.json.JSONObject
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }
    fun get(view: View){
        val downloadData=Download()
        try {
            val editText = findViewById<EditText>(R.id.editText)
            val url= "https://data.fixer.io/api/latest"
            val chosenBase = editText.text.toString()


            downloadData.execute(url+chosenBase)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }



    inner class Download : AsyncTask<String, Void, String>(){
        override fun doInBackground(vararg params: String?): String {

            var result= " "
            var url:URL
            val httpURLConnection: HttpURLConnection
            try {
                url=URL(params[0])
                httpURLConnection = url.openConnection() as HttpURLConnection
                val inputStream = httpURLConnection.inputStream
                val inputStramReader = InputStreamReader(inputStream)
                var data= inputStramReader.read()
                while (data>0){
                    val character=data.toChar()
                    result +=character
                    data=inputStramReader.read()
                }
                return result
            }catch (e: Exception){
                e.printStackTrace()
                return result
            }
            return  result
        }
        override fun onPostExecute(result: String?) {

            super.onPostExecute(result)
            try {

                val textView=findViewById<TextView>(R.id.textView)
                val textView2=findViewById<TextView>(R.id.textView2)
                val textView3=findViewById<TextView>(R.id.textView3)
               /* val jSONObject=JSONObject(result)
                println(jSONObject)
                val code=jSONObject.getString("code")
                println(code)

                val alphaCode=jSONObject.getString("alphaCode")
                println(alphaCode)

                val numericCode=jSONObject.getString("numericCode")
                println(numericCode)

                val newJsonObject=JSONObject(numericCode)
                val eur= newJsonObject.getString("EUR")
                println(eur)

                val gbp= newJsonObject.getString("GBP")
                println(gbp)

                val cad= newJsonObject.getString("CAD")
                println(cad)*/

                val jSONObject = JSONObject(result)
                println(jSONObject)
                val base = jSONObject.getString("base")
                println(base)
                val date = jSONObject.getString("date")
                println(date)
                val rates = jSONObject.getString("rates")
                println(rates)

                val newJsonObject = JSONObject(rates)
                val chf = newJsonObject.getString("CHF")
                println(chf)
                val czk = newJsonObject.getString("CZK")
                val tl = newJsonObject.getString("TRY")

                textView.text="CHF: "+chf
                textView2.text="CZK: "+czk
                textView3.text="TRY: "+tl




            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}