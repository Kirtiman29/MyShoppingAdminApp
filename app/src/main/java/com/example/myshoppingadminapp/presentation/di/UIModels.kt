package com.example.myshoppingadminapp.presentation.di

import com.example.myshoppingadminapp.data.repoimpl.repoimple
import com.example.myshoppingadminapp.domain.repo.repo
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UIModels {




    @Provides
    fun provideRepo(
        firebaseFirestore: FirebaseFirestore
    ): repo{
        return repoimple(
            firebaseFirestore
        )


    }
}