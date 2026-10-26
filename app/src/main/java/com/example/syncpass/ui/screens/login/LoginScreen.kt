package com.example.syncpass.ui.screens.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.syncpass.domain.model.TipoParticipante
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    onAutenticar: suspend (String, String, TipoParticipante) -> Boolean,
    onLoginSuccess: (TipoParticipante) -> Unit,
    onRegistroClick: () -> Unit
) {

    var nombreUsuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var tipoUsuario by remember { mutableStateOf(TipoParticipante.DOCENTE) }
    var error by remember { mutableStateOf<String?>(null) }
    var verificando by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
            .imePadding()
    ) {
        LogoTexto()
        Spacer(Modifier.height(24.dp))

        CampoTexto(
            valor = nombreUsuario,
            cambioValor = { nombreUsuario = it; error = null },
            etiqueta = "Usuario",
            placeholder = "usuario.demo",
            imeAction = ImeAction.Next
        )
        Spacer(Modifier.height(16.dp))

        CampoTexto(
            valor = contrasena,
            cambioValor = { contrasena = it; error = null },
            etiqueta = "Contraseña",
            placeholder = "********",
            imeAction = ImeAction.Done,
            esContrasena = true
        )
        Spacer(Modifier.height(16.dp))

        TextoSelector("Ingresar como")
        TipoSelector(seleccionado = tipoUsuario, cambioSeleccion = { tipoUsuario = it })

        Spacer(Modifier.weight(1f))

        error?.let {
            Text(it, color = MaterialTheme.colorScheme.error, fontSize = 13.sp)
            Spacer(Modifier.height(8.dp))
        }

        Button(
            onClick = {
                if (nombreUsuario.isBlank() || contrasena.isBlank()) {
                    error = "Completa usuario y contraseña"
                } else {
                    verificando = true
                    scope.launch {
                        try {
                            val valido = onAutenticar(
                                nombreUsuario.trim(),
                                contrasena,
                                tipoUsuario
                            )
                            if (valido) {
                                onLoginSuccess(tipoUsuario)
                            } else {
                                error = "Usuario, contraseña o tipo incorrecto"
                            }
                        } catch (e: CancellationException) {
                            throw e
                        } catch (e: Exception) {
                            error = "No se pudo iniciar sesión"
                        } finally {
                            verificando = false
                        }
                    }
                }
            },
            enabled = !verificando,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(28.dp)
        ) {
            Text("Ingresar", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
        TextButton(
            onClick = onRegistroClick,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Si no tienes cuenta, Registrate aca")
        }
    }

}

private val opcionesTipo = listOf(
    TipoParticipante.DOCENTE,
    TipoParticipante.ESTUDIANTE,
    TipoParticipante.ASISTENTE
)

private val TipoParticipante.etiqueta: String
    get() = when (this) {
        TipoParticipante.DOCENTE -> "Docente"
        TipoParticipante.ESTUDIANTE -> "Estudiante"
        TipoParticipante.ASISTENTE -> "Asistente"
    }

@Composable
private fun LogoTexto() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier.size(44.dp).background(MaterialTheme.colorScheme.primary,
                RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text("SP", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimary)
        }
        Spacer(Modifier.width(12.dp))
        Text(
            "SyncPass",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
private fun TextoSelector(texto: String) {
    Text(
        text = texto,
        fontSize = 13.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.padding(bottom = 6.dp)
    )
}

@Composable
private fun CampoTexto(
    valor: String,
    cambioValor: (String) -> Unit,
    etiqueta: String,
    placeholder: String,
    imeAction: ImeAction,
    esContrasena: Boolean = false
) {
    OutlinedTextField(
        value = valor,
        onValueChange = cambioValor,
        label = { Text(etiqueta) },
        placeholder = { Text(placeholder) },
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        visualTransformation = if (esContrasena) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(
            keyboardType = if (esContrasena) KeyboardType.Password else KeyboardType.Text,
            imeAction = imeAction
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedLabelColor = MaterialTheme.colorScheme.onSurface
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun TipoSelector(
    seleccionado: TipoParticipante,
    cambioSeleccion: (TipoParticipante) -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        opcionesTipo.forEach { opcion ->
            val activo = opcion == seleccionado
            OutlinedButton(
                onClick = { cambioSeleccion(opcion) },
                modifier = Modifier.weight(1f).height(48.dp),
                shape = RoundedCornerShape(50),
                contentPadding = PaddingValues(horizontal = 8.dp),
                border = BorderStroke(
                    1.dp,
                    if (activo) MaterialTheme.colorScheme.onSurface
                    else MaterialTheme.colorScheme.outline
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (activo) MaterialTheme.colorScheme.secondaryContainer
                    else MaterialTheme.colorScheme.surface,
                    contentColor = if (activo) MaterialTheme.colorScheme.onSecondaryContainer
                    else MaterialTheme.colorScheme.onSurface
                )
            ) {
                Text(opcion.etiqueta, fontWeight = FontWeight.Bold, fontSize = 13.sp, maxLines = 1)
            }
        }
    }
}
