package com.example.myshoppingadminapp.data.repoimpl

import com.example.myshoppingadminapp.common.CATEGORY
import com.example.myshoppingadminapp.common.State
import com.example.myshoppingadminapp.domain.model.category
import com.example.myshoppingadminapp.domain.repo.repo
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class repoimple @Inject constructor(
    private val FirebaseFirestore: FirebaseFirestore
): repo{
    override fun addCategory(category: category): Flow<State<String>> = callbackFlow {
        trySend(State.Loading)
        FirebaseFirestore.collection(CATEGORY).add(category).addOnSuccessListener {
            trySend(State.Success("Category Add Successfully"))
        }.addOnFailureListener{
            trySend(State.Error(it.toString()))
        }
        awaitClose{
            close()
        }

    }
}