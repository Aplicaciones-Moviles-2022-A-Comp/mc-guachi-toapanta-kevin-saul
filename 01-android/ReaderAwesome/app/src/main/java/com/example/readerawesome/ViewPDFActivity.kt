package com.example.readerawesome

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.barteksc.pdfviewer.PDFView

class ViewPDFActivity : AppCompatActivity() {
    lateinit var pdfView: PDFView
    val PDF_SELECTION_CODE =100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pdfactivity)

        pdfView=findViewById(R.id.pdfView)
        selectPdfFromStorage()
    }

    private fun selectPdfFromStorage(){
        val browseStorage   = Intent(Intent.ACTION_GET_CONTENT)
        browseStorage.type = "application/pdf"
        browseStorage.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(Intent.createChooser(browseStorage, "Select PDF"), PDF_SELECTION_CODE)
    }

    fun showPDFFromUri(uri: Uri){
        pdfView.fromUri(uri)
            .defaultPage(0)
            .spacing(10)
            .load()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==PDF_SELECTION_CODE && resultCode==Activity.RESULT_OK && data != null){
            val selectedPdf = data.data
            showPDFFromUri(selectedPdf!!)
        }
    }

}