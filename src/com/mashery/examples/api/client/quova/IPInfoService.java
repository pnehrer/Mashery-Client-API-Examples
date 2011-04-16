package com.mashery.examples.api.client.quova;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("quova/ipinfo")
public interface IPInfoService extends RemoteService {

	IPInfo getIPInfo(String ip) throws IPInfoException;
}
