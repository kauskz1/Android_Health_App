package com.kaustubh.fisac;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import androidx.appcompat.app.AppCompatActivity;

public class DietGuidelinesActivity extends AppCompatActivity {
    private WebView pdfView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_guidelines);

        pdfView = findViewById(R.id.dietWvPdf);
        String pdfUrl = "https://drive.google.com/file/d/1XO3N8TezX6OvkGViLTLDVDUZCFRTH1eS/view?usp=sharing";
        setupWebViewWithUrl(pdfView,pdfUrl);
    }

    private void setupWebViewWithUrl(WebView webView, String url) {
        if (webView != null) {
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setLoadWithOverviewMode(true);
            webSettings.setUseWideViewPort(true);

            webView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    return false;
                }
            });
            webView.setWebChromeClient(new WebChromeClient() {});
            String htmlContent = getPDFHtml(url);
            webView.loadData(htmlContent, "text/html", "utf-8");
        }
    }
    private String getPDFHtml(String url) {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, user-scalable=no\">\n" +
                "    <style>\n" +
                "        body, html {\n" +
                "            margin: 0;\n" +
                "            height: 100%;\n" +
                "            overflow: hidden;\n" +
                "        }\n" +
                "        iframe {\n" +
                "            position: absolute;\n" +
                "            top: 0;\n" +
                "            left: 0;\n" +
                "            width: 100%;\n" +
                "            height: 100%;\n" +
                "            border: none;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <iframe src=\"" + url + "\" allow=\"autoplay\"></iframe>\n" +
                "</body>\n" +
                "</html>";
    }
}