package com.example.movie_app_xml.view.add

import android.app.Activity.RESULT_OK
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.movie_app_xml.R
import com.example.movie_app_xml.api.ApiFactory
import com.example.movie_app_xml.databinding.FragmentAddBinding
import com.example.movie_app_xml.view_model.DetailViewModel
import com.example.movie_app_xml.view_model.FormAddViewModel
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.widget.DatePicker

import androidx.core.app.ActivityCompat.startActivityForResult
import com.example.movie_app_xml.data.AppDatabase
import com.example.movie_app_xml.data.Repository
import com.example.movie_app_xml.data.entity.OnTheAirLocal
import com.example.movie_app_xml.data.entity.TrendingLocal
import com.example.movie_app_xml.model.Genre
import com.example.movie_app_xml.util.Const
import com.example.movie_app_xml.util.Const.GALLERY
import java.text.SimpleDateFormat
import java.util.*


class AddFragment : Fragment() {
    private lateinit var _binding : FragmentAddBinding
    private val binding get() = _binding
    private lateinit var addViewModel: FormAddViewModel
    private val restApi by lazy { ApiFactory.create() }
    lateinit var datePickerDialog : DatePickerDialog

    val itemsAdult = listOf("Pilih Status","Yes", "No")
    val itemsType = listOf("Pilih Type Item","Movie", "Tv Show")
    var adultPosition = 0
    var listOfGenre = arrayListOf<Genre>()
    var currentPhotoPath = ""
    var typePosition = 0
    var bitmap : Bitmap? = null
    var vTitle : String = ""
    var vPopularity : String = ""
    var vDate : String = ""
    var vAdult : String = ""
    var vLanguage : String = ""
    var vGenre : String = ""
    var vOverview : String = ""
    var vType : String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapterAdult = ArrayAdapter(requireContext(), R.layout.list_item, itemsAdult)
        val adapterType = ArrayAdapter(requireContext(), R.layout.list_item, itemsType)
        dateTimePicker()
        addViewModel = FormAddViewModel()
        addViewModel.repositor = Repository(restApi, AppDatabase.getDatabase(requireContext()))

        binding.apply {
            actvAddAdult.setAdapter(adapterAdult)
            actvAddAdult.setOnItemClickListener { parent, view, position, id ->
                vAdult = itemsAdult[position]
                adultPosition = position
            }

            actvAddType.setAdapter(adapterType)
            actvAddType.setOnItemClickListener { parent, view, position, id ->
                vType = itemsType[position]
                typePosition = position
            }

            ivAddPicture.setOnClickListener { imageChooser() }
            btnAddPickDate.setOnClickListener {
                datePickerDialog.show()
                btnAddPickDate.text = vDate
            }
            tietAddTitle.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun afterTextChanged(s: Editable?) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    vTitle = s.toString()
                }
            })
            tietAddPopularity.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun afterTextChanged(s: Editable?) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    vPopularity = s.toString()
                }
            })
            tietAddLang.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun afterTextChanged(s: Editable?) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    vLanguage = s.toString()
                }
            })
            tietAddOverview.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun afterTextChanged(s: Editable?) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    vOverview = s.toString()
                }
            })
            btnAddSave.setOnClickListener {
                if (vTitle.isNotEmpty() && vPopularity.isNotEmpty() && vAdult.isNotEmpty() && adultPosition > 0 && vDate.isNotEmpty() && vLanguage.isNotEmpty()  && vOverview.isNotEmpty() && vType.isNotEmpty() && typePosition > 0) {
                    if (vType.equals("Movie")) {
                        val trending = TrendingLocal()
                        trending.title = vTitle
                        trending.releaseDate = vDate
                        trending.popularity = vPopularity.toDouble()
                        trending.adult = vAdult.equals("Yes")
                        trending.originalLanguage = vLanguage
                        var listGenre = ArrayList<Genre>()
                        if (checkboxRomance.isChecked){
                            val genre = Genre()
                            genre.name = checkboxRomance.text.toString()
                            listGenre.add(genre)
                        }
                        if (checkboxAction.isChecked){
                            val genre = Genre()
                            genre.name = checkboxAction.text.toString()
                            listGenre.add(genre)
                        }
                        if (checkboxHorror.isChecked){
                            val genre = Genre()
                            genre.name = checkboxHorror.text.toString()
                            listGenre.add(genre)
                        }
                        trending.backdropPath = currentPhotoPath
                        trending.posterPath = currentPhotoPath
                        trending.mediaType = "movie"
                        trending.genres = listGenre
                        trending.overview = vOverview
                        trending.typeTrending = Const.TYPE_TRENDING_LOCAL
                        addViewModel.insertTrendingMovie(trending)
                    } else if (vType.equals("Tv Show")) {
                        var listGenre = ArrayList<Genre>()
                        if (checkboxRomance.isChecked){
                            val genre = Genre()
                            genre.name = checkboxRomance.text.toString()
                            listGenre.add(genre)
                        }
                        if (checkboxAction.isChecked){
                            val genre = Genre()
                            genre.name = checkboxAction.text.toString()
                            listGenre.add(genre)
                        }
                        if (checkboxHorror.isChecked){
                            val genre = Genre()
                            genre.name = checkboxHorror.text.toString()
                            listGenre.add(genre)
                        }

                        val onTheAirLocal = OnTheAirLocal(
                            voteAverage = 0f,
                            backdropPath = currentPhotoPath,
                            firstAirDate = vDate,
                            genres = listGenre,
                            language = vLanguage,
                            overview = vOverview,
                            popularity = vPopularity.toDouble(),
                            posterPath = currentPhotoPath,
                            name = vTitle,
                            id = null,
                            typeOnTheAir = Const.TYPE_ONTHEAIR_LOCAL,
                            adult = vAdult.equals("Yes")
                        )
                        addViewModel.insertOnTheAir(onTheAirLocal)
                    }
                    tietAddTitle.setText("")
                    btnAddPickDate.text = ""
                    tietAddPopularity.setText("")
                    tietAddLang.setText("")
                    tietAddOverview.setText("")
                    actvAddAdult.setText("")
                    actvAddType.setText("")
                }
            }
        }
    }

    fun dateTimePicker(){
        val mYear: Int
        val mMonth: Int
        val mDay: Int
        val now = Calendar.getInstance()
        mYear = now.get(Calendar.YEAR)
        mMonth = now.get(Calendar.MONTH)
        mDay = now.get(Calendar.DAY_OF_MONTH)
        now.time = Date()
        datePickerDialog = DatePickerDialog(
            requireContext(),
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                val cal = Calendar.getInstance()
                cal.set(year, month, dayOfMonth)
                vDate = SimpleDateFormat("yyyy-MM-dd").format(cal.time)
                binding.btnAddPickDate.text = vDate
            }, mYear, mMonth, mDay
        )
    }

    fun imageChooser() {
        // create an instance of the
        // intent of the type image
        val i = Intent()
        i.type = "image/*"
        i.action = Intent.ACTION_GET_CONTENT

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), GALLERY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == GALLERY) {
                // Get the url of the image from data
                val selectedImageUri: Uri? = data?.data
                if (null != selectedImageUri) {
                    selectedImageUri.let {
                        if (Build.VERSION.SDK_INT < 28) {
                            bitmap = it?.let { image ->
                                MediaStore.Images
                                    .Media.getBitmap(requireActivity().contentResolver, image)
                            }

                        } else {
                            val source = it?.let { image ->
                                ImageDecoder
                                    .createSource(requireActivity().contentResolver, image)
                            }
                            bitmap = source?.let { deco -> ImageDecoder.decodeBitmap(deco) }
                        }
                        binding.ivAddPicture.setImageBitmap(bitmap)

                        var cursor =
                            it?.let { uri -> requireActivity().contentResolver.query(uri, null, null,null, null) }
                        if (cursor == null){
                            currentPhotoPath = it?.path.toString()
                        } else {
                            cursor.moveToFirst()
                            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                            currentPhotoPath = cursor.getString(idx)
                            cursor.close()
                        }
                    }
                }
            }
        }
    }
}