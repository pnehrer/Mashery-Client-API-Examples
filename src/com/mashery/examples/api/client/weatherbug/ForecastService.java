package com.mashery.examples.api.client.weatherbug;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("weatherbug/forecast")
public interface ForecastService extends RemoteService {

	Weather getForecast(double lat, double lng) throws ForecastException;
}
