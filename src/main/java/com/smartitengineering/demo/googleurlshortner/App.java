package com.smartitengineering.demo.googleurlshortner;

import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Hello world!
 *
 */
public class App {

  public static void main(String[] args) {
    HttpPost request =
        new HttpPost(
        "http://goo.gl/api/url?user=toolbar@google.com&url=http%3A%2F%2Fkenai.com%2Fprojects%2Fsmart-cms%2Fpages%2FWorkspace&auth_token=764104184615");
    request.addHeader("Origin",
        "chrome-extension://iblijlcdoidgdpfknkckljiocdbnlagk");
    request.addHeader("User-Agent", "Mozilla/5.0 (X11; U; Linux i686; en-US) AppleWebKit/532.5 (KHTML, like Gecko) Chrome/4.0.249.43 Safari/532.5");
    request.addHeader("Accept", "*/*");
    request.addHeader("Accept-Encoding", "gzip,deflate,sdch");
    request.addHeader("Accept-Language", "en-US,en;q=0.8");
    request.addHeader("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.3");
    HttpClient client = new DefaultHttpClient();
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
