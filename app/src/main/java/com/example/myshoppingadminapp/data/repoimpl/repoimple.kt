package com.example.myshoppingadminapp.data.repoimpl

import android.net.Uri
import com.example.myshoppingadminapp.common.CATEGORY
import com.example.myshoppingadminapp.common.Products
import com.example.myshoppingadminapp.common.State
import com.example.myshoppingadminapp.domain.model.ProductDataModel
import com.example.myshoppingadminapp.domain.model.category
import com.example.myshoppingadminapp.domain.repo.repo
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class repoimple @Inject constructor(
    private val FirebaseFirestore: FirebaseFirestore,
    private val firebaseStorage: FirebaseStorage
) : repo {
    override fun addCategory(category: category): Flow<State<String>> = callbackFlow {
        trySend(State.Loading)
        FirebaseFirestore.collection(CATEGORY).add(category).addOnSuccessListener {
            trySend(State.Success("Category Add Successfully"))
        }.addOnFailureListener {
            trySend(State.Error(it.toString()))
        }
        awaitClose {
            close()
        }

    }

    override fun getCategories(): Flow<State<List<category>>> = callbackFlow {
        trySend(State.Loading)
        FirebaseFirestore.collection(CATEGORY).get()
            .addOnSuccessListener { querySnapshot ->

                val categories = querySnapshot.documents.mapNotNull {
                    it.toObject(category::class.java)


                }

                trySend(State.Success(categories))
            }.addOnFailureListener {
                trySend(State.Error(it.toString()))
            }
        awaitClose {
            close()

        }

    }

    override fun addProducts(ProductDataModel: ProductDataModel): Flow<State<String>> =
        callbackFlow {

            trySend(State.Loading)
            FirebaseFirestore.collection(Products).add(ProductDataModel)
                .addOnSuccessListener {
                    trySend(State.Success("Product Add Successfully"))
                }.addOnFailureListener {
                    trySend(State.Error(it.toString()))
                }
            awaitClose {
                close()
            }

        }

    override fun uploadImage(image: Uri): Flow<State<String>> = callbackFlow {
        trySend(State.Loading)


        firebaseStorage.reference.child("Products/${System.currentTimeMillis()}")
            .putFile(image ?: Uri.EMPTY).addOnSuccessListener {
                it.storage.downloadUrl.addOnCanceledListener {
                    trySend(State.Success(it.toString()))

                }
                if (it.error != null) {
                    trySend(State.Error(it.error!!.message.toString()))

                }
            }
        awaitClose {
            close()
        }

    }
}