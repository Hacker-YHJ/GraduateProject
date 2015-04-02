package com.yanghj.newinputtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by yanghj on 3/27/15.
 * A custom LinearLayout that handles multitouch event
 * Draw a touch indicator showing trajectory
 * And send the trajectory to {@link com.yanghj.newinputtest.MultiTouchView.TrajectoryCalculator}
 */
public class MultiTouchView extends LinearLayout {

    // Hold data for active touch pointer IDs
    private SparseArray<TouchHistory> mTouches;

    private TrajectoryCalculator traCalc;

    public MultiTouchView(Context context) {
        super(context);
    }

    public MultiTouchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouches = new SparseArray<>(10);
        initialisePaint();
    }

    public MultiTouchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        traCalc = new TrajectoryCalculator();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {

        final int action = event.getAction();
        final int count = event.getPointerCount();

        /*
         * Switch on the action. The action is extracted from the event by
         * applying the MotionEvent.ACTION_MASK. Alternatively a call to
         * event.getActionMasked() would yield in the action as well.
         */
        switch (action & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN: {
                // first pressed gesture has started

                /*
                 * Only one touch event is stored in the MotionEvent. Extract
                 * the pointer identifier of this touch from the first index
                 * within the MotionEvent object.
                 */
                int id = event.getPointerId(0);

                TouchHistory data = TouchHistory.obtain(event.getX(0), event.getY(0),
                        event.getPressure(0));
                data.label = "id: " + 0;

                /*
                 * Store the data under its pointer identifier. The pointer
                 * number stays consistent for the duration of a gesture,
                 * accounting for other pointers going up or down.
                 */
                mTouches.put(id, data);

                break;
            }

            case MotionEvent.ACTION_POINTER_DOWN: {
                /*
                 * A non-primary pointer has gone down, after an event for the
                 * primary pointer (ACTION_DOWN) has already been received.
                 */

                /*
                 * The MotionEvent object contains multiple pointers. Need to
                 * extract the index at which the data for this particular event
                 * is stored.
                 */
                int index = event.getActionIndex();
                int id = event.getPointerId(index);

                TouchHistory data = TouchHistory.obtain(event.getX(index), event.getY(index),
                        event.getPressure(index));
                data.label = "id: " + id;

                /*
                 * Store the data under its pointer identifier. The index of
                 * this pointer can change over multiple events, but this
                 * pointer is always identified by the same identifier for this
                 * active gesture.
                 */
                mTouches.put(id, data);

                break;
            }

            case MotionEvent.ACTION_UP: {
                /*
                 * Final pointer has gone up and has ended the last pressed
                 * gesture.
                 */

                /*
                 * Extract the pointer identifier for the only event stored in
                 * the MotionEvent object and remove it from the list of active
                 * touches.
                 */
                int id = event.getPointerId(0);
                TouchHistory data = mTouches.get(id);
                mTouches.remove(id);
                traCalc.calc(data);
                data.recycle();

                break;
            }

            case MotionEvent.ACTION_POINTER_UP: {
                /*
                 * A non-primary pointer has gone up and other pointers are
                 * still active.
                 */

                /*
                 * The MotionEvent object contains multiple pointers. Need to
                 * extract the index at which the data for this particular event
                 * is stored.
                 */
                int index = event.getActionIndex();
                int id = event.getPointerId(index);

                TouchHistory data = mTouches.get(id);
                mTouches.remove(id);
                data.recycle();

                break;
            }

            case MotionEvent.ACTION_MOVE: {
                /*
                 * A change event happened during a pressed gesture. (Between
                 * ACTION_DOWN and ACTION_UP or ACTION_POINTER_DOWN and
                 * ACTION_POINTER_UP)
                 */

                /*
                 * Loop through all active pointers contained within this event.
                 * Data for each pointer is stored in a MotionEvent at an index
                 * (starting from 0 up to the number of active pointers). This
                 * loop goes through each of these active pointers, extracts its
                 * data (position and pressure) and updates its stored data. A
                 * pointer is identified by its pointer number which stays
                 * constant across touch events as long as it remains active.
                 * This identifier is used to keep track of a pointer across
                 * events.
                 */
                for (int index = 0; index < count; index++) {
                    // get pointer id for data stored at this index
                    int id = event.getPointerId(index);

                    // get the data stored externally about this pointer.
                    TouchHistory data = mTouches.get(id);

                    // add previous position to history and add new values
                    data.addHistory(data.x, data.y);
                    data.setTouch(event.getX(index), event.getY(index),
                            event.getPressure(index));

                }

                break;
            }
        }

        // trigger redraw on UI thread
        this.postInvalidate();

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(BACKGROUND_ACTIVE);

        // loop through all active touches and draw them
        for (int i = 0; i < mTouches.size(); i++) {

            // get the pointer id and associated data for this index
            int id = mTouches.keyAt(i);
            TouchHistory data = mTouches.valueAt(i);

            // draw the data and its history to the canvas
            drawCircle(canvas, id, data);
        }
    }

    /*
     * Below are only helper methods and variables required for drawing.
     */

    // radius of active touch circle in dp
    private static final float CIRCLE_RADIUS_DP = 75f;
    // radius of historical circle in dp
    private static final float CIRCLE_HISTORICAL_RADIUS_DP = 7f;

    // calculated radiuses in px
    private float mCircleHistoricalRadius;

    private Paint mCirclePaint = new Paint();
    private Paint mTextPaint = new Paint();

    private static final int BACKGROUND_ACTIVE = Color.TRANSPARENT;

    // inactive border
    private static final float INACTIVE_BORDER_DP = 15f;
    private static final int INACTIVE_BORDER_COLOR = 0xFFffd060;
    private Paint mBorderPaint = new Paint();
    private float mBorderWidth;

    public final int[] COLORS = {
            0xFF33B5E5, 0xFFAA66CC, 0xFF99CC00, 0xFFFFBB33, 0xFFFF4444,
            0xFF0099CC, 0xFF9933CC, 0xFF669900, 0xFFFF8800, 0xFFCC0000
    };

    /**
     * Sets up the required {@link android.graphics.Paint} objects for the screen density of this
     * device.
     */
    private void initialisePaint() {

        // Calculate radiuses in px from dp based on screen density
        float density = getResources().getDisplayMetrics().density;
        mCircleHistoricalRadius = CIRCLE_HISTORICAL_RADIUS_DP * density;

        // Setup text paint for circle label
        mTextPaint.setTextSize(27f);
        mTextPaint.setColor(Color.BLACK);

        // Setup paint for inactive border
        mBorderWidth = INACTIVE_BORDER_DP * density;
        mBorderPaint.setStrokeWidth(mBorderWidth);
        mBorderPaint.setColor(INACTIVE_BORDER_COLOR);
        mBorderPaint.setStyle(Paint.Style.STROKE);

    }

    /**
     * Draws the data encapsulated by a {@link MultiTouchView.TouchHistory} object to a canvas.
     * A large circle indicates the current position held by the
     * {@link MultiTouchView.TouchHistory} object, while a smaller circle is drawn for each
     * entry in its history. The size of the large circle is scaled depending on
     * its pressure, clamped to a maximum of <code>1.0</code>.
     *
     * @param canvas
     * @param id
     * @param data
     */
    protected void drawCircle(Canvas canvas, int id, TouchHistory data) {
        // select the color based on the id
        int color = COLORS[id % COLORS.length];
        mCirclePaint.setColor(color);

        /*
         * Draw the circle, size scaled to its pressure. Pressure is clamped to
         * 1.0 max to ensure proper drawing. (Reported pressure values can
         * exceed 1.0, depending on the calibration of the touch screen).
         */
//        float pressure = Math.min(data.pressure, 1f);
//        float radius = pressure * mCircleRadius;

        canvas.drawCircle(data.x, data.y, mCircleHistoricalRadius,
                mCirclePaint);
        if (traCalc.test(data.x, data.y)) {
            Log.d("DOT", String.format("x = %f, y = %f", data.x, data.y));
        }

        // draw all historical points with a lower alpha value
        mCirclePaint.setAlpha(125);
        for (int j = 0; j < data.history.length && j < data.historyCount; j++) {
            PointF p = data.history[j];
            canvas.drawCircle(p.x, p.y, mCircleHistoricalRadius, mCirclePaint);
        }

        // draw its label next to the main circle
//        canvas.drawText(data.label, data.x + radius, data.y
//                - radius, mTextPaint);
    }

    /**
     * Touch History, aka Trajectory
     */
    static final class TouchHistory {

        // number of historical points to store
        public static final int HISTORY_COUNT = 20;

        public float x;
        public float y;
        public float pressure = 0f;
        public String label = null;

        // current position in history array
        public int historyIndex = 0;
        public int historyCount = 0;

        // array of pointer position history
        public PointF[] history = new PointF[HISTORY_COUNT];

        private static final int MAX_POOL_SIZE = 10;
        private static final Pools.SimplePool<TouchHistory> sPool =
                new Pools.SimplePool<>(MAX_POOL_SIZE);

        public static TouchHistory obtain(float x, float y, float pressure) {
            TouchHistory data = sPool.acquire();
            if (data == null) {
                data = new TouchHistory();
            }

            data.setTouch(x, y, pressure);

            return data;
        }

        public TouchHistory() {

            // initialise history array
            for (int i = 0; i < HISTORY_COUNT; i++) {
                history[i] = new PointF();
            }
        }

        public void setTouch(float x, float y, float pressure) {
            this.x = x;
            this.y = y;
            this.pressure = pressure;
        }

        public void recycle() {
            this.historyIndex = 0;
            this.historyCount = 0;
            sPool.release(this);
        }

        /**
         * Add a point to its history. Overwrites oldest point if the maximum
         * number of historical points is already stored.
         *
         * @param
         */
        public void addHistory(float x, float y) {
            PointF p = history[historyIndex];
            p.x = x;
            p.y = y;

            historyIndex = (historyIndex + 1) % history.length;

            if (historyCount < HISTORY_COUNT) {
                historyCount++;
            }
        }

    }

    final class TrajectoryCalculator {
        private TextView btn_u;
        private TextView btn_i;
        private TextView btn_e;
        private TextView btn_a;
        private TextView btn_o;
        private TextView btn_n;
        private TextView btn_g;

        private YunRect rect_u;
        private YunRect rect_i;
        private YunRect rect_e;
        private YunRect rect_a;
        private YunRect rect_o;
        private YunRect rect_n;
        private YunRect rect_g;

        private YunRect[][] trajectories;
        public TrajectoryCalculator() {
            getTextViews();
        }

        /**
         * Calculate the touch history and translate the trajectory into correct 韵母
         * @param data history points
         * @return 韵母 or null
         */
        public String calc(TouchHistory data) {
            if (null == trajectories) buildTrajectory();

            YunRect easyDetect = isInSameRect(data.history[0], data.history[data.historyCount-1]);
            if (null != easyDetect) {
                return easyDetect.yun;
            }
            else {
                YunRect sR = locate(data.history[0]);
                YunRect eR = locate(data.history[data.historyCount-1]);
            }
            return null;
        }

        public boolean test(float x, float y) {
            return rect_u.contains((int)x, (int)y);
        }

        /**
         * If two point end up within one same TestView, return the YunRect
         * else return null
         * @param x point 1
         * @param y point 2
         * @return YunRect or null
         */
        private YunRect isInSameRect(PointF x, PointF y) {
            if (x.equals(y)) return locate(x);

            YunRect x_on = locate(x);
            YunRect y_on = locate(y);

            if (x_on.equals(y_on)) {
                return x_on;
            }
            else return null;
        }

        /**
         * find out which TextView a point is on
         * @param x the point
         * @return which
         */
        private YunRect locate(PointF x) {
            if (rect_a.contains(x)) {
                return rect_a;
            }
            else if (rect_e.contains(x)) {
                return rect_e;
            }
            else if (rect_i.contains(x)) {
                return rect_i;
            }
            else if (rect_o.contains(x)) {
                return rect_o;
            }
            else if (rect_u.contains(x)) {
                return rect_u;
            }
            else if (rect_n.contains(x)) {
                return rect_n;
            }
            else if (rect_g.contains(x)) {
                return rect_g;
            }
            else {
                throw new RuntimeException("not in any rect");
            }
        }

        /**
         * Find every TextView and their bounding rectangle
         * when their position is determined
         */
        private void getTextViews() {
            btn_u = (TextView)findViewById(R.id.char_u);
            btn_i = (TextView)findViewById(R.id.char_i);
            btn_e = (TextView)findViewById(R.id.char_e);
            btn_a = (TextView)findViewById(R.id.char_a);
            btn_o = (TextView)findViewById(R.id.char_o);
            btn_n = (TextView)findViewById(R.id.char_n);
            btn_g = (TextView)findViewById(R.id.char_g);

            btn_u.addOnLayoutChangeListener(new OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    rect_u = new YunRect(left, top, right, bottom);
                    rect_u.setYun(InputManager.Y_U);
                }
            });

            btn_i.addOnLayoutChangeListener(new OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    rect_i = new YunRect(left, top, right, bottom);
                    rect_i.setYun(InputManager.Y_I);
                }
            });

            btn_e.addOnLayoutChangeListener(new OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    rect_e = new YunRect(left, top, right, bottom);
                    rect_e.setYun(InputManager.Y_E);
                }
            });

            btn_a.addOnLayoutChangeListener(new OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    rect_a = new YunRect(left, top, right, bottom);
                    rect_a.setYun(InputManager.Y_A);
                }
            });

            btn_o.addOnLayoutChangeListener(new OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    rect_o = new YunRect(left, top, right, bottom);
                    rect_o.setYun(InputManager.Y_O);
                }
            });

            btn_n.addOnLayoutChangeListener(new OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    rect_n = new YunRect(left, top, right, bottom);
                    rect_u.setYun(null);
                }
            });

            btn_g.addOnLayoutChangeListener(new OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    rect_g = new YunRect(left, top, right, bottom);
                    rect_g.setYun(null);
                }
            });
        }

        /**
         * write all trajectories into an two-dimensional array
         */
        private void buildTrajectory() {
            trajectories = new YunRect[26][];

            // ai
            trajectories[0] = new YunRect[2];
            trajectories[0][0] = rect_a;
            trajectories[0][1] = rect_i;

            // ao
            trajectories[1] = new YunRect[2];
            trajectories[1][0] = rect_a;
            trajectories[1][1] = rect_o;

            // an
            trajectories[2] = new YunRect[2];
            trajectories[2][0] = rect_a;
            trajectories[2][1] = rect_n;

            // ang
            trajectories[3] = new YunRect[3];
            trajectories[3][0] = rect_a;
            trajectories[3][1] = rect_n;
            trajectories[3][2] = rect_g;

            // ei
            trajectories[4] = new YunRect[2];
            trajectories[4][0] = rect_e;
            trajectories[4][1] = rect_i;

            // en
            trajectories[5] = new YunRect[2];
            trajectories[5][0] = rect_e;
            trajectories[5][1] = rect_n;

            // eng
            trajectories[6] = new YunRect[3];
            trajectories[6][0] = rect_e;
            trajectories[6][1] = rect_n;
            trajectories[6][2] = rect_g;

            // in
            trajectories[7] = new YunRect[2];
            trajectories[7][0] = rect_i;
            trajectories[7][1] = rect_n;

            // ing
            trajectories[8] = new YunRect[3];
            trajectories[8][0] = rect_i;
            trajectories[8][1] = rect_n;
            trajectories[8][2] = rect_g;

            // ia
            trajectories[9] = new YunRect[2];
            trajectories[9][0] = rect_i;
            trajectories[9][1] = rect_a;

            // ian
            trajectories[10] = new YunRect[3];
            trajectories[10][0] = rect_i;
            trajectories[10][1] = rect_a;
            trajectories[10][2] = rect_n;

            // iang
            trajectories[11] = new YunRect[4];
            trajectories[11][0] = rect_i;
            trajectories[11][1] = rect_a;
            trajectories[11][2] = rect_n;
            trajectories[11][3] = rect_g;

            // iao
            trajectories[12] = new YunRect[3];
            trajectories[12][0] = rect_i;
            trajectories[12][1] = rect_a;
            trajectories[12][2] = rect_o;

            // ie
            trajectories[13] = new YunRect[2];
            trajectories[13][0] = rect_i;
            trajectories[13][1] = rect_e;

            // iu
            trajectories[14] = new YunRect[2];
            trajectories[14][0] = rect_i;
            trajectories[14][1] = rect_u;

            // iong
            trajectories[15] = new YunRect[4];
            trajectories[15][0] = rect_i;
            trajectories[15][1] = rect_o;
            trajectories[15][2] = rect_n;
            trajectories[15][3] = rect_g;

            // ou
            trajectories[16] = new YunRect[2];
            trajectories[16][0] = rect_o;
            trajectories[16][1] = rect_u;

            // ong
            trajectories[17] = new YunRect[3];
            trajectories[17][0] = rect_o;
            trajectories[17][1] = rect_n;
            trajectories[17][2] = rect_g;

            // ua
            trajectories[18] = new YunRect[2];
            trajectories[18][0] = rect_u;
            trajectories[18][1] = rect_a;

            // uan
            trajectories[19] = new YunRect[3];
            trajectories[19][0] = rect_u;
            trajectories[19][1] = rect_a;
            trajectories[19][2] = rect_n;

            // uang
            trajectories[20] = new YunRect[4];
            trajectories[20][0] = rect_u;
            trajectories[20][1] = rect_a;
            trajectories[20][2] = rect_n;
            trajectories[20][3] = rect_g;

            // uai
            trajectories[21] = new YunRect[3];
            trajectories[21][0] = rect_u;
            trajectories[21][1] = rect_a;
            trajectories[21][2] = rect_i;

            // ue
            trajectories[22] = new YunRect[2];
            trajectories[22][0] = rect_u;
            trajectories[22][1] = rect_e;

            // ui
            trajectories[23] = new YunRect[2];
            trajectories[23][0] = rect_u;
            trajectories[23][1] = rect_i;

            // uo
            trajectories[24] = new YunRect[2];
            trajectories[24][0] = rect_u;
            trajectories[24][1] = rect_o;

            // un
            trajectories[25] = new YunRect[2];
            trajectories[25][0] = rect_u;
            trajectories[25][1] = rect_n;
        }
    }
}
