package com.example.penjualan_fikamariska

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.penjualan_fikamariska.room.Constant
import com.example.penjualan_fikamariska.room.db_penjualan
import com.example.penjualan_fikamariska.room.tb_barang
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    val db by lazy { db_penjualan(this) }
    lateinit var PenjualAdapter : PenjualanAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        halEdit()
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    fun loadData(){
        CoroutineScope(Dispatchers.IO).launch {
            val penjualan = db.TbBarangDao().tampilbarang()
            Log.d("MainActivity","dbResponse: $penjualan")
            withContext(Dispatchers.Main){
                PenjualAdapter.setData(penjualan)
            }
        }
    }

    fun halEdit(){
        btninput.setOnClickListener{
            intentEdit(0, Constant.TYPE_CREATE)
        }
    }

    fun intentEdit(Id_barang: Int, intentType: Int){
        startActivity(
            Intent(applicationContext, EditActivity::class.java)
                .putExtra("intent_id_brg", Id_barang)
                .putExtra("intent_type", intentType)
        )
    }

    fun setupRecyclerView(){
        PenjualAdapter = PenjualanAdapter(arrayListOf(), object: PenjualanAdapter.onAdapterlistener{
            override fun onClick(tbBarang: tb_barang) {
                intentEdit(tbBarang.id_brg, Constant.TYPE_READ)
            }

            override fun onUpdate(tbBarang: tb_barang) {
                intentEdit(tbBarang.id_brg, Constant.TYPE_UPDATE)
            }

            override fun onDelete(tbBarang: tb_barang) {
                deleteBarang(tbBarang)
            }
        }
        )

        listdataPenjualan.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = PenjualAdapter
        }
    }

    private fun deleteBarang (tbBarang: tb_barang){
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("Konfirmasi")
            setMessage("Yakin hapus ${tbBarang.nama_brg}?")
            setNegativeButton("Batal") { dialogInterface, i -> dialogInterface.dismiss()
            }
            setPositiveButton("Hapus") { dialogInterface, i ->
                CoroutineScope(Dispatchers.IO).launch {
                    db.TbBarangDao().deletetbbarang(tbBarang)
                    dialogInterface.dismiss()
                    loadData()
                }
            }
        }
        alertDialog.show()

    }
    }
