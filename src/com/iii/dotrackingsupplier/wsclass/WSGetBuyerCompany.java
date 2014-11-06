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

import com.iii.dotrackingsupplier.MainActivity;
import com.iii.dotrackingsupplier.R;
import com.iii.dotrackingsupplier.config.ConfigurationServer;
import com.iii.dotrackingsupplier.config.ConfigurationWS;

public class WSGetBuyerCompany extends AsyncTask<Void, Void, ArrayList<String>> {
	// ------------------default floor1-----------------------------//
		private ConfigurationWS mWs;
		private ProgressDialog mProgress;
		private ArrayList<String> lstBuyerCom ;
		private Context context;
		private String rd_date, rd_time;
		private Spinner spnBuyerCom;
		
		public WSGetBuyerCompany(Context mContext, String rd_date, String rd_time, ArrayList<String> lstBuyerCom
				, Spinner spnBuyerCom) {
			super();
			context = mContext;
			mWs = new ConfigurationWS(mContext);
			mProgress = new ProgressDialog(mContext);
			this.spnBuyerCom = spnBuyerCom;
			this.lstBuyerCom = lstBuyerCom;
			this.rd_date = rd_date;
			this.rd_time = rd_time;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgress.setMessage( context.getResources().getString(R.string.loading) );
			mProgress.setCancelable(false);
			mProgress.show();
		}

		@Override
		protected ArrayList<String> doInBackground(Void... params) {
			if( lstBuyerCom.size() > 0 ) lstBuyerCom.clear();
			try {
				// ---------------get String ------------------------//
				String UrlGetDataDo = ConfigurationServer.getURLServer() + "wsgetBuyerCom.php";
				JSONObject json = new JSONObject();
				json.put("rd_date", this.rd_date);
				json.put("rd_time", this.rd_time);
				
				JSONArray jarr = mWs.connectWSPut_Get_Data(UrlGetDataDo, json,
						"do");
				for (int i = 0; i < jarr.length(); i++) {
					JSONObject element = jarr.getJSONObject(i);
					lstBuyerCom.add(element.getString("BUYER_COMPANY"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			return lstBuyerCom;
		}

		@Override
		protected void onPostExecute( ArrayList<String> result) {
			setDataSpinner();
			if( mProgress.isShowing() )
				mProgress.dismiss();
		}
		
		 private void setDataSpinner(){
	    	 ArrayAdapter<String> adapterDoNo = new ArrayAdapter<String>  (context, android.R.layout.simple_spinner_item, lstBuyerCom);
	    	 adapterDoNo.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
	    	 spnBuyerCom.setAdapter(adapterDoNo);
	    	 adapterDoNo.notifyDataSetChanged();
	    	
	    	 
	    }
}
