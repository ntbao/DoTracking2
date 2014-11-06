package com.iii.dotrackingsupplier;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.iii.dotrackingsupplier.model.DO;

public class ListDOAdapter extends ArrayAdapter<DO> {
	private Context context;
	private List<DO> arrayList;

	public ListDOAdapter(Context context, int resource, List<DO> list) {
		super(context, resource, list);
		this.context = context;
		this.arrayList = list;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		try {
			if (convertView == null) {
				holder = new ViewHolder();
				LayoutInflater inflate = (LayoutInflater) this.context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflate.inflate(R.layout.item_do_layout,null);
				/*-----------------------------------------------------------*/
				holder.tvdono = (TextView) convertView .findViewById(R.id.tvDONO);
				holder.tvdonoseq = (TextView) convertView.findViewById(R.id.tvDONOSEQ);
				holder.tvpono = (TextView) convertView.findViewById(R.id.tvPONO);
				holder.tvponoseq = (TextView) convertView.findViewById(R.id.tvPONOSEQ);
				holder.tvitemcode = (TextView) convertView.findViewById(R.id.tvITEMCODE);
				holder.tvquantity = (TextView) convertView.findViewById(R.id.tvQUANTITY);
				
				holder.tvbuyercom = (TextView) convertView.findViewById(R.id.tvBUYERCOM);
				holder.tvbuyerdiv = (TextView) convertView.findViewById(R.id.tvBUYERDIV);
				holder.tvseller = (TextView) convertView.findViewById(R.id.tvSELLER);
				holder.tvprocesstype = (TextView) convertView.findViewById(R.id.tvPROCESSTYPE);
				holder.tvworkno = (TextView) convertView.findViewById(R.id.tvWORKNO);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			final DO domodel = arrayList.get(position);
			holder.tvdono.setText(domodel.getDo_no());
			holder.tvdonoseq.setText(domodel.getDo_no_seq());
			holder.tvpono.setText(domodel.getPo_no());
			holder.tvponoseq.setText(domodel.getPo_no_seq());
			holder.tvitemcode.setText(domodel.getMaterial());
			holder.tvquantity.setText(domodel.getQuantity());
			
			holder.tvbuyercom.setText(domodel.getBuyercom());
			holder.tvbuyerdiv.setText(domodel.getBuyerdiv());
			holder.tvseller.setText(domodel.getSeller());
			holder.tvprocesstype.setText(domodel.getProcesstype());
			holder.tvworkno.setText(domodel.getWorkno());

		} catch (Exception e) {
			// TODO: handle exception
		}

		return convertView;
	}

	class ViewHolder {
		TextView tvdono;
		TextView tvdonoseq;
		TextView tvpono;
		TextView tvponoseq;
		TextView tvitemcode;
		TextView tvquantity;
		TextView tvbuyercom;
		TextView tvbuyerdiv;
		TextView tvseller;
		TextView tvprocesstype;
		TextView tvworkno;
	}

}
