package com.chocolateloverraj.androidapiwebview;

import android.app.AlertDialog;
import android.content.Context;
import android.util.ArraySet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.play.core.splitinstall.SplitInstallManager;
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
    private static boolean shouldShowAsInstalled(
            Collection<String> installedModules,
            Collection<String> modulesThatWillBeUninstalledInBackground, String module) {
        return installedModules.contains(module) && !modulesThatWillBeUninstalledInBackground.contains(module);
    }

    private final LayoutInflater inflater;
    private final SplitInstallManager installManager;
    private final Collection<String> modulesThatWillBeUninstalledInBackground = new ArraySet<>();

    public MyRecyclerViewAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        installManager = SplitInstallManagerFactory.create(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = inflater.inflate(R.layout.apis_list_item, parent, false);
        return new ViewHolder(view, installManager, modulesThatWillBeUninstalledInBackground);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Apis.Api api = Apis.apis[position];
        holder.apiIcon.setImageResource(api.getIcon());
        holder.apiName.setText(api.getName());
        holder.apiDescription.setText(api.getDescription());
        final Set<String> installedModules = installManager.getInstalledModules();
        final String module = api.getModule();
        final boolean checked = shouldShowAsInstalled(installedModules, modulesThatWillBeUninstalledInBackground, module);
        holder.downloadSwitch.setChecked(checked);
        holder.downloadSwitch.setButtonDrawable(checked
                ? R.drawable.ic_baseline_download_done_24
                : R.drawable.ic_baseline_download_24);
    }

    @Override
    public int getItemCount() {
        return Apis.apis.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView apiIcon;
        TextView apiName;
        TextView apiDescription;
        SwitchMaterial downloadSwitch;

        public ViewHolder(
                @NonNull View itemView,
                SplitInstallManager installManager,
                Collection<String> modulesThatWillBeUninstalledInBackground) {
            super(itemView);

            apiIcon = itemView.findViewById(R.id.api_icon);
            apiName = itemView.findViewById(R.id.api_name);
            apiDescription = itemView.findViewById(R.id.api_description);
            downloadSwitch = itemView.findViewById(R.id.api_download_switch);

            downloadSwitch.setOnCheckedChangeListener((compoundButton, b) -> {
                if (!b) {
                    final String module = Apis.apis[getLayoutPosition()].getModule();
                    if (shouldShowAsInstalled(installManager.getInstalledModules(), modulesThatWillBeUninstalledInBackground, module)) {
                        installManager.deferredUninstall(Collections.singletonList(module));
                        System.out.println(module + ": " + installManager.getInstalledModules().contains(module));
                    }
                    new AlertDialog.Builder(itemView.getContext())
                            .setTitle("Installing and uninstalling is not implemented yet")
                            .show();
                }
            });
        }
    }
}
