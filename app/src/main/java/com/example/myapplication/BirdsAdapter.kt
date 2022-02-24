package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.database.Birds
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.list_item.view.*

class BirdsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var compositeDisposable = CompositeDisposable()

    var birds: List<Birds> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = birds.size

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        compositeDisposable.clear()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val bird = birds[position]
        holder.itemView.textView.text = bird.name
        holder.itemView.imageFavorite.isChecked = bird.favorite == 1

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}