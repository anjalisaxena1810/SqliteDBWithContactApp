package com.example.sqlitedbwithcontactapp

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlitedbwithcontactapp.databinding.LayoutCustomViewBinding

class ContactRecyclerViewAdapter(val  context: Context, private var contactList: List<Contact>, private val onItemClickListener: OnItemClickListener): RecyclerView.Adapter<ContactRecyclerViewAdapter.CustomViewHolder>(){

    inner class CustomViewHolder(var layoutCustomViewBinding: LayoutCustomViewBinding) :
        RecyclerView.ViewHolder(layoutCustomViewBinding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        var layoutCustomViewBinding: LayoutCustomViewBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.layout_custom_view,
            parent,
            false
        )
        return CustomViewHolder(layoutCustomViewBinding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        var contacts = contactList.get(position)
        val requestPermissionList = arrayOf(Manifest.permission.CALL_PHONE)
        val REQUEST_CODE = 1

        holder.layoutCustomViewBinding.tvFname.text = contacts.fname
        holder.layoutCustomViewBinding.tvLname.text = contacts.lname
        holder.layoutCustomViewBinding.tvNumber.text = contacts.mobNo


        val mobileNo: String = contacts.mobNo
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$mobileNo"))
        holder.layoutCustomViewBinding.tvDial.setOnClickListener {
            context.startActivity(intent)
        }

        holder.layoutCustomViewBinding.tvCall.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.CALL_PHONE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$mobileNo"))
                context.startActivity(intent)
            } else {
                ActivityCompat.requestPermissions(
                    context as Activity,
                    requestPermissionList,
                    REQUEST_CODE
                )
            }

        }

        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(contacts, position)
        }

    }

    override fun getItemCount(): Int {
        return contactList.size
    }
}

