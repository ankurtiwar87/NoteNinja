package com.ankur.noteninja.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.ankur.noteninja.Models.Note
import com.ankur.noteninja.R
import com.ankur.noteninja.databinding.ListItemBinding
import com.google.android.material.card.MaterialCardView
import kotlin.random.Random

class NotesAdapter(private val context:Context, private val listener:NotesClickListener):RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    private val fullList=ArrayList<Note>()
    private val list = ArrayList<Note>()


    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {

        val binding =ListItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item,parent,false))
    }

    override fun getItemCount(): Int {
        return  list.size
    }


     fun updateList(newList:ArrayList<Note>){

        fullList.clear()
        fullList.addAll(newList)


        list.clear()
        list.addAll(fullList)
        notifyDataSetChanged()


    }

     fun filterList(search:String)
    {

        list.clear()
        for (item in fullList){
            if (item.title?.lowercase()?.contains(search.lowercase())==true ||
                item.note?.lowercase()?.contains(search.lowercase())==true)
            {
                list.add(item)
            }

        }

        notifyDataSetChanged()
    }


    private fun randomColor():Int{

        val colorList = ArrayList<Int>()
        colorList.add(R.color.color1)
        colorList.add(R.color.color2)
        colorList.add(R.color.color3)
        colorList.add(R.color.color4)
        colorList.add(R.color.color5)
        colorList.add(R.color.color6)
        colorList.add(R.color.color7)

        val seed = System.currentTimeMillis().toInt()
        val randomIndex = Random(seed).nextInt(colorList.size)

        return colorList[randomIndex]
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = list[position]
        holder.binding.notesTitle.text=currentItem.title
        holder.binding.notesTitle.isSelected=true

        holder.binding.Notes.text=currentItem.note
        holder.binding.NotesDate.text=currentItem.date
        holder.binding.NotesDate.isSelected=true

        holder.binding.cardView.setCardBackgroundColor(holder.binding.cardView.resources.getColor(randomColor(),null))

        holder.binding.cardView.setOnClickListener {
            listener.onItemClicked(list[holder.adapterPosition])
        }
        holder.binding.cardView.setOnLongClickListener{
            listener.onLongItemClick(list[holder.adapterPosition],holder.binding.cardView)
            true
        }



    }



    interface NotesClickListener{

        fun onItemClicked(note: Note)

        fun onLongItemClick(note: Note,cardView: CardView)
    }
}