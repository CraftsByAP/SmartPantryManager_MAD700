package com.example.smartpantrymanager_mad700;
import android.app.Activity;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.view.*;
import android.widget.*;
import android.text.*;
import android.text.style.*;
import java.math.*;
import java.util.*;

//This file creates my chosen UI for this app. Glassmorphism
    final class Ui {
    static final int TEXT = Color.rgb(246, 246, 255);
    static final int MUTED = Color.rgb(190, 198, 222);
    static final int ACCENT = Color.rgb(190, 177, 255);
    static final int MINT = Color.rgb(139, 230, 215);
    private static final int NAVY = Color.rgb(15, 21, 43);
        private Ui() {}
    static int dp(Activity activity, int value) {
        return Math.round(value * activity.getResources().getDisplayMetrics().density);
    }
    //This creates a new drawable per view
    static GradientDrawable glass(Activity activity, int radius) {
        GradientDrawable background = new GradientDrawable(
                GradientDrawable.Orientation.TL_BR,
                new int[]{
                        0x303F4973, 0x18566083
                });
        background.setCornerRadius(dp(activity, radius));
        background.setStroke(dp(activity, 1), 0x40E1DEFF);
        return background;
    }

    static LinearLayout column(Activity activity) {
        LinearLayout column = new LinearLayout(activity) {
            @Override public void onViewAdded(View child) {
                super.onViewAdded(child);
                styleChild(activity, child);
            }
        };
        column.setOrientation(LinearLayout.VERTICAL);
        column.setPadding(dp(activity, 20), dp(activity, 18),
                dp(activity, 20), dp(activity, 12));
        column.setBackground(new Atmosphere());
        activity.getWindow().setStatusBarColor(NAVY);
        activity.getWindow().setNavigationBarColor(NAVY);
        activity.getWindow().getDecorView().setSystemUiVisibility(0);
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // Keeping controls clear of system bars.
        column.setOnApplyWindowInsetsListener((view, insets) -> {
            if (!(view.getParent() instanceof ScrollView)) {
                view.setPadding(dp(activity, 20), dp(activity, 18) + insets.getSystemWindowInsetTop(),
                        dp(activity, 20), dp(activity, 12) + insets.getSystemWindowInsetBottom());
                return insets.consumeSystemWindowInsets();
            }
            return insets;
        });
        column.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override public void onViewAttachedToWindow(View view) {
                view.requestApplyInsets();
            }
            @Override public void onViewDetachedFromWindow(View view) { }
        });
        return column;
    }

    static LinearLayout.LayoutParams space(Activity activity) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -2);
        params.bottomMargin = dp(activity, 12);
        return params;
    }

    static TextView text(Activity activity, String value, int size) {
        TextView label = new TextView(activity);
        label.setText(value);
        label.setTextSize(size);
        label.setTextColor(size >= 18 ? TEXT : MUTED);
        label.setFontFeatureSettings("kern");
        label.setLineSpacing(dp(activity, 3), 1.05f);
        label.setPadding(dp(activity, 3), dp(activity, 7), dp(activity, 3), dp(activity, 7));
        return label;
    }

    static TextView text(Activity activity, String value) {
            return text(activity, value, 16);
        }

    static TextView title(Activity activity, String value) {
        String lower = value.toLowerCase(Locale.ROOT);
        String heading = value;
        if (lower.equals("my pantry") || lower.equals("the pantry")) heading = "Your pantry";
        if (lower.equals("suggested recipes")) heading = "Ready to cook";
        if (lower.equals("add ingredient")) heading = "New ingredient";
        String overline = "SMART PANTRY";
        SpannableString title = new SpannableString(overline + "\n" + heading);
        title.setSpan(new AbsoluteSizeSpan(11, true), 0, overline.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        title.setSpan(new ForegroundColorSpan(MINT), 0, overline.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        TextView label = text(activity, "", 28);
        label.setText(title);
        label.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        label.setPadding(0, dp(activity, 6), 0, dp(activity, 15));
        label.setLineSpacing(dp(activity, 8), 1);
        return label;
    }

    static Button button(Activity activity, String value, Runnable action) {
        Button button = new Button(activity);
        button.setText(value);
        button.setTextSize(14);
        button.setAllCaps(false);
        button.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        button.setMinHeight(dp(activity, 52));
        button.setMinimumHeight(dp(activity, 52));
        button.setPadding(dp(activity, 14), dp(activity, 9), dp(activity, 14), dp(activity, 9));
        button.setBackgroundTintList(null);
        String lower = value.toLowerCase(Locale.ROOT);
        GradientDrawable background;
        if (lower.contains("delete")) {
            background = glass(activity, 17);
            background.setStroke(dp(activity, 1), 0x80FF9EAB);
            button.setTextColor(0xFFFFB6C2);
        } else if (lower.contains("cancel") || lower.contains("back")) {
            background = glass(activity, 17);
            button.setTextColor(TEXT);
        } else {
            background = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
                    new int[]{0xFFC2B2FF, 0xFF9DBBF5});
            background.setCornerRadius(dp(activity, 17));
            button.setTextColor(NAVY);
        }
        button.setBackground(new RippleDrawable(ColorStateList.valueOf(0x40FFFFFF), background, null));
        button.setElevation(0);
        button.setOnClickListener(view -> action.run());
        return button;
    }

    private static void styleChild(Activity activity, View child) {
        if (child instanceof EditText) {
            EditText input = (EditText) child;
            input.setTextColor(TEXT);
            input.setHintTextColor(MUTED);
            input.setTextSize(15);
            input.setBackgroundTintList(null);
            input.setBackground(glass(activity, 16));
            input.setPadding(dp(activity, 16), dp(activity, 13), dp(activity, 16), dp(activity, 13));
            input.setMinimumHeight(dp(activity, 55));
            input.setSingleLine(true);
            input.setSelectAllOnFocus(true);
        } else if (child instanceof Spinner) {
            Spinner spinner = (Spinner) child;
            SpinnerAdapter existing = spinner.getAdapter();
            ArrayList<String> units = new ArrayList<>();
            if (existing != null) {
                for (int i = 0; i < existing.getCount(); i++) units.add(String.valueOf(existing.getItem(i)));
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,
                        android.R.layout.simple_spinner_item, units) {
                    @Override
                    public View getView(int position, View recycled, ViewGroup parent) {
                        TextView label = Ui.text(activity, getItem(position) + "   \u25BE", 15);
                        label.setTextColor(TEXT);
                        label.setPadding(dp(activity, 16), dp(activity, 13), dp(activity, 16), dp(activity, 13));
                        return label;
                    }
                    @Override
                    public View getDropDownView(int position, View recycled, ViewGroup parent) {
                        TextView label = Ui.text(activity, getItem(position), 16);
                        label.setTextColor(TEXT);
                        label.setPadding(dp(activity, 18), dp(activity, 13), dp(activity, 18), dp(activity, 13));
                        return label;
                    }
                };
                int selected = spinner.getSelectedItemPosition();
                spinner.setAdapter(adapter);
                if (selected >= 0 && selected < units.size()) spinner.setSelection(selected);
            }
            spinner.setBackground(glass(activity, 16));
            GradientDrawable popup = new GradientDrawable();
            popup.setColor(0xFF252E4B);
            popup.setCornerRadius(dp(activity, 12));
            spinner.setPopupBackgroundDrawable(popup);
        } else if (child instanceof ListView) {
            ListView list = (ListView) child;
            list.setDivider(null);
            list.setCacheColorHint(Color.TRANSPARENT);
            list.setSelector(android.R.color.transparent);
            list.setPadding(0, dp(activity, 5), 0, dp(activity, 10));
            list.setClipToPadding(false);
        } else if (child instanceof Switch) {
            Switch toggle = (Switch) child;
            toggle.setTextColor(TEXT);
            toggle.setTextSize(15);
            toggle.setPadding(dp(activity, 14), dp(activity, 16), dp(activity, 14), dp(activity, 16));
            toggle.setBackground(glass(activity, 16));
            toggle.setThumbTintList(new ColorStateList(
                    new int[][]{new int[]{android.R.attr.state_checked}, new int[]{}},
                    new int[]{MINT, MUTED}));
            toggle.setTrackTintList(ColorStateList.valueOf(0x665F7299));
        }
    }

    static void nav(Activity activity, LinearLayout root) {
        LinearLayout bar = new LinearLayout(activity);
        bar.setOrientation(LinearLayout.HORIZONTAL);
        bar.setGravity(Gravity.CENTER);
        bar.setPadding(dp(activity, 5), dp(activity, 5), dp(activity, 5), dp(activity, 5));
        bar.setBackground(glass(activity, 22));
        String[] labels = {"Pantry", "Recipes", "Settings"};
        Class<?>[] screens = {PantryActivity.class, suggestionsRecipe.class, pantrySettings.class};
        for (int i = 0; i < labels.length; i++) {
            final Class<?> screen = screens[i];
            boolean active = activity.getClass() == screen;
            TextView tab = text(activity, labels[i], 12);
            tab.setGravity(Gravity.CENTER);
            tab.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
            tab.setTextColor(active ? NAVY : MUTED);
            tab.setCompoundDrawablePadding(dp(activity, 3));
            NavigationIcon icon = new NavigationIcon(i, active ? NAVY : MUTED);
            icon.setBounds(0, 0, dp(activity, 20), dp(activity, 20));
            tab.setCompoundDrawables(null, icon, null, null);
            GradientDrawable surface = new GradientDrawable();
            surface.setColor(active ? ACCENT : Color.TRANSPARENT);
            surface.setCornerRadius(dp(activity, 17));
            tab.setBackground(new RippleDrawable(ColorStateList.valueOf(0x30FFFFFF), surface, null));
            tab.setOnClickListener(view -> open(activity, screen));
            LinearLayout.LayoutParams cell = new LinearLayout.LayoutParams(0, dp(activity, 60), 1);
            cell.setMargins(dp(activity, 2), 0, dp(activity, 2), 0);
            bar.addView(tab, cell);
        }
        LinearLayout.LayoutParams params = space(activity);
        params.topMargin = dp(activity, 10);
        params.bottomMargin = 0;
        root.addView(bar, params);
    }

    static void open(Activity activity, Class<?> screen) {
        if (activity.getClass() != screen) {
            Intent intent = new Intent(activity, screen);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            activity.startActivity(intent);
            activity.finish();
        }
    }

    static void scrolled(Activity activity, LinearLayout root, LinearLayout content) {
        //Method panel is made transparent.
        content.setBackgroundColor(Color.TRANSPARENT);
        content.setPadding(0, 0, 0, dp(activity, 16));
        ScrollView scroll = new ScrollView(activity);
        scroll.setFillViewport(true);
        scroll.addView(content);
        root.addView(scroll, new LinearLayout.LayoutParams(-1, 0, 1));
    }

    static String amount(double number) {
        return BigDecimal.valueOf(number).stripTrailingZeros().toPlainString();
    }

    // Used by suggestionsRecipe to change black-text ArrayAdapter.
    static ArrayAdapter<String> recipeAdapter(Activity activity, List<String> names) {
        return new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, names) {
            @Override public View getView(int position, View recycled, ViewGroup parent) {
                LinearLayout card = new LinearLayout(activity);
                card.setOrientation(LinearLayout.VERTICAL);
                card.setPadding(dp(activity, 17), dp(activity, 12), dp(activity, 17), dp(activity, 12));
                card.setBackground(glass(activity, 20));
                TextView badge = text(activity, "READY TO COOK", 10);
                badge.setTextColor(MINT);
                card.addView(badge);
                TextView name = text(activity, getItem(position), 19);
                name.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
                card.addView(name);
                card.addView(text(activity, "All ingredients available  \u00B7  View recipe \u2192", 12));
                LinearLayout shell = new LinearLayout(activity);
                shell.setPadding(0, 0, 0, dp(activity, 12));
                shell.addView(card, new LinearLayout.LayoutParams(-1, -2));
                return shell;
            }
        };
    }

    /** Painted gradients create the atmosphere without images or a blur dependency. */
    private static final class Atmosphere extends Drawable {
        private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        @Override public void draw(Canvas canvas) {
            float width = getBounds().width(), height = getBounds().height();
            if (width <= 0 || height <= 0) return;
            canvas.drawColor(NAVY);
            glow(canvas, width * 0.94f, height * 0.05f, width * 0.90f, 0x806D54B8);
            glow(canvas, width * 0.02f, height * 0.60f, width * 0.72f, 0x552A8E9B);
            glow(canvas, width * 0.95f, height * 0.98f, width * 0.85f, 0x505B469D);
            paint.setShader(null);
        }
        private void glow(Canvas canvas, float x, float y, float radius, int color) {
            paint.setShader(new RadialGradient(x, y, radius, color, Color.TRANSPARENT, Shader.TileMode.CLAMP));
            canvas.drawRect(getBounds(), paint);
        }
        @Override public void setAlpha(int alpha) { }
        @Override public void setColorFilter(ColorFilter filter) { }
        @Override public int getOpacity() { return PixelFormat.OPAQUE; }
    }

    private static final class NavigationIcon extends Drawable {
        private final int kind;
        private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        NavigationIcon(int kind, int color) {
            this.kind = kind;
            paint.setColor(color);
        }
        @Override
        public void draw(Canvas canvas) {
            canvas.save();
            canvas.translate(getBounds().left, getBounds().top);
            canvas.scale(getBounds().width() / 24f, getBounds().height() / 24f);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(1.7f);
            paint.setStrokeCap(Paint.Cap.ROUND);
            if (kind == 0) {
                canvas.drawRoundRect(4, 3, 20, 21, 3, 3, paint);
                canvas.drawLine(4, 11, 20, 11, paint);
                canvas.drawLine(8, 6, 8, 8, paint);
                canvas.drawLine(8, 14, 8, 17, paint);
            } else if (kind == 1) {
                canvas.drawCircle(12, 12, 8, paint);
                canvas.drawCircle(12, 12, 4, paint);
            } else {
                canvas.drawLine(4, 6, 20, 6, paint);
                canvas.drawLine(4, 12, 20, 12, paint);
                canvas.drawLine(4, 18, 20, 18, paint);
                paint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(9, 6, 2.5f, paint);
                canvas.drawCircle(16, 12, 2.5f, paint);
                canvas.drawCircle(7, 18, 2.5f, paint);
            }
            canvas.restore();
        }
        @Override
        public void setAlpha(int alpha) {
            paint.setAlpha(alpha);
        }
        @Override
        public void setColorFilter(ColorFilter filter) {
            paint.setColorFilter(filter);
        }
        @Override public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
        }
    }
}