package com.example.penjualan_fikamariska

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.penjualan_fikamariska.room.Constant
import com.example.penjualan_fikamariska.room.db_penjualan
import com.example.penjualan_fikamariska.room.tb_barang
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditActivity : AppCompatActivity() {

    val db by lazy { db_penjualan(this) }
    private var tbBarangIdbrg: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        simpandata()
        setupView()
        tbBarangIdbrg = intent.getIntExtra("intent_id_brg", 0)
        Toast.makeText(this,tbBarangIdbrg.toString(),Toast.LENGTH_SHORT).show()
    }

    fun setupView(){
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type", 0)
        when (intentType){
            Constant.TYPE_CREATE -> {
                btnupdate.visibility = View.GONE

            }
            Constant.TYPE_READ -> {
                btnsavee.visibility = View.GONE
                btnupdate.visibility = View.GONE
                tampilbarang()

            }
            Constant.TYPE_UPDATE -> {
                btnsavee.visibility = View.GONE
                tampilbarang()

            }
        }
    }

    fun simpandata(){
        btnsavee.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                db.TbBarangDao().addtbbarang(
                    tb_barang(idbrg.text.toString().toInt(),
                        namabrg.text.toString(),
                        hargabrg.text.toString().toInt(),
                        stok.text.toString().toInt())
                )
                finish()
            }
        }

        btnupdate.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                db.TbBarangDao().updatetbbarang(
                    tb_barang(tbBarangIdbrg,
                        namabrg.text.toString(),
                        hargabrg.text.toString().toInt(),
                        stok.text.toString().toInt())
                )
                finish()
            }
        }
    }

    fun tampilbarang(){
        tbBarangIdbrg = intent.getIntExtra("intent_id_brg", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val barangg = db.TbBarangDao().tampilsemua(tbBarangIdbrg) [0]
            val dataId : String = barangg.id_brg.toString()
            val dataHarga : String = barangg.harga_brg.toString()
            val dataStok: String = barangg.stok.toString()
            idbrg.setText(dataId)
            namabrg.setText(barangg.nama_brg)
            hargabrg.setText(dataHarga)
            stok.setText(dataStok)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}