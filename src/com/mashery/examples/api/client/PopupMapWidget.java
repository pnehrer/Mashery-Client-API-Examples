package com.mashery.examples.api.client;

import java.util.HashMap;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.maps.client.HasMap;
import com.google.gwt.maps.client.MapOptions;
import com.google.gwt.maps.client.MapTypeId;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.base.HasLatLng;
import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.maps.client.overlay.HasMarker;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ToggleButton;

public class PopupMapWidget extends Composite {

	private final PopupPanel mapPanel;
	
	private final MapWidget mapWidget;

	private final ToggleButton mapButton;
	
	private final HashMap<MarkerKey, HasMarker> markers = new HashMap<MarkerKey, HasMarker>();

	public PopupMapWidget() {
		mapPanel = new PopupPanel(true);
		mapPanel.setAutoHideOnHistoryEventsEnabled(true);
		mapPanel.setAnimationEnabled(true);
		
		MapOptions options = new MapOptions();
	    options.setZoom(1);
	    options.setCenter(new LatLng(0d, 0d));
	    options.setMapTypeId(new MapTypeId().getRoadmap());
	    options.setDraggable(true);
	    options.setScrollwheel(true);
	    options.setNavigationControl(true);
	    options.setMapTypeControl(true);
	    mapWidget = new MapWidget(options);
	    mapWidget.setSize("512px", "512px");

	    FlowPanel mapContainer = new FlowPanel();
	    mapPanel.setWidget(mapContainer);
	    mapContainer.add(mapWidget);
	    
	    Anchor clearMarkersLink = new Anchor("Clear Markers", "#");
	    mapContainer.add(clearMarkersLink);
	    clearMarkersLink.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				event.preventDefault();
				// there's no way to setMap(null) on a marker
				// instead, assign it to a dummy map
				MapWidget garbage = new MapWidget(new MapOptions());
				HasMap map = garbage.getMap();
				for (HasMarker marker : markers.values())
					marker.setMap(map);
				
				markers.clear();
			}
		});
	    
	    mapButton = new ToggleButton("Map");
	    mapPanel.addAutoHidePartner(mapButton.getElement());
	    mapButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (mapButton.isDown())
					mapPanel.showRelativeTo(mapButton);
				else
					mapPanel.hide();
			}
		});
	    
	    mapPanel.addCloseHandler(new CloseHandler<PopupPanel>() {
			@Override
			public void onClose(CloseEvent<PopupPanel> event) {
				mapButton.setDown(false);
			}
		});

	    initWidget(mapButton);
	}
	
	public HasMap getMap() {
		return mapWidget.getMap();
	}
	
	public void addAutoHidePartner(Element partner) {
		mapPanel.addAutoHidePartner(partner);
	}
	
	public void show() {
		mapButton.setDown(true);
		if (!mapPanel.isShowing())
			mapPanel.showRelativeTo(this);
	}
	
	public void show(HasLatLng latLng) {
		mapWidget.getMap().panTo(latLng);
		if (mapWidget.getMap().getZoom() <= 2)
			mapWidget.getMap().setZoom(8);

		show();
	}
	
	public void show(HasMarker marker) {
		HasLatLng position = marker.getPosition();
		double lat = position == null ? 0d : position.getLatitude();
		double lng = position == null ? 0d : position.getLongitude();
		MarkerKey key = new MarkerKey(marker.getTitle(), lat, lng);
		if (!markers.containsKey(key)) {
			marker.setMap(mapWidget.getMap());
			markers.put(key, marker);
		}
		
		show(position == null ? new LatLng(lat, lng) : position);
	}
	
	public void hide() {
		mapPanel.hide();
	}
	
	private static class MarkerKey {
		
		private final String title;
		
		private final double lat;
		
		private final double lng;
		
		private final int hashCode;
		
		public MarkerKey(String title, double lat, double lng) {
			this.title = title;
			this.lat = lat;
			this.lng = lng;
			int c = 17;
			c = 37 * c + (title == null ? 0 : title.hashCode());
			c = 37 * c + (int) lat;
			c = 37 * c + (int) lng;
			hashCode = c;
		}
		
		@Override
		public int hashCode() {
			return hashCode;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj == this)
				return true;
			
			if (obj == null || obj.getClass() != getClass())
				return false;
			
			MarkerKey o = (MarkerKey) obj;
			return (title == null ? o.title == null : title.equals(o.title)) && lat == o.lat && lng == o.lng;
		}
	}
}
