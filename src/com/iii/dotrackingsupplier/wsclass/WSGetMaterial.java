package com.iii.dotrackingsupplier.wsclass;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

import com.iii.dotrackingsupplier.MainActivity;
import com.iii.dotrackingsupplier.config.ConfigurationServer;
import com.iii.dotrackingsupplier.config.ConfigurationWS;

public class WSGetMaterial extends AsyncTask<Void, Void, ArrayList<String>> {
	// ------------------default floor1-----------------------------//
		private ConfigurationWS mWs;
		private ProgressDialog mProgress;
		private ArrayList<String> lstMaterial ;
		private Context context;
		private String rd_date, rd_time;
		private Spinner spnMaterial;
		
		public WSGetMaterial(Context mContext, String rd_date, String rd_time, ArrayList<String> lstMaterial, Spinner spnMaterial) {
			super();
			context = mContext;
			mWs = new ConfigurationWS(mContext);
			mProgress = new ProgressDialog(mContext);
			this.lstMaterial = lstMaterial;
			this.rd_date = rd_date;
			this.rd_time = rd_time;
			this.spnMaterial = spnMaterial;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected ArrayList<String> doInBackground(Void... params) {
			if( lstMaterial.size() > 0 ) lstMaterial.clear();
			try {
				// ---------------get String ------------------------//
				String UrlGetDataDo = ConfigurationServer.getURLServer() + "wsgetMaterial.php";
				JSONObject json = new JSONObject();
				json.put("rd_date", this.rd_date);
				json.put("rd_time", this.rd_time);
				
				JSONArray jarr = mWs.connectWSPut_Get_Data(UrlGetDataDo, json,
						"do");
				for (int i = 0; i < jarr.length(); i++) {
					JSONObject element = jarr.getJSONObject(i);
					lstMaterial.add(element.getString("ITEM_CODE"));
					Log.d(">>>ITEM_CODE", element.getString("ITEM_CODE"));
				}
			} catch (Exception e) {
			}

			return lstMaterial;
		}

		@Override
		protected void onPostExecute( ArrayList<String> result) {
			setDataSpinner();
		}
		
		private void setDataSpinner(){
	    	 ArrayAdapter<String> adapterDoNo = new ArrayAdapter<String>  (context, android.R.layout.simple_spinner_item, lstMaterial);
	    	 adapterDoNo.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
	    	 spnMaterial.setAdapter(adapterDoNo);
	    	 adapterDoNo.notifyDataSetChanged();
		}
}
