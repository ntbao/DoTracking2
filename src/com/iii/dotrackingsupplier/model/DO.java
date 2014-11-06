package com.iii.dotrackingsupplier.model;

public class DO {

	private String do_no;
	private String do_no_seq;
	private String po_no;
	private String po_no_seq;
	private String material;
	private String quantity;
	private String proce;
	private String buyercom;
	private String buyerdiv;
	private String seller;
	private String processtype;
	private String workno;
	public DO(String do_no, String do_no_seq, String po_no, String po_no_seq,
			String material, String quantity, String proce) {
		super();
		this.do_no = do_no;
		this.do_no_seq = do_no_seq;
		this.po_no = po_no;
		this.po_no_seq = po_no_seq;
		this.material = material;
		this.quantity = quantity;
		this.proce = proce;
	}
	
	public DO() {
		// TODO Auto-generated constructor stub
	}

	public String getDo_no() {
		return do_no;
	}

	public void setDo_no(String do_no) {
		this.do_no = do_no;
	}

	public String getDo_no_seq() {
		return do_no_seq;
	}

	public void setDo_no_seq(String do_no_seq) {
		this.do_no_seq = do_no_seq;
	}

	public String getPo_no() {
		return po_no;
	}

	public void setPo_no(String po_no) {
		this.po_no = po_no;
	}

	public String getPo_no_seq() {
		return po_no_seq;
	}

	public void setPo_no_seq(String po_no_seq) {
		this.po_no_seq = po_no_seq;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getProce() {
		return proce;
	}

	public void setProce(String proce) {
		this.proce = proce;
	}

	public String getBuyercom() {
		return buyercom;
	}

	public void setBuyercom(String buyercom) {
		this.buyercom = buyercom;
	}

	public String getBuyerdiv() {
		return buyerdiv;
	}

	public void setBuyerdiv(String buyerdiv) {
		this.buyerdiv = buyerdiv;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public String getProcesstype() {
		return processtype;
	}

	public void setProcesstype(String processtype) {
		this.processtype = processtype;
	}

	public String getWorkno() {
		return workno;
	}

	public void setWorkno(String workno) {
		this.workno = workno;
	}
	
	
	
	

}
