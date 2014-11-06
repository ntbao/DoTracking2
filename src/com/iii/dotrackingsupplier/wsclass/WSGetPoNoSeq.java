package com.iii.dotrackingsupplier.wsclass;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.iii.dotrackingsupplier.config.ConfigurationServer;
import com.iii.dotrackingsupplier.config.ConfigurationWS;

public class WSGetPoNoSeq extends AsyncTask<Void, Void, ArrayList<String>> {
	// ------------------default floor1-----------------------------//
		private ConfigurationWS mWs;
		private ProgressDialog mProgress;
		private ArrayList<String> lstPoNoSeq ;
		private Context context;
		private String rd_date, rd_time;
		private Spinner spnPoNoSeq;
		
		public WSGetPoNoSeq(Context mContext, String rd_date, String rd_time, ArrayList<String> lstPoNoSeq, Spinner spnPoNoSeq) {
			super();
			context = mContext;
			mWs = new ConfigurationWS(mContext);
			mProgress = new ProgressDialog(mContext);
			this.lstPoNoSeq = lstPoNoSeq;
			this.rd_date = rd_date;
			this.rd_time = rd_time;
			this.spnPoNoSeq = spnPoNoSeq;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected ArrayList<String> doInBackground(Void... params) {
			if( lstPoNoSeq.size() > 0 ) lstPoNoSeq.clear();
			try {
				// ---------------get String ------------------------//
				String UrlGetDataDo = ConfigurationServer.getURLServer() + "wsgetPoNoSeq.php";
				JSONObject json = new JSONObject();
				json.put("rd_date", this.rd_date);
				json.put("rd_time", this.rd_time);
				
				JSONArray jarr = mWs.connectWSPut_Get_Data(UrlGetDataDo, json,
						"do");
				for (int i = 0; i < jarr.length(); i++) {
					JSONObject element = jarr.getJSONObject(i);
					lstPoNoSeq.add(element.getString("PO_NO_SEQ"));
					Log.d(">>>PO_NO_SEQ", element.getString("PO_NO_SEQ"));
				}
			} catch (Exception e) {
			}

			return lstPoNoSeq;
		}

		@Override
		protected void onPostExecute( ArrayList<String> result) {
			setDataSpinner();
		}
		
		private void setDataSpinner(){
	    	 ArrayAdapter<String> adapterDoNo = new ArrayAdapter<String>  (context, android.R.layout.simple_spinner_item, lstPoNoSeq);
	    	 adapterDoNo.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
	    	 spnPoNoSeq.setAdapter(adapterDoNo);
	    	 adapterDoNo.notifyDataSetChanged();
	    }
}
