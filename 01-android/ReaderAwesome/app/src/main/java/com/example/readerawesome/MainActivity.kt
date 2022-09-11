package com.example.readerawesome

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.readerawesome.adapter.DocumentosAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.karumi.dexter.Dexter
import com.karumi.dexter.DexterBuilder
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import java.io.File
import java.security.Permission

class MainActivity : AppCompatActivity() {
    var documentosArray = listOf<DocumentosData>(
        DocumentosData("safd","asdf",0,"asdf")
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //validatePermission()
        replaceFragment(Documentos())

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.documentos -> replaceFragment(Documentos())
                R.id.biblioteca -> replaceFragment(Bibliteca())
                R.id.categorias -> replaceFragment(Categorias())
                R.id.configuracion -> replaceFragment(Configuracion())

                else -> {

                }
            }
            true
        }


    }

    fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }

    private fun validatePermission() {
        Dexter.withActivity(this)
            .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {

                }

                override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) {
                    token?.continuePermissionRequest()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {

                }
            }
            ).check()
    }

    fun findPDF(file:File):ArrayList<File>{
        var filesArray = ArrayList<File>()
        var files=file.listFiles()

        for(singleFile in files){
            if(singleFile.isDirectory && !singleFile.isHidden()){
                filesArray.addAll(findPDF(singleFile))
            }else{
                if(singleFile.getName().endsWith(".pdf")){
                    filesArray.add(singleFile)
                }
            }

        }
        return filesArray
    }

    fun displayPDF(){

    }
}