package com.example.sqlitedbwithcontactapp

import androidx.lifecycle.ViewModel

class ContactViewModel(private val repository:ContactDBRepository): ViewModel() {

    fun saveContact(fname:String, lname:String, mobNo:String){
        repository.saveContact(fname, lname, mobNo)
    }

    fun getContacs():List<Contact>{
        return repository.getContacts()
    }

    fun updateContact(fname:String, lname:String, mobNo:String, srNo: Int){
        repository.updateContact(fname, lname, mobNo, srNo)
    }
    fun deleteSingleContact(srNo:Int){
        repository.deleteSingleContact(srNo)
    }

    fun deleteAllContact(){
        repository.deleteAllContact()
    }
}