/*******************************************************************************
 * Copyright (C) 2013 Snowdream Mobile
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/

package com.github.snowdream.android.util;

import android.annotation.TargetApi;
import android.os.Build;

/**
 * @author snowdream <yanghui1986527@gmail.com>
 * @date 2013年9月8日
 * @version v1.0
 */
public abstract class AsyncTask<Params, Progress, Result> extends
        android.os.AsyncTask<Params, Progress, Result> {
    private TaskListener<Progress, Result> listener = null;

    public AsyncTask() {
        super();
    }

    public AsyncTask(TaskListener<Progress, Result> listener) {
        super();
        this.listener = listener;
    }

    /**
     * TODO <BR/>
     * if error occurs,carry it out.<BR/>
     * 
     * <pre>
     * if (listener != null) {
     *    listener.onError(new Throwable());
     * }
     * </pre>
     */
    @Override
    protected abstract Result doInBackground(Params... params);

    @Override
    protected void onCancelled() {
        super.onCancelled();
        if (listener != null) {
            listener.onCancelled();
            listener.onFinish();
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCancelled(Result result) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            super.onCancelled(result);
        }else{
            super.onCancelled();
        }
    }

    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
        if (listener != null) {
            listener.onSuccess(result);
            listener.onFinish();
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (listener != null) {
            listener.onStart();
        }
    }

    @Override
    protected void onProgressUpdate(Progress... values) {
        super.onProgressUpdate(values);
        if (listener != null) {
            listener.onProgressUpdate(values);
        }
    }
}
