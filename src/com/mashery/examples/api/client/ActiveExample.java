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
package com.mashery.examples.api.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.maps.client.base.HasLatLng;
import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.maps.client.geocoder.Geocoder;
import com.google.gwt.maps.client.geocoder.GeocoderCallback;
import com.google.gwt.maps.client.geocoder.GeocoderRequest;
import com.google.gwt.maps.client.geocoder.HasAddressComponent;
import com.google.gwt.maps.client.geocoder.HasGeocoderResult;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SubmitButton;
import com.google.gwt.user.client.ui.TextBox;
import com.mashery.examples.api.client.active.Meta;
import com.mashery.examples.api.client.active.SearchResponse;
import com.mashery.examples.api.client.active.SearchResult;
import com.mashery.examples.api.client.active.SearchService;
import com.mashery.examples.api.client.active.SearchServiceAsync;

public class ActiveExample extends Composite {
	
	private final PopupPanel infoPanel;
	
	private final FlexTable infoGrid;
	
	private final Anchor visitLink;
	
	private SearchResult selectedResult;

	public ActiveExample(final PopupMapWidget mapWidget) {
		FlowPanel panel = new FlowPanel();
		
		panel.add(new HTML("<h1>Search</h1>"));
		
		FormPanel form = new FormPanel();
		panel.add(form);
		
		FlexTable entryGrid = new FlexTable();
		form.add(entryGrid);
		FlexTable.FlexCellFormatter formatter = (FlexCellFormatter) entryGrid.getCellFormatter();
		entryGrid.setWidget(0, 0, new Label("Keywords:"));
		formatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		
		final TextBox keywordsText = new TextBox();
		entryGrid.setWidget(0, 1, keywordsText);
		keywordsText.setWidth("200px");
		
		entryGrid.setWidget(1, 0, new Label("Location:"));
		formatter.setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		
		final TextBox locationText = new TextBox();
		entryGrid.setWidget(1, 1, locationText);
		locationText.setWidth("200px");
		
		Anchor fromMapLink = new Anchor("<- from Map");
		final Geocoder geocoder = new Geocoder();
		fromMapLink.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				mapWidget.show();
				HasLatLng center = mapWidget.getMap().getCenter();
				GeocoderRequest request = new GeocoderRequest();
				request.setLatLng(center);
				geocoder.geocode(request, new GeocoderCallback() {
					@Override
					public void callback(List<HasGeocoderResult> responses, String status) {
						if (responses != null) {
							String postalCode = null;
							String country = null;
							RESULTS: for (HasGeocoderResult result : responses) {
								if (result.getTypes().contains("postal_code")) {
									for (HasAddressComponent addr : result.getAddressComponents()) {
										if (postalCode == null && addr.getTypes().contains("postal_code"))
											postalCode = addr.getLongName();
										
										if (country == null && addr.getTypes().contains("country"))
											country = addr.getLongName();
										
										if (postalCode != null && country != null)
											break RESULTS;
									}
								}
							}
							
							if (postalCode != null) {
								if (country == null)
									locationText.setText(postalCode);
								else
									locationText.setText(postalCode + ", " + country);
							}
						}
					}
				});
			}
		});
		
		entryGrid.setWidget(1, 2, fromMapLink);
		
		SubmitButton submitButton = new SubmitButton("Search");
		entryGrid.setWidget(2, 0, submitButton);
		formatter.setColSpan(2, 0, 3);
		formatter.setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		
		final SearchResultsTable table = new SearchResultsTable(10);
		panel.add(table);
		table.setWidth("500px");
		
		form.addSubmitHandler(new FormPanel.SubmitHandler() {
			@Override
			public void onSubmit(SubmitEvent event) {
				String keywords = keywordsText.getValue().trim();
				String location = locationText.getValue().trim();
				table.loadData(keywords.length() == 0 ? null : keywords, location.length() == 0 ? null : location);
				event.cancel();
			}
		});
		
		infoPanel = new PopupPanel(true);
		infoPanel.setAutoHideOnHistoryEventsEnabled(true);
		infoGrid = new FlexTable();
		infoPanel.setWidget(infoGrid);
		FlowPanel linkPanel = new FlowPanel();
		infoGrid.setWidget(3, 0, linkPanel);
		
		Anchor mapLink = new Anchor("Map", "#");
		linkPanel.add(mapLink);
		mapLink.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				event.preventDefault();
				if (selectedResult == null)
					return;
				
				Meta meta = selectedResult.getMeta();
				if (meta == null) {
					Window.alert("No location available.");
					return;
				}
				
				MarkerOptions opt = new MarkerOptions();
				if (meta.getAssetName() != null)
					opt.setTitle(meta.getAssetName().trim());
				
				opt.setPosition(new LatLng(meta.getLatitude(), meta.getLongitude()));
				opt.setClickable(true);
				opt.setVisible(true);
				mapWidget.show(new Marker(opt));
			}
		});
		
		mapWidget.addAutoHidePartner(mapLink.getElement());
		
		linkPanel.add(new InlineHTML("&nbsp;|&nbsp;"));
		linkPanel.add(visitLink = new Anchor("Visit", "#"));

		((FlexTable.FlexCellFormatter) infoGrid.getCellFormatter()).setColSpan(1, 0, 2);
		((FlexTable.FlexCellFormatter) infoGrid.getCellFormatter()).setColSpan(2, 0, 2);
		((FlexTable.FlexCellFormatter) infoGrid.getCellFormatter()).setColSpan(3, 0, 2);

		initWidget(new ScrollPanel(panel));
	}
	
	private class SearchResultsTable extends PagedTable<SearchResponse> {

		private final SearchServiceAsync searchSvc;

		public SearchResultsTable(int pageSize) {
			super(pageSize);
			table.setHTML(0, 0, "<strong>#</strong>");
			table.setHTML(0, 1, "<strong>Title</strong>");
			table.getCellFormatter().setWidth(0, 1, "100%");
			table.setText(1, 1, "No data");
			searchSvc = GWT.create(SearchService.class);
		}
		
		@Override
		protected void reloadData(Object... args) {
			search((String) args[0], (String) args[1]);
		}
		
		private void search(String keywords, String location) {
			searchSvc.search(keywords, location, pageIndex + 1, pageSize, dataLoadedCallback);
		}
		
		@Override
		protected void dataLoaded(SearchResponse response) {
			resultCount = response.getTotalResults();
			pageCount = ((resultCount - 1) / pageSize) + 1;
			int offset = (response.getPage() - 1) * pageSize;
			
			SearchResult[] results = response.getResults();
			for (int i = 0; i < results.length; ++i) {
				final SearchResult result = results[i];
				table.setText(i + 1, 0, result.getNum() + offset + ".");
				final HTML label = new HTML(result.getTitle());
				table.setWidget(i + 1, 1, label);
				label.addMouseOverHandler(new MouseOverHandler() {
					@Override
					public void onMouseOver(MouseOverEvent event) {
						selectedResult = result;
						visitLink.setHref(result.getUrl() == null ? "#" : result.getUrl());
						Meta meta = result.getMeta();
						if (meta == null) {
							infoGrid.setText(0, 0, "Error: No event details available.");
							infoGrid.setText(0, 1, null);
							infoGrid.setText(1, 0, null);
							infoGrid.setText(2, 1, null);
						} else {
							String imageURL = meta.getImage1();
							if (imageURL == null)
								infoGrid.setText(0, 0, null);
							else
								infoGrid.setWidget(0, 0, new Image(imageURL));
							
							if (meta.getAssetName() == null)
								infoGrid.setText(0, 1, null);
							else
								infoGrid.setHTML(0, 1, "<strong>" + meta.getAssetName().trim() + "</strong>");
							
							if (meta.getEventDate() == null)
								infoGrid.setText(1, 0, null);
							else
								infoGrid.setText(1, 0, meta.getEventDate().toString());
							
							StringBuilder buf = new StringBuilder();
							if (meta.getLocation() != null)
								buf.append(meta.getLocation());
							
							if (meta.getEventAddress() != null) {
								if (buf.length() > 0)
									buf.append(", ");
								
								buf.append(meta.getEventAddress());
							}
							
							if (meta.getCity() != null) {
								if (buf.length() > 0)
									buf.append(", ");
								
								buf.append(meta.getCity());
							}
							
							if (meta.getState() != null) {
								if (buf.length() > 0)
									buf.append(", ");
								
								buf.append(meta.getState());
							}
							
							if (meta.getZip() != null) {
								if (buf.length() > 0)
									buf.append(' ');
								
								buf.append(meta.getZip());
							}
							
							if (meta.getCountry() != null) {
								if (buf.length() > 0)
									buf.append(", ");
								
								buf.append(meta.getCountry());
							}
							
							infoGrid.setText(2, 0, buf.toString());
						}
						
						infoPanel.showRelativeTo(label);
					}
				});
			}
			
			for (int i = Math.min(pageSize + 1, table.getRowCount()) - 1; i > results.length; --i) {
				table.removeRow(i);
			}
		}
	}
}
