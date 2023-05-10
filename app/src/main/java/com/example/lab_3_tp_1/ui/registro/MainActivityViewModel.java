package com.example.lab_3_tp_1.ui.registro;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.lab_3_tp_1.models.Usuario;
import com.example.lab_3_tp_1.ui.login.LoginActivity;
import com.example.lab_3_tp_1.ui.request.ApiClient;

import java.io.File;
import java.util.ArrayList;

public class MainActivityViewModel extends AndroidViewModel {

    private Context context;
    private ApiClient apiClient;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        apiClient = new ApiClient();
    }

    public void registrarUsuario(String nombre, String apellido, String dni, String mail, String clave) {
        try {
            Long dniLong = Long.parseLong(dni);

            // Validar si el correo electrónico ya está en uso
            Usuario usuarioRegistrado = apiClient.login(context, mail, clave);
            if (usuarioRegistrado != null) {
                Toast.makeText(context, "Ya existe un usuario registrado con ese correo electrónico", Toast.LENGTH_SHORT).show();
                return;
            }

            Usuario usuario = new Usuario(nombre, apellido, dniLong, mail, clave);
            apiClient.registrar(context, usuario);

            Intent intent = new Intent(context, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);

        } catch (NumberFormatException e) {
            Toast.makeText(context, "El DNI debe ser un número válido", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
        }
    }
}

