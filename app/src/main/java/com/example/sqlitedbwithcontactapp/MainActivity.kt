package com.example.sqlitedbwithcontactapp

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sqlitedbwithcontactapp.databinding.ActivityMainBinding
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private lateinit var dataBinding : ActivityMainBinding
    private lateinit var factory:ContactVMFactory
    private lateinit var viewModel: ContactViewModel
    private lateinit var adapter:ContactRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        factory = ContactVMFactory(ContactDBRepository(this))
        viewModel = ViewModelProvider(this, factory)[ContactViewModel::class.java]



            val layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            dataBinding.rcwView.layoutManager = layoutManager
            dataBinding.rcwView.setHasFixedSize(true)

            updateUI()

            dataBinding.addContact.setOnClickListener {
                val dialog = Dialog(this)
                val manager= WindowManager.LayoutParams()
                manager.width = WindowManager.LayoutParams.MATCH_PARENT
                manager.height = WindowManager.LayoutParams.WRAP_CONTENT

                dialog.setContentView(R.layout.create_contact_dialog)
                dialog.setCancelable(false)

                dialog.window!!.attributes=manager

                val btnClose= dialog.findViewById<ImageButton>(R.id.btn_close)
                val etFname = dialog.findViewById<EditText>(R.id.et_fname)
                val etLname= dialog.findViewById<EditText>(R.id.et_lname)
                val etNumber= dialog.findViewById<TextView>(R.id.et_number)
                val btnCreate = dialog.findViewById<Button>(R.id.btn_create)


                btnCreate.setOnClickListener {
                    if((etFname.text.toString().isNotEmpty() || etLname.text.toString().isNotEmpty()) && etNumber.text.toString().isNotEmpty()){
                        viewModel.saveContact(etFname.text.toString(), etLname.text.toString(), etNumber.text.toString())
                        updateUI()
                        dialog.dismiss()
                    }
                    else Toast.makeText(this, "Name and Number Required", Toast.LENGTH_SHORT).show()
                }
                btnClose.setOnClickListener { dialog.dismiss() }
                dialog.show()

            }

        }

        override fun onCreateOptionsMenu(menu: Menu?): Boolean {
            menuInflater.inflate(R.menu.contact_option_menu, menu)
            return super.onCreateOptionsMenu(menu)
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            when(item.itemId){
                R.id.it_delete_all->{
                    viewModel.deleteAllContact()
                    updateUI()
                }
            }
            return super.onOptionsItemSelected(item)
        }
        fun getContactList(): List<Contact>{
            return viewModel.getContacs()
        }
        fun updateUI(){

            adapter=ContactRecyclerViewAdapter(this,getContactList(), object:OnItemClickListener{
                override fun onItemClick(contact: Contact, position: Int) {


                    val intent= Intent(this@MainActivity, updateActivity::class.java)
                    intent.putExtra(Keys.CONTACT, Gson().toJson(contact))
                    startActivity(intent)
                }
            })


            dataBinding.rcwView.adapter=adapter
        }
        override fun onRestart() {
            super.onRestart()
            updateUI()
        }

    }
