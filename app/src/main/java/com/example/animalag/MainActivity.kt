package com.example.animalag

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.example.animalag.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentSeekBarValue: Int = 0 // Class-level variable to store SeekBar value

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSpinner()
        setupSeekBar()
        setupGoButton()
    }

    private fun setupSeekBar() {
        binding.NumScroller.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // Update the class-level variable with the current SeekBar value
                currentSeekBarValue = progress
                // Optionally, update the SeekBar value display next to the SeekBar
                binding.seekBarValue.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun setupGoButton() {
        binding.GoButton.setOnClickListener {
            // Use the stored SeekBar value to calculate and display the age and expectancy
            calculateAndDisplayAgeAndExpectancy(currentSeekBarValue)
        }
    }
    private fun setupSpinner() {
        ArrayAdapter.createFromResource(
            this,
            R.array.animalTypes, // Make sure this array exists in your resources
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinner.adapter = adapter
        }
    }

    private fun calculateAndDisplayAgeAndExpectancy(humanAge: Int) {
        val selectedAnimal = binding.spinner.selectedItem.toString()
        val actualAge = calculateActualAge(selectedAnimal, humanAge)
        val lifeExpectancy = getLifeExpectancy(selectedAnimal)

        // Update textView3 with the calculated actual age
        binding.textView3.text = getString(R.string.your_pet_s_real_age, actualAge.toString())

        // Update LifeExpectancy TextView with the calculated life expectancy
        binding.LifeExpectancy.text = getString(R.string.your_pet_s_life_expectancy, lifeExpectancy.toString())

        // Assuming textView2 should display the human equivalent age of the pet
        binding.textView2.text = actualAge.toString()

        // Assuming textView4 should display the life expectancy of the pet
        binding.textView4.text = lifeExpectancy.toString()
    }

    private fun calculateActualAge(animal: String, humanAge: Int): Number {
        return when (animal) {
            "Dog" -> calculateDogYears(humanAge)
            "Rabbit" -> calculateRabbitYears(humanAge)
            "Hamster" -> calculateHamsterYears(humanAge)
            "Goldfish" -> calculateGoldfishYears(humanAge) // Adjusted based on the 30-year lifespan you mentioned
            "Cat" -> calculateCatYears(humanAge)
            "Parakeet" -> calculateParakeetYears(humanAge)
            else -> humanAge
        }
    }

    private fun calculateDogYears(humanAge: Int): Int {
        return when {
            humanAge <= 1 -> 15
            humanAge == 2 -> 24
            else -> 24 + (humanAge - 2) * 5
        }
    }
    private fun calculateGoldfishYears(humanAge: Int): Int {
        return when {
            humanAge <= 0.5 -> (humanAge * 28).toInt() // The first 0.5 years scale up to 14 human years
            else -> 14 + ((humanAge - 0.5) * (86 / 29.5)).toInt() // After 0.5 years, scale the age with a different ratio
        }
    }

    private fun calculateRabbitYears(humanAge: Int): Number {
        return when {
            humanAge <= 1 -> ((humanAge / 1.0) * 8) + 12 // Approximation for first year
            else -> 20 + (humanAge - 1) * 6
        }
    }

    private fun calculateHamsterYears(humanAge: Int): Int {
        return humanAge * 25 // Adjusting the scale to fit the 2-weeks to 1 human year ratio
    }

    private fun calculateCatYears(humanAge: Int): Int {
        return when {
            humanAge <= 1 -> 15
            humanAge == 2 -> 24
            else -> 24 + (humanAge - 2) * 4
        }
    }
    private fun calculateParakeetYears(parakeetAge: Int): Int {
        // Given that 1 parakeet year is equivalent to 16 human years,
        // we directly apply this conversion rate.
        return parakeetAge * 16
    }


    private fun getLifeExpectancy(animal: String): Int {
        return when (animal) {
            "Parakeet" -> 20
            "Rabbit" -> 9
            "Hamster" -> 3
            "Dog" -> 13
            "Goldfish" -> 30
            "Cat" -> 15
            else -> 0
        }
    }
}
