package com.example.drawing
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class DataAdapter(val context: Context, val fileList : List<ModelClass>) : RecyclerView.Adapter<DataAdapter.MyViewHolder>() {
    private val TIME_INTERVAL = 3000
    private var mBackPressed: Long = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.mylist,
            parent,false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val file = fileList[position]
        val fileUri: String = file.imgUri
        holder.name.text=file.imgName
        holder.creator.text=file.creatorName
        holder.desc.text=file.desc
        holder.contri.text=file.contri
        Glide
            .with(context)
            .load(fileUri)
            .into(holder.imageView)

        holder.imageView.setOnClickListener {
            val i = Intent(context, SingleImageActivity::class.java)
            i.putExtra("uri", file.imgUri)
            context.startActivity(i)
        }

    }

    override fun getItemCount(): Int {

        return fileList.size
    }



    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val imageView:ImageView=itemView.findViewById(R.id.img_preview)
        val creator:TextView=itemView.findViewById(R.id.creator)
        val name:TextView=itemView.findViewById(R.id.name)
        val desc:TextView=itemView.findViewById(R.id.desc)
        val contri:TextView=itemView.findViewById(R.id.contri)


    }




}