/*
 * Copyright (c) 2010, 2011 Mashery, Inc. All Rights Reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mashery.examples.api.client.weatherbug;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.maps.client.HasMapCanvasProjection;
import com.google.gwt.maps.client.base.HasLatLngBounds;
import com.google.gwt.maps.client.base.HasPoint;
import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.maps.client.base.Point;
import com.google.gwt.maps.client.event.Event;
import com.google.gwt.maps.client.event.EventCallback;
import com.google.gwt.maps.client.event.HasMapsEventListener;
import com.google.gwt.maps.client.overlay.HasProjection;
import com.google.gwt.maps.client.overlay.OverlayView;
import com.google.gwt.user.client.ui.UIObject;

public class WeatherBugOverlayView extends OverlayView {
	
	private static final int TILE_CACHE_SIZE = 64;

	private static final String WEATHERBUG_ENDPOINT = "http://i.wxbug.net/GEO/Google/${layerType}/GetTile_v2.aspx?as=0&c=0&fq=0&mw=0&ds=0&stl=1&api_key=cn63prcp8pmrzmu7exhg9pgd";
	
	private static final LayerType DEFAULT_LAYER_TYPE = LayerType.TEMPERATURE;

	private final LinkedHashMap<TileKey, ImageElement> tiles = new LinkedHashMap<TileKey, ImageElement>();
	
	private LayerType layerType = DEFAULT_LAYER_TYPE;
	
	private DivElement div;
	
	private HasMapsEventListener boundsChangedListener, zoomChangedListener;
	
	public LayerType getLayerType() {
		return layerType;
	}
	
	public void setLayerType(LayerType layerType) {
		if (layerType == null)
			layerType = DEFAULT_LAYER_TYPE;
		
		this.layerType = layerType;
		
		for (ImageElement img : tiles.values())
			img.removeFromParent();
		
		tiles.clear();
		
		if (div != null)
			draw();
	}

	@Override
	public void onAdd() {
		Document doc = Document.get();
		DivElement div = doc.createDivElement();
		div.getStyle().setBorderStyle(BorderStyle.NONE);
		div.getStyle().setBorderWidth(0d, Unit.PX);
		div.getStyle().setPosition(Position.ABSOLUTE);
		div.getStyle().setOpacity(0.4d);
		
		this.div = div;
		getPanes().getOverlayLayer().appendChild(div);
		
		boundsChangedListener = Event.addListener(getMap(), "bounds_changed", new EventCallback() {
			@Override
			public void callback() {
				WeatherBugOverlayView.this.draw();
			}
		});
		
		zoomChangedListener = Event.addListener(getMap(), "zoom_changed", new EventCallback() {
			@Override
			public void callback() {
				for (Node child = WeatherBugOverlayView.this.div.getFirstChild(); child != null;) {
					Node node = child;
					child = child.getNextSibling();
					node.removeFromParent();
				}
				
				tiles.clear();
			}
		});
	}

	@Override
	public void draw() {
		HasLatLngBounds bounds = getMap().getBounds();
		HasMapCanvasProjection ovrPrj = getProjection();
		HasPoint ovrNE = ovrPrj.fromLatLngToDivPixel(bounds.getNorthEast());
		HasPoint ovrSW = ovrPrj.fromLatLngToDivPixel(bounds.getSouthWest());

		div.getStyle().setLeft(ovrSW.getX(), Unit.PX);
		div.getStyle().setTop(ovrNE.getY(), Unit.PX);
		div.getStyle().setWidth(ovrNE.getX() - ovrSW.getX(), Unit.PX);
		div.getStyle().setHeight(ovrSW.getY() - ovrNE.getY(), Unit.PX);
		
		String endpoint = WEATHERBUG_ENDPOINT.replace("${layerType}", layerType.getPath());
		HashSet<TileKey> usedTiles = new HashSet<TileKey>();
		
		int zoom = getMap().getZoom();
		double scale = Math.pow(2d, zoom);
		HasProjection prj = getMap().getProjection();
		LatLng nwLatLng = new LatLng(bounds.getNorthEast().getLatitude(), bounds.getSouthWest().getLongitude());
		HasPoint nwRaw = prj.fromLatLngToPoint(nwLatLng);
		LatLng seLatLng = new LatLng(bounds.getSouthWest().getLatitude(), bounds.getNorthEast().getLongitude());
		HasPoint seRaw = prj.fromLatLngToPoint(seLatLng);
		
		Point nw = new Point(Math.floor(nwRaw.getX() * scale), Math.floor(nwRaw.getY() * scale));
		Point se = new Point(Math.floor(seRaw.getX() * scale), Math.floor(seRaw.getY() * scale));
		
		double offsetX = nw.getX() % 256d;
		double offsetY = nw.getY() % 256d;
		Point startPoint = new Point(nw.getX() - offsetX, nw.getY() - offsetY);

		while (startPoint.getY() < se.getY()) {
			Point p = new Point(startPoint.getX(), startPoint.getY());
			startPoint = new Point(startPoint.getX(), startPoint.getY() + 256);
			while (p.getX() < se.getX()) {
				int tx = (int) p.getX() / 256;
				int ty = (int) p.getY() / 256;
				
				TileKey tileKey = new TileKey(tx, ty, zoom);
				usedTiles.add(tileKey);
				
				ImageElement img = tiles.remove(tileKey);
				if (img == null) {
					img = Document.get().createImageElement();
					img.getStyle().setBorderStyle(BorderStyle.NONE);
					img.getStyle().setBorderWidth(0d, Unit.PX);
					img.getStyle().setPosition(Position.ABSOLUTE);
					
					StringBuilder buf = new StringBuilder(endpoint);
					buf.append("&tx=").append(tx);
					buf.append("&ty=").append(ty);
					buf.append("&zm=").append(zoom);
					img.setSrc(buf.toString());
					
					div.appendChild(img);
				} else {
					img.getStyle().setVisibility(Visibility.VISIBLE);
				}
				
				img.getStyle().setLeft(p.getX() - nw.getX(), Unit.PX);
				img.getStyle().setTop(p.getY() - nw.getY(), Unit.PX);
				img.getStyle().setWidth(256d, Unit.PX);
				img.getStyle().setHeight(256d, Unit.PX);
				tiles.put(tileKey, img);
				
				p = new Point(p.getX() + 256, p.getY());
			}
		}

		for (Iterator<Map.Entry<TileKey, ImageElement>> i = tiles.entrySet().iterator(); i.hasNext();) {
			Map.Entry<TileKey, ImageElement> entry = i.next();
			if (!usedTiles.remove(entry.getKey())) {
				if (tiles.size() > TILE_CACHE_SIZE) {
					i.remove();
					entry.getValue().removeFromParent();
				} else {
					entry.getValue().getStyle().setVisibility(Visibility.HIDDEN);
				}
			}
		}
	}

	@Override
	public void onRemove() {
		Event.removeListener(boundsChangedListener);
		Event.removeListener(zoomChangedListener);
		div.removeFromParent();
		div = null;
		tiles.clear();
	}
	
	public void show() {
		UIObject.setVisible(div, true);
	}
	
	public void hide() {
		UIObject.setVisible(div, false);
	}

	private static class TileKey {
		
		private final int tx;
		
		private final int ty;
		
		private final int zoom;
		
		private final int hashCode;
		
		public TileKey(int tx, int ty, int zoom) {
			this.tx = tx;
			this.ty = ty;
			this.zoom = zoom;
			int hashCode = 17;
			hashCode = 37 * hashCode + tx;
			hashCode = 37 * hashCode + ty;
			hashCode = 37 * hashCode + zoom;
			this.hashCode = hashCode;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			
			if (obj == null || obj.getClass() != getClass())
				return false;
			
			TileKey o = (TileKey) obj;
			return tx == o.tx && ty == o.ty && zoom == o.zoom;
		}
		
		@Override
		public int hashCode() {
			return hashCode;
		}
	}
}
