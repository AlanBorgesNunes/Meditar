package com.app.meditar

import android.app.AlertDialog
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.app.meditar.databinding.FragmentOneBinding
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import java.io.IOException


class OneFragment : Fragment() {
    private lateinit var binding: FragmentOneBinding
    private var totalTime: Int = 0
    var mediaPlayer : MediaPlayer? = null
    private lateinit var dialog: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOneBinding.inflate(layoutInflater).apply {
            viewLifecycleOwner
        }
        initView()
        return binding.root
    }

    private fun initView() {

        mediaPlayer = MediaPlayer()
        mediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
        binding.seekBar.progress = 0

        binding.seekBar.max = mediaPlayer!!.duration

        totalTime = mediaPlayer!!.duration


        binding.btnPlay.setOnClickListener {
            play()
        }

        binding.btnPause.setOnClickListener{
          dialog()
        }

    }

    private fun play(){
        val audioUrl = "https://firebasestorage.googleapis.com/v0/b/meditar-f176f.appspot.com/o/15%20Minutos%20para%20Meditar_%20Relaxamento%20Profundo%2C%20Medita%C3%A7%C3%A3o%20e%20Inspira%C3%A7%C3%A3o.mp3?alt=media&token=dd19a4e9-a9fb-4d5b-81b1-737afa2e4c7d"

        mediaPlayer = MediaPlayer()
        mediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)

        try {
            mediaPlayer!!.setDataSource(audioUrl)
            mediaPlayer!!.prepare()
            mediaPlayer!!.start()
            binding.animation.playAnimation()
            Toast.makeText(requireContext(),
            "Relaxe!",Toast.LENGTH_LONG).show()
        }catch (e : IOException){
            e.printStackTrace()
        }
    }

    private fun pause(){
        mediaPlayer = MediaPlayer()
        mediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)

            if (!mediaPlayer!!.isPlaying){
                mediaPlayer!!.stop()
                mediaPlayer!!.reset()
                mediaPlayer!!.release()
                binding.animation.pauseAnimation()
            }else{
                Toast.makeText(requireContext(),
                "rodando",Toast.LENGTH_LONG).show()
            }

    }





    private fun dialog(){
        val buid = AlertDialog.Builder(requireContext(), R.style.ThemeCustomdialog)
        val view = layoutInflater.inflate(R.layout.alertdialog, null)
        buid.setView(view)

        val btnAceito = view.findViewById<Button>(R.id.aceita)
        btnAceito.setOnClickListener {
            if (pause() != null){
                Toast.makeText(requireContext(), "O Aúdio será interrompido em alguns instantes, Aguarde!", Toast.LENGTH_LONG).show()
                dialog.dismiss()
            }else{
                Toast.makeText(requireContext(), "Erro! Tente novamente.", Toast.LENGTH_LONG).show()
            }

        }

        val btnClose = view.findViewById<ImageButton>(R.id.close)
        btnClose.setOnClickListener {
            dialog.dismiss()

            Toast.makeText(requireContext(), "Ótima opção!!", Toast.LENGTH_LONG).show()
        }

        dialog = buid.create()
        dialog.show()
    }

}