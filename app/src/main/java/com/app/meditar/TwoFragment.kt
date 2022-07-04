package com.app.meditar

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Message
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.app.meditar.databinding.FragmentTwoBinding


class TwoFragment : Fragment() {
    private lateinit var binding: FragmentTwoBinding
    private var totalTime: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTwoBinding.inflate(layoutInflater).apply {
            viewLifecycleOwner
        }
        initView()
        return binding.root
    }


    private fun initView() {
        val mediaPleyer = MediaPlayer.create(requireContext(), R.raw.segunda_musica)

        binding.seekBar.progress = 0

        binding.seekBar.max = mediaPleyer.duration

        totalTime = mediaPleyer.duration

        binding.btnPlay.setOnClickListener {
            if (!mediaPleyer.isPlaying){
                mediaPleyer.start()
                binding.btnPlay.setImageResource(R.drawable.btn_pause)
                binding.animation.playAnimation()
            }else{
                mediaPleyer.pause()
                binding.btnPlay.setImageResource(R.drawable.btn_er)
                binding.animation.pauseAnimation()
            }
        }

        binding.seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, pos: Int, changed: Boolean) {
                if (changed){
                    mediaPleyer.seekTo(pos)
                }

            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })

        Thread(Runnable {
            while (mediaPleyer != null){
                try {
                    var msg = Message()
                    msg.what = mediaPleyer.currentPosition
                    handler.sendMessage(msg)
                    Thread.sleep(1000)

                }catch (e: InterruptedException){

                }
            }
        }).start()
    }

    @SuppressLint("HandlerLeak")
    var handler = object : Handler(){
        override fun handleMessage(msg: Message) {
            var currentPosition = msg.what

            binding.seekBar.progress = currentPosition

            var elapseTime = createTimeLabel(currentPosition)
            binding.ini.text = elapseTime



            var remaningTime = createTimeLabel(totalTime - currentPosition)

            binding.fim.text = "-$remaningTime"


        }
    }

    private fun createTimeLabel(time: Int): String{

        var timeLabel = ""
        var min = time / 1000 / 60
        var sec = time / 1000 % 60

        timeLabel = "$min:"
        if (sec < 10 ) timeLabel += "0"
        timeLabel += sec

        return timeLabel

    }

}