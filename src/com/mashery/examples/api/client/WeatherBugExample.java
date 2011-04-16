package com.mashery.examples.api.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.maps.client.base.HasLatLng;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.mashery.examples.api.client.weatherbug.Forecast;
import com.mashery.examples.api.client.weatherbug.ForecastService;
import com.mashery.examples.api.client.weatherbug.ForecastServiceAsync;
import com.mashery.examples.api.client.weatherbug.Forecasts;
import com.mashery.examples.api.client.weatherbug.LayerType;
import com.mashery.examples.api.client.weatherbug.Location;
import com.mashery.examples.api.client.weatherbug.Weather;
import com.mashery.examples.api.client.weatherbug.WeatherBugOverlayView;

public class WeatherBugExample extends Composite {
	
	private final PopupMapWidget mapWidget;
	
	private final FlexTable table;

	public WeatherBugExample(final PopupMapWidget mapWidget) {
		this.mapWidget = mapWidget;
		
		FlowPanel panel = new FlowPanel();
		
		panel.add(new HTML("<h1>Weather Forecast</h1>"));
		
		Grid controlPanel = new Grid(1, 3);
		panel.add(controlPanel);
		
		Button refreshButton = new Button("Get Weather Forecast", new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				fetchWeather();
			}
		});
		
		controlPanel.setWidget(0, 0, refreshButton);
		mapWidget.addAutoHidePartner(refreshButton.getElement());
		
		controlPanel.setHTML(0, 1, "&nbsp;&nbsp;&nbsp;Map Overlay:");
		
		final ListBox overlayBox = new ListBox();
		controlPanel.setWidget(0, 2, overlayBox);
		overlayBox.addItem("(none)", "");
		for (LayerType layerType : LayerType.values())
			overlayBox.addItem(layerType.getDescription(), layerType.toString());

	    final WeatherBugOverlayView overlay = new WeatherBugOverlayView();
		
		overlayBox.addChangeHandler(new ChangeHandler() {
			
			private boolean initialized;
			
			private boolean visible = true;
			
			@Override
			public void onChange(ChangeEvent event) {
				int index = overlayBox.getSelectedIndex();
				LayerType layerType = index == 0 ? null : LayerType.valueOf(overlayBox.getValue(index));
				if (layerType == null) {
					if (initialized && visible) {
						overlay.hide();
						visible = false;
					}
				} else {
				    overlay.setLayerType(layerType);
				    if (initialized) {
				    	if (!visible) {
					    	overlay.show();
					    	visible = true;
				    	}
				    } else {
				    	overlay.setMap(mapWidget.getMap());
				    	initialized = true;
				    }

				    mapWidget.show();
				}
			}
		});
		
		mapWidget.addAutoHidePartner(overlayBox.getElement());
		
		panel.add(new HTML());
		
		table = new FlexTable();
		panel.add(table);
		table.setCellSpacing(20);
		((FlexTable.FlexCellFormatter) table.getCellFormatter()).setColSpan(0, 0, 5);
		
		initWidget(new ScrollPanel(panel));
	}
	
	private void fetchWeather() {
		table.setText(0, 0, "Loading...");
		ForecastServiceAsync svc = GWT.create(ForecastService.class);
		mapWidget.show();
		HasLatLng latLng = mapWidget.getMap().getCenter();
		svc.getForecast(latLng.getLatitude(), latLng.getLongitude(), new AsyncCallback<Weather>() {
			
			@Override
			public void onSuccess(Weather weather) {
				table.setText(0, 0, "");
				mapWidget.hide();
				
				Forecasts forecastWrapper = weather.getForecasts();
				if (forecastWrapper == null)
					return;
				
				Location location = forecastWrapper.getLocation();
				if (location != null) {
					StringBuilder buf = new StringBuilder("<strong>");
					if (location.getCity() != null)
						buf.append(location.getCity());
					
					if (location.getState() != null) {
						if (buf.length() > 0)
							buf.append(", ");
						
						buf.append(location.getState());
					}
					
					if (location.getZip() != null) {
						if (buf.length() > 0)
							buf.append(' ');
						
						buf.append(location.getZip());
					}
					
					if (location.getCountry() != null) {
						if (buf.length() > 0)
							buf.append(", ");
						
						buf.append(location.getCountry());
					}
					
					table.setHTML(0, 0, buf.append("</strong>").toString());
				}
				
				Forecast[] forecasts = forecastWrapper.getForecasts();
				if (forecasts != null) {
					for (int i = 0; i < forecasts.length; ++i) {
						table.setText(i + 1, 0, forecasts[i].getDescription());
						table.setWidget(i + 1, 1, new Image(forecasts[i].getImage()));
						table.setHTML(i + 1, 2, forecasts[i].getPrediction());
						table.setHTML(i + 1, 3, forecasts[i].getHigh() + "&deg;F");
						table.setHTML(i + 1, 4, forecasts[i].getLow() + "&deg;F");
					}
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				table.setText(0, 0, "");
				mapWidget.hide();
				Window.alert(caught.getLocalizedMessage());
			}
		});
	}
}
