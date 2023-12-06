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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.first
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
    private lateinit var myAppRepository: MyAppRepository
    private lateinit var colorStateViewModel: ColorStateViewModel


    private var redIntensity = 0.0
    private var greenIntensity = 0.0
    private var blueIntensity = 0.0

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putDouble("redIntensity", redIntensity)
        outState.putDouble("greenIntensity", greenIntensity)
        outState.putDouble("blueIntensity", blueIntensity)

        outState.putBoolean("switchRedChecked", switchRed.isChecked)
        outState.putBoolean("seekBarRedEnabled", seekBarRed.isEnabled)
        outState.putInt("seekBarRedProgress", seekBarRed.progress)
        outState.putString("editTextRedText", editTextRed.text.toString())

        outState.putBoolean("switchGreenChecked", switchGreen.isChecked)
        outState.putBoolean("seekBarGreenEnabled", seekBarGreen.isEnabled)
        outState.putInt("seekBarGreenProgress", seekBarGreen.progress)
        outState.putString("editTextGreenText", editTextGreen.text.toString())

        outState.putBoolean("switchBlueChecked", switchBlue.isChecked)
        outState.putBoolean("seekBarBlueEnabled", seekBarBlue.isEnabled)
        outState.putInt("seekBarBlueProgress", seekBarBlue.progress)
        outState.putString("editTextBlueText", editTextBlue.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        switchRed.isChecked = savedInstanceState.getBoolean("switchRedChecked", true)
        seekBarRed.isEnabled = savedInstanceState.getBoolean("seekBarRedEnabled", true)
        seekBarRed.progress = savedInstanceState.getInt("seekBarRedProgress", 0)
        editTextRed.setText(savedInstanceState.getString("editTextRedText", "Red"))

        switchGreen.isChecked = savedInstanceState.getBoolean("switchGreenChecked", true)
        seekBarGreen.isEnabled = savedInstanceState.getBoolean("seekBarGreenEnabled", true)
        seekBarGreen.progress = savedInstanceState.getInt("seekBarGreenProgress", 0)
        editTextGreen.setText(savedInstanceState.getString("editTextGreenText", "Green"))

        switchBlue.isChecked = savedInstanceState.getBoolean("switchBlueChecked", true)
        seekBarBlue.isEnabled = savedInstanceState.getBoolean("seekBarBlueEnabled", true)
        seekBarBlue.progress = savedInstanceState.getInt("seekBarBlueProgress", 0)
        editTextBlue.setText(savedInstanceState.getString("editTextBlueText", "Blue"))

        redIntensity = savedInstanceState.getDouble("redIntensity", redIntensity)
        greenIntensity = savedInstanceState.getDouble("greenIntensity", greenIntensity)
        blueIntensity = savedInstanceState.getDouble("blueIntensity", blueIntensity)

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
        val defaultSeekBarValue = 0
        val defaultEditTextRed = "Red"
        val defaultEditTextGreen = "Green"
        val defaultEditTextBlue = "Blue"


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
                if (redIntensity != 0.0) {
                    seekBarRed.progress = (redIntensity * seekBarRed.max).toInt()
                    editTextRed.setText(redIntensity.toString())
                }
            } else {
                redIntensity = seekBarRed.progress.toDouble() / 100
                seekBarRed.isEnabled = false
                editTextRed.isEnabled = false
                editTextRed.setText("Red")
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
                if (greenIntensity != 0.0) {
                    seekBarGreen.progress = (greenIntensity * seekBarGreen.max).toInt()
                    editTextGreen.setText(greenIntensity.toString())
                }
            } else {
                greenIntensity = seekBarGreen.progress.toDouble() / 100
                seekBarGreen.isEnabled = false
                editTextGreen.isEnabled = false
                editTextGreen.setText("Green")
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
                if (blueIntensity != 0.0) {
                    seekBarBlue.progress = (blueIntensity * seekBarBlue.max).toInt()
                    editTextBlue.setText(blueIntensity.toString())
                }
            } else {
                blueIntensity = seekBarBlue.progress.toDouble() / 100
                seekBarBlue.isEnabled = false
                editTextBlue.isEnabled = false
                editTextBlue.setText("Blue")
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
            seekBarRed.progress = defaultSeekBarValue
            seekBarGreen.progress = defaultSeekBarValue
            seekBarBlue.progress = defaultSeekBarValue
            switchRed.isChecked = false
            switchGreen.isChecked = false
            switchBlue.isChecked = false
            editTextRed.setText(defaultEditTextRed)
            editTextGreen.setText(defaultEditTextGreen)
            editTextBlue.setText(defaultEditTextBlue)
            redIntensity = 0.0
            greenIntensity = 0.0
            blueIntensity = 0.0
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
        super.onPause()
        lifecycleScope.launch {
            myAppRepository.saveIntensities(redIntensity, greenIntensity, blueIntensity)
        }
    }

}