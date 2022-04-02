package com.chocolateloverraj.androidapiwebview.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.chocolateloverraj.androidapiwebview.PageViewer;
import com.chocolateloverraj.androidapiwebview.databinding.FragmentHomeBinding;

import java.util.Objects;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        binding.openButton.setOnClickListener(this::onOpen);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void onOpen(View view) {
        System.out.println(binding.openText.getText());
        Intent intent = new Intent(getActivity(), PageViewer.class);
        Uri uri = Uri.parse(Objects.requireNonNull(binding.openText.getText()).toString());
        if (uri.getScheme() == null)
            uri = Uri.fromParts("android-api-webview", uri.getPath(), uri.getFragment());
        else if (!uri.getScheme().equals("https")) {
            Toast.makeText(getActivity(), "Url must be https", Toast.LENGTH_LONG).show();
            return;
        }
        System.out.println(uri);
        System.out.println(uri.getScheme() + ", " + uri.getHost() + ", " + uri.getPath() + ", " + uri.getFragment());
        intent.setData(uri);
        startActivity(intent);
    }
}