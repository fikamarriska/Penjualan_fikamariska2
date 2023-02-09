package com.example.penjualan_fikamariska.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [tb_barang::class],
    version = 1
)
abstract class db_penjualan : RoomDatabase() {
    abstract fun TbBarangDao() : tb_barangDao

    companion object{
        @Volatile private var instance : db_penjualan?= null
        private val LOCK = Any()

        operator fun invoke (context: Context) =
            instance?: synchronized(LOCK){
                instance?: buildDatabase(context).also {
                    instance = it
                }
            }
        private fun buildDatabase (context: Context) = Room.databaseBuilder(
            context.applicationContext,
            db_penjualan::class.java,
            "penjualan123"
        ).build()
    }
}