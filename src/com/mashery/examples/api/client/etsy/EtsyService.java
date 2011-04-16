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
package com.mashery.examples.api.client.etsy;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("etsy/etsy")
public interface EtsyService extends RemoteService {
	
	User getUser() throws EtsyException, OAuthException, NotLoggedInException;
	
	void deleteAccessTokens() throws NotLoggedInException;

	FavoriteListing createUserFavoriteListing(int listingId) throws EtsyException, OAuthException, NotLoggedInException;

	void deleteUserFavoriteListing(int listingId) throws EtsyException, OAuthException, NotLoggedInException;
}
