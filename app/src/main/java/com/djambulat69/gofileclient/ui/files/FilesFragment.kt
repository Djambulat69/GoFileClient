package com.djambulat69.gofileclient.ui.files

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.djambulat69.gofileclient.R
import com.djambulat69.gofileclient.databinding.FragmentFilesBinding
import com.djambulat69.gofileclient.ui.files.recyclerview.FileDiffCallback
import com.djambulat69.gofileclient.ui.files.recyclerview.FilesAdapter
import com.djambulat69.gofileclient.utils.dispatchTo
import com.djambulat69.gofileclient.utils.disposeSafe
import com.djambulat69.gofileclient.utils.viewBinding
import com.djambulat69.gofileclient.utils.viewModelsFactory
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable

class FilesFragment : Fragment(R.layout.fragment_files) {

    private val disposable = CompositeDisposable()

    private val binding: FragmentFilesBinding by viewBinding { FragmentFilesBinding.bind(requireView()) }

    private val viewModel: FilesViewModel by viewModelsFactory()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.filesRecyclerView.adapter = FilesAdapter(FileDiffCallback())

        viewModel.files
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe { newFiles -> (binding.filesRecyclerView.adapter as FilesAdapter).items = newFiles }
            .dispatchTo(disposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.disposeSafe()
    }
}
