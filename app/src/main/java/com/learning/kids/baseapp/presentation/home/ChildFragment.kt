package com.learning.kids.baseapp.presentation.home


import android.os.Bundle
import androidx.fragment.app.Fragment


import com.learning.kids.baseapp.R
import com.learning.kids.baseapp.core.fragments.BaseFragment

/**
 * A simple [Fragment] subclass.
 */
class ChildFragment : BaseFragment() {
    override val layoutId: Int
        get() = R.layout.fragment_child



    companion object {
        const val POSITION_KEY = "FragmentPositionKey"
        fun newInstance(args: Bundle): ChildFragment = ChildFragment().apply { arguments = args }
    }
}
