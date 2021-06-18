package com.example.socialapp.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.socialapp.R
import com.example.socialapp.adapters.UserViewModel_chat
import com.example.socialapp.model.user
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.firebase.ui.firestore.paging.LoadingState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.auth.User
import java.lang.Exception


class List_People : Fragment() {


    lateinit var madapter: FirestorePagingAdapter<user, UserViewModel_chat>
    val auth by lazy {
        FirebaseAuth.getInstance()
    }
    val db by lazy {
        FirebaseFirestore.getInstance().collection("users").orderBy("displayname", Query.Direction.DESCENDING)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        SetupAdapter()
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_list__people, container, false)
    }

    private fun SetupAdapter() {
        val config = PagedList.Config.Builder().setPrefetchDistance(2).setPageSize(10).setEnablePlaceholders(false).build()
        val options = FirestorePagingOptions.Builder<user>()
                .setLifecycleOwner(viewLifecycleOwner)
                .setQuery(db,config,user::class.java)
                .build()
        madapter = object :FirestorePagingAdapter<user,UserViewModel_chat>(options){
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewModel_chat {
                val view = layoutInflater.inflate(R.layout.item_chat_list,parent,false)
                return UserViewModel_chat(view)
            }

            override fun onBindViewHolder(holder: UserViewModel_chat, position: Int, model: user) {
                holder.bind(user = model)
            }

            override fun onLoadingStateChanged(state: LoadingState) {
                super.onLoadingStateChanged(state)
//                while (state){
//                    LoadingState.ERROR{
//
//                    }
//                }
            }

            override fun onError(e: Exception) {
                super.onError(e)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       val recyclerView : RecyclerView = getView()?.findViewById(R.id.List_people1)!!
       recyclerView.apply {
           layoutManager=LinearLayoutManager(requireContext())
           adapter=madapter
       }
    }
}


