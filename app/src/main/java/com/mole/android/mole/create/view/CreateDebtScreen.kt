package com.mole.android.mole.create.view

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.mole.android.mole.MoleBaseFragment
import com.mole.android.mole.R
import com.mole.android.mole.chat.view.ChatViewImplementation
import com.mole.android.mole.component
import com.mole.android.mole.create.data.usersTestData
import com.mole.android.mole.create.view.chooseside.ChooseSideScreen
import com.mole.android.mole.create.view.steps.CreateStepsScreen
import com.mole.android.mole.databinding.FragmentCreateDebtBinding
import com.mole.android.mole.setResultListenerGeneric

class CreateDebtScreen : MoleBaseFragment<FragmentCreateDebtBinding>(FragmentCreateDebtBinding::inflate) {

    private val router = component().routingModule.router

    private val childNavigator by lazy {
        AppNavigator(
            requireActivity(),
            R.id.create_debt_host,
            fragmentManager = childFragmentManager
        )
    }

    private val navigatorHolder = component().routingModule.navigationHolder

    override fun getNavigator(): Navigator {
        return AppNavigator(requireActivity(), R.id.nav_host_fragment)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val id = arguments?.getInt(EXTRA_ID, -1) ?: -1
        val openChat = arguments?.getBoolean(EXTRA_OPEN_CHAT, false) ?: false

        withChildNavigation {
            router.navigateTo(Screens.ChooseSide(id))
        }
        router.setResultListenerGeneric<Boolean>(ChooseSideScreen.EXTRA_SIDE) {
            withChildNavigation {
                router.replaceScreen(Screens.CreateSteps(it, id))
            }
        }
        router.setResultListenerGeneric<CreatedDebt>(CreateStepsScreen.EXTRA_CREATED_DEBT) {
            if (openChat) {
                router.replaceScreen(Screens.Chat(it.name, -1, it.avatarUrl))
            } else {
                router.exit()
                router.sendResult(EXTRA_CREATED_DEBT, it)
            }
        }
    }

    private fun withChildNavigation(action: () -> Unit) {
        navigatorHolder.setNavigator(childNavigator)
        action()
        navigatorHolder.setNavigator(getNavigator())
    }

    internal object Screens {
        fun ChooseSide(id: Int) = FragmentScreen { ChooseSideScreen.instance(id) }
        fun CreateSteps(side: Boolean, id: Int) = FragmentScreen { CreateStepsScreen.instance(side, id) }
        fun Chat(name: String, totalDebts: Int, avatarUrl: String?) = FragmentScreen { ChatViewImplementation.newInstance(name, totalDebts, avatarUrl) }
    }

    data class CreatedDebt(
        val id: Int,
        val side: Boolean,
        val amount: Int,
        val tag: String,
        val name: String,
        val avatarUrl: String?,
        val userId: Int
    ) : Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readByte() != 0.toByte(),
            parcel.readInt(),
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString(),
            parcel.readInt()
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeInt(id)
            parcel.writeByte(if (side) 1 else 0)
            parcel.writeInt(amount)
            parcel.writeString(tag)
            parcel.writeString(name)
            parcel.writeString(avatarUrl)
            parcel.writeInt(userId)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<CreatedDebt> {
            override fun createFromParcel(parcel: Parcel): CreatedDebt {
                return CreatedDebt(parcel)
            }

            override fun newArray(size: Int): Array<CreatedDebt?> {
                return arrayOfNulls(size)
            }
        }
    }

    companion object {
        fun instance(id: Int = -1, openChat: Boolean = false): CreateDebtScreen {
            return CreateDebtScreen().apply {
                arguments = Bundle().apply {
                    putInt(EXTRA_ID, id)
                    putBoolean(EXTRA_OPEN_CHAT, openChat)
                }
            }
        }
        private const val EXTRA_ID = "create_debt_screen_extra_id"
        private const val EXTRA_OPEN_CHAT = "create_debt_screen_extra_open_chat"
        const val EXTRA_CREATED_DEBT = "created_debt_id_key"
    }

}