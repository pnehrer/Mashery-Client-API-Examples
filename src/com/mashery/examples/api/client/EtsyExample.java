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

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.drop.SimpleDropController;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.http.client.URL;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mashery.examples.api.client.etsy.EtsyService;
import com.mashery.examples.api.client.etsy.EtsyServiceAsync;
import com.mashery.examples.api.client.etsy.NotLoggedInException;
import com.mashery.examples.api.client.etsy.OAuthException;
import com.mashery.examples.api.client.etsy.User;
import com.mashery.examples.api.client.etsy.UserProfile;
import com.mashery.examples.api.client.etsy.jso.EtsyResponse;
import com.mashery.examples.api.client.etsy.jso.FavoriteListing;
import com.mashery.examples.api.client.etsy.jso.FeaturedListing;
import com.mashery.examples.api.client.etsy.jso.Listing;
import com.mashery.examples.api.client.etsy.jso.ListingImage;
import com.mashery.examples.api.client.etsy.jso.Shop;

public class EtsyExample extends Composite {

//	private static final String ETSY_FEATURED_LISTINGS_ENDPOINT = "http://openapi.etsy.com/v2/sandbox/public/homepages/listings/.js?api_key=qu7u5h7bu7twn6s873mnqznz";
	private static final String ETSY_FEATURED_LISTINGS_ENDPOINT = "http://openapi.etsy.com/v2/public/homepages/listings/.js?api_key=eqctc7pcj9w5mqqna3caby3h";

//	private static final String ETSY_FAVORITE_LISTINGS_ENDPOINT = "http://openapi.etsy.com/v2/sandbox/public/users/${userId}/favorites/listings.js?api_key=qu7u5h7bu7twn6s873mnqznz";
	private static final String ETSY_FAVORITE_LISTINGS_ENDPOINT = "http://openapi.etsy.com/v2/public/users/${userId}/favorites/listings.js?api_key=eqctc7pcj9w5mqqna3caby3h";
	
	private static int COLS = 10;
	
	private final PagedTable<EtsyResponse<FeaturedListing>> featuredListingsTable;
	
	private final FavoriteListingsTable favoriteListingsTable;

	private final DeckPanel bottomPanel;
	
	private final InlineHTML userLabel;

	private final PickupDragController dragController;
	
	private final SimpleDropController dropController;
	
	private final PopupPanel infoPanel;
	
	private final Grid infoGrid;

	private final Anchor mapListingLink;
	
	private final Anchor deleteFavListingLink;
	
	private final EtsyServiceAsync etsySvc;

	private boolean initialized;
	
	private User user;
	
	private Listing selectedListing;

	public EtsyExample(final PopupMapWidget mapWidget) {
		AbsolutePanel rootPanel = new AbsolutePanel();
		rootPanel.setSize("100%", "100%");
		
		SplitLayoutPanel panel = new SplitLayoutPanel();
		rootPanel.add(panel);
		panel.setHeight("100%");
		
		FlowPanel topPanel = new FlowPanel();
		panel.addNorth(topPanel, 250d);
		
		topPanel.add(new HTML("<h1>Featured Listings</h1>"));

		dragController = new PickupDragController(rootPanel, false);
		dragController.setBehaviorDragProxy(true);
		dragController.setBehaviorMultipleSelection(false);
		dragController.setBehaviorDragStartSensitivity(2);
		
		featuredListingsTable = new FeaturedListingsTable(20);
		topPanel.add(new ScrollPanel(featuredListingsTable));
		
		DockLayoutPanel bottomPanelContainer = new DockLayoutPanel(Unit.PX);
		panel.add(bottomPanelContainer);
		
		bottomPanelContainer.addNorth(new HTML("<h1>Favorite Listings</h1>"), 50d);
		
		bottomPanel = new DeckPanel();
		bottomPanelContainer.add(bottomPanel);

		bottomPanel.add(new HTML("Obtaining your Etsy account information..."));

		favoriteListingsTable = new FavoriteListingsTable(20);
		
		dropController = new SimpleDropController(favoriteListingsTable) {
			public void onDrop(DragContext context) {
				Image img = (Image) context.draggable;
				String id = img.getElement().getId();
				if (id != null && id.startsWith("listing_")) {
					try {
						int listingId = Integer.parseInt(id.substring("listing_".length()));
						favoriteListingsTable.createUserFavoriteListing(listingId);
					} catch (NumberFormatException e) {
						GWT.log("Unable to parse listing id.", e);
					}
				}
			}
		};

		dragController.registerDropController(dropController);
		
		FlowPanel userPanel = new FlowPanel();

		userPanel.add(userLabel = new InlineHTML());
		userPanel.add(new InlineHTML("&nbsp;&nbsp;&nbsp;"));
		Anchor disconnectLink = new Anchor("Disconnect", "#");
		userPanel.add(disconnectLink);
		disconnectLink.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				event.preventDefault();
				disconnectEtsyAccount();
			}
		});
		
		userPanel.add(new InlineHTML("&nbsp;|&nbsp;"));
		Anchor mapProfileLink = new Anchor("Map Profile", "#");
		userPanel.add(mapProfileLink);
		mapProfileLink.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				event.preventDefault();
				if (user != null) {
					UserProfile profile = user.getProfile();
					if (profile != null) {
						MarkerOptions opt = new MarkerOptions();
						opt.setTitle(profile.getLoginName());
						opt.setPosition(new LatLng(profile.getLat(), profile.getLon()));
						opt.setClickable(true);
						opt.setVisible(true);
						mapWidget.show(new Marker(opt));
					}
				}
			}
		});
		
		mapWidget.addAutoHidePartner(mapProfileLink.getElement());
		
		userPanel.add(new HTML());
		userPanel.add(favoriteListingsTable);
		
		bottomPanel.add(new ScrollPanel(userPanel));
		bottomPanel.add(new HTML("You must be logged in in order to manage your favorite listings."));
		bottomPanel.add(createOAuthPanel());
		
		etsySvc = GWT.create(EtsyService.class);
		
		infoPanel = new PopupPanel(true);
		infoPanel.setAutoHideOnHistoryEventsEnabled(true);
		infoGrid = new Grid(2, 1);
		infoPanel.setWidget(infoGrid);
		infoGrid.setWidth("240px");
		
		mapListingLink = new Anchor("Map", "#");
		mapListingLink.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				event.preventDefault();
				if (selectedListing == null)
					return;
				
				Shop shop = selectedListing.getShop();
				if (shop == null) {
					Window.alert("No shop information available.");
					return;
				}
				
				if (!shop.hasLatLon()) {
					Window.alert("No location information available.");
					return;
				}
				
				MarkerOptions opt = new MarkerOptions();
				opt.setTitle(shop.getShopName());
				opt.setPosition(new LatLng(shop.getLat(), shop.getLon()));
				opt.setClickable(true);
				opt.setVisible(true);
				mapWidget.show(new Marker(opt));
			}
		});
		
		mapWidget.addAutoHidePartner(mapListingLink.getElement());
		
		deleteFavListingLink = new Anchor("Delete", "#");
		deleteFavListingLink.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				event.preventDefault();
				favoriteListingsTable.deleteUserFavoriteListing(selectedListing.getListingId());
				infoPanel.hide();
			}
		});
		
		initWidget(rootPanel);
		
		bottomPanel.showWidget(0);
	}
	
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible && !initialized) {
			initialized = true;
			initialize();
		}
	}
	
	private void initialize() {
		featuredListingsTable.loadData();
		etsySvc.getUser(new AsyncCallback<User>() {
			
			@Override
			public void onSuccess(User user) {
				EtsyExample.this.user = user;
				userLabel.setHTML("<strong>User:</strong> " + user.getLoginName());
				bottomPanel.showWidget(1);
				favoriteListingsTable.loadData(user.getUserId());
			}
			
			@Override
			public void onFailure(Throwable caught) {
				if (caught instanceof NotLoggedInException) {
					bottomPanel.showWidget(2);
				} else if (caught instanceof OAuthException) {
					bottomPanel.showWidget(3);
				} else {
					Window.alert(caught.getLocalizedMessage());
				}
			}
		});
	}

	private Widget createOAuthPanel() {
		FlowPanel panel = new FlowPanel();
		
		panel.add(new HTML("You are not connected to your Etsy account."));
		
		final Anchor requestTokenLink = new Anchor("Connect to Your Etsy Account", "#");
		panel.add(requestTokenLink);
		requestTokenLink.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String callbackURL = Window.Location.createUrlBuilder().buildString();
				String requestTokenURL = "/examples/etsy/oauth/request_token?callbackURL=" + URL.encodeQueryString(callbackURL);
				requestTokenLink.setHref(requestTokenURL);
			}
		});
		
		return new ScrollPanel(panel);
	}
	
	private void disconnectEtsyAccount() {
		etsySvc.deleteAccessTokens(new AsyncCallback<Void>() {

			@Override
			public void onSuccess(Void result) {
				bottomPanel.showWidget(3);
				EtsyExample.this.user = null;
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getLocalizedMessage());
			}
		});
	}
	
	private Image createFeaturedListingImg(final Listing listing) {
		final Image img = createListingImg(listing);
		if (img != null) {
			img.getElement().setId("listing_" + listing.getListingId());
			dragController.makeDraggable(img);
			img.addMouseOverHandler(new MouseOverHandler() {
				@Override
				public void onMouseOver(MouseOverEvent event) {
					onFeaturedListingSelected(img, listing);
				}
			});
		}

		return img;
	}
	
	private Image createFavoriteListingImg(final Listing listing) {
		final Image img = createListingImg(listing);
		if (img != null) {
			img.addMouseOverHandler(new MouseOverHandler() {
				@Override
				public void onMouseOver(MouseOverEvent event) {
					onFavoriteListingSelected(img, listing);
				}
			});
		}

		return img;
	}
	
	private Image createListingImg(Listing listing) {
		JsArray<ListingImage> images = listing.getImages();
		if (images != null && images.length() > 0) {
			Image img = new Image(images.get(0).getURL75x75());
			img.setPixelSize(75, 75);
			img.getElement().getStyle().setMargin(2d, Unit.PX);
			return img;
		}
		
		return null;
	}
	
	private void onFeaturedListingSelected(Image img, Listing listing) {
		Shop shop = listing.getShop();
		if (shop == null)
			infoGrid.setText(1, 0, null);
		else
			infoGrid.setWidget(1, 0, mapListingLink);
		
		onListingSelected(img, listing);
	}
	
	private void onFavoriteListingSelected(Image img, Listing listing) {
		FlowPanel linkPanel = new FlowPanel();
		linkPanel.add(deleteFavListingLink);
		linkPanel.add(new InlineHTML("&nbsp;|&nbsp;"));
		linkPanel.add(mapListingLink);
		infoGrid.setWidget(1, 0, linkPanel);
		onListingSelected(img, listing);
	}
	
	private void onListingSelected(Image img, Listing listing) {
		selectedListing = listing;
		infoGrid.setHTML(0, 0, "<strong>" + listing.getTitle() + "</strong>");
		infoPanel.showRelativeTo(img);
	}
	
	private class FeaturedListingsTable extends PagedTable<EtsyResponse<FeaturedListing>> {
		
		public FeaturedListingsTable(int pageSize) {
			super(pageSize);
			table.setCellPadding(0);
			table.setCellSpacing(0);
			table.setText(0, 0, "No data");
			table.getRowFormatter().removeStyleName(0, "gwt-PagedTableHeader");
		}

		@Override
		protected void reloadData(Object... args) {
			StringBuilder buf = new StringBuilder(ETSY_FEATURED_LISTINGS_ENDPOINT);
			buf.append("&limit=").append(pageSize);
			buf.append("&offset=").append(pageIndex * pageSize);
			buf.append("&includes=Listing/Images,Listing/Shop");
			JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
			jsonp.setTimeout(60000);
			jsonp.requestObject(buf.toString(), dataLoadedCallback);
		}

		@Override
		protected void dataLoaded(EtsyResponse<FeaturedListing> response) {
			if (response.isOk()) {
				resultCount = response.getCount();
				pageCount = ((resultCount - 1) / pageSize) + 1;
				
				JsArray<FeaturedListing> listings = response.getResults();
				FlowPanel panel = null;
				for (int i = 0, n = listings.length(); i < n; ++i) {
					if (i % COLS == 0) {
						panel = new FlowPanel();
						table.setWidget(i / COLS, 0, panel);
					}
					
					FeaturedListing featuredListing = listings.get(i);
					Listing listing = featuredListing.getListing();
					if (listing != null) {
						Image img = createFeaturedListingImg(listing);
						if (img != null)
							panel.add(img);
					}
				}
				
				for (int i = table.getRowCount() - 1, n = (listings.length() - 1) / COLS; i > n; --i) {
					table.removeRow(i);
				}
			} else {
				Window.alert(response.getError());
			}
		}
	}

	private class FavoriteListingsTable extends PagedTable<EtsyResponse<FavoriteListing>> {
		
		public FavoriteListingsTable(int pageSize) {
			super(pageSize);
			table.setCellPadding(0);
			table.setCellSpacing(0);
			table.setText(0, 0, "No data");
			table.getRowFormatter().removeStyleName(0, "gwt-PagedTableHeader");
		}

		@Override
		protected void reloadData(Object... args) {
			StringBuilder buf = new StringBuilder(ETSY_FAVORITE_LISTINGS_ENDPOINT.replace("${userId}", String.valueOf(args[0])));
			buf.append("&limit=").append(pageSize);
			buf.append("&offset=").append(pageIndex * pageSize);
			buf.append("&includes=Listing/Images,Listing/Shop");
			JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
			jsonp.requestObject(buf.toString(), dataLoadedCallback);
		}

		@Override
		protected void dataLoaded(EtsyResponse<FavoriteListing> response) {
			if (response.isOk()) {
				resultCount = response.getCount();
				pageCount = ((resultCount - 1) / pageSize) + 1;
				
				JsArray<FavoriteListing> listings = response.getResults();
				FlowPanel panel = null;
				for (int i = 0, n = listings.length(); i < n; ++i) {
					if (i % COLS == 0) {
						panel = new FlowPanel();
						table.setWidget(i / COLS, 0, panel);
					}
					
					FavoriteListing favoriteListing = listings.get(i);
					Listing listing = favoriteListing.getListing();
					if (listing != null) {
						Image img = createFavoriteListingImg(listing);
						if (img != null)
							panel.add(img);
					}
				}
				
				for (int i = table.getRowCount() - 1, n = (listings.length() - 1) / COLS; i > n; --i) {
					table.removeRow(i);
				}
				
				if (listings.length() == 0) {
					table.setText(0, 0, "Drag and drop your favorite listing here.");
				}
			} else {
				Window.alert(response.getError());
			}
		}

		public void createUserFavoriteListing(int listingId) {
			showLoading(true);
			etsySvc.createUserFavoriteListing(listingId, new AsyncCallback<com.mashery.examples.api.client.etsy.FavoriteListing>() {

				@Override
				public void onSuccess(com.mashery.examples.api.client.etsy.FavoriteListing result) {
					if (resultCount < 0)
						resultCount = 0;
					
					++resultCount;
					pageCount = ((resultCount - 1) / pageSize) + 1;
					pageIndex = 0;
					showLoading(true);
					reloadData(args);
				}

				@Override
				public void onFailure(Throwable caught) {
					showLoading(false);
					Window.alert(caught.getLocalizedMessage());
				}
			});
		}

		public void deleteUserFavoriteListing(int listingId) {
			showLoading(true);
			etsySvc.deleteUserFavoriteListing(listingId, new AsyncCallback<Void>() {

				@Override
				public void onSuccess(Void result) {
					if (resultCount < 0)
						resultCount = 1;
					
					--resultCount;
					pageCount = ((resultCount - 1) / pageSize) + 1;
					showLoading(true);
					reloadData(args);
				}

				@Override
				public void onFailure(Throwable caught) {
					showLoading(false);
					Window.alert(caught.getLocalizedMessage());
				}
			});
		}
	}
}
