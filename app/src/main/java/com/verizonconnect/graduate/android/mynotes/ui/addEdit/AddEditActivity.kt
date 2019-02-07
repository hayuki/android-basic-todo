package com.verizonconnect.graduate.android.mynotes.ui.addEdit

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.view.Menu
import android.view.MenuItem
import com.verizonconnect.graduate.android.mynotes.R
import com.verizonconnect.graduate.android.mynotes.data.model.Note
import com.verizonconnect.graduate.android.mynotes.presentation.addEdit.contract.AddEditNoteContract
import kotlinx.android.synthetic.main.activity_add_edit.*
import org.koin.android.ext.android.inject

class AddEditActivity : AppCompatActivity(), AddEditNoteContract.View {
    
    private val presenter: AddEditNoteContract.Presenter by inject()
    private var noteId: String? = null
    
    companion object {
        const val NOTE_ID = "note_id"
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit)
        
        setupToolbar()
        
        interpretIntent()
        
        setupPresenter()
        
    }
    
    private fun setupToolbar() {
        setSupportActionBar(toolbar_edit)
    
        toolbar_edit.setNavigationOnClickListener { onBackPressed() }
    }
    
    private fun interpretIntent() {
        val extras = intent.extras
        if (extras !== null && extras[NOTE_ID] !== null) {
            noteId = extras[NOTE_ID] as String
        }
    }
    
    private fun setupPresenter() {
        presenter.init(this, noteId)
    }
    
    override fun updateTitle(editing: Boolean) {
        supportActionBar?.title = if (editing) resources.getString(R.string.title_edit_note) else resources.getString(R.string.title_new_note)
    }
    
    override fun populateWithNote(note: Note?) {
        note_title.editText?.text = Editable.Factory.getInstance().newEditable(note?.title)
        note_contents.text = Editable.Factory.getInstance().newEditable(note?.value)
    }
    
    /** TODO [5] Override onCreateOptionsMenu and onOptionsItemSelected methods
     *  Inflate the menu resource file in the onCreateOptionsMenu method
     *  Find the menu item id and call presenter.saveNote(title, contents)
     */
    
    
    override fun onNoteSaved() {
        finish()
    }
    
    override fun onDestroy() {
        presenter.destroy()
        super.onDestroy()
    }
}
