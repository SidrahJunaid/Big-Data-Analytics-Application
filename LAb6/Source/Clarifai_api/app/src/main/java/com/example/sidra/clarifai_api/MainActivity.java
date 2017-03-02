package com.example.sidra.clarifai_api;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewStub;

import com.clarifai.api.ClarifaiClient;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public void onPredict(View v) {

        final ClarifaiClient client = new ClarifaiBuilder("bUzFIeQ4_f_ppceNjZ6b2a6v7R-JWaaUL12ISlFA", "AS1W894o7WWIL712OSlWUZr6TCC6JrVBuqEMQZGi")


        final List<ClarifaiOutput<Concept>> onPredict =
                client.getDefaultModel.generalModel()
                .predict()
                .withInputs(
                        ClarifaiInput.forImage.of("https://samples.clarifai.com/download.jpeg")
                )
        }

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                RxPermissions.getInstance(this)
                        .request(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .subscribe(new Action1<Boolean>() {
                            @Override public void call(Boolean granted) {
                                if (!granted) {
                                    new AlertDialog.Builder(BaseActivity.this)
                                            .setCancelable(false)
                                            .setMessage(R.string.error_external_storage_permission_not_granted)
                                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                                @Override public void onClick(DialogInterface dialog, int which) {
                                                    moveTaskToBack(true);
                                                    finish();
                                                }
                                            })
                                            .show();
                                }
                            }
                        });
            }

            @SuppressLint("InflateParams") final View wrapper = getLayoutInflater().inflate(R.layout.activity_wrapper, null);
            final ViewStub stub = ButterKnife.findById(wrapper, R.id.content_stub);
            stub.setLayoutResource(layoutRes());
            stub.inflate();
            setContentView(wrapper);
            unbinder = ButterKnife.bind(this);

            final Toolbar toolbar = ButterKnife.findById(this, R.id.toolbar);

            final Drawer drawer = new DrawerBuilder()
                    .withActivity(this)
                    .withToolbar(toolbar)
                    .withDrawerItems(drawerItems())
                    .build();

            // Show the "hamburger"
            setSupportActionBar(toolbar);
            final ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(false);
            }
            drawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);

            // Set the selected index to what the intent said we're in
            drawer.setSelectionAtPosition(getIntent().getIntExtra(INTENT_EXTRA_DRAWER_POSITION, 0));
        }

        @Override
        protected void onDestroy() {
            unbinder.unbind();
            super.onDestroy();
        }

        @NonNull
        protected List<IDrawerItem> drawerItems() {
            return Arrays.<IDrawerItem>asList(
                    new PrimaryDrawerItem()
                            .withName(R.string.drawer_item_recognize_tags)
                            .withOnDrawerItemClickListener(goToActivityListener(RecognizeConceptsActivity.class))
            );
        }

        /**
         * @return the layout file to use. This is used in place of {@code R.id.content_stub} in the activity_wrapper.xml
         * file, by using a {@link ViewStub}.
         */
        @LayoutRes
        protected abstract int layoutRes();

        private Drawer.OnDrawerItemClickListener goToActivityListener(
                @NonNull final Class<? extends Activity> activityClass) {
            return new Drawer.OnDrawerItemClickListener() {
                @Override
                public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                    // Don't open a new activity if we're already in the activity the user clicked on
                    if (!drawerItem.isSelected()) {
                        startActivity(new Intent(BaseActivity.this, activityClass).putExtra(INTENT_EXTRA_DRAWER_POSITION, position));
                    }
                    return true;
                }
            };
        }
    }
