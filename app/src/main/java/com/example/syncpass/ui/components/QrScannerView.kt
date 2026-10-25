package com.example.syncpass.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.zxing.BarcodeFormat
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import com.journeyapps.barcodescanner.DefaultDecoderFactory

@Composable
fun QrScannerView(
    modifier: Modifier = Modifier,
    onQrLeido: (String) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val callbackActual = rememberUpdatedState(onQrLeido)

    val lector = remember(context) {
        DecoratedBarcodeView(context).apply {
            setDecoderFactory(DefaultDecoderFactory(listOf(BarcodeFormat.QR_CODE)))
            setStatusText("")
        }
    }

    AndroidView(factory = { lector }, modifier = modifier)

    DisposableEffect(lector, lifecycleOwner) {
        lector.decodeSingle(object : BarcodeCallback {
            override fun barcodeResult(result: BarcodeResult) {
                result.text?.let { callbackActual.value(it) }
            }

            override fun possibleResultPoints(resultPoints: List<ResultPoint>) = Unit
        })

        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> lector.resume()
                Lifecycle.Event.ON_PAUSE -> lector.pause()
                else -> Unit
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        if (lifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
            lector.resume()
        }

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            lector.pause()
        }
    }
}
