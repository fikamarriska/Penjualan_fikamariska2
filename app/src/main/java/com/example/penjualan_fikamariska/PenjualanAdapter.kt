package com.example.penjualan_fikamariska

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.penjualan_fikamariska.room.tb_barang
import kotlinx.android.synthetic.main.activity_edit.view.*
import kotlinx.android.synthetic.main.activity_penjualan_adapter.view.*

class PenjualanAdapter (private val penjualan: ArrayList<tb_barang>, private val listener:  onAdapterlistener):
        RecyclerView.Adapter<PenjualanAdapter.PenjualanViewholder>(){

    class PenjualanViewholder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PenjualanViewholder {
       return PenjualanViewholder(
           LayoutInflater.from(parent.context).inflate(R.layout.activity_penjualan_adapter, parent, false)
       )
    }

    override fun onBindViewHolder(holder: PenjualanViewholder, position: Int) {
        val penjualann = penjualan [position]
        holder.view.Tnamabrg.text = penjualann.nama_brg
        holder.view.Tidbrg.text = penjualann.id_brg.toString()
        holder.view.CV.setOnClickListener{
            listener.onClick(penjualann)
        }
        holder.view.ic_edit.setOnClickListener{
            listener.onUpdate(penjualann)
        }
        holder.view.ic_delete.setOnClickListener{
            listener.onDelete(penjualann)
        }
    }

    override fun getItemCount() = penjualan.size

    fun setData (list: List<tb_barang>){
        penjualan.clear()
        penjualan.addAll(list)
        notifyDataSetChanged()
    }
    interface onAdapterlistener{
        fun onClick(tbBarang: tb_barang)
        fun onUpdate(tbBarang: tb_barang)
        fun onDelete(tbBarang: tb_barang)
    }

}

