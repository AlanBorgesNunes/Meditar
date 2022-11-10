package com.app.meditar

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Message
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.Toast
import com.app.meditar.databinding.FragmentFiveBinding
import java.io.IOException

class FiveFragment : Fragment() {
    private lateinit var dialog: AlertDialog
 private lateinit var binding: FragmentFiveBinding
    private var totalTime: Int = 0
    var mediaPlayer : MediaPlayer? = null
  override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFiveBinding.inflate(layoutInflater).apply {
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
        val audioUrl = "https://firebasestorage.googleapis.com/v0/b/meditar-f176f.appspot.com/o/Medita%C3%A7%C3%A3o%20-%2035%20minutos%20de%20sons%20relaxantes.mp3?alt=media&token=9093ca80-d023-4e3c-8074-b4bcb92598ba"

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