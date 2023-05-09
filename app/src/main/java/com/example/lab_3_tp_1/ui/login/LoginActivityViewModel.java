package com.example.lab_3_tp_1.ui.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.lab_3_tp_1.models.Usuario;
import com.example.lab_3_tp_1.ui.request.ApiClient;

public class LoginActivityViewModel extends AndroidViewModel {

    private Context context;
    private ApiClient apiClient;
    private MutableLiveData<Boolean> loginSuccess;
    private MutableLiveData<Usuario> dataUsuarioMutable;

    public LoginActivityViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        apiClient = new ApiClient();
    }

    public LiveData<Boolean> getLoginSuccess() {
        loginSuccess = new MutableLiveData<>();
        return loginSuccess;
    }
    public LiveData<Usuario> getDataUsuarioMutable() {
        if (dataUsuarioMutable == null) {
            dataUsuarioMutable = new MutableLiveData<>();
        }
        return dataUsuarioMutable;
    }


    public void confirmarLogin(String mail, String clave) {
        Usuario usuario = apiClient.login(context,mail,clave);

        loginSuccess.setValue(usuario != null ? true : false);
    }

    public void leerDatos() {
        Usuario usuario = apiClient.leer(context);
        if(usuario != null){
            dataUsuarioMutable.setValue(usuario);
        }else{
            Toast.makeText(context, "Credenciales erroneas", Toast.LENGTH_SHORT).show();
        }
    }



}

