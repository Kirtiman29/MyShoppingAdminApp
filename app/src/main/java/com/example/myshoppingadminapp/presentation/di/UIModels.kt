package com.example.myshoppingadminapp.presentation.di

import com.example.myshoppingadminapp.data.repoimpl.repoimple
import com.example.myshoppingadminapp.domain.repo.repo
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UIModels {

    @Provides
    @Singleton
    fun provideRepo(
        firebaseFirestore: FirebaseFirestore,
        firebaseStorage: FirebaseStorage
    ): repo {
        return repoimple(
            firebaseFirestore,
            firebaseStorage
        )
    }
}
