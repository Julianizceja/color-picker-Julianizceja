package edu.jc.csu411.project1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.Switch

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
    private lateinit var colorBox: View

    private var redIntensity = 0.0
    private var greenIntensity = 0.0
    private var blueIntensity = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        switchRed = findViewById(R.id.switchRed)
        seekBarRed = findViewById(R.id.seekBarRed)
        editTextRed = findViewById(R.id.editTextRed)
        switchGreen = findViewById(R.id.switchGreen)
        seekBarGreen = findViewById(R.id.seekBarGreen)
        editTextGreen = findViewById(R.id.editTextGreen)
        switchBlue = findViewById(R.id.switchBlue)
        seekBarBlue = findViewById(R.id.seekBarBlue)
        editTextBlue = findViewById(R.id.editTextBlue)
        colorBox = findViewById(R.id.colorBox)

        updateColorBox()

        switchRed.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                seekBarRed.isEnabled = true
                editTextRed.isEnabled = true
            } else {
                redIntensity = 0.0
                seekBarRed.isEnabled = false
                editTextRed.isEnabled = false
            }
            updateColorBox()
        }

        seekBarRed.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (!switchRed.isChecked) {
                    return
                }
                redIntensity = progress.toDouble() / 100
                editTextRed.setText(redIntensity.toString())
                updateColorBox()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        switchGreen.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                seekBarGreen.isEnabled = true
                editTextGreen.isEnabled = true
            } else {
                greenIntensity = 0.0
                seekBarGreen.isEnabled = false
                editTextGreen.isEnabled = false
            }
            updateColorBox()
        }

        seekBarGreen.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (!switchGreen.isChecked) {
                    return
                }
                greenIntensity = progress.toDouble() / 100
                editTextGreen.setText(greenIntensity.toString())
                updateColorBox()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        switchBlue.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                seekBarBlue.isEnabled = true
                editTextBlue.isEnabled = true
            } else {
                blueIntensity = 0.0
                seekBarBlue.isEnabled = false
                editTextBlue.isEnabled = false
            }
            updateColorBox()
        }

        seekBarBlue.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (!switchBlue.isChecked) {
                    return
                }
                blueIntensity = progress.toDouble() / 100
                editTextBlue.setText(blueIntensity.toString())
                updateColorBox()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
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
            updateColorBox()
        }
        //updateColorBox()
    }

    private fun updateColorBox() {
        val color = android.graphics.Color.rgb(

            (redIntensity * 255).toInt(),
            (greenIntensity * 255).toInt(),
            (blueIntensity * 255).toInt()
        )
        colorBox.setBackgroundColor(color)
    }
}