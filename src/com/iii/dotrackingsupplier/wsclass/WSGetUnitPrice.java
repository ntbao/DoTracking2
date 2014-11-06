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
import android.widget.TextView;

import com.iii.dotrackingsupplier.MainActivity;
import com.iii.dotrackingsupplier.R;
import com.iii.dotrackingsupplier.config.ConfigurationServer;
import com.iii.dotrackingsupplier.config.ConfigurationWS;

public class WSGetUnitPrice extends AsyncTask<Void, Void, String> {
	// ------------------default floor1-----------------------------//
		private ConfigurationWS mWs;
		private ProgressDialog mProgress;
		private Context context;
		private String rd_date, rd_time, strUnitPrice="", itemCode;
		private TextView tvUnitPrice;
		
		public WSGetUnitPrice(Context mContext, String rd_time, String rd_date, String itemCode,TextView tvUnitPrice) {
			super();
			context = mContext;
			mWs = new ConfigurationWS(mContext);
			mProgress = new ProgressDialog(mContext);
			this.tvUnitPrice = tvUnitPrice;
			this.rd_date = rd_date;
			this.rd_time = rd_time;
			this.itemCode = itemCode;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(Void... params) {
			try {
				// ---------------get String ------------------------//
				String UrlGetDataDo = ConfigurationServer.getURLServer() + "wsgetUnitPrice.php";
				JSONObject json = new JSONObject();
				json.put("rd_date", this.rd_date);
				json.put("rd_time", this.rd_time);
				json.put("itemcode", this.itemCode);
				
				JSONArray jarr = mWs.connectWSPut_Get_Data(UrlGetDataDo, json,
						"do");
				for (int i = 0; i < jarr.length(); i++) {
					JSONObject element = jarr.getJSONObject(i);
					strUnitPrice = element.getString("UNIT_PRICE");
				}
			} catch (Exception e) {
			}

			return strUnitPrice;
		}

		@Override
		protected void onPostExecute( String result) {
			tvUnitPrice.setText(strUnitPrice);
		}
		
}
