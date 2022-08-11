package com.singularitycoder.perfectme

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.singularitycoder.perfectme.databinding.FragmentMenuBottomSheetBinding
import com.singularitycoder.perfectme.databinding.ListItemBottomSheetMenuBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize

@AndroidEntryPoint
class MenuBottomSheetFragment : BottomSheetDialogFragment() {

    companion object {
        @JvmStatic
        fun newInstance(bottomSheetMenuList: List<BottomSheetMenu>) = MenuBottomSheetFragment().apply {
            arguments = Bundle().apply { putParcelableArrayList(ARG_PARAM_MENU_LIST, ArrayList(bottomSheetMenuList)) }
        }
    }

    private lateinit var binding: FragmentMenuBottomSheetBinding

    private val viewModel: SharedViewModel by activityViewModels()

    private var bottomSheetMenuList = ArrayList<BottomSheetMenu>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bottomSheetMenuList = arguments?.getParcelableArrayList<BottomSheetMenu>(ARG_PARAM_MENU_LIST) as ArrayList<BottomSheetMenu>
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMenuBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setupUI()
        binding.setupUserActionListeners()
    }

    private fun FragmentMenuBottomSheetBinding.setupUI() {
        setTransparentBackground()
        if (bottomSheetMenuList.size > 5) root.layoutParams.height = deviceHeight() / 2
        rvBottomSheet.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = MenuBottomSheetAdapter().apply { menuList = bottomSheetMenuList }
        }
    }

    private fun FragmentMenuBottomSheetBinding.setupUserActionListeners() {
        (rvBottomSheet.adapter as MenuBottomSheetAdapter).setItemLongClickListener { it: BottomSheetMenu ->
            viewModel.menuLiveData.value = it
        }
    }

    inner class MenuBottomSheetAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var menuList = emptyList<BottomSheetMenu>()
        private var itemClickListener: (bottomSheetMenu: BottomSheetMenu) -> Unit = {}

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val itemBinding = ListItemBottomSheetMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return BottomSheetViewHolder(itemBinding)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            (holder as BottomSheetViewHolder).setData(menuList[position])
        }

        override fun getItemCount(): Int = menuList.size

        override fun getItemViewType(position: Int): Int = position

        fun setItemLongClickListener(listener: (bottomSheetMenu: BottomSheetMenu) -> Unit) {
            itemClickListener = listener
        }

        inner class BottomSheetViewHolder(
            private val itemBinding: ListItemBottomSheetMenuBinding,
        ) : RecyclerView.ViewHolder(itemBinding.root) {
            @SuppressLint("SetTextI18n")
            fun setData(bottomSheetMenu: BottomSheetMenu) {
                itemBinding.apply {
                    tvTitle.text = bottomSheetMenu.title
                    tvTitle.showHideIcon(
                        context = requireContext(),
                        showTick = true,
                        leftIcon = bottomSheetMenu.icon,
                        leftIconColor = R.color.purple_200,
                        direction = 1
                    )
                    root.setOnLongClickListener {
                        itemClickListener.invoke(bottomSheetMenu)
                        false
                    }
                }
            }
        }
    }
}

private const val ARG_PARAM_MENU_LIST = "ARG_PARAM_MENU_LIST"

@Parcelize
data class BottomSheetMenu(
    val id: Long,
    val title: String,
    @DrawableRes val icon: Int,
) : Parcelable