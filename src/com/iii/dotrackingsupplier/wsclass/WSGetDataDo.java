package com.iii.dotrackingsupplier.wsclass;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.iii.dotrackingsupplier.R;
import com.iii.dotrackingsupplier.config.ConfigurationServer;
import com.iii.dotrackingsupplier.config.ConfigurationWS;

public class WSGetDataDo extends AsyncTask<Void, Void, ArrayList<String>> {
	// ------------------default floor1-----------------------------//
		private ConfigurationWS mWs;
		private ProgressDialog mProgress;
		private ArrayList<String> lstDo ;
		private Context context;
		private String rd_date, rd_time;
		
		public WSGetDataDo(Context mContext, String rd_date, String rd_time, ArrayList<String> lstDo) {
			super();
			context = mContext;
			mWs = new ConfigurationWS(mContext);
			mProgress = new ProgressDialog(mContext);
			this.lstDo = lstDo;
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
			if( lstDo.size() > 0 ) lstDo.clear();
			try {
				// ---------------get String ------------------------//
				String UrlGetDataDo = ConfigurationServer.getURLServer() + "wsgetDoNo.php";
				JSONObject json = new JSONObject();
				json.put("rd_date", this.rd_date);
				json.put("rd_time", this.rd_time);
				
				JSONArray jarr = mWs.connectWSPut_Get_Data(UrlGetDataDo, json,
						"do");
				for (int i = 0; i < jarr.length(); i++) {
					JSONObject element = jarr.getJSONObject(i);
					lstDo.add(element.getString("DO_NO"));
					Log.d(">>>DO_NO", element.getString("DO_NO"));
				}
			} catch (Exception e) {
			}

			return lstDo;
		}

		@Override
		protected void onPostExecute( ArrayList<String> result) {
			if( mProgress.isShowing() )
				mProgress.dismiss();
		}
}
