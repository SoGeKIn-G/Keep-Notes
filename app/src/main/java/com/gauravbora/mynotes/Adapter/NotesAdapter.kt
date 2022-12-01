package com.gauravbora.mynotes.Adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.gauravbora.mynotes.Modals.Note
import com.gauravbora.mynotes.R
import kotlin.random.Random

class NotesAdapter(private val context: Context, val listener: NotesClickListener) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private val notesList= ArrayList<Note>()
    private val fullList= ArrayList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        )
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = notesList[position]
        holder.title.text = currentNote.title
        holder.title.isSelected = true
        holder.note.text = currentNote.note
        holder.date.text = currentNote.date
        holder.date.isSelected = true

        holder.notes_layout.setCardBackgroundColor(
            holder.itemView.resources.getColor(
                randomColor(),
                null
            )
        )


        holder.notes_layout.setOnClickListener {
            listener.onItemClicked(notesList[holder.adapterPosition])
        }

        holder.notes_layout.setOnLongClickListener {
            listener.onLongItemClicked(notesList[holder.adapterPosition], holder.notes_layout)
            true
        }

    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val notes_layout = itemView.findViewById<CardView>(R.id.card_layout)
        val title = itemView.findViewById<TextView>(R.id.title)
        val note = itemView.findViewById<TextView>(R.id.note)
        val date = itemView.findViewById<TextView>(R.id.date)

    }


    fun randomColor(): Int {
        val list = ArrayList<Int>()
        list.add(R.color.NoteColor1)
        list.add(R.color.NoteColor2)
        list.add(R.color.NoteColor3)
        list.add(R.color.NoteColor4)
        list.add(R.color.NoteColor5)
        list.add(R.color.NoteColor6)
        list.add(R.color.NoteColor7)
        list.add(R.color.NoteColor8)
        list.add(R.color.NoteColor9)
        list.add(R.color.NoteColor10)

        val seed = System.currentTimeMillis().toInt()
        val randomindex = Random(seed).nextInt(list.size)
        return list[randomindex]
    }

    interface NotesClickListener {

        fun onItemClicked(note: Note)
        fun onLongItemClicked(note: Note, cardView: CardView)

    }

    fun updateList(newList: List<Note>) {

        fullList.clear()
        fullList.addAll(newList)

        notesList.clear()
        notesList.addAll(fullList)

        notifyDataSetChanged()
    }


    fun filterList(search: String) {
        notesList.clear()

        for (item in fullList) {
            if (item.title?.lowercase()?.contains(search.lowercase()) == true
                || item.note?.lowercase()?.contains(search.lowercase()) == true
            ) {
                notesList.add(item)
            }
        }
        notifyDataSetChanged()
    }

}