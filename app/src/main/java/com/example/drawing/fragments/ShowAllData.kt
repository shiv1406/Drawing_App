package com.example.drawing.fragments

import android.annotation.SuppressLint
import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.drawing.DataAdapter
import com.example.drawing.ModelClass
import com.example.drawing.R
import com.example.drawing.database.DrawingDb


class ShowAllData : Fragment() {
    val fileList = ArrayList<ModelClass>()
    @SuppressLint("Range", "SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v: View = inflater.inflate(R.layout.fragment_show_all_data, container, false)
        val recyclerView = v.findViewById<RecyclerView>(R.id.rView)
        val db = DrawingDb(requireContext(), null)

        val cursor: Cursor = db.name
        if (cursor.moveToFirst()) {

               val imgUri = cursor.getString(cursor.getColumnIndex(DrawingDb.img_uri))
            val creator=cursor.getString(cursor.getColumnIndex(DrawingDb.creator_name))
            val name=cursor.getString(cursor.getColumnIndex(DrawingDb.img_name))
            val desc=cursor.getString(cursor.getColumnIndex(DrawingDb.design_description))
            val contri=cursor.getString(cursor.getColumnIndex(DrawingDb.contributed_by))
               fileList.add(ModelClass(name,imgUri,creator,contri,desc))
            while (cursor.moveToNext()) {

                val imgUri = cursor.getString(cursor.getColumnIndex(DrawingDb.img_uri))
                val creator=cursor.getString(cursor.getColumnIndex(DrawingDb.creator_name))
                val name=cursor.getString(cursor.getColumnIndex(DrawingDb.img_name))
                val desc=cursor.getString(cursor.getColumnIndex(DrawingDb.design_description))
                val contri=cursor.getString(cursor.getColumnIndex(DrawingDb.contributed_by))
                fileList.add(ModelClass(name,imgUri,creator,contri,desc))
            }
            cursor.close()
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = DataAdapter(requireContext(), fileList)
        v.findViewById<ImageView>(R.id.add).setOnClickListener {
            val nextFrag = AddImage()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit()
            fileList.clear()
        }
        return v
    }


}