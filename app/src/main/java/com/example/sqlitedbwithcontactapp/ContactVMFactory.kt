package com.example.sqlitedbwithcontactapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ContactVMFactory(private val repository:ContactDBRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactViewModel::class.java)){
            return ContactViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown Class")
    }
}