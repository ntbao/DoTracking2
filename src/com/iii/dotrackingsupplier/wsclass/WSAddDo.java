package com.iii.dotrackingsupplier.wsclass;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.iii.dotrackingsupplier.MainActivity;
import com.iii.dotrackingsupplier.R;
import com.iii.dotrackingsupplier.config.ConfigurationServer;
import com.iii.dotrackingsupplier.config.ConfigurationWS;


/**
 * 
 * @author tranminhthuan Copyright (C) 2013 III COMPANY
 * 
 */
/* -----------add new an Invoice to server------------------------------ */
public class WSAddDo extends AsyncTask<Void, Void, Void> {
	private String rdDate;
	private String rdTime;
	private String ASNNo;
	private String doNo;
	private String doSeq;
	private String poNo;
	private String poSeq;
	private String itemCode;
	private String quantity;
	private String imageDo;
	private String strBuyerCom;
	private String strBuyerDiv;
	private String strSeller;
	private String strProcessType;
	private String strPrice;
	private String strrecDate;
	private String strrecTime;
	private ConfigurationWS mWS;
	private ProgressDialog dialog;
	private Context mContext;
	
	

	// --------constructor-----------------------------//
	public WSAddDo(Context mContext, String rdDate, String rdTime, String ASNNo, String doNo, String doSeq, 
					String poNo, String poSeq, String itemCode, String quantity, String imageDo,
					String strBuyerCom, String strBuyerDiv, String strSeller, String strProcessType, String strPrice,
					String strrecDate, String strrecTime) {
		this.rdDate = rdDate;
		this.rdTime = rdTime;
		this.ASNNo = ASNNo;
		this.doNo = doNo;
		this.doSeq = doSeq;
		this.poNo = poNo;
		this.poSeq = poSeq;
		this.itemCode =itemCode;
		this.quantity =quantity;
		this.imageDo= imageDo;
		this.strBuyerCom = strBuyerCom;
		this.strBuyerDiv = strBuyerDiv;
		this.strSeller = strSeller;
		this.strProcessType = strProcessType;
		this.strPrice = strPrice;
		this.strrecDate = strrecDate;
		this.strrecTime = strrecTime;
		this.mContext = mContext;
		mWS = new ConfigurationWS(mContext);
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
//		dialog = new ProgressDialog(mContext);
//		dialog.setMessage( mContext.getResources().getString(R.string.loading) );
//		dialog.show();
	}

	// --------background method-------------------//
	@Override
	protected synchronized Void doInBackground(Void... params) {
		try {

			String URLAddClient = ConfigurationServer.getURLServer() + "wsaddDo.php";
			JSONObject json = new JSONObject();
			json.put("rdDate", rdDate);
			json.put("rdTime", rdTime);
			json.put("ASNNo", ASNNo);
			json.put("doNo", doNo);
			json.put("doSeq", doSeq);
			json.put("poNo", poNo);
			json.put("poSeq", poSeq);
			json.put("itemCode", itemCode);
			json.put("quantity", quantity);
			json.put("imageDo", imageDo);
			
			json.put("buyerCom", strBuyerCom);
			json.put("buyerDiv", strBuyerDiv);
			json.put("seller", strSeller);
			json.put("processType", strProcessType);
			json.put("price", strPrice);
			json.put("recDate", strrecDate);
			json.put("recTime", strrecTime);
			
			
			String jsonData = mWS.getDataJson(URLAddClient, json, "posts");
			JSONObject jsonObject = new JSONObject(jsonData);
			String message = jsonObject.getString("message");
			Log.i("Log : ", "" + message);
		} catch (Exception e) {
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
//		if( dialog.isShowing() )
//			dialog.dismiss();
	}

}