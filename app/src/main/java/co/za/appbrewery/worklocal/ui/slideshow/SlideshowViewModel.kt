package co.za.appbrewery.worklocal.ui.slideshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SlideshowViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Oops! nothing has been added yet"
    }
    val text: LiveData<String> = _text
}