package com.icare_clinics.app.dataobject;


import com.icare_clinics.app.webaccessLayer.ServiceMethods;

import java.io.Serializable;

public class ResponseDO implements Serializable
{
	public ServiceMethods method;
	public boolean isError;
	public Object data;

	public ResponseDO(ServiceMethods method, boolean isError, Object data)
	{
		this.method = method;
		this.isError = isError;
		this.data = data;
	}
}
