package com.AdrianoDev.ADS_Ecommerce
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.AdrianoDev.ADS_Ecommerce.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.android.material.internal.ContextUtils.getActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import de.hdodenhof.circleimageview.CircleImageView


class LoginActivity : AppCompatActivity() {

    // Variáveis
    private lateinit var binding: ActivityLoginBinding

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.loginGoogle.setOnClickListener {
            if (isInternetAvailable()) {
                signInGoogle()
            } else {
                Toast.makeText(this, "Sem conexão com a Internet", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveLoginState(isLoggedIn: Boolean) {
        val sharedPref = this.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("isLoggedIn", isLoggedIn)
        editor.apply()
    }

    private fun signInGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResults(task)
        }
    }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful) {
            val account: GoogleSignInAccount? = task.result
            if (account != null) {
                updateUI(account)
                saveLoginState(true)
            }
        } else {
            Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                val intent = Intent(this, SplashActivity::class.java)
                intent.putExtra("imagem", account.photoUrl.toString())
                intent.putExtra("user", account.displayName)
                intent.putExtra("type", "google")
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(
                    baseContext, "Erro de autenticação com o Google",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    override fun onStart() {
        super.onStart()
        val sharedPref = this.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPref.getBoolean("isLoggedIn", false)
        if (isLoggedIn) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }



    }
}

    //      binding.foto.setOnClickListener {
  //          val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
  //          startActivityForResult(intent, REQUEST_CODE_PICK_PHOTO)

    //    binding.login.setOnClickListener {
    //        val name = binding.nome.text.toString()
    //        //   Verifica se o nome e a foto foram preenchidos
   //         if (name.isNotEmpty() && photoUrl != null) {
   //             CoroutineScope(Dispatchers.IO).launch {
//
    //                val app = application as App
    //                val userDao = app.db.listDao()
    //                val newLista = listadeprodutos(
      //                  nome = 0, photoUrl = photoUrl.toString(), name = "$name",
      //                  type = "", foto = 0, quantidadeCx = "",
      //                  quantidadeMt = "", valorUni = "", valordopiso = ""
      //              )
      //              userDao.insert(newLista)
       //             withContext(Dispatchers.Main) {
          //              startActivity(Intent(this@LoginActivity, MainActivity::class.java).apply {
            //                putExtra("type", "type")
            //                putExtra("user", name)
              //              putExtra("imagem", photoUrl.toString())
            //                finish()

           //             })
           //         }
           //     }

           // } else {
             //   Toast.makeText(this, "Nome e foto são obrigatórios", Toast.LENGTH_SHORT).show()

          //  }
      //  }
//    }

    // Função de retorno de chamada para a atividade resultante
   // override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
     //   super.onActivityResult(requestCode, resultCode, data)

        // Verifica se a solicitação é para seleção de foto e se a operação foi bem-sucedida
      //  if (requestCode == REQUEST_CODE_PICK_PHOTO && resultCode == Activity.RESULT_OK) {
      //      photoUrl = data?.data
       //     binding.foto.setImageURI(photoUrl)
       // }
  //  }

