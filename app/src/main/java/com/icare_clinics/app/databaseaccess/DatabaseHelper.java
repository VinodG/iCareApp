package com.icare_clinics.app.databaseaccess;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;


public class DatabaseHelper extends SQLiteOpenHelper
{
	public final static String DB_NAME = "iCare.sqlite";
	private final static String DB_KEY = "db_key";

	public static String DATABASE_PATH;
	private static SQLiteDatabase sqliteDatabase;
	private static int DB_VERSION = 1;

	private static Context myContext;


	public DatabaseHelper(Context context)
	{
		super(context, DB_NAME, null, DB_VERSION);
		myContext = context;
		DATABASE_PATH = myContext.getFilesDir().toString() + "/" + DB_NAME;
//		DATABASE_PATH = myContext.getFilesDir().toString() + "/" + DB_NAME;
		//initDB();
	}

	private void initDB()
	{
		if(isDbExists())
		{
			openDataBase();
		}
		else
		{
			//CopyDataBaseFromAsset();
		}
	}
	public boolean createDataBase() {
		boolean database=isDbExists();
		if (!database) {
			database=CopyDataBaseFromAsset();
		}
		return database;
	}

	private boolean isDbExists()
	{
		return new File(DATABASE_PATH).exists();
	}


	//To open the database
	public static SQLiteDatabase openDataBase() throws SQLException
	{
		try {
			if(sqliteDatabase == null || !sqliteDatabase.isOpen())
			{
				if(DATABASE_PATH==null || DATABASE_PATH.equalsIgnoreCase(""))
					DATABASE_PATH = myContext.toString() + "/" + DB_NAME;
				sqliteDatabase = SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS |SQLiteDatabase.OPEN_READWRITE | SQLiteDatabase.CREATE_IF_NECESSARY);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//Open the database
		return sqliteDatabase;
	}


	public synchronized static void closeDatabase()
	{
		if(sqliteDatabase != null && sqliteDatabase.isOpen())
			sqliteDatabase.close();
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
	}

	public void copyDataBase()
	{
		//If database not copied from assets
		try
		{
			boolean isFound = false;
			ZipFile zip = null;
			ZipEntry zipen = null;
			while(!isFound)
			{
				try
				{
					String path=myContext.getPackageResourcePath();
					zip = new ZipFile(path);
					zipen = zip.getEntry("assets/" + DB_NAME);
					isFound = true;
				}
				catch(Exception e)
				{
					isFound = false;
				}
			}

			InputStream is = zip.getInputStream(zipen);
			OutputStream os = null;

			os = myContext.openFileOutput(DB_NAME, Context.MODE_WORLD_READABLE);

			int len;
			byte[] buffer = new byte[4096];
			while ((len = is.read(buffer)) >= 0)
			{
				os.write(buffer, 0, len);
			}
			is.close();
			os.close();

			openDataBase();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}


	public boolean copyDataBase(InputStream inputStream) {
		File sqLite = new File(DATABASE_PATH);
		if(sqLite.exists()) {
			sqLite.delete();
		}
		try {
			ZipInputStream zipInputStream = new ZipInputStream(inputStream);
			ZipEntry zipEntry;
			OutputStream outputStream = new FileOutputStream(sqLite);
			byte[] buffer = new byte[1024];
			int length;
			while ((zipEntry = zipInputStream.getNextEntry())!=null) {
				while ((length = zipInputStream.read(buffer)) > 0) {
					outputStream.write(buffer, 0, length);
				}
			}
			outputStream.flush();
			outputStream.close();
			zipInputStream.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean CopyDataBaseFromAsset(){
		File sqLite = new File(DATABASE_PATH);
		if(sqLite.exists()) {
			sqLite.delete();
		}
		try {
			InputStream myInput = myContext.getAssets().open(DB_NAME);
			OutputStream outputStream = new FileOutputStream(sqLite);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = myInput.read(buffer)) > 0) {
				outputStream.write(buffer, 0, length);
			}

			outputStream.flush();
			outputStream.close();
			myInput.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}
