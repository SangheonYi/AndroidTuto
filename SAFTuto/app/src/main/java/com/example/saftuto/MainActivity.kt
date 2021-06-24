package com.example.saftuto

import android.Manifest
import android.app.Activity
import android.content.ClipData
import android.content.ContentUris
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.io.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private var imageView: ImageView? = null
    fun performFileSearch() {
        Log.i("퍼폼 서치", "performFileSearch()")
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)

//        intent.addCategory(Intent.CATEGORY_OPENABLE);

//        intent.setType("text/plain");
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        startActivityForResult(intent, READ_REQUEST_CODE)
    }

    @Throws(IOException::class)
    private fun readTextFromUri(uri: Uri): String {
        val stringBuilder = StringBuilder()
        contentResolver.openInputStream(uri).use { inputStream ->
            BufferedReader(
                    InputStreamReader(Objects.requireNonNull(inputStream))).use { reader ->
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    stringBuilder.append(line)
                }
            }
        }
        return stringBuilder.toString()
    }

    private fun createFile(mimeType: String, fileName: String) {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = mimeType
        intent.putExtra(Intent.EXTRA_TITLE, fileName)
        startActivityForResult(intent, CREATE_FILE)
    }

    /**
     * Open a file for writing and append some text to it.
     */
    private fun editDocument() {
        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's
        // file browser.
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones).
        intent.addCategory(Intent.CATEGORY_OPENABLE)

        // Filter to show only text files.
        intent.type = "text/plain"
        startActivityForResult(intent, EDIT_REQUEST_CODE)
    }

    private fun alterDocument(uri: Uri?) {
        try {
            val path = Environment.getExternalStorageDirectory().path
            Log.i("alterDoc", "path: $path")
            val parcelFileDescriptor = this@MainActivity.contentResolver.openFileDescriptor(uri!!, "wa")
            val bufferedWriter = BufferedWriter(FileWriter(parcelFileDescriptor!!.fileDescriptor))
            bufferedWriter.write("횟수" + cnt++)
            bufferedWriter.close()
            parcelFileDescriptor.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (!(PermissionUtil.checkPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        && PermissionUtil.checkPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE))) {
            Log.i("에헤라", "권한 요청하러 들옴")
            PermissionUtil.requestExternalPermissions(this)
        }
        imageView = findViewById(R.id.imageView)
        val file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val folders = file.list()
//        Log.i("onCreate", "string: ${folders.first()}")


/*        val projection = arrayOf(MediaStore.MediaColumns.TITLE)
        val cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, null, null, null)
        if (cursor != null) {
            cursor.moveToNext()
            Log.i("onCreate", "string: ${cursor.getString(0)}")
        }*/
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.i("onActivityResult", "onActivityResult()")
        super.onActivityResult(requestCode, resultCode, data)
        val uri: Uri?
        if (requestCode == CREATE_FILE && resultCode == RESULT_OK) {
            if (data != null) {
                uri = data.data
                Log.i("onActivityResult", "CREATE_FILE uri: $uri")
            }
        }
        else if (requestCode == EDIT_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                uri = data.data
                Log.i("onActivityResult", "EDIT_REQUEST_CODE uri: $uri")
                Log.i("onActivityResult", "EDIT_REQUEST_CODE uri.getPath(): " + uri!!.path)
                alterDocument(uri)
            }
        }
        else if (requestCode == DELETE_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                uri = data.data
                Log.i("onActivityResult", "READ_REQUEST_CODE uri: $uri")
                try {
                    DocumentsContract.deleteDocument(contentResolver, uri)
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }
            }
        }
        else if (requestCode == READ_REQUEST_CODE && resultCode == RESULT_OK) {
            Log.i("앨범", "Environment.getExternalStorageDirectory ().getPath (): " + Environment.getExternalStorageDirectory ().path)

            if (data != null) {
                val imageUri: Uri?
                val projection = arrayOf(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME)
                val cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        null, null, null, null)
                val tv = findViewById<TextView>(R.id.logView)
                val mAlbumsList = ArrayList<Album>()
                mAlbumsList.clear()
                if (cursor != null) {
//                    Log.i("앨범", "column name: " + Arrays.toString(cursor.getColumnNames()) + " =======================");
                    cursor.moveToNext()
                    for (j in 0 until cursor.columnCount) {
                        Log.i("앨범", cursor.getColumnName(j) + " " + cursor.getString(j))
                        tv.append(cursor.getColumnName(j) + " " + cursor.getString(j))
                    }
                    Log.i("앨범", "Environment.getExternalStorageDirectory ().getPath (): " + Environment.getExternalStorageDirectory ().path)
                    while (cursor.moveToNext()) {
                        Log.i("앨범", "column name: " + Arrays.toString(cursor.columnNames) + " =======================")
                        Log.i("앨범", "display name: " + cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)))
                        //                    Log.i("앨범", "id: " + cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media._ID)));
                    }
                    cursor.close()
                }
                var clip: ClipData?
                imageUri = data.data
                if (imageUri != null && data.clipData == null) {
                    clip = ClipData.newUri(contentResolver, "URI", imageUri)
                    Log.i("onActivityResult", "READ one clip: $clip")
                }
                clip = data.clipData
                Log.i("onActivityResult", "READ multi clip: $clip")
                Log.i("onActivityResult", "READ uri: $imageUri")
            }
        }
    }

    fun getUriFromPath(activity: Activity, filePath: String): Uri {
        val cursor = activity.contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, "_data = '$filePath'", null, null)
        cursor!!.moveToNext()
        val id = cursor.getInt(cursor.getColumnIndex("_id"))
        return ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id.toLong())
    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.fileSave -> createFile("text/plain", "whatthe.txt")
            R.id.fileEdit -> editDocument()
            R.id.fileRead -> performFileSearch()
        }
    }

    public override fun onDestroy() {
        Log.i("onDestroy", "onDestroy()")
        super.onDestroy()
    }

    public override fun onStart() {
        super.onStart()
        Log.i("LifeCycle", "onStart() 호출")
    }

    public override fun onResume() {
        super.onResume()
        Log.i("LifeCycle", "onResume() 호출")
    }

    public override fun onPause() {
        super.onPause()
        Log.i("LifeCycle", "onPause() 호출")
    }

    public override fun onStop() {
        super.onStop()
        Log.i("LifeCycle", "onStop() 호출")
    }

    companion object {
        private const val CREATE_FILE = 1
        private const val EDIT_REQUEST_CODE = 44
        private const val DELETE_REQUEST_CODE = 42
        private const val READ_REQUEST_CODE = 43
        private var cnt = 0
    }
}