package edu.jc.csu411.project1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.Switch
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt




class MainActivity : AppCompatActivity() {

    private lateinit var switchRed : Switch
    private lateinit var switchGreen : Switch
    private lateinit var switchBlue : Switch
    private lateinit var seekBarRed : SeekBar
    private lateinit var seekBarGreen : SeekBar
    private lateinit var seekBarBlue : SeekBar
    private lateinit var editTextRed: EditText
    private lateinit var editTextGreen: EditText
    private lateinit var editTextBlue: EditText
    private lateinit var resetButton: Button
    private lateinit var imageColor: ImageView
    private lateinit var colorViewModel: ColorViewModel
    private lateinit var myAppRepository: MyAppRepository


    private var redIntensity = 0.0
    private var greenIntensity = 0.0
    private var blueIntensity = 0.0

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putDouble("redIntensity", redIntensity)
        outState.putDouble("greenIntensity", greenIntensity)
        outState.putDouble("blueIntensity", blueIntensity)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        redIntensity = savedInstanceState.getDouble("redIntensity", 0.0)
        greenIntensity = savedInstanceState.getDouble("greenIntensity", 0.0)
        blueIntensity = savedInstanceState.getDouble("blueIntensity", 0.0)
        updatePumpkinColor()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myAppRepository = MyAppRepository.getInstance(this)

        switchRed = findViewById(R.id.switchRed)
        seekBarRed = findViewById(R.id.seekBarRed)
        editTextRed = findViewById(R.id.editTextRed)
        switchGreen = findViewById(R.id.switchGreen)
        seekBarGreen = findViewById(R.id.seekBarGreen)
        editTextGreen = findViewById(R.id.editTextGreen)
        switchBlue = findViewById(R.id.switchBlue)
        seekBarBlue = findViewById(R.id.seekBarBlue)
        editTextBlue = findViewById(R.id.editTextBlue)
        imageColor = findViewById(R.id.imageColor)

        switchRed.isChecked = false
        seekBarRed.isEnabled = false
        editTextRed.isEnabled = false
        switchGreen.isChecked = false
        seekBarGreen.isEnabled = false
        editTextGreen.isEnabled = false
        switchBlue.isChecked = false
        seekBarBlue.isEnabled = false
        editTextBlue.isEnabled = false

        updatePumpkinColor()

        lifecycleScope.launch {
            myAppRepository.redIntensity.collect {
                redIntensity = it.toDouble()
                updatePumpkinColor()
            }
        }

        lifecycleScope.launch {
            myAppRepository.greenIntensity.collect {
                greenIntensity = it.toDouble()
                updatePumpkinColor()
            }
        }

        lifecycleScope.launch {
            myAppRepository.blueIntensity.collect {
                blueIntensity = it.toDouble()
                updatePumpkinColor()
            }
        }


        switchRed.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                seekBarRed.isEnabled = true
                editTextRed.isEnabled = true
            } else {
                redIntensity = 0.0
                seekBarRed.progress = 0
                seekBarRed.isEnabled = false
                editTextRed.isEnabled = false
                editTextRed.setText("0.0")
            }
            updatePumpkinColor()
        }

        seekBarRed.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (!switchRed.isChecked) {
                    return
                }
                redIntensity = progress.toDouble() / 100
                editTextRed.setText(redIntensity.toString())
                updatePumpkinColor()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        editTextRed.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val inputVal = (s.toString().toDoubleOrNull() ?: 0.0).coerceIn(0.0, 1.0)
                seekBarRed.progress = (inputVal * seekBarRed.max).roundToInt()
                updatePumpkinColor()
            }
        })

        switchGreen.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                seekBarGreen.isEnabled = true
                editTextGreen.isEnabled = true
            } else {
                greenIntensity = 0.0
                seekBarGreen.isEnabled = false
                seekBarGreen.progress = 0
                editTextGreen.isEnabled = false
                editTextGreen.setText("0.0")
            }
            updatePumpkinColor()
        }

        seekBarGreen.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (!switchGreen.isChecked) {
                    return
                }
                greenIntensity = progress.toDouble() / 100
                editTextGreen.setText(greenIntensity.toString())
                updatePumpkinColor()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        editTextGreen.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val inputVal = (s.toString().toDoubleOrNull() ?: 0.0).coerceIn(0.0, 1.0)
                seekBarGreen.progress = (inputVal * seekBarGreen.max).roundToInt()
                updatePumpkinColor()
            }
        })

        switchBlue.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                seekBarBlue.isEnabled = true
                editTextBlue.isEnabled = true
            } else {
                blueIntensity = 0.0
                seekBarBlue.isEnabled = false
                seekBarBlue.progress = 0
                editTextBlue.isEnabled = false
                editTextBlue.setText("0.0")
            }
            updatePumpkinColor()
        }

        seekBarBlue.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (!switchBlue.isChecked) {
                    return
                }
                blueIntensity = progress.toDouble() / 100
                editTextBlue.setText(blueIntensity.toString())
                updatePumpkinColor()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        editTextBlue.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val inputVal = (s.toString().toDoubleOrNull() ?: 0.0).coerceIn(0.0, 1.0)
                seekBarBlue.progress = (inputVal * seekBarBlue.max).roundToInt()
                updatePumpkinColor()
            }
        })

        val resetButton = findViewById<Button>(R.id.resetButton)
        resetButton.setOnClickListener {
            switchRed.isChecked = false
            seekBarRed.progress = 0
            editTextRed.setText("0.0")
            switchGreen.isChecked = false
            seekBarGreen.progress = 0
            editTextGreen.setText("0.0")
            switchBlue.isChecked = false
            seekBarBlue.progress = 0
            editTextBlue.setText("0.0")
            updatePumpkinColor()
        }

    }

    private fun updatePumpkinColor() {
        val redIntensity = calculateIntensity(seekBarRed, editTextRed, switchRed)
        val greenIntensity = calculateIntensity(seekBarGreen, editTextGreen, switchGreen)
        val blueIntensity = calculateIntensity(seekBarBlue, editTextBlue, switchBlue)

        val color = android.graphics.Color.rgb(
            (redIntensity * 255).toInt(),
            (greenIntensity * 255).toInt(),
            (blueIntensity * 255).toInt()
        )
        imageColor.setColorFilter(color)
    }

    private fun calculateIntensity(seekBar: SeekBar, editText: EditText, switch: Switch): Float {
        return if (switch.isChecked) {
            val seekBarVal = seekBar.progress / seekBar.max.toFloat()
            val inputVal = editText.text.toString().toDoubleOrNull()?.toFloat() ?: 0.0f
            if (seekBarVal > inputVal) {
                seekBarVal
            } else {
                inputVal
            }
        } else {
            0.0f
        }
    }

    override fun onPause() {
        lifecycleScope.launch {
            myAppRepository.saveIntensities(redIntensity, greenIntensity, blueIntensity)
        }
        super.onPause()
    }
}