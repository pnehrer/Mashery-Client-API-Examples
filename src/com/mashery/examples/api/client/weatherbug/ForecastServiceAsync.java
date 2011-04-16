package com.mashery.examples.api.client.weatherbug;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ForecastServiceAsync {

	void getForecast(double lat, double lng, AsyncCallback<Weather> callback);
}
