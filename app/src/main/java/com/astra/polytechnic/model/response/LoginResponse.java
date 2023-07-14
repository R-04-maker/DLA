package com.astra.polytechnic.model.response;

import com.astra.polytechnic.model.LoginModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

	@SerializedName("data")
	@Expose
	private LoginModel mUser;

	@SerializedName("message")
	@Expose
	private String message;

	@SerializedName("status")
	@Expose
	private int status;

	public LoginModel getUser() {
		return mUser;
	}

	public void setUser(LoginModel user) {
		mUser = user;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "LoginResponse{" +
				"msUser=" + mUser +
				", message='" + message + '\'' +
				", status=" + status +
				'}';
	}
}