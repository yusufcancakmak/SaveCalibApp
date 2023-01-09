package com.yusufcancakmak.noteappkotlin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yusufcancakmak.noteappkotlin.databinding.ItemNotesBinding

class AdapterNotes:RecyclerView.Adapter<AdapterNotes.MyviewHolder>() {
    private var notes= emptyList<Notes>()

    inner class MyviewHolder(val binding: ItemNotesBinding):RecyclerView.ViewHolder(binding.root)
    //item click
    var OnItemClick:((Notes)-> Unit)? =null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyviewHolder {
        return MyviewHolder(ItemNotesBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyviewHolder, position: Int) {
        val currentlist=notes[position]
        holder.binding.tvNoteTitle.text=currentlist.noteTitle.toString()
        holder.binding.tvNoteDesc.text=currentlist.noteDescription.toString()

        holder.itemView.setOnClickListener {
            OnItemClick?.invoke(currentlist)
        }
    }

    override fun getItemCount()=notes.size


    fun setData(notes: List<Notes>){
        this.notes= notes
        notifyDataSetChanged()

    }
}