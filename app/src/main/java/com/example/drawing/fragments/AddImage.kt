package com.example.drawing.fragments

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.drawing.database.DrawingDb
import com.example.drawing.databinding.FragmentAddImageBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class AddImage : Fragment() {
    private  lateinit var binding: FragmentAddImageBinding
    private  var file:File?=null
    private var imageUri: Uri? = null
    var SELECT_PICTURE = 200
    var check=false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddImageBinding.inflate(inflater, container, false)
        binding.select.setOnClickListener {
            imageChooser()
        }
        binding.add.setOnClickListener {
            if (file!=null) {
                    uploadData()
            }
            else
             Toast.makeText(requireContext(),"Image not selected",Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                val selectedImageUri: Uri? = data!!.data
                if (null != selectedImageUri) {
                    binding.imgPreview!!.setImageURI(selectedImageUri)
                    onSelectFromGalleryResult(data)

                }
            }
        }
    }
    private fun onSelectFromGalleryResult(data: Intent) {
        var bm: Bitmap? = null
        if (data != null) {
            try {

                bm =
                    MediaStore.Images.Media.getBitmap(requireContext().contentResolver, data.data)
                save(bm)


            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun uploadData()
    {
        val db = DrawingDb(requireContext(), null)
        db.addData("",file.toString(),"","","")
        Toast.makeText(requireContext(),"Image added successfully",Toast.LENGTH_SHORT).show()

        val nextFrag = ShowAllData()
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(com.example.drawing.R.id.fragment, nextFrag, "findThisFragment")
            .addToBackStack(null)
            .commit()
    }
private fun save(bitmap:Bitmap){
        val cw = ContextWrapper(requireContext())
        val directory: File = cw.getDir("imageDir", Context.MODE_PRIVATE)
        file = File(directory, "${System.currentTimeMillis()}" + ".jpg")
        if (!file!!.exists()) {
            Log.d("path", file.toString())
            var fos: FileOutputStream? = null
            try {
                fos = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                fos.flush()
                fos.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

}
    private fun imageChooser() {
        val i = Intent()
        i.type = "image/*"
        i.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE)
    }


}