package com.example.penjualan_fikamariska.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class tb_barang (
    @PrimaryKey
    val id_brg : Int,
    val nama_brg : String,
    val harga_brg : Int,
    val stok : Int
)