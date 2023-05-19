package com.example.drawing.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.database.Cursor
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.drawing.MainActivity
import com.example.drawing.ModelClass
import com.example.drawing.database.DrawingDb
import com.example.drawing.databinding.FragmentInputBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class InputBottomSheet(val uri:String) : BottomSheetDialogFragment() {
    lateinit var binding: FragmentInputBottomSheetBinding

var creator=""
    var name=""
    var desc=""
    var contri=""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentInputBottomSheetBinding.inflate(inflater, container, false)

        return binding.root
    }

    @SuppressLint("Range")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomSheet = view.parent as View
        bottomSheet.backgroundTintMode = PorterDuff.Mode.CLEAR
        bottomSheet.backgroundTintList = ColorStateList.valueOf(Color.TRANSPARENT)
        bottomSheet.setBackgroundColor(Color.TRANSPARENT)
        binding.close.setOnClickListener{

            dismiss()
        }
        val db = DrawingDb(requireContext(), null)

        val cursor: Cursor = db.name
        if (cursor.moveToFirst()) {
            if(cursor.getString(cursor.getColumnIndex(DrawingDb.img_uri)).equals(uri)) {
                 creator = cursor.getString(cursor.getColumnIndex(DrawingDb.creator_name))
                 name = cursor.getString(cursor.getColumnIndex(DrawingDb.img_name))
                 desc = cursor.getString(cursor.getColumnIndex(DrawingDb.design_description))
                 contri = cursor.getString(cursor.getColumnIndex(DrawingDb.contributed_by))
            }
            while (cursor.moveToNext()) {
                if(cursor.getString(cursor.getColumnIndex(DrawingDb.img_uri)).equals(uri)) {
                     creator = cursor.getString(cursor.getColumnIndex(DrawingDb.creator_name))
                     name = cursor.getString(cursor.getColumnIndex(DrawingDb.img_name))
                     desc = cursor.getString(cursor.getColumnIndex(DrawingDb.design_description))
                     contri = cursor.getString(cursor.getColumnIndex(DrawingDb.contributed_by))
                }
            }
            cursor.close()
            if(creator.isNotEmpty()){
                binding.creator.visibility=View.GONE
            }
            if(name.isNotEmpty()){
                binding.name.visibility=View.GONE
            }
            if(desc.isNotEmpty()){
                binding.desc.visibility=View.GONE
            }
            if(contri.isNotEmpty()){
                binding.contri.visibility=View.GONE
            }
        if (creator.isNotEmpty() && name.isNotEmpty() && desc.isNotEmpty() && contri.isNotEmpty() )
        {
            binding.update.visibility=View.GONE
            binding.msg.visibility=View.VISIBLE
        }
        }

        binding.update.setOnClickListener{
            if(name.isEmpty()){
             name=binding.name.text.toString().trim()}
            if(creator.isEmpty()){
              creator=binding.creator.text.toString().trim()}
            if(desc.isEmpty()){
              desc=binding.desc.text.toString().trim()}
            if(contri.isEmpty()){
              contri=binding.contri.text.toString().trim()}
            if(!(name.isEmpty() && creator.isEmpty() && desc.isEmpty()&& contri.isEmpty())){
            val db = DrawingDb(requireContext(), null)
            db.update(name,creator,desc,contri,uri)
                dismiss()
                startActivity(Intent(requireContext(),MainActivity::class.java))
            }
            else{
                Toast.makeText(requireContext(),"Fill least one field to update",Toast.LENGTH_SHORT).show()
            }
        }

    }
}