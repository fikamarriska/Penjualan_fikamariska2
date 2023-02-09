package com.example.penjualan_fikamariska.room

import androidx.room.*

@Dao
interface tb_barangDao {
    @Insert
    fun addtbbarang (tbBarang: tb_barang)

    @Update
    fun updatetbbarang (tbBarang: tb_barang)

    @Delete
    fun deletetbbarang (tbBarang: tb_barang)

    @Query("SELECT * FROM tb_barang")
    fun tampilbarang(): List<tb_barang>

    @Query("SELECT * FROM tb_barang WHERE id_brg=:tb_barang_idbrg")
    fun tampilsemua(tb_barang_idbrg: Int): List<tb_barang>
}