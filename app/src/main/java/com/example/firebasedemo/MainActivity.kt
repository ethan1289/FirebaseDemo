package com.example.firebasedemo

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*


class MainActivity : AppCompatActivity() {
    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("ID")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAdd: Button = findViewById(R.id.btnAdd)
        btnAdd.setOnClickListener(){

            val stdID: String = findViewById<TextView>(R.id.tfID).text.toString()
            val stdName: String = findViewById<TextView>(R.id.tfName).text.toString()
            val stdProgramme: String = findViewById<TextView>(R.id.tfProgramme).text.toString()

            //set value
            myRef.child(stdID).child("Name").setValue(stdName)
            myRef.child(stdID).child("Programme").setValue(stdProgramme)

        }

        //abstract class
        val getData = object: ValueEventListener{

            override fun onDataChange(db: DataSnapshot) {
                var sb = StringBuilder()

                for(std in db.children){
                    var name = std.child("Name").getValue()
                    var prog = std.child("Programme").getValue()

                    sb.append("${name} \n")
                }

                val tvResult: TextView = findViewById(R.id.tvResult)
                tvResult.setText(sb)
            }

            override fun onCancelled(db: DatabaseError) {

            }

        }

        val btnGet: Button = findViewById(R.id.btnGet)
        btnGet.setOnClickListener(){

            //these code to get everything
            //myRef.addValueEventListener(getData)
            //myRef.addListenerForSingleValueEvent(getData)

            val qry: Query = myRef.orderByChild("Programme").equalTo("RSF")
            qry.addValueEventListener(getData)
            qry.addListenerForSingleValueEvent(getData)
        }
    }

}