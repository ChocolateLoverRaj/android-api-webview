package com.chocolateloverraj.androidapiwebview.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.chocolateloverraj.androidapiwebview.PageViewer
import com.chocolateloverraj.androidapiwebview.databinding.FragmentHomeBinding
import java.util.*

class HomeFragment : Fragment() {
    private var binding: FragmentHomeBinding? = null
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding!!.openButton.setOnClickListener { view: View? -> onOpen() }
        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun onOpen() {
        println(binding!!.openText.text)
        val intent = Intent(activity, PageViewer::class.java)
        var uri = Uri.parse(Objects.requireNonNull(binding!!.openText.text).toString())
        if (uri.scheme == null) uri = Uri.fromParts("android-api-webview", uri.path, uri.fragment) else if (uri.scheme != "https") {
            Toast.makeText(activity, "Url must be https", Toast.LENGTH_LONG).show()
            return
        }
        println(uri)
        println(uri.scheme + ", " + uri.host + ", " + uri.path + ", " + uri.fragment)
        intent.data = uri
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
        startActivity(intent)
    }
}