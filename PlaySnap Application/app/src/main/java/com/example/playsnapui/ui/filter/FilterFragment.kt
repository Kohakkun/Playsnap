package com.example.playsnapui.ui.filter

import SharedData
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.playsnapui.R
import com.example.playsnapui.databinding.BottomSheetFilterPageBinding
import com.example.playsnapui.databinding.FragmentFilterBinding
import com.example.playsnapui.databinding.FragmentHomeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import androidx.lifecycle.lifecycleScope
import com.example.playsnapui.data.Games
import com.example.playsnapui.ui.recommendgame.RecommendGameFragment
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class FilterFragment : Fragment() {

    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!

    private var bottomSheetBinding: BottomSheetFilterPageBinding? = null
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterBinding.inflate(inflater, container, false)

        // INI PERBAIKANNYA: Inflate BottomSheet secara mandiri
        bottomSheetBinding = BottomSheetFilterPageBinding.inflate(inflater, binding.bottomSheetFilterPage, true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        var batasUsia1 : Int = 0
//        var batasUsia2Bawah : Int = 0
//        var batasUsia2Atas : Int = 0
//        var batasUsia3 : Int = 0
        var batasUsiaBawah : Int = 0
        var batasUsiaAtas : Int = 0
        var batasPemain1 : Int = 0
        var batasPemain2Bawah : Int = 0
        var batasPemain2Atas : Int = 0
        var batasPemain3 : Int = 0
        var lokasiContainer : String = ""
        var propertyContainer : String = ""
        var isNullUsia : Boolean = true
        var isNullLokasi : Boolean = true
        var isNullPemain : Boolean = true
        var isNullProperti : Boolean = true

        binding!!.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_FilterFragment_to_HomeFragment)
        }

        bottomSheetBinding?.let { sheetBinding ->
            // Set listener untuk tombol usia
            sheetBinding.usiaOpt1Btn.setOnClickListener {
                if(isNullUsia == true){
                    isNullUsia = false
                    binding.usiaValue.text = "<6 th"
                    batasUsiaBawah = 0
                    batasUsiaAtas = 5
                }
                else if(isNullUsia == false){
                    isNullUsia = true
                    binding.usiaValue.text = "-"
                    batasUsiaBawah = 0
                    batasUsiaAtas = 0
                }
            }
            sheetBinding.usiaOpt2Btn.setOnClickListener {
                if(isNullUsia == true){
                    isNullUsia = false
                    binding.usiaValue.text = "6 - 10 th"
                    batasUsiaBawah = 6
                    batasUsiaAtas = 10
                }
                else if(isNullUsia == false){
                    isNullUsia = true
                    binding.usiaValue.text = "-"
                    batasUsiaBawah = 0
                    batasUsiaAtas = 0
                }
            }
            sheetBinding.usiaOpt3Btn.setOnClickListener {
                if(isNullUsia == true){
                    isNullUsia = false
                    binding.usiaValue.text = ">10 th"
                    batasUsiaBawah = 11
                    batasUsiaAtas = 13
                }
                else if(isNullUsia == false){
                    isNullUsia = true
                    binding.usiaValue.text = "-"
                    batasUsiaBawah = 0
                    batasUsiaAtas = 0
                }
            }

            // Set listener untuk tombol lokasi
            sheetBinding.lokasiOpt1Btn.setOnClickListener {
                if(isNullLokasi == true){
                    isNullLokasi = false
                    binding.lokasiValue.text = "Indoor"
                    lokasiContainer = "Indoor"
                }
                else if(isNullLokasi == false){
                    isNullLokasi = true
                    binding.lokasiValue.text = "-"
                    lokasiContainer = ""
                }
            }
            sheetBinding.lokasiOpt2Btn.setOnClickListener {
                if(isNullLokasi == true){
                    isNullLokasi = false
                    binding.lokasiValue.text = "Outdoor"
                    lokasiContainer = "Outdoor"
                }
                else if(isNullLokasi == false){
                    isNullLokasi = true
                    binding.lokasiValue.text = "-"
                    lokasiContainer = ""
                }
            }

            // Set listener untuk tombol jumlah pemain
            sheetBinding.pemainOpt1Btn.setOnClickListener {
                if(isNullPemain == true){
                    isNullPemain = false
                    binding.pemainValue.text = "<3 org"
                    batasPemain1 = 2
                }
                else if(isNullPemain == false){
                    isNullPemain = true
                    binding.pemainValue.text = "-"
                    batasPemain1 = 0
                    batasPemain2Bawah = 0
                    batasPemain2Atas = 0
                    batasPemain3 = 0
                }
            }
            sheetBinding.pemainOpt2Btn.setOnClickListener {
                if(isNullPemain == true){
                    isNullPemain = false
                    binding.pemainValue.text = "3 - 5 org"
                    batasPemain2Bawah = 3
                    batasPemain2Atas = 5
                }
                else if(isNullPemain == false){
                    isNullPemain = true
                    binding.pemainValue.text = "-"
                    batasPemain1 = 0
                    batasPemain2Bawah = 0
                    batasPemain2Atas = 0
                    batasPemain3 = 0
                }
            }
            sheetBinding.pemainOpt3Btn.setOnClickListener {
                if(isNullPemain == true){
                    isNullPemain = false
                    binding.pemainValue.text = ">5 org"
                    batasPemain3 = 6
                }
                else if(isNullPemain == false){
                    isNullPemain = true
                    binding.pemainValue.text = "-"
                    batasPemain1 = 0
                    batasPemain2Bawah = 0
                    batasPemain2Atas = 0
                    batasPemain3 = 0
                }
            }

            // Set listener untuk tombol properti
            sheetBinding.propertiOpt1Btn.setOnClickListener {
                if(isNullProperti == true){
                    isNullProperti = false
                    binding.propertiValue.text = "Ya"
                    propertyContainer = "Ya"
                }
                else if(isNullProperti == false){
                    isNullProperti = true
                    binding.lokasiValue.text = "-"
                    propertyContainer = ""
                }
            }
            sheetBinding.propertiOpt2Btn.setOnClickListener {
                if(isNullProperti == true){
                    isNullProperti = false
                    binding.propertiValue.text = "Tidak"
                    propertyContainer = "Tidak"
                }
                else if(isNullProperti == false){
                    isNullProperti = true
                    binding.lokasiValue.text = "-"
                    propertyContainer = ""
                }
            }
        }


        binding.mulaiButton.setOnClickListener {
            compareGamesWithDatabase(isNullUsia, isNullLokasi, isNullPemain, isNullProperti, batasPemain1, batasPemain2Bawah, batasPemain2Atas, batasPemain3, batasUsiaBawah, batasUsiaAtas, lokasiContainer, propertyContainer)
        }


    }

    private fun compareGamesWithDatabase(isNullUsia : Boolean, isNullLokasi : Boolean, isNullPemain : Boolean, isNullProperti : Boolean, batasPemain1 : Int, batasPemain2Bawah : Int, batasPemain2Atas : Int, batasPemain3 : Int, batasUsiaBawah : Int, batasUsiaAtas : Int, lokasiContainer : String, propertyContainer : String){
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val gamesSnapshot = db.collection("games").get().await()
                var matchingGames: MutableSet<Games> = mutableSetOf()

                for (document in gamesSnapshot.documents) {
                    val game = document.toObject(Games::class.java)  // 🔥 Konversi langsung ke objek Game

                    if (game != null) {
                        // cek games filter

                        var flag1 : Boolean = false

                        // Usia
                        if(isNullUsia == false){
                            for (i in game.usiaMin..game.usiaMax) {
                                if (i in batasUsiaBawah..batasUsiaAtas) {
                                    matchingGames.add(game)
                                    if(i > 13){
                                        break
                                    }
                                }
                            }
                        }
                        else if(isNullUsia == true){
                            matchingGames.add(game)
                        }
                    }
                }

                var tempGames: MutableSet<Games> = mutableSetOf()
                tempGames = matchingGames.toMutableSet()

                var index1 = 0
                for(game in tempGames){
                    Log.d("Games Temp", "Games : $game")
                    index1++
                }
                Log.d("Games Temp 1" , "Total 1 : $index1")

                if (isNullLokasi == false) {
                    tempGames.removeIf { game ->
                        (lokasiContainer == "Indoor" && game.jenisLokasi == "Outdoor") || (lokasiContainer == "Outdoor" && game.jenisLokasi == "Indoor")
                    }
                }

                var index2 = 0
                for(game in tempGames){
                    Log.d("Games Temp", "Games : $game")
                    index2++
                }
                Log.d("Games Temp 2", "Total 2 : $index2")


                if (isNullProperti == false) {
                    tempGames.removeIf { game ->
                        (propertyContainer == "Ya" && game.properti == "") || (propertyContainer == "Tidak" && game.properti != "")
                    }
                }

                var index3 = 0
                for(game in tempGames){
                    Log.d("Games Temp", "Games : $game")
                    index3++
                }
                Log.d("Games Temp 3", "Total 3 : $index3")

                if (!isNullPemain) {
                    val batasPemain = 6
                    tempGames.removeIf { game ->
                        val rangePemain = game.pemainMin..game.pemainMax
                        var shouldRemove = false

                        if (batasPemain1 != 0) {
                            shouldRemove = shouldRemove || rangePemain.all { it > batasPemain1 }
                        }
                        if (batasPemain2Bawah != 0 && batasPemain2Atas != 0) {
                            shouldRemove = shouldRemove || (rangePemain.all { it < batasPemain2Bawah } || rangePemain.all { it > batasPemain2Atas })
                        }
                        if (batasPemain3 != 0) {
                            shouldRemove = shouldRemove || rangePemain.all { it < batasPemain3 }
                        }

                        shouldRemove
                    }
                }

                var index4 = 0
                for(game in tempGames){
                    Log.d("Games Temp", "Games : $game")
                    index4++
                }
                Log.d("Games Temp 4", "Total 4 : $index4")

                matchingGames = tempGames.toMutableSet()

                var index = 0
                for (game in matchingGames){
                    index++
                    Log.d("Games", "Games : $game")
                }
                Log.d("Index", "Total : $index")

                // kirim data ke fragment sebelah
//                SharedData.batasUsia1 = batasUsia1
//                SharedData.batasUsia2Bawah = batasUsia2Bawah
//                SharedData.batasUsia2Atas = batasUsia2Atas
//                SharedData.batasUsia3 = batasUsia3
                SharedData.batasUsiaBawah = batasUsiaBawah
                SharedData.batasUsiaAtas = batasUsiaAtas
                SharedData.batasPemain1 = batasPemain1
                SharedData.batasPemain2Bawah = batasPemain2Bawah
                SharedData.batasPemain2Atas = batasPemain2Atas
                SharedData.batasPemain3 = batasPemain3
                SharedData.lokasiContainer = lokasiContainer
                SharedData.propertyContainer = propertyContainer

                val emptySet : MutableSet<Games> = mutableSetOf()
                SharedData.recommendedGames = emptySet.toList()
                SharedData.recommendedGames = matchingGames.toList()

                findNavController().navigate(R.id.action_FilterFragment_to_RecGameFragment)

            } catch (e: Exception) {
                lifecycleScope.launch(Dispatchers.Main) {

                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        bottomSheetBinding = null
    }
}
