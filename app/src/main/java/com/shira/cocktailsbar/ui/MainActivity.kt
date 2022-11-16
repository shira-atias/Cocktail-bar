//the image speech_bubble_white comes for free from https://pngtree.com/element/down?id=ODQ0ODEyMQ==&type=1&time=1664292365&token=OWQwZjRhZGEwNTY5OWU3NzNhNDllMzE4NTgxNzNhN2U=&t=0
//the image nightclub comes for free from https://pngtree.com/back/down?id=MTMyOTE0Mg==&type=1&time=1664294612&token=MGI4Y2EwZDZkZDJmZWQ3ZTU3Y2FkYTVjNDgxMTUwMWM=
//the animation waitress and heart_animation come for free from https://app.lottiefiles.com/animation
//the music com for free from https://freemusicarchive.org/music/Wolf_Asylum/Dogflower/Wolf_Asylum_-_Dogflower_-EP-_-_03_Koord/
package com.shira.cocktailsbar.ui

import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import com.shira.cocktailsbar.R
import com.shira.cocktailsbar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var ivStopPlay:ImageView
    private lateinit var viewModel: ViewModel
    private var isPlay = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel= ViewModelProvider(this).get(ViewModel::class.java)
        mediaPlayer = MediaPlayer.create(this,R.raw.wolf_asylum_koord)
        mediaPlayer.start()
        ivStopPlay = binding.ivPlayMusic

        ivStopPlay.setOnClickListener{
            if (mediaPlayer.isPlaying){
                mediaPlayer.pause()
                ivStopPlay.setImageResource(R.drawable.ic_baseline_play_circle_outline_24)
                isPlay = false
            }else{
                mediaPlayer.start()
                ivStopPlay.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24)
                isPlay = true
            }

        }

    }

    override fun onStop() {
        super.onStop()
            mediaPlayer.pause()
    }

    override fun onStart() {
        super.onStart()
        if (isPlay){
            mediaPlayer.start()
        }else{
            mediaPlayer.pause()
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}