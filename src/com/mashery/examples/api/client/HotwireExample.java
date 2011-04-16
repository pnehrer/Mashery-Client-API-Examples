package com.mashery.examples.api.client;

import java.util.Date;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.maps.client.base.HasLatLng;
import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
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
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.mashery.examples.api.client.hotwire.jso.HotelDeal;
import com.mashery.examples.api.client.hotwire.jso.HotwireResponse;

public class HotwireExample extends Composite {
	
	private final PopupPanel infoPanel;
	
	private final FlexTable infoGrid;
	
	private final Anchor visitLink;
	
	private HotelDeal selectedResult;

	public HotwireExample(final PopupMapWidget mapWidget) {
		FlowPanel panel = new FlowPanel();
		
		panel.add(new HTML("<h1>Hotel Deals</h1>"));
		
		FormPanel form = new FormPanel();
		panel.add(form);
		
		FlexTable entryGrid = new FlexTable();
		form.add(entryGrid);
		FlexTable.FlexCellFormatter formatter = (FlexCellFormatter) entryGrid.getCellFormatter();
		entryGrid.setWidget(0, 0, new Label("Destination:"));
		formatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		
		FlowPanel destPanel = new FlowPanel();
		entryGrid.setWidget(0, 1, destPanel);
		formatter.setColSpan(0, 1, 5);
		
		final TextBox destText = new TextBox();
		destPanel.add(destText);
		destText.setWidth("220px");
		
		Anchor fromMapLink = new Anchor("<- from Map");
		fromMapLink.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				HasLatLng center = mapWidget.getMap().getCenter();
				destText.setText(center.getLatitude() + "," + center.getLongitude());
			}
		});

		destPanel.add(new InlineHTML("&nbsp;"));
		destPanel.add(fromMapLink);
		
		entryGrid.setWidget(1, 0, new Label("From:"));
		formatter.setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		
		final TextBox startDateText = new TextBox();
		entryGrid.setWidget(1, 1, startDateText);
		startDateText.setWidth("100px");
		
		final PopupPanel startDatePanel = new PopupPanel(true);
		startDatePanel.setAutoHideOnHistoryEventsEnabled(true);
		
		final ToggleButton startDateButton = new ToggleButton(new Image("resources/img/date-picker.gif"));
		entryGrid.setWidget(1, 2, startDateButton);
		startDateButton.setPixelSize(16, 16);
		startDatePanel.addAutoHidePartner(startDateButton.getElement());
		startDateButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				startDatePanel.showRelativeTo(startDateButton);
			}
		});
		
		startDatePanel.addCloseHandler(new CloseHandler<PopupPanel>() {
			@Override
			public void onClose(CloseEvent<PopupPanel> event) {
				startDateButton.setDown(false);
			}
		});
		
		final DateTimeFormat dateFormat = DateTimeFormat.getFormat("MM/dd/yyyy");
		
		final DatePicker startDatePicker = new DatePicker();
		startDatePanel.setWidget(startDatePicker);
		startDatePicker.addValueChangeHandler(new ValueChangeHandler<Date>() {
			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				startDateText.setText(dateFormat.format(event.getValue()));
				startDatePanel.hide();
			}
		});
		
		startDateText.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				try {
					Date date = dateFormat.parse(event.getValue());
					startDatePicker.setValue(date);
				} catch (IllegalArgumentException e) {
					// ignore
				}
			}
		});
		
		Date date = new Date();
		startDatePicker.setValue(date, true);
		
		entryGrid.setWidget(1, 3, new Label("To:"));
		formatter.setHorizontalAlignment(1, 2, HasHorizontalAlignment.ALIGN_RIGHT);
		
		final TextBox endDateText = new TextBox();
		entryGrid.setWidget(1, 4, endDateText);
		endDateText.setWidth("100px");
		
		final PopupPanel endDatePanel = new PopupPanel(true);
		endDatePanel.setAutoHideOnHistoryEventsEnabled(true);
		
		final ToggleButton endDateButton = new ToggleButton(new Image("resources/img/date-picker.gif"));
		entryGrid.setWidget(1, 5, endDateButton);
		endDateButton.setPixelSize(16, 16);
		endDatePanel.addAutoHidePartner(endDateButton.getElement());
		endDateButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				endDatePanel.showRelativeTo(endDateButton);
			}
		});
		
		endDatePanel.addCloseHandler(new CloseHandler<PopupPanel>() {
			@Override
			public void onClose(CloseEvent<PopupPanel> event) {
				endDateButton.setDown(false);
			}
		});
		
		final DatePicker endDatePicker = new DatePicker();
		endDatePanel.setWidget(endDatePicker);
		endDatePicker.addValueChangeHandler(new ValueChangeHandler<Date>() {
			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				endDateText.setText(dateFormat.format(event.getValue()));
				endDatePanel.hide();
			}
		});
		
		endDateText.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				try {
					Date date = dateFormat.parse(event.getValue());
					endDatePicker.setValue(date);
				} catch (IllegalArgumentException e) {
					// ignore
				}
			}
		});
		
		date = CalendarUtil.copyDate(date);
		CalendarUtil.addDaysToDate(date, 1);
		endDatePicker.setValue(date, true);
		
		SubmitButton submitButton = new SubmitButton("Search");
		entryGrid.setWidget(2, 0, submitButton);
		formatter.setColSpan(2, 0, 6);
		formatter.setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		
		final HotelDealsTable table = new HotelDealsTable(10);
		panel.add(table);
		table.setWidth("500px");
		
		form.addSubmitHandler(new FormPanel.SubmitHandler() {
			@Override
			public void onSubmit(SubmitEvent event) {
				String dest = destText.getValue().trim();
				String start = startDateText.getValue().trim();
				String end = endDateText.getValue().trim();
				table.loadData(dest.length() == 0 ? null : dest, start.length() == 0 ? null : start, end.length() == 0 ? null : end);
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
				
				MarkerOptions opt = new MarkerOptions();
				opt.setTitle(selectedResult.getHeadline());
				LatLng latLng = new LatLng(selectedResult.getNeighborhoodLatitude(), selectedResult.getNeighborhoodLongitude());
				opt.setPosition(latLng);
				opt.setClickable(true);
				opt.setVisible(true);
				mapWidget.show(new Marker(opt));

			}
		});
		
		mapWidget.addAutoHidePartner(mapLink.getElement());
		
		linkPanel.add(new InlineHTML("&nbsp;|&nbsp;"));
		linkPanel.add(visitLink = new Anchor("Visit", "#"));

		initWidget(new ScrollPanel(panel));
	}
	
	private class HotelDealsTable extends PagedTable<HotwireResponse> {

		public HotelDealsTable(int pageSize) {
			super(pageSize);
			table.setHTML(0, 0, "<strong>#</strong>");
			table.setHTML(0, 1, "<strong>Headline</strong>");
			table.setHTML(0, 2, "<strong>Rating</strong>");
			table.getCellFormatter().setWidth(0, 1, "100%");
			table.setText(1, 1, "No data");
		}
		
		@Override
		protected void reloadData(Object... args) {
			search((String) args[0], (String) args[1], (String) args[2]);
		}
		
		private void search(String dest, String start, String end) {
			StringBuilder buf = new StringBuilder("/examples/hotwire/hoteldeals?limit=");
			buf.append(pageSize);
			if (pageIndex > 0)
				buf.append("%7C").append(pageIndex * pageSize);

			if (dest != null)
				buf.append("&dest=").append(URL.encodeQueryString(dest));
			
			if (start != null)
				buf.append("&startdate=").append(URL.encodeQueryString(start));
			
			if (end != null)
				buf.append("&enddate=").append(URL.encodeQueryString(end));

			RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, buf.toString());
			try {
				builder.sendRequest(null, new RequestCallback() {
					
					@Override
					public void onResponseReceived(Request request, Response response) {
						if (response.getStatusCode() == 200) {
							String text = response.getText();
							HotwireResponse result = HotwireResponse.fromSource(text);
							dataLoadedCallback.onSuccess(result);
						} else {
							dataLoadedCallback.onFailure(new RuntimeException(response.getStatusText()));
						}
					}
					
					@Override
					public void onError(Request request, Throwable exception) {
						dataLoadedCallback.onFailure(exception);
					}
				});
			} catch (RequestException e) {
				dataLoadedCallback.onFailure(e);
			}
		}
		
		@Override
		protected void dataLoaded(HotwireResponse response) {
			// TODO handle error responses
			int n = 0;
			JsArray<HotelDeal> hotelDeals = response.getHotelDeals();
			if (hotelDeals != null) {
				n = hotelDeals.length();
				pageCount = n >= pageSize ? pageIndex + 2 : pageIndex + 1;
				int offset = pageIndex * pageSize;
			
				for (int i = 0; i < n; ++i) {
					final HotelDeal hotelDeal = hotelDeals.get(i);
					table.setText(i + 1, 0, i + offset + 1 + ".");
					final HTML label = new HTML(hotelDeal.getHeadline());
					table.setWidget(i + 1, 1, label);
					
					double r = hotelDeal.getStarRating();
					int w = (int) r * 17;
					Image img = new Image("resources/img/stars.gif", 0, 0, w + 2, 15);
					table.setWidget(i + 1, 2, img);
					if (r == 1d)
						img.setTitle("1 star");
					else
						img.setTitle((r == Math.rint(r) ? Integer.toString((int) r) : Double.toString(r)) + " stars");
					
					label.addMouseOverHandler(new MouseOverHandler() {
						@Override
						public void onMouseOver(MouseOverEvent event) {
							selectedResult = hotelDeal;
							visitLink.setHref(hotelDeal.getUrl() == null ? "#" : hotelDeal.getUrl());

							StringBuilder buf = new StringBuilder();
							if (hotelDeal.getNeighborhood() != null)
								buf.append("<strong>").append(hotelDeal.getNeighborhood()).append("</strong>");
							
							if (hotelDeal.getCity() != null) {
								if (buf.length() > 0)
									buf.append(", ");
								
								buf.append(hotelDeal.getCity());
							}
							
							if (hotelDeal.getStateCode() != null) {
								if (buf.length() > 0)
									buf.append(", ");
								
								buf.append(hotelDeal.getStateCode());
							}
							
							if (hotelDeal.getCountryCode() != null && !hotelDeal.getCountryCode().equals(hotelDeal.getStateCode())) {
								if (buf.length() > 0)
									buf.append(", ");
								
								buf.append(hotelDeal.getCountryCode());
							}
							
							infoGrid.setHTML(0, 0, buf.toString());
							
							infoGrid.setText(1, 0, "Found on: " + DateTimeFormat.getFormat(PredefinedFormat.DATE_MEDIUM).format(new Date((long) hotelDeal.getFoundDate())));
							infoGrid.setHTML(2, 0, "Price per night: <strong>" + NumberFormat.getCurrencyFormat().format(hotelDeal.getPrice()) + "</strong>");
							
							infoPanel.showRelativeTo(label);
						}
					});
				}
			}
			
			for (int i = Math.min(pageSize + 1, table.getRowCount()) - 1; i > n; --i) {
				table.removeRow(i);
			}
		}
	}
}
