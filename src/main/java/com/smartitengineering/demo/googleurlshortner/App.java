package com.smartitengineering.demo.googleurlshortner;

import java.io.InputStream;
import java.net.URLEncoder;
import java.util.zip.GZIPInputStream;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

/**
 * Hello world!
 *
 */
public class App {

  public static void main(String[] args)
      throws Exception {
    String url =
        "http://coding.derkeiler.com/Archive/General/comp.programming/2006-11/msg00386.html";
    String encodedUrl = URLEncoder.encode(url, "utf-8");
    InputStream hackScript = App.class.getClassLoader().getResourceAsStream(
        "hackScript.js");
    String hackScriptStr;
    try {
      hackScriptStr = IOUtils.toString(hackScript);
    }
    catch (Exception ex) {
      ex.printStackTrace();
      hackScriptStr = null;
    }
    System.out.println("HackString: " + hackScriptStr);
    String authToken;
    if (hackScript != null) {
      Context context = Context.enter();
      Scriptable scriptable = context.initStandardObjects();
      String invokable =
          hackScriptStr
          + "\r\ngetAuthToken(\""+url+"\");";
      Object result = context.evaluateString(scriptable, invokable, "<cmd>", 1,
          null);
      authToken = result.toString();
      System.out.println("RESULT: " + authToken);
    }
    else {
      return;
    }
    HttpPost request =
        new HttpPost(
        "http://goo.gl/api/url?user=toolbar@google.com&url=" + encodedUrl
        + "&auth_token="+authToken);
    request.addHeader("Origin",
        "chrome-extension://iblijlcdoidgdpfknkckljiocdbnlagk");
    request.addHeader("User-Agent",
        "Mozilla/5.0 (X11; U; Linux i686; en-US) AppleWebKit/532.5 (KHTML, like Gecko) Chrome/4.0.249.43 Safari/532.5");
    request.addHeader("Accept", "*/*");
    request.addHeader("Accept-Encoding", "gzip,deflate,sdch");
    request.addHeader("Accept-Language", "en-US,en;q=0.8");
    request.addHeader("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.3");

    HttpClient client = new DefaultHttpClient();
    if (true) {
      try {
        HttpResponse response = client.execute(request);
        System.out.println("Response Code: " + response.getStatusLine().
            getStatusCode());
        HttpEntity entity = response.getEntity();
        InputStream input = entity.getContent();
        String output = IOUtils.toString(new GZIPInputStream(input), "utf-8");
        System.out.println("Encoding: " + entity.getContentEncoding());
        System.out.println("OUTPUT CT: " + entity.getContentType().toString());
        System.out.println("OUTPUT: " + output);
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }
}
