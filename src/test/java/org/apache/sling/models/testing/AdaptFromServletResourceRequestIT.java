/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.sling.models.testing;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.osgi.services.HttpClientBuilderFactory;
import org.apache.sling.junit.Activator;
import org.apache.sling.junit.rules.TeleporterRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class AdaptFromServletResourceRequestIT {

    @Rule
    public final TeleporterRule teleporter = TeleporterRule.forClass(getClass(), "SM_Teleporter");

    private String httpEndpoint;
    private HttpClientBuilderFactory httpBuilderFactory;

    @Before
    public void setUp() throws Exception {
        BundleContext bundleContext = Activator.getBundleContext();
        ServiceReference ref = bundleContext.getServiceReference("org.osgi.service.http.HttpService");
        httpEndpoint = ((String[]) ref.getProperty("osgi.http.endpoint"))[0];
        httpBuilderFactory = teleporter.getService(HttpClientBuilderFactory.class);
    }

    @Test
    public void testRequestWithParameter() throws IOException {
        requestAndAssertStatus(httpEndpoint + "apps/rtpickerrequest?foo=bar", 200);
    }

    @Test
    public void testRequestWithout() throws IOException {
        requestAndAssertStatus(httpEndpoint + "apps/rtpickerrequest", 400);
    }

    private void requestAndAssertStatus(String url, int status) throws IOException {
        HttpGet get = new HttpGet(url);
        CloseableHttpClient client = httpBuilderFactory.newBuilder().build();
        CloseableHttpResponse response = client.execute(get);
        assertEquals(status, response.getStatusLine().getStatusCode());
        response.close();
        client.close();
    }
}
