package com.thethirdeye.esport.thethirdeye.admin

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.thethirdeye.esport.thethirdeye.AdminAdaptor.NotificationAdapter
import com.thethirdeye.esport.thethirdeye.AdminAdaptor.RunningTournamentAdapter
import com.thethirdeye.esport.thethirdeye.R
import com.thethirdeye.esport.thethirdeye.databinding.ActivityAdminMainBinding
import com.thethirdeye.esport.thethirdeye.databinding.DialogCreateRoomBinding
import com.thethirdeye.esport.thethirdeye.model.NotificationModel
import com.thethirdeye.esport.thethirdeye.model.TournamentModel
import java.util.*
import kotlin.jvm.java

class AdminMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminMainBinding
    private val db = FirebaseFirestore.getInstance()

    private var imageUri: Uri? = null
    private lateinit var roomDialogBinding: DialogCreateRoomBinding

    // Tournament
    private val list = ArrayList<TournamentModel>()
    private lateinit var adapter: RunningTournamentAdapter


    private val imagePicker =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                imageUri = it
                roomDialogBinding.imgRoom.setImageURI(it)
            }
        }



    // Notification
    private val notificationList = ArrayList<NotificationModel>()
    private lateinit var notificationAdapter: NotificationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAdminMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // RoomCreate

        binding.cardCreateRoom.setOnClickListener {
            openRoomDialog()
        }
        // Buttons
        binding.cardCreateTournament.setOnClickListener {
            startActivity(Intent(this, CreateTournamentActivity::class.java))
        }

        binding.cardAllTournaments.setOnClickListener {
            startActivity(Intent(this, AllTournamentActivity::class.java))
        }

        binding.cardNotifications.setOnClickListener {
            openNotificationDialog()
        }


        setupTournamentRecycler()
        setupNotificationRecycler()

        loadTournamentData()
        loadNotifications()
    }

    // ================= TOURNAMENT =================

    private fun setupTournamentRecycler() {
        adapter = RunningTournamentAdapter(list)
        binding.recyclerRunningTournament.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerRunningTournament.adapter = adapter
    }

    private fun loadTournamentData() {
        db.collection("tournaments")
            .get()
            .addOnSuccessListener {
                list.clear()
                for (doc in it.documents) {
                    val model = TournamentModel(
                        doc.getString("title") ?: "",
                        doc.getString("game") ?: "",
                        doc.get("prize")?.toString() ?: "",
                        doc.get("entry")?.toString() ?: "",
                        doc.getString("description") ?: "",
                        doc.getString("image") ?: ""
                    )
                    list.add(model)
                }
                adapter.notifyDataSetChanged()
            }
    }

    // ================= NOTIFICATION =================

    private fun setupNotificationRecycler() {
        notificationAdapter = NotificationAdapter(notificationList)

        binding.recyclerNotifications.layoutManager =
            LinearLayoutManager(this)

        binding.recyclerNotifications.adapter = notificationAdapter
    }

    private fun loadNotifications() {
        db.collection("notifications")
            .get()
            .addOnSuccessListener {

                notificationList.clear()

                for (doc in it.documents) {

                    val model = NotificationModel(
                        doc.getString("title") ?: "",
                        doc.getString("message") ?: "",
                        doc.getString("date") ?: "",
                        doc.getString("time") ?: ""
                    )

                    notificationList.add(model)
                }

                notificationAdapter.notifyDataSetChanged()
            }
    }

    // ================= DIALOG =================

    private fun openNotificationDialog() {

        val view = LayoutInflater.from(this)
            .inflate(R.layout.fragment_dialog_create_notification, null)

        val etTitle = view.findViewById<EditText>(R.id.etTitle)
        val etMessage = view.findViewById<EditText>(R.id.etMessage)
        val etDate = view.findViewById<EditText>(R.id.etDate)
        val etTime = view.findViewById<EditText>(R.id.etTime)

        val dialog = AlertDialog.Builder(this)
            .setView(view)
            .create()

        // DATE PICKER
        etDate.setOnClickListener {
            val cal = Calendar.getInstance()
            DatePickerDialog(this,
                { _, y, m, d ->
                    etDate.setText("$d/${m + 1}/$y")
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        // TIME PICKER
        etTime.setOnClickListener {
            val cal = Calendar.getInstance()
            TimePickerDialog(this,
                { _, h, m ->
                    etTime.setText("$h:$m")
                },
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }

        view.findViewById<android.widget.Button>(R.id.btnSend).setOnClickListener {

            val title = etTitle.text.toString()
            val message = etMessage.text.toString()
            val date = etDate.text.toString()
            val time = etTime.text.toString()

            if (title.isEmpty() || message.isEmpty()) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val data = hashMapOf(
                "title" to title,
                "message" to message,
                "date" to date,
                "time" to time
            )

            db.collection("notifications")
                .add(data)
                .addOnSuccessListener {
                    Toast.makeText(this, "Notification Added", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                    loadNotifications() //  refresh list
                }
        }

        dialog.show()
    }




    private fun openRoomDialog() {

        roomDialogBinding = DialogCreateRoomBinding.inflate(layoutInflater)

        val dialog = AlertDialog.Builder(this)
            .setView(roomDialogBinding.root)
            .setCancelable(true)
            .create()

        dialog.show()

        //  Image Select
        roomDialogBinding.btnSelectImage.setOnClickListener {
            imagePicker.launch("image/*")
        }

        //  Create Room Button
        roomDialogBinding.btnCreate.setOnClickListener {

            val name = roomDialogBinding.etRoomName.text.toString().trim()
            val roomId = roomDialogBinding.etRoomId.text.toString().trim()
            val password = roomDialogBinding.etPassword.text.toString().trim()

            if (name.isEmpty() || roomId.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "All fields required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //  Auto Date & Time
            val currentTime = System.currentTimeMillis()

            val data = hashMapOf(
                "name" to name,
                "roomId" to roomId,
                "password" to password,
                "image" to (imageUri?.toString() ?: ""),
                "createdAt" to currentTime
            )

            //  Firebase Save
            db.collection("rooms")
                .add(data)
                .addOnSuccessListener {
                    Toast.makeText(this, "Room Created Successfully", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}