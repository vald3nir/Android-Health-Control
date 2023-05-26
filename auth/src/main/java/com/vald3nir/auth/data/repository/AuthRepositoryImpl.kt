package com.vald3nir.auth.data.repository

import android.app.Activity
import android.content.Context
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.vald3nir.auth.data.dtos.LoginDTO
import com.vald3nir.core_repository.firebase.FirebaseAuthenticator
import com.vald3nir.core_repository.storage.loadDataString
import com.vald3nir.core_repository.storage.saveDataString
import com.vald3nir.core_repository.toDTO

class AuthRepositoryImpl : AuthRepository {

    private val authenticator = FirebaseAuthenticator()
    private val LOGIN_KEY = "Login"
    private val USERS_PATH = "Usuários"
    override fun getUserEmail(): String? {
        return Firebase.auth.currentUser?.email
    }

    override suspend fun login(
        activity: Activity,
        loginDTO: LoginDTO,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit
    ) {
        val email: String = loginDTO.email ?: ""
        val password: String = loginDTO.password ?: ""
        authenticator.signInWithEmailAndPassword(activity, email, password, onSuccess, onError)
    }

    override fun logout() {
        Firebase.auth.signOut()
    }

    override suspend fun saveLoginData(
        context: Context?,
        loginDTO: LoginDTO
    ) {
        if (loginDTO.rememberLogin) {
            context?.saveDataString(LOGIN_KEY, loginDTO.toJson())
        } else {
            context?.saveDataString(LOGIN_KEY, null)
        }
    }

    override suspend fun loadLoginData(context: Context?): LoginDTO {
        val json = context?.loadDataString(LOGIN_KEY)
        return json.toDTO(LoginDTO::class.java)
    }

    override suspend fun registerNewUser(
        activity: Activity,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit
    ) {
        val firebaseAuthenticator = FirebaseAuthenticator()
        firebaseAuthenticator.createUserWithEmailAndPassword(
            activity = activity,
            email = email,
            password = password,
            onSuccess = onSuccess,
            onError = onError,
        )
    }

//    override suspend fun registerClient(
//        activity: Activity,
//        clientDTO: ClientDTO,
//        onSuccess: () -> Unit,
//        onError: (e: Exception?) -> Unit
//    ) {
//        val database = Firebase.firestore
//        database.collection(USERS_PATH)
//            .add(clientDTO)
//            .addOnSuccessListener { onSuccess.invoke() }
//            .addOnFailureListener { onError.invoke(it) }
////        insertOrUpdate(
////            path = USERS_PATH,
////            dataJson = clientDTO,
////            onSuccess = { onSuccess.invoke() },
////            onError
////        )
//    }
}