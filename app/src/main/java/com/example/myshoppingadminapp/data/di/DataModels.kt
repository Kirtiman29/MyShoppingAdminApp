package com.example.myshoppingadminapp.data.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object DataModels {

    @Provides
    fun provideFireStore(): FirebaseFirestore{
        return  FirebaseFirestore.getInstance()
    }

    @Provides
    fun provideStorage() : FirebaseStorage{
        return FirebaseStorage.getInstance()
    }


}