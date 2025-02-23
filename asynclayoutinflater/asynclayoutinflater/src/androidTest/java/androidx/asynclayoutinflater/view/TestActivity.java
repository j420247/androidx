/*
 * Copyright 2018 The Android Open Source Project
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
package androidx.asynclayoutinflater.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.asynclayoutinflater.test.R;

public class TestActivity extends AppCompatActivity {

    private AsyncLayoutInflater mAsyncLayoutInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_AppCompat);
        super.onCreate(savedInstanceState);
        mAsyncLayoutInflater = new AsyncLayoutInflater(this);
    }

    public AsyncLayoutInflater getAsyncLayoutInflater() {
        return mAsyncLayoutInflater;
    }
}
