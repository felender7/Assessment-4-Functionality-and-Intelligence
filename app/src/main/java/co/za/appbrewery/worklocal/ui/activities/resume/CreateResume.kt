package co.za.appbrewery.worklocal.ui.activities.resume

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import co.za.appbrewery.worklocal.R
import android.Manifest;
import android.app.Activity
import android.content.ContentValues
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.*
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


class CreateResume : AppCompatActivity() {
    private val REQUEST_CODE = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_resume)

        //actionbar
        val actionbar = supportActionBar

        //set actionbar title
        "Create Resume".also { actionbar!!.title = it }

        //set back button
        actionbar?.setDisplayHomeAsUpEnabled(true)
        actionbar?.setDisplayHomeAsUpEnabled(true)

        // write permission to access the storage
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            1
        )
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1
        )


        //Identification type dropDown
        val identification_type = resources.getStringArray(R.array.id_passport)
        val dropDownAutoCompletetxtIdent =
            findViewById<AutoCompleteTextView>(R.id.autoCompleteIDPassport)
        val arrayAdapterIdentType =
            ArrayAdapter(this, R.layout.dropdown_item_resume, identification_type)
        dropDownAutoCompletetxtIdent.setAdapter(arrayAdapterIdentType)


        //Gender dropDown
        val gender = resources.getStringArray(R.array.gender)
        val dropDownAutoCompletetxtGender =
            findViewById<AutoCompleteTextView>(R.id.autoCompleteGender)
        val arrayAdapterGender = ArrayAdapter(this, R.layout.dropdown_item_resume, gender)
        dropDownAutoCompletetxtGender.setAdapter(arrayAdapterGender)

        //Disability dropDown
        val disability = resources.getStringArray(R.array.disability)
        val dropDownAutoCompletetxtDisability =
            findViewById<AutoCompleteTextView>(R.id.autoCompleteDisability)
        val arrayAdapterDisability = ArrayAdapter(this, R.layout.dropdown_item_resume, disability)
        dropDownAutoCompletetxtDisability.setAdapter(arrayAdapterDisability)

        //Job Function  dropDown
        val job_function = resources.getStringArray(R.array.job_function)
        val dropDownAutoCompletetxtJobFunction =
            findViewById<AutoCompleteTextView>(R.id.autoCompleteJobFunction)
        val arrayAdapterJobFunction =
            ArrayAdapter(this, R.layout.dropdown_item_resume, job_function)
        dropDownAutoCompletetxtJobFunction.setAdapter(arrayAdapterJobFunction)


        //Job Industry dropDown
        val job_industry = resources.getStringArray(R.array.job_industry)
        val dropDownAutoCompletetxtJobIndustry =
            findViewById<AutoCompleteTextView>(R.id.autoCompleteJobIndustry)
        val arrayAdapterJobIndustry =
            ArrayAdapter(this, R.layout.dropdown_item_resume, job_industry)
        dropDownAutoCompletetxtJobIndustry.setAdapter(arrayAdapterJobIndustry)

        //Salary Rage dropDown
        val salary_rage = resources.getStringArray(R.array.salary_rage)
        val dropDownAutoCompletetxtSalaryRage =
            findViewById<AutoCompleteTextView>(R.id.autoCompleteSalaryRage)
        val arrayAdapterSalaryRage = ArrayAdapter(this, R.layout.dropdown_item_resume, salary_rage)
        dropDownAutoCompletetxtSalaryRage.setAdapter(arrayAdapterSalaryRage)


        //availability dropDown
        val availability = resources.getStringArray(R.array.availability)
        val dropDownAutoCompletetxtAvailability =
            findViewById<AutoCompleteTextView>(R.id.autoCompleteAvailability)
        val arrayAdapterAvailability =
            ArrayAdapter(this, R.layout.dropdown_item_resume, availability)
        dropDownAutoCompletetxtAvailability.setAdapter(arrayAdapterAvailability)

        //Education
        val education = resources.getStringArray(R.array.education)
        val dropDownAutoCompletetxtEducation =
            findViewById<AutoCompleteTextView>(R.id.autoCompleteEducation)
        val arrayAdapterEducation = ArrayAdapter(this, R.layout.dropdown_item_resume, education)
        dropDownAutoCompletetxtEducation.setAdapter(arrayAdapterEducation)

        //hooks
        val btnSaveResume = findViewById<Button>(R.id.btnSaveResume)
        val pickImageView = findViewById<ImageView>(R.id.pickImageView)


        btnSaveResume.setOnClickListener {
            Intent(this, DetailsResume::class.java).also {
                startActivity(it)
            }
        }
        pickImageView.setOnClickListener {
            this.capturePhoto()
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    //Capture Photo
    private fun capturePhoto() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, REQUEST_CODE)
    }

    // this method saves the image to gallery
    private fun saveMediaToStorage(bitmap: Bitmap) {
        // Generating a file name
        val filename = "${System.currentTimeMillis()}.jpg"

        // Output stream
        var fos: OutputStream? = null

        // For devices running android >= Q
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // getting the contentResolver
            this.contentResolver?.also { resolver ->

                // Content resolver will process the contentvalues
                val contentValues = ContentValues().apply {

                    // putting file information in content values
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }

                // Inserting the contentValues to
                // contentResolver and getting the Uri
                val imageUri: Uri? =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                // Opening an outputstream with the Uri that we got
                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            // These for devices running on android < Q
            val imagesDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }

        fos?.use {
            // Finally writing the bitmap to the output stream that we opened
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            Toast.makeText(this, "Captured View and saved to Gallery", Toast.LENGTH_SHORT).show()
        }
    }

    //Display image after Capture
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE && data != null) {
            val imageView = findViewById<ImageView>(R.id.pickImageView)
            imageView.setImageBitmap(data.extras?.get("data") as Bitmap)
        }
    }

}