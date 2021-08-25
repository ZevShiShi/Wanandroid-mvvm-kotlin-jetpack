package com.zevzhu.wanandroid.ext

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.http.SslError
import android.os.Build
import android.view.ViewGroup
import android.webkit.*
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.LogUtils
import com.just.agentweb.*
import com.just.agentweb.WebChromeClient
import com.just.agentweb.WebViewClient
import com.zevzhu.wanandroid.R
import com.zevzhu.wanandroid.app.appContext


private const val WEB_TAG = "WebEx"
private var mCallback: WebCallback? = null
lateinit var agentWeb: AgentWeb

/**
 * 先初始化该Web方法
 */
fun Fragment.initWeb(
    url: String,
    parentView: ViewGroup,
    callback: WebCallback = WebCallbackImpl(),
    width: Int = -1,
    height: Int = -1,
    progress: Int = 100
) {
    mCallback = callback
    if (AppUtils.isAppDebug())
        AgentWebConfig.debug()
    agentWeb = AgentWeb.with(this)
        .setAgentWebParent(parentView, -1, LinearLayout.LayoutParams(width, height))
        .closeIndicator()
        .setWebViewClient(AgentWebViewClient())
        .setWebChromeClient(AgentWebChromeClient(progress))
        .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
        .setMainFrameErrorView(R.layout.layout_error, -1)
        .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)
        .interceptUnkownUrl()
        .createAgentWeb()
        .ready()
        .go(url)
}


fun Fragment.reloadUrl() {
    agentWeb.urlLoader.reload()
}

fun Fragment.loadUrl(url: String?) {
    agentWeb.urlLoader.loadUrl(url)
}

fun Fragment.resumeWeb() {
    agentWeb.webLifeCycle.onResume()
}

fun Fragment.pauseWeb() {
    agentWeb.webLifeCycle.onPause()
}

fun Fragment.destroyWeb() {
    agentWeb.destroy()
}

fun Fragment.getWebView(): WebView {
    return agentWeb.webCreator.webView
}


fun Fragment.getCurrentUrl(): String {
    return getWebView().url!!
}


fun Fragment.goBack() {
    if (getWebView().canGoBack()) {
        getWebView().goBack()
    } else {
        nav().navigateUp()
    }
}


fun Fragment.goForward() {
    if (getWebView().canGoForward()) {
        getWebView().goForward()
    }
}


internal class AgentWebViewClient : WebViewClient() {
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun shouldOverrideUrlLoading(
        view: WebView,
        request: WebResourceRequest
    ): Boolean {
        mCallback?.onGoWebDetail(request.url.toString())
        return super.shouldOverrideUrlLoading(view, request)
    }

    override fun shouldOverrideUrlLoading(
        view: WebView,
        url: String
    ): Boolean {
        mCallback?.onGoWebDetail(url)
        return super.shouldOverrideUrlLoading(view, url)
    }

    override fun onPageStarted(
        view: WebView?,
        url: String?,
        favicon: Bitmap?
    ) {
        super.onPageStarted(view, url, favicon)
        mCallback?.pageStart(view, url)
    }

    override fun onPageFinished(view: WebView, url: String) {
        super.onPageFinished(view, url)
        //            view.loadUrl("javascript:(function(){" + "document.getElementsByTagName('body')[0].style.height = window.innerHeight+'px';" + "})()");
        // 调用js代码需要延迟1秒才能够成功调用
        if (mCallback != null && view.progress == 100) {
            mCallback?.pageEnd(view, url)
        }
    }

    override fun onReceivedError(
        view: WebView,
        request: WebResourceRequest,
        error: WebResourceError
    ) {
        super.onReceivedError(view, request, error)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            LogUtils.e(
                WEB_TAG,
                "onReceivedError====" + error.description
                    .toString() + "======" + view.progress
            )
        }
    }

    override fun onReceivedHttpError(
        view: WebView,
        request: WebResourceRequest,
        errorResponse: WebResourceResponse
    ) {
        super.onReceivedHttpError(view, request, errorResponse)
        LogUtils.e(
            WEB_TAG, "onReceivedError====" + errorResponse.reasonPhrase
        )
    }

    override fun onReceivedSslError(
        view: WebView,
        handler: SslErrorHandler,
        error: SslError
    ) {
        super.onReceivedSslError(view, handler, error)
        //              handler.proceed();// 接受所有网站的证书
    } /*错误页回调该方法 ， 如果重写了该方法， 上面传入了布局将不会显示 ， 交由开发者实现，注意参数对齐。*/ //        public void onMainFrameError(AbsAgentWebUIController agentWebUIController, WebView view, int errorCode, String description, String failingUrl) {
    //            LogUtils.d(TAG, "AgentWebFragment onMainFrameError");
    //            agentWebUIController.onMainFrameError(view, errorCode, description, failingUrl);
    //        }
}


internal class AgentWebChromeClient(private val progress: Int) :
    WebChromeClient() {
    override fun onProgressChanged(view: WebView, newProgress: Int) {
        super.onProgressChanged(view, newProgress)
        if (mCallback == null) return
        if (mCallback is WebCallbackEx) {
            LogUtils.d("onProgressChanged=====$newProgress")
            (mCallback as WebCallbackImpl).onProgress(view, newProgress)
        }
    }

    override fun getDefaultVideoPoster(): Bitmap? {
        return try {
            //这个地方是加载h5的视频列表 默认图   点击前的视频图
            BitmapFactory.decodeResource(
                appContext.resources,
                R.mipmap.ic_launcher
            )
        } catch (e: Exception) {
            super.getDefaultVideoPoster()
        }
    }

    override fun onReceivedTitle(view: WebView, title: String) {
        super.onReceivedTitle(view, title)
        LogUtils.d(
            WEB_TAG,
            "onReceivedTitle:" + title + "  view:" + view.url
        )
        if (mCallback != null && mCallback is WebCallbackEx) {
            (mCallback as WebCallbackImpl).onReceivedTitle(title, view.url)
        }
    }

}

internal open class WebCallbackImpl : WebCallbackEx {
    override fun onProgress(view: WebView?, newProgress: Int) {
    }

    override fun onReceivedTitle(title: String?, url: String?) {
    }

    override fun onGoWebDetail(url: String?) {
    }

    override fun pageStart(web: WebView?, url: String?) {
    }

    override fun pageEnd(web: WebView?, url: String?) {
    }

    override fun error(web: WebView?, msg: String?) {
    }

}

interface WebCallback {
    fun onGoWebDetail(url: String?)
    fun pageStart(web: WebView?, url: String?)
    fun pageEnd(web: WebView?, url: String?)
    fun error(web: WebView?, msg: String?)
}

interface WebCallbackEx : WebCallback {
    fun onProgress(view: WebView?, newProgress: Int)
    fun onReceivedTitle(title: String?, url: String?)
}