package com.chocolateloverraj.androidapiwebview

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import com.google.android.play.core.splitinstall.SplitInstallManager
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.switchmaterial.SwitchMaterial
import android.widget.CompoundButton
import com.google.android.material.snackbar.Snackbar
import android.content.Intent
import android.net.Uri
import android.util.ArraySet
import android.view.View
import android.widget.ImageView
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus

class ApisRecyclerViewAdapter(private val context: Context) : RecyclerView.Adapter<ApisRecyclerViewAdapter.ViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val installManager: SplitInstallManager = SplitInstallManagerFactory.create(context)
    private val modulesThatWillBeUninstalledInBackground: Collection<String> = ArraySet()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.apis_list_item, parent, false)
        return ViewHolder(view, installManager, modulesThatWillBeUninstalledInBackground, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val api = Apis.apis[position]
        holder.apiIcon.setImageResource(api.icon)
        holder.apiName.setText(api.name)
        holder.apiDescription.setText(api.description)
        val installedModules = installManager.installedModules
        val module = api.module
        val checked = shouldShowAsInstalled(installedModules, modulesThatWillBeUninstalledInBackground, module)
        holder.downloadSwitch.isChecked = checked
        updateDownloadSwitchDrawable(holder.downloadSwitch)
    }

    override fun getItemCount(): Int {
        return Apis.apis.size
    }

    class ViewHolder(
            itemView: View,
            installManager: SplitInstallManager,
            modulesThatWillBeUninstalledInBackground: Collection<String>,
            context: Context) : RecyclerView.ViewHolder(itemView) {
        var apiIcon: ImageView = itemView.findViewById(R.id.api_icon)
        var apiName: TextView = itemView.findViewById(R.id.api_name)
        var apiDescription: TextView = itemView.findViewById(R.id.api_description)
        var downloadSwitch: SwitchMaterial = itemView.findViewById(R.id.api_download_switch)

        init {
            downloadSwitch.setOnCheckedChangeListener { _: CompoundButton?, on: Boolean ->
                updateDownloadSwitchDrawable(downloadSwitch)
                val module = Apis.apis[layoutPosition].module
                if (on) {
                    val request = SplitInstallRequest
                            .newBuilder()
                            .addModule(module)
                            .build()
                    installManager
                            .startInstall(request)
                            .addOnSuccessListener { sessionId ->
                                Snackbar
                                        .make(itemView, "Started downloading feature", Snackbar.LENGTH_SHORT)
                                        .show()
                                val listener  = SplitInstallStateUpdatedListener {state ->
                                    if (state.sessionId() == sessionId) {
                                        // TODO: Show progress
                                        if (state.status() == SplitInstallSessionStatus.INSTALLED) {
                                            Snackbar
                                                    .make(itemView, "Installed feature", Snackbar.LENGTH_SHORT)
                                                    .show()
                                        }
                                    }
                                }
                                installManager.registerListener(listener)
                            }
                            .addOnFailureListener {
                                Snackbar
                                        .make(itemView, "Failed to download feature", Snackbar.LENGTH_LONG)
                                        .show()
                            }
                } else {
                    if (shouldShowAsInstalled(installManager.installedModules, modulesThatWillBeUninstalledInBackground, module)) {
                        installManager.deferredUninstall(listOf(module))
                        println(module + ": " + installManager.installedModules.contains(module))
                    }
                    Snackbar.make(itemView, R.string.deferred_uninstall_message, Snackbar.LENGTH_SHORT)
                            .setAction(R.string.more_info) {
                                context.startActivity(Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse("https://developer.android.com/guide/playcore/feature-delivery/on-demand#uninstall_modules"))) }
                            .show()
                }
            }
        }
    }

    companion object {
        private fun shouldShowAsInstalled(
                installedModules: Collection<String>,
                modulesThatWillBeUninstalledInBackground: Collection<String>, module: String): Boolean {
            return installedModules.contains(module) && !modulesThatWillBeUninstalledInBackground.contains(module)
        }

        private fun updateDownloadSwitchDrawable(downloadSwitch: SwitchMaterial) {
            downloadSwitch.setButtonDrawable(if (downloadSwitch.isChecked) R.drawable.ic_baseline_download_done_24 else R.drawable.ic_baseline_download_24)
        }
    }

}