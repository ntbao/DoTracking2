	package com.iii.dotrackingsupplier;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.iii.dotrackingsupplier.config.ConfigurationServer;
import com.iii.dotrackingsupplier.config.ConfigurationWS;
import com.iii.dotrackingsupplier.config.MultipartEntity;
import com.iii.dotrackingsupplier.model.DO;
import com.iii.dotrackingsupplier.wsclass.WSAddDo;
import com.iii.dotrackingsupplier.wsclass.WSGetBuyerCompany;
import com.iii.dotrackingsupplier.wsclass.WSGetBuyerDiv;
import com.iii.dotrackingsupplier.wsclass.WSGetDoNo;
import com.iii.dotrackingsupplier.wsclass.WSGetDoNoSeq;
import com.iii.dotrackingsupplier.wsclass.WSGetMaterial;
import com.iii.dotrackingsupplier.wsclass.WSGetPoNo;
import com.iii.dotrackingsupplier.wsclass.WSGetPoNoSeq;
import com.iii.dotrackingsupplier.wsclass.WSGetProcessType;
import com.iii.dotrackingsupplier.wsclass.WSGetSeller;
import com.iii.dotrackingsupplier.wsclass.WSGetUnitPrice;


public class MainActivity extends ActionBarActivity implements OnClickListener{
	private EditText edtRDDate, edtRDTime, edtAsnNo, edtQty;
	private Button btnGetAllDataDo, btnOk;
	private Spinner spnDoNo, spnDoNoSeq, spnPoNo, spnPoNoSeq, spnItemcode, spnBuyerCom, spnBuyerDiv, spnSeller, spnProcessType,
			spnWorKNo;
	private ImageView imvRDDate, imvRDTime, imgvDO, imvDeleteEdittext;
	private ListView lsvDo;
	private TextView tvUnitPrice;
	
	private int mYear, mMonth, mDay, mHour, mMinute;
	private String rd_date, rd_time, asn_no, strrddate, strrdtime, strdoNo="", strdoSeq="", strpoNo="", strpoSeq="", stritemcode="", 
				 strquantity, strpicture, strprice = "";
	public String message,imagefilename = "", mCurrentPhotoPath ="";
	private String strBuyerCom = "", strBuyerDiv = "", strSeller ="", strProcessType = "", strWorkNo = "";
	int CAMERA_PIC_REQUEST = 1337; 
	private ArrayList<String> lstDoNo, lstDoNoSeq, lstPoNo, lstPoNoSeq, lstItemCode, lstBuyerCom, lstBuyerDiv, lstSeller, 
							  lstProcessType, lstWorkNo;
	private ArrayList<DO> lstDo;
	private ListDOAdapter adapter;
	private BluetoothAdapter mBluetoothAdapter = null;
	private Bitmap imgbitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setUI();
    }
    
    //-----Vu: initialization controlls for UI -----//
    private void setUI(){
    	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
    	lstDoNo = new ArrayList<String>();
    	lstPoNo = new ArrayList<String>();
    	lstDoNoSeq = new ArrayList<String>();
    	lstPoNoSeq = new ArrayList<String>();
    	lstItemCode = new ArrayList<String>();
    	lstBuyerCom = new ArrayList<String>();
    	lstBuyerDiv = new ArrayList<String>();
    	lstSeller = new ArrayList<String>();
    	lstProcessType = new ArrayList<String>();
    	lstWorkNo = new ArrayList<String>();
    	lstDo = new ArrayList<DO>();
    	
    	edtRDDate = (EditText)findViewById(R.id.edtRDDate);
    	edtRDTime = (EditText)findViewById(R.id.edtRDTime);
    	edtAsnNo = (EditText)findViewById(R.id.edtAsnNo);
    	edtQty =(EditText)findViewById(R.id.edtQuantity);
    	btnGetAllDataDo = (Button) findViewById(R.id.btnGetAllDataDo);
    	btnOk = (Button)findViewById(R.id.btnOK);
    	spnDoNo = (Spinner) findViewById(R.id.spnDoNo);
    	spnDoNoSeq = (Spinner) findViewById(R.id.spnDoSeq);
    	spnPoNo = (Spinner) findViewById(R.id.spnPoNo);
    	spnPoNoSeq = (Spinner) findViewById(R.id.spnPoSeq);
    	spnItemcode = (Spinner) findViewById(R.id.spnItemcode);
    	spnBuyerCom = (Spinner)findViewById(R.id.spnBuyerCom);
    	spnBuyerDiv = (Spinner)findViewById(R.id.spnBuyerDev);
    	spnSeller = (Spinner)findViewById(R.id.spnSeller);
    	spnProcessType = (Spinner)findViewById(R.id.spnProcessType);
    	//spnWorKNo = (Spinner)findViewById(R.id.spnWorkNo);
    	tvUnitPrice = (TextView)findViewById(R.id.tvUnitPrice);
    	imvRDDate = (ImageView)findViewById(R.id.imvRDDate);
    	imvRDTime = (ImageView)findViewById(R.id.imvRDTime);
    	imvDeleteEdittext = (ImageView)findViewById(R.id.imvDeleteEdittext);
    	lsvDo = (ListView)findViewById(R.id.lsvDo);
    	imgvDO = (ImageView)findViewById(R.id.imgvDO);
    	
    	imvRDDate.setOnClickListener(this);
    	imvRDTime.setOnClickListener(this);
    	btnGetAllDataDo.setOnClickListener(this);
    	btnOk.setOnClickListener(this);
    	imgvDO.setOnClickListener(this);
    	imvDeleteEdittext.setOnClickListener(this);
    	edtAsnNo.setOnEditorActionListener(mWriteListener);
    	
    	mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter != null) {
			Toast.makeText(this, "khong ho tro barecode",
					Toast.LENGTH_LONG).show();
			finish();
			return;
		}
   	 spnItemcode.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					String strItemcode = lstItemCode.get(position);
					new WSGetUnitPrice(MainActivity.this, rd_date,  rd_time,strItemcode, tvUnitPrice).execute();
					
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					// TODO Auto-generated method stub
					
				}
			});

    }
    
    private void setData(String rdDate, String rdTime){
    	new WSGetAllDo(MainActivity.this, rdDate, rdTime).execute();
    }
    
    private TextView.OnEditorActionListener mWriteListener = new TextView.OnEditorActionListener() {
		public boolean onEditorAction(TextView view, int actionId,
				KeyEvent event) {
			// If the action is a key-up event on the return key, send the
			// message
			
			if (actionId == EditorInfo.IME_NULL
					&& event.getAction() == KeyEvent.ACTION_UP) {
				message = view.getText().toString();
				view.setText("");
				if (!message.equals("")) {
					Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG)
							.show();
						 edtAsnNo.setText(message);
						
				}
			}
			return true;
		}
	};
    
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imvRDDate:
			// Process to get Current Date
			final Calendar c = Calendar.getInstance();
			mYear = c.get(Calendar.YEAR);
			mMonth = c.get(Calendar.MONTH);
			mDay = c.get(Calendar.DAY_OF_MONTH);

			// Launch Date Picker Dialog
			DatePickerDialog dpd = new DatePickerDialog(this,
				new DatePickerDialog.OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						// Display Selected date in textbox
						String month = String.valueOf(monthOfYear + 1);
						if ((monthOfYear + 1)<10) {
							 month = "0" + String.valueOf(monthOfYear + 1);
						} 
						rd_date = String.valueOf(year) + month + String.valueOf(dayOfMonth) ;
						edtRDDate.setText(rd_date);

					}
				}, mYear, mMonth, mDay);
			dpd.show();
			break;
		
		case R.id.imvRDTime:
			// Process to get Current Time
			final Calendar ct = Calendar.getInstance();
			mHour = ct.get(Calendar.HOUR_OF_DAY);
			mMinute = ct.get(Calendar.MINUTE);

			// Launch Time Picker Dialog
			TimePickerDialog tpd = new TimePickerDialog(this,
				new TimePickerDialog.OnTimeSetListener() {

					@Override
					public void onTimeSet(TimePicker view, int hourOfDay,
							int minute) {
						// Display Selected time in textbox
						String hours = String.valueOf(hourOfDay);
						if (hourOfDay < 10) {
							hours = "0" + String.valueOf(hourOfDay);
						} 
						String minutes = String.valueOf(minute);
						if (minute <10) {
							minutes = "0" + String.valueOf(minute);
						} 
						rd_time = hours + minutes+"00";
						edtRDTime.setText(rd_time);
					}
				}, mHour, mMinute, true);
			tpd.show();
			break;
		
		case R.id.btnGetAllDataDo:
			setData(edtRDDate.getText().toString().trim(),  edtRDTime.getText().toString().trim());
			new WSGetBuyerCompany(MainActivity.this, edtRDDate.getText().toString().trim(),  edtRDTime.getText().toString().trim(), lstBuyerCom, spnBuyerCom).execute();
			new WSGetBuyerDiv(MainActivity.this, edtRDDate.getText().toString().trim(),  edtRDTime.getText().toString().trim(), lstBuyerDiv, spnBuyerDiv).execute();
			new WSGetSeller(MainActivity.this, edtRDDate.getText().toString().trim(),  edtRDTime.getText().toString().trim(), lstSeller, spnSeller).execute();
			
			new WSGetDoNo(MainActivity.this, edtRDDate.getText().toString().trim(),  edtRDTime.getText().toString().trim(), lstDoNo,  spnDoNo ).execute();
			new WSGetDoNoSeq(MainActivity.this, edtRDDate.getText().toString().trim(),  edtRDTime.getText().toString().trim(), lstDoNoSeq, spnDoNoSeq).execute();
			new WSGetPoNo(MainActivity.this, edtRDDate.getText().toString().trim(),  edtRDTime.getText().toString().trim(), lstPoNo, spnPoNo).execute();
			new WSGetPoNoSeq(MainActivity.this, edtRDDate.getText().toString().trim(),  edtRDTime.getText().toString().trim(), lstPoNoSeq, spnPoNoSeq).execute();
			new WSGetMaterial(MainActivity.this, edtRDDate.getText().toString().trim(),  edtRDTime.getText().toString().trim(), lstItemCode, spnItemcode).execute();
			new WSGetProcessType(MainActivity.this, edtRDDate.getText().toString().trim(),  edtRDTime.getText().toString().trim(), lstProcessType, spnProcessType).execute();
			//new WSGetWorkNo(MainActivity.this, edtRDDate.getText().toString().trim(),  edtRDTime.getText().toString().trim(), lstWorkNo, spnWorKNo).execute();
			
			break;
			
		case R.id.btnOK:
			 //----Get data---
	    	asn_no = edtAsnNo.getText().toString().trim();
	    	strrddate =  edtRDDate.getText().toString().trim();
	    	strrdtime = edtRDTime.getText().toString().trim();
	    	try {
	    		strdoNo = spnDoNo.getSelectedItem().toString();
			} catch (Exception e) {}
	    	
	    	try {
	    		stritemcode = spnItemcode.getSelectedItem().toString();
			} catch (Exception e) {}
	    	
	    	try {
	    		strdoSeq = spnDoNoSeq.getSelectedItem().toString();
			} catch (Exception e) {}
	    	
	    	try {
	    		strpoNo = spnPoNo.getSelectedItem().toString();
			} catch (Exception e) {}
	    	
	    	try {
	    		strpoSeq = spnPoNoSeq.getSelectedItem().toString();
			} catch (Exception e) {}
	    	
	    	//===================
	    	
	    	try {
	    		strBuyerCom = spnBuyerCom.getSelectedItem().toString();
			} catch (Exception e) {}
	    	
	    	try {
	    		strBuyerDiv = spnBuyerDiv.getSelectedItem().toString();
			} catch (Exception e) {}
	    	
	    	try {
	    		strSeller = spnSeller.getSelectedItem().toString();
			} catch (Exception e) {}
	    	
	    	try {
	    		strProcessType = spnProcessType.getSelectedItem().toString();
			} catch (Exception e) {}
	    	
	    	try {
	    		//strWorkNo = spnWorKNo.getSelectedItem().toString();
			} catch (Exception e) {}
	    	
	    	strquantity = edtQty.getText().toString().trim();
	    	String recTime = new SimpleDateFormat("HHmmss").format(new Date());
	    	String recDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
	    	strprice = tvUnitPrice.getText().toString().trim();
	    	if(strdoNo.equals("") ||strquantity.equals("") 
	    	  || strrddate.equals("") || strrdtime.equals("") || asn_no.equals("") 
	    	){
	    		Toast.makeText(MainActivity.this, "Ä�iá»�n Ä‘áº§y Ä‘á»§ thÃ´ng tin", 1).show();
	    	}else{
	    		new WSAddDo(MainActivity.this, strrddate, strrdtime, asn_no, strdoNo, 
						strdoSeq, strpoNo, strpoSeq, stritemcode, strquantity, imagefilename, strBuyerCom,
						strBuyerDiv, strSeller, strProcessType, strprice, recDate, recTime).execute();
				new WSGetAllDo(MainActivity.this, strrddate, strrdtime).execute();
				MyUploadTask myUploadTask = new MyUploadTask();
				try {
					if(!imgbitmap.equals(null)){
						myUploadTask.execute(imgbitmap);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
	    	}
			break;
			
		case R.id.imgvDO:
			 Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
	            // request code

	         startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
			break;
			
		case R.id.imvDeleteEdittext:
			edtAsnNo.getText().clear();
			break;

		default:
			break;
		}
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		try {
		    if( requestCode == 1337)
		    {
		    //  data.getExtras()
		        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
		        imgvDO.setImageBitmap(thumbnail);
		        //3
	            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
	            thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
	            //4
	            File testDirectory1 =new File(Environment.getExternalStorageDirectory()+"/DO");
	            if(!testDirectory1.exists()){
	                testDirectory1.mkdir();
	            }
	            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	            imagefilename = "JPEG_" + timeStamp+".jpg";
	            File file = new File(testDirectory1+"/"+ imagefilename);
	            mCurrentPhotoPath = file.getAbsolutePath();
	            imgbitmap = thumbnail;
	            try {
	                file.createNewFile();
	                FileOutputStream fo = new FileOutputStream(file);
	                //5
	                fo.write(bytes.toByteArray());
	                fo.close();
	            } catch (IOException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	
		    }
		    else 
		    {
		        Toast.makeText(MainActivity.this, "Picture NOt taken", Toast.LENGTH_LONG);
		    }
		    super.onActivityResult(requestCode, resultCode, data);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
		
	
	public class WSGetAllDo extends AsyncTask<Void, Void, ArrayList<DO>> {
		// ------------------default floor1-----------------------------//
			private ConfigurationWS mWs;
			private ProgressDialog mProgress;
			private String rdDate;
			private String rdTime;
			private Context context;
			
			public WSGetAllDo(Context mContext, String rdDate, String rdTime) {
				super();
				context = mContext;
				mWs = new ConfigurationWS(mContext);
				mProgress = new ProgressDialog(mContext);
				this.rdDate = rdDate;
				this.rdTime = rdTime;
			}

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				mProgress.setMessage( MainActivity.this.getResources().getString(R.string.loading) );
				mProgress.setCancelable(false);
				mProgress.show();
			}

			@Override
			protected ArrayList<DO> doInBackground(Void... params) {
				if( lstDo.size() > 0 ) lstDo.clear();
				try {
					// ---------------get String ------------------------//
					String UrlGetDataDo = ConfigurationServer.getURLServer() + "wsgetallDo.php";
					JSONObject json = new JSONObject();
					json.put("rd_date", this.rdDate);
					json.put("rd_time", this.rdTime);
					
					JSONArray jarr = mWs.connectWSPut_Get_Data(UrlGetDataDo, json,
							"do");
					for (int i = 0; i < jarr.length(); i++) {
						JSONObject element = jarr.getJSONObject(i);
						DO domodel = new DO();
						domodel.setDo_no(element.getString("DO_NO"));
						domodel.setDo_no_seq(element.getString("DO_NO_SEQ"));
						domodel.setPo_no(element.getString("PO_NO"));
						domodel.setPo_no_seq(element.getString("PO_DTL_SEQ"));
						domodel.setMaterial(element.getString("ITEM_CODE"));
						domodel.setQuantity(element.getString("QTY"));
						domodel.setBuyercom(element.getString("BUYER_COMPANY"));
						domodel.setBuyerdiv(element.getString("BUYER_DIVISION"));
						domodel.setProcesstype(element.getString("PROCESS_TYPE"));
						domodel.setSeller(element.getString("SELLER_COMPANY"));
						domodel.setWorkno(element.getString("WORK_ORDER_NO"));
						lstDo.add(domodel);
					}
				} catch (Exception e) {
				}

				return lstDo;
			}

			@Override
			protected void onPostExecute( ArrayList<DO> result) {
				setDataListview();
				if( mProgress.isShowing() )
					mProgress.dismiss();
			}
			
			 public void setDataListview(){
		    	 adapter = new ListDOAdapter(MainActivity.this, R.layout.item_do_layout, lstDo);
		    	 lsvDo.setAdapter(adapter);
		    	 adapter.notifyDataSetChanged();
		    }
	}
	private class MyUploadTask extends AsyncTask<Bitmap, Void, Void> {

		@Override
		protected Void doInBackground(Bitmap... bitmaps) {
			if (bitmaps[0] == null)
				return null;
			setProgress(0);

			Bitmap bitmap = bitmaps[0];
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream); // convert
																		// Bitmap
																		// to
																		// ByteArrayOutputStream
			InputStream in = new ByteArrayInputStream(stream.toByteArray()); // convert
																				// ByteArrayOutputStream
																				// to
																				// ByteArrayInputStream

			DefaultHttpClient httpclient = new DefaultHttpClient();
			try {
				HttpPost httppost = new HttpPost(
						"http://117.6.131.222:6789/pos/wspos/DOTRACKING/savetofile.php"); // server

				MultipartEntity reqEntity = new MultipartEntity();
				reqEntity.addPart("myFile", imagefilename, in);
				httppost.setEntity(reqEntity);

				Log.i("TAG", "request " + httppost.getRequestLine());
				HttpResponse response = null;
				try {
					response = httpclient.execute(httppost);
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					if (response != null)
						Log.i("TAG", "response "
								+ response.getStatusLine().toString());
				} finally {

				}
			} finally {

			}

			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			Toast.makeText(MainActivity.this, "upload thanh cong",
					Toast.LENGTH_LONG).show();
			super.onPostExecute(result);
		}

	}
	
	}
