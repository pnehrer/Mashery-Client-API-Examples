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

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class Examples implements EntryPoint {
	
	private final ArrayList<Example> examples = new ArrayList<Example>();
	
	private final HashMap<String, Integer> exampleIndex = new HashMap<String, Integer>();

	private DeckPanel contentPanel;
	
	private PopupMapWidget mapWidget;
	
	@Override
	public void onModuleLoad() {
		DockLayoutPanel mainPanel = new DockLayoutPanel(Unit.PX);
		RootLayoutPanel.get().add(mainPanel);
		
		DockLayoutPanel headerPanel = new DockLayoutPanel(Unit.PX);
		mainPanel.addNorth(headerPanel, 80d);

		FlexTable rightPanel = new FlexTable();
		headerPanel.addEast(rightPanel, 250d);
		rightPanel.setCellPadding(4);
		rightPanel.setSize("100%", "100%");
		rightPanel.getCellFormatter().setHeight(0, 0, "100%");
		rightPanel.getCellFormatter().setWidth(1, 0, "100%");
		rightPanel.getCellFormatter().setAlignment(0, 0, HasHorizontalAlignment.ALIGN_RIGHT, HasVerticalAlignment.ALIGN_TOP);
		((FlexTable.FlexCellFormatter) rightPanel.getCellFormatter()).setColSpan(0, 0, 2);
		
		final FlowPanel userPanel = new FlowPanel();
		rightPanel.setWidget(0, 0, userPanel);

		LoginServiceAsync loginSvc = GWT.create(LoginService.class);
		loginSvc.getUserEmail(new AsyncCallback<String>() {
			
			@Override
			public void onSuccess(final String email) {
				final Anchor loginLink = new Anchor();
				loginLink.setHref(email == null ? "/examples/login" : "/examples/login?logout=true");
				loginLink.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						String callbackURL = Window.Location.createUrlBuilder().buildString();
						StringBuilder buf = new StringBuilder("/examples/login?callbackURL=");
						buf.append(URL.encodeQueryString(callbackURL));
						if (email != null)
							buf.append("&logout=true");
						
						loginLink.setHref(buf.toString());
					} 
				});
				
				if (email == null) {
					loginLink.setHTML("Sign&nbsp;in");
				} else {
					loginLink.setHTML("Sign&nbsp;out");
					userPanel.add(new InlineLabel(email));
					userPanel.add(new InlineHTML("&nbsp;|&nbsp;"));
				}
				
				userPanel.add(loginLink);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				userPanel.add(new Label(caught.getLocalizedMessage()));
			}
		});
		
	    mapWidget = new PopupMapWidget();
	    rightPanel.setWidget(1, 1, mapWidget);
		
		Grid logoPanel = new Grid(1, 2);
		logoPanel.setCellSpacing(8);
		logoPanel.getRowFormatter().setVerticalAlign(0, HasVerticalAlignment.ALIGN_TOP);
		headerPanel.add(logoPanel);

		Image masheryLogoImg = new Image("resources/img/mashery.gif");
		Anchor masheryLink = new Anchor();
		masheryLink.setTitle("Mashery, Inc.");
		masheryLink.setHref("http://developer.mashery.com");
		masheryLink.getElement().appendChild(masheryLogoImg.getElement());
		logoPanel.setWidget(0, 0, masheryLink);
		
		InlineLabel logoLabel = new InlineLabel("Mashery Examples");
		logoLabel.getElement().getStyle().setFontWeight(FontWeight.BOLD);
		logoLabel.getElement().getStyle().setFontSize(28d, Unit.PT);
		logoLabel.getElement().getStyle().setColor("#c1080a");
		logoPanel.setWidget(0, 1, logoLabel);
		
		SplitLayoutPanel centerPanel = new SplitLayoutPanel();
		mainPanel.add(centerPanel);

		final StackLayoutPanel examplesPanel = new StackLayoutPanel(Unit.PX);
		centerPanel.addWest(examplesPanel, 200d);
		
	    contentPanel = new DeckPanel();
		centerPanel.add(contentPanel);
		contentPanel.getElement().getStyle().setPaddingLeft(8d, Unit.PX);
		contentPanel.setAnimationEnabled(true);

		addExample(new Example("quova", "Quova") {
			@Override
			public Widget createContent() {
				return new QuovaExample(mapWidget);
			}
		}, examplesPanel);
		
		addExample(new Example("etsy", "Etsy") {
			@Override
			public Widget createContent() {
				return new EtsyExample(mapWidget);
			}
		}, examplesPanel);

		addExample(new Example("weatherbug", "WeatherBug") {
			@Override
			public Widget createContent() {
				return new WeatherBugExample(mapWidget);
			}
		}, examplesPanel);
		
		addExample(new Example("hotwire", "Hotwire") {
			@Override
			public Widget createContent() {
				return new HotwireExample(mapWidget);
			}
		}, examplesPanel);
		
		addExample(new Example("active", "Active") {
			@Override
			public Widget createContent() {
				return new ActiveExample(mapWidget);
			}
		}, examplesPanel);
		
		String historyToken = History.getToken();
		Integer index = exampleIndex.get(historyToken);
		if (index == null)
			index = 0;
		
		examplesPanel.showWidget(index);
		showExample(index);
		
		examplesPanel.addSelectionHandler(new SelectionHandler<Integer>() {
			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				int index = event.getSelectedItem();
				Example example = examples.get(index);
				if (example != null)
					History.newItem(example.getId());
			}
		});
		
		History.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				String historyToken = event.getValue();
				Integer index = exampleIndex.get(historyToken);
				if (index != null) {
					examplesPanel.showWidget(index);
					showExample(index);
				}
			}
		});
	}
	
	private void addExample(Example example, StackLayoutPanel headerPanel) {
		examples.add(example);
		int i = headerPanel.getWidgetCount();
		exampleIndex.put(example.getId(), i);
		headerPanel.add(example.createHeaderContent(), example.getHeaderTitle(), 32d);
		contentPanel.add(example.createContent());
	}
	
	private void showExample(int index) {
		contentPanel.showWidget(index);
	}
	
	private static abstract class Example {
		
		private final String id;
		
		private final String headerTitle;
		
		public Example(String id, String headerTitle) {
			this.id = id;
			this.headerTitle = headerTitle;
		}
		
		public String getId() {
			return id;
		}

		public String getHeaderTitle() {
			return headerTitle;
		}

		public Widget createHeaderContent() {
			SimplePanel panel = new SimplePanel();
			panel.getElement().getStyle().setBorderWidth(0d, Unit.PX);
			panel.getElement().getStyle().setBorderStyle(BorderStyle.NONE);
			panel.getElement().getStyle().setMargin(0d, Unit.PX);
			panel.getElement().getStyle().setPadding(0d, Unit.PX);
			panel.getElement().getStyle().setProperty("borderTop", "1px solid #c0c0c0");
			panel.getElement().getStyle().setProperty("borderRight", "1px solid #c0c0c0");
			Frame frame = new Frame("resources/html/" + id + ".html");
			panel.setWidget(frame);
			frame.setSize("100%", "100%");
			frame.getElement().getStyle().setBorderWidth(0d, Unit.PX);
			frame.getElement().getStyle().setBorderStyle(BorderStyle.NONE);
			return panel;
		}

		public abstract Widget createContent();
	}
}
