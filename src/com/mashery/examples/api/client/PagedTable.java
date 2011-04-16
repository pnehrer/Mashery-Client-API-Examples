package com.mashery.examples.api.client;

import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.PushButton;

public class PagedTable<T> extends Composite {
	
	protected final int pageSize;

	protected final FlexTable table;

	protected final PushButton firstButton;
	
	protected final PushButton prevButton;

	protected final PushButton lastButton;

	protected final PushButton nextButton;
	
	protected final FlowPanel refreshPanel;
	
	protected final InlineLabel pageLabel;

	protected int pageIndex;
	
	protected int pageCount = 1;
	
	protected int resultCount = -1;
	
	protected Object[] args;
	
	protected final AsyncCallback<T> dataLoadedCallback = new AsyncCallback<T>() {

		@Override
		public void onSuccess(T result) {
			dataLoaded(result);
			showLoading(false);
		}

		@Override
		public void onFailure(Throwable caught) {
			showLoading(false);
			Window.alert(caught.getLocalizedMessage());
		}
	};

	private PopupPanel loadingPopup;
	
	public PagedTable(int pageSize) {
		assert pageSize > 0;
		this.pageSize = pageSize;
		
		Grid rootPanel = new Grid(2, 1);

		table = new FlexTable();
		rootPanel.setWidget(0, 0, table);
		table.addStyleName("gwt-PagedTable");
		table.setWidth("100%");
//		table.setBorderWidth(1);
		table.getRowFormatter().addStyleName(0, "gwt-PagedTableHeader");

		Grid controlPanel = new Grid(1, 5);
		rootPanel.setWidget(1, 0, controlPanel);
		controlPanel.setSize("100%", "2em");

		firstButton = new PushButton("|<", new ControlButtonHandler() {
			@Override
			protected void setPageIndex() {
				pageIndex = 0;
			}
		});
		
		firstButton.setWidth("4em");
		firstButton.getElement().getStyle().setProperty("textAlign", "center");

		prevButton = new PushButton("<<", new ControlButtonHandler() {
			@Override
			protected void setPageIndex() {
				--pageIndex;
			}
		});

		prevButton.setWidth("4em");
		prevButton.getElement().getStyle().setProperty("textAlign", "center");

		nextButton = new PushButton(">>", new ControlButtonHandler() {
			@Override
			protected void setPageIndex() {
				++pageIndex;
			}
		});

		nextButton.setWidth("4em");
		nextButton.getElement().getStyle().setProperty("textAlign", "center");

		lastButton = new PushButton(">|", new ControlButtonHandler() {
			@Override
			protected void setPageIndex() {
				pageIndex = pageCount - 1;
			}
		});
		
		lastButton.setWidth("4em");
		lastButton.getElement().getStyle().setProperty("textAlign", "center");
		
		Grid centerPanel = new Grid(1, 2);
		
		pageLabel = new InlineLabel();
		centerPanel.setWidget(0, 0, pageLabel);
		
		Anchor refreshLink = new Anchor("Refresh", "#");
		refreshLink.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				event.preventDefault();
				showLoading(true);
				reloadData(args);
			}
		});
		
		refreshPanel = new FlowPanel();
		centerPanel.setWidget(0, 1, refreshPanel);
		
		refreshPanel.add(new InlineHTML("&nbsp;|&nbsp;"));
		refreshPanel.add(refreshLink);
		
		controlPanel.setWidget(0, 0, firstButton);
		controlPanel.setWidget(0, 1, prevButton);
		controlPanel.setWidget(0, 2, centerPanel);
		controlPanel.setWidget(0, 3, nextButton);
		controlPanel.setWidget(0, 4, lastButton);

		controlPanel.getRowFormatter().setVerticalAlign(0, HasVerticalAlignment.ALIGN_MIDDLE);
		controlPanel.getCellFormatter().setHorizontalAlignment(0, 2, HasHorizontalAlignment.ALIGN_CENTER);
		controlPanel.getCellFormatter().setWidth(0, 2, "100%");
		
		updateNavigationControls();
		
		loadingPopup = new PopupPanel();
		loadingPopup.getElement().getStyle().setBorderStyle(BorderStyle.NONE);
		loadingPopup.getElement().getStyle().setBorderWidth(0d, Unit.PX);
		loadingPopup.getElement().getStyle().setOpacity(0.75d);
		Grid loadingLabel = new Grid(1, 1);
		loadingPopup.setWidget(loadingLabel);
		loadingLabel.setWidget(0, 0, new Image("resources/img/ajax-loader.gif"));
		loadingLabel.getCellFormatter().setAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);
		
		initWidget(rootPanel);
	}

	public void setRefreshEnabled(boolean refreshEnabled) {
		refreshPanel.setVisible(refreshEnabled);
	}
	
	public void loadData(Object... args) {
		this.args = args;
		pageIndex = 0;
		pageCount = 1;
		showLoading(true);
		reloadData(args);
	}
	
	protected void showLoading(boolean loading) {
		if (loading) {
			firstButton.setEnabled(false);
			prevButton.setEnabled(false);
			nextButton.setEnabled(false);
			lastButton.setEnabled(false);
			refreshPanel.setVisible(false);

			Element element = table.getElement();
			int x = element.getAbsoluteLeft();
			int y = element.getAbsoluteTop();
			int w = element.getAbsoluteRight() - x;
			int h = element.getAbsoluteBottom() - y;
			loadingPopup.setSize(w + "px", h + "px");
			loadingPopup.setPopupPosition(x, y);
			loadingPopup.show();
		} else {
			loadingPopup.hide();
			updateNavigationControls();
		}
	}
	
	protected void updateNavigationControls() {
		firstButton.setEnabled(pageIndex > 0);
		prevButton.setEnabled(pageIndex > 0);
		nextButton.setEnabled(pageIndex < pageCount - 1);
		lastButton.setEnabled(pageIndex < pageCount - 1);
		StringBuilder buf = new StringBuilder("Page ");
		buf.append(pageIndex + 1);
		buf.append(" of ");
		buf.append(pageCount);
		if (resultCount != -1) {
			buf.append(" (");
			if (resultCount == 1)
				buf.append("1 item)");
			else
				buf.append(resultCount).append(" items)");
		}
		
		pageLabel.setText(buf.toString());
		refreshPanel.setVisible(true);
	}
	
	protected void reloadData(Object... args) {
		// call service with args, passing dataLoadedCallback as callback
	}
	
	protected void dataLoaded(T result) {
		// populate table with result
	}
	
	protected abstract class ControlButtonHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			setPageIndex();
			showLoading(true);
			reloadData(args);
		}
		
		protected abstract void setPageIndex();
	}
}
