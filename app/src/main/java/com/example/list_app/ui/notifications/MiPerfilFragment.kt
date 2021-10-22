package com.example.list_app.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.list_app.R
import com.example.list_app.databinding.FragmentMiPerfilBinding

class MiPerfilFragment : Fragment() {

    private lateinit var notificationsViewModel: MiPerfilViewModel
    private var _binding: FragmentMiPerfilBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    //private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_mi_perfil, container, false)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}