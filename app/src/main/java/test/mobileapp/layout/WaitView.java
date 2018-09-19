package test.mobileapp.layout;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.github.ybq.android.spinkit.SpinKitView;

import test.mobileapp.R;

public class WaitView  extends RelativeLayout {
    Context context;
    RelativeLayout relativeLayout;
    SpinKitView waitIcon;
    WaitViewHandler handler = null;
    LinearLayout invisibleBackground;
    public WaitView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public WaitView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }
    void init()
    {
        View rootView = inflate(context, R.layout.view_wait, this);
        relativeLayout = rootView.findViewById(R.id.view_wait_rl);
        waitIcon = rootView.findViewById(R.id.view_wait_icon);
        invisibleBackground = rootView.findViewById(R.id.wait_view_invisible_background);
        relativeLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void invisibleBackground()
    {
        invisibleBackground.setVisibility(VISIBLE);
        waitIcon.setColor(Color.rgb(111, 188, 255));
    }

    public void setHandler(WaitViewHandler handler) {
        this.handler = handler;
    }

    public void enable()
    {
        relativeLayout.setVisibility(VISIBLE);
    }

    public void finishOnSuccess()
    {
        relativeLayout.setVisibility(GONE);
        if(handler != null)
            handler.onSuccess();
    }
    public void finishOnFailure()
    {
        relativeLayout.setVisibility(GONE);
        if(handler != null)
            handler.onFailure();
    }

    public interface WaitViewHandler
    {
        void onSuccess();
        void onFailure();
    }
}
