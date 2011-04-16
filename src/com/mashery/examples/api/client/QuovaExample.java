package com.mashery.examples.api.client;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.TimeZone;
import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SubmitButton;
import com.google.gwt.user.client.ui.TextBox;
import com.mashery.examples.api.client.quova.IPInfo;
import com.mashery.examples.api.client.quova.IPInfoService;
import com.mashery.examples.api.client.quova.IPInfoServiceAsync;

public class QuovaExample extends Composite {

	private final FlexTable generalTable;
	
	private final FlexTable networkTable;
	
	private final DisclosurePanel locationPanel;
	
	private final FlexTable locationTable;
	
	private final TextBox addressText;

	private final SubmitButton submitButton;

	private final Button mapButton;

	private IPInfo result;

	public QuovaExample(final PopupMapWidget mapWidget) {
		FlowPanel panel = new FlowPanel();
		
		panel.add(new HTML("<h1>IP Info</h1>"));
		
		FormPanel entryForm = new FormPanel();
		panel.add(entryForm);

		Grid entryPanel = new Grid(1, 3);
		entryForm.setWidget(entryPanel);
		
		entryPanel.setWidget(0, 0, new Label("IP Address:"));
		entryPanel.getCellFormatter().setAlignment(0, 0, HasHorizontalAlignment.ALIGN_RIGHT, HasVerticalAlignment.ALIGN_MIDDLE);
		
		addressText = new TextBox();
		entryPanel.setWidget(0, 1, addressText);
		addressText.setWidth("150px");
		entryPanel.getCellFormatter().setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		
		submitButton = new SubmitButton("Look Up");
		entryPanel.setWidget(0, 2, submitButton);
		entryPanel.getCellFormatter().setAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT, HasVerticalAlignment.ALIGN_MIDDLE);
		
		final IPInfoServiceAsync ipInfoSvc = GWT.create(IPInfoService.class);
		entryForm.addSubmitHandler(new FormPanel.SubmitHandler() {
			@Override
			public void onSubmit(SubmitEvent event) {
				addressText.setReadOnly(true);
				submitButton.setEnabled(false);
				
				String value = addressText.getText().trim();
				lookupIPInfo(ipInfoSvc, value.length() == 0 ? null : value);
				event.cancel();
			}
		});

		panel.add(new HTML("<em>Note: Leave blank to use your auto-detected IP address.</em>"));
		panel.add(new HTML());

		generalTable = new FlexTable();
		panel.add(generalTable);

		FlexTable.FlexCellFormatter formatter = (FlexCellFormatter) generalTable.getCellFormatter();
		int row = -1;
		formatter.setWidth(0, 0, "160px");
		generalTable.setText(++row, 0, "IP Address:");
		generalTable.setText(row, 1, "");
		formatter.setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		generalTable.setText(++row, 0, "IP Type:");
		generalTable.setText(row, 1, "");
		formatter.setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		generalTable.setText(++row, 0, "Anonymizer Status:");
		generalTable.setText(row, 1, "");
		formatter.setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_RIGHT);

		for (int i = 0, n = generalTable.getRowCount(); i < n; ++i)
			formatter.getElement(i, 0).getStyle().setFontWeight(FontWeight.BOLD);

		DisclosurePanel networkPanel = new DisclosurePanel("Network");
		panel.add(networkPanel);
		networkPanel.setAnimationEnabled(true);
		
		networkTable = new FlexTable();
		networkPanel.setContent(networkTable);
		
		formatter = (FlexCellFormatter) networkTable.getCellFormatter();
		row = -1;
		formatter.setWidth(0, 0, "144px");
		networkTable.setText(++row, 0, "Organization:");
		networkTable.setText(row, 1, "");
		formatter.setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		networkTable.setText(++row, 0, "Carrier:");
		networkTable.setText(row, 1, "");
		formatter.setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		networkTable.setText(++row, 0, "ASN:");
		networkTable.setText(row, 1, "");
		formatter.setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		networkTable.setText(++row, 0, "Connection Type:");
		networkTable.setText(row, 1, "");
		formatter.setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		networkTable.setText(++row, 0, "Line Speed:");
		networkTable.setText(row, 1, "");
		formatter.setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		networkTable.setText(++row, 0, "IP Routing Type:");
		networkTable.setText(row, 1, "");
		formatter.setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		networkTable.setText(++row, 0, "Domain:");
		networkTable.setText(row, 1, "");
		formatter.setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_RIGHT);

		for (int i = 0, n = networkTable.getRowCount(); i < n; ++i)
			formatter.getElement(i, 0).getStyle().setFontWeight(FontWeight.BOLD);
		
		locationPanel = new DisclosurePanel("Location");
		panel.add(locationPanel);
		locationPanel.setAnimationEnabled(true);
		
		locationTable = new FlexTable();
		locationPanel.setContent(locationTable);
		
		formatter = (FlexCellFormatter) locationTable.getCellFormatter();
		row = -1;
		formatter.setWidth(0, 0, "144px");
		locationTable.setText(++row, 0, "Continent:");
		locationTable.setText(row, 1, "");
		formatter.setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		locationTable.setText(++row, 0, "Country:");
		locationTable.setText(row, 1, "");
		formatter.setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		locationTable.setText(++row, 0, "Region:");
		locationTable.setText(row, 1, "");
		formatter.setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		locationTable.setText(++row, 0, "State:");
		locationTable.setText(row, 1, "");
		formatter.setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		locationTable.setText(++row, 0, "DMA:");
		locationTable.setText(row, 1, "");
		formatter.setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		locationTable.setText(++row, 0, "MSA:");
		locationTable.setText(row, 1, "");
		formatter.setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		locationTable.setText(++row, 0, "City:");
		locationTable.setText(row, 1, "");
		formatter.setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		locationTable.setText(++row, 0, "Postal Code:");
		locationTable.setText(row, 1, "");
		formatter.setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		locationTable.setText(++row, 0, "Time Zone:");
		locationTable.setText(row, 1, "");
		formatter.setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		locationTable.setText(++row, 0, "Area Code:");
		locationTable.setText(row, 1, "");
		formatter.setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		locationTable.setText(++row, 0, "Latitude:");
		locationTable.setText(row, 1, "");
		formatter.setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		locationTable.setText(++row, 0, "Longitude:");
		locationTable.setText(row, 1, "");
		formatter.setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_RIGHT);

		for (int i = 0, n = locationTable.getRowCount(); i < n; ++i)
			formatter.getElement(i, 0).getStyle().setFontWeight(FontWeight.BOLD);

		mapButton = new Button("Locate on Map", new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				MarkerOptions opt = new MarkerOptions();
				opt.setTitle(result.getIpAddress());
				opt.setPosition(new LatLng(result.getLatitude(), result.getLongitude()));
				opt.setClickable(true);
				opt.setVisible(true);
				mapWidget.show(new Marker(opt));
			}
		});

		mapButton.setEnabled(false);
		locationTable.setWidget(++row, 0, mapButton);
		formatter.setColSpan(row, 0, 2);
//		formatter.setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		
		initWidget(new ScrollPanel(panel));
	}
	
	private void lookupIPInfo(IPInfoServiceAsync ipInfoSvc, String address) {
		ipInfoSvc.getIPInfo(address, new AsyncCallback<IPInfo>() {

			@Override
			public void onSuccess(IPInfo result) {
				addressText.setReadOnly(false);
				addressText.selectAll();
				submitButton.setEnabled(true);
				if (!mapButton.isEnabled())
					locationPanel.setOpen(true);
				
				int row = 0;
				generalTable.setText(row++, 1, result.getIpAddress());
				generalTable.setText(row++, 1, result.getIpType());
				generalTable.setText(row++, 1, result.getAnonymizerStatus());

				row = 0;
				networkTable.setText(row++, 1, result.getOrganization());
				networkTable.setText(row++, 1, result.getCarrier());
				networkTable.setText(row++, 1, String.valueOf(result.getAsn()));
				networkTable.setText(row++, 1, result.getConnectionType());
				networkTable.setText(row++, 1, result.getLineSpeed());
				networkTable.setText(row++, 1, result.getIpRoutingType());
				StringBuilder domainBuf = new StringBuilder();
				if (result.getSld() != null)
					domainBuf.append(result.getSld());
				
				if (result.getTld() != null) {
					if (domainBuf.length() > 0)
						domainBuf.append('.');
					
					domainBuf.append(result.getTld());
				}
				
				networkTable.setText(row++, 1, domainBuf.length() > 0 ? domainBuf.toString() : "");

				row = 0;
				locationTable.setText(row++, 1, result.getContinent());
				locationTable.setText(row++, 1, result.getCountry() + " [" + result.getCountryCode() + "] (" + result.getCountryCF() + "% confident)");
				locationTable.setText(row++, 1, result.getRegion());
				locationTable.setText(row++, 1, result.getState() + " [" + result.getStateCode() + "] (" + result.getStateCF() + "% confident)");
				locationTable.setText(row++, 1, String.valueOf(result.getDma()));
				locationTable.setText(row++, 1, String.valueOf(result.getMsa()));
				locationTable.setText(row++, 1, result.getCity() + " (" + result.getCityCF() + "% confident)");
				locationTable.setText(row++, 1, result.getPostalCode());
				String tz = DateTimeFormat.getFormat("zzzz").format(new Date(), TimeZone.createTimeZone(result.getTimeZone() * 60));
				locationTable.setText(row++, 1, tz);
				locationTable.setText(row++, 1, result.getAreaCode());
				locationTable.setText(row++, 1, String.valueOf(result.getLatitude()));
				locationTable.setText(row++, 1, String.valueOf(result.getLongitude()));
				
				QuovaExample.this.result = result;
				mapButton.setEnabled(true);
			}

			@Override
			public void onFailure(Throwable caught) {
				addressText.setReadOnly(false);
				addressText.selectAll();
				submitButton.setEnabled(true);
				Window.alert(caught.getLocalizedMessage());
			}
		});
	}
}
