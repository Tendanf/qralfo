package org.boofcv

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CameraMetadata.LENS_FACING_FRONT
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import boofcv.android.camera2.VisualizeCamera2Activity


class MainActivity : AppCompatActivity() {
    private val webView: WebView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val webView = findViewById<WebView>(R.id.webView)
        webView.webViewClient = WebViewClient()
        webView.loadUrl("https://dev.fajfer.org")
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        requestCameraPermission()

    }

    fun clickedQrCode(view: View) {
        val intent = Intent(this, QrCodeActivity::class.java)
        startActivity(intent)
    }

   /**
     * Newer versions of Android require explicit permission from the user
     */

    private fun requestCameraPermission() {
        val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 0)
            // a dialog should open and this dialog will resume when a decision has been made
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            0 -> {
                // If request is cancelled, the result arrays are empty.
                if (!(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    dialogNoCameraPermission()

                }
            }
        }
    }

    private fun dialogNoCameraPermission() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Denied access to the camera! Exiting.")
            .setCancelable(false)
            .setPositiveButton("OK") { dialog, id -> System.exit(0) }
        val alert = builder.create()
        alert.show()
    }


}