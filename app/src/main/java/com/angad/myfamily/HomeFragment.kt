package com.angad.myfamily

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    lateinit var inviteAdapter: InviteAdapter

    private val listContacts:ArrayList<ContactModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nameU:String
        val listMembers = listOf(
            MemberModel(
                nameU = "Angad",
                address = "Mahatma Joytiba Phule nagar IIT market, Powai-400076",
                battery = "80%",
                distance = "210"
            ),
            MemberModel(
                nameU = "Ram",
                address = "17 street gandhi nagar near Dhramshala, Gorakhpur",
                battery = "55%",
                distance = "580"
            ),
            MemberModel(
                nameU = "Shyam",
                address = "Jai bhim office near tilak chok, Sant Nagar",
                battery = "75%",
                distance = "430"
            ),
            MemberModel(
                nameU = "Rajan",
                address = "Dream the mall near Bhandup Station",
                battery = "83%",
                distance = "500"
            ),
        )

        val adapter = MemberAdapter(listMembers)

        val recycler = requireView().findViewById<RecyclerView>(R.id.recycler_member)
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter

//        **************************************************************************
//        Fetching the contact functionality



        Log.d("FetchContact45", "fetchcontact: start karne wale hai ")

//        Creating or working on a thread using coroutine

        Log.d("FetchContact45", "fetchcontact: start ho gaya hai ${listContacts.size} ")
        inviteAdapter = InviteAdapter(listContacts)

        fetchDatabaseContacts()

        Log.d("FetchContact45", "fetchcontact: end ho gaya hai")

        CoroutineScope(Dispatchers.IO).launch {
            Log.d("FetchContact45", "fetchcontact: coroutine start ")

//            listContacts.clear()
//            listContacts.addAll(fetchDatabaseContacts())
//            withContext(Dispatchers.Main){
//                inviteAdapter.notifyDataSetChanged()
//            }

//            listContacts.addAll(fetchContacts())

//            fetchDatabaseContacts()

            insertDatabaseContacts(fetchContacts())

//            withContext(Dispatchers.Main){
//                inviteAdapter.notifyDataSetChanged()
//            }
            Log.d("FetchContact45", "fetchcontact: coroutine end ${listContacts.size} ")
        }



        val inviteRecycler = requireView().findViewById<RecyclerView>(R.id.recycler_invite)
        inviteRecycler.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        inviteRecycler.adapter = inviteAdapter

    }

    private  fun fetchDatabaseContacts()  {
        val database = MyFamilyDatabase.getDatabase(requireContext())
        database.contactDao().getAllContact().observe(viewLifecycleOwner){

            listContacts.clear()
            listContacts.addAll(it)

            inviteAdapter.notifyDataSetChanged()
        }
    }

    //    this function insert the data into MyFamilyDatabase
    private suspend fun insertDatabaseContacts(listContacts: ArrayList<ContactModel>) {
        val database = MyFamilyDatabase.getDatabase(requireContext())
        database.contactDao().insertAll(listContacts)
    }

    @SuppressLint("Range")
    private fun fetchContacts():ArrayList<ContactModel> {
        Log.d("FetchContact45", "fetchcontact: start ")

        val cr = requireActivity().contentResolver
        val cursor = cr.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null)
        //  creating an empty array list
        val listContacts: ArrayList<ContactModel> = ArrayList()

        if(cursor!=null && cursor.count>0){
            while(cursor!=null && cursor.moveToNext()){
                val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val hasPhoneNumber = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))

                if(hasPhoneNumber>0){
                    val pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        arrayOf(id),
                        ""
                    )

                    if (pCur != null && pCur.count>0){
                        while (pCur != null && pCur.moveToNext()){
                            val number =
                                pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                            listContacts.add(ContactModel(name,number))
                        }
                        pCur.close()
                    }
                }
            }
            if(cursor != null){
                cursor.close()
            }
        }
        Log.d("FetchContact45", "fetchcontact: end ")
        return listContacts
    }

    companion object {

        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}