package com.icare_clinics.app.webaccessLayer;

import com.icare_clinics.app.dataobject.ResponseDO;

public interface HttpListener {
	void onResponseReceived(ResponseDO response);
}
