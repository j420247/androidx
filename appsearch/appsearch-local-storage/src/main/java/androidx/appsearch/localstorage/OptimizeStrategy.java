/*
 * Copyright 2021 The Android Open Source Project
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

package androidx.appsearch.localstorage;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

import com.google.android.icing.proto.GetOptimizeInfoResultProto;

/**
 * An interface class for implementing a strategy to determine when to trigger
 * {@link AppSearchImpl#optimize()}.
 * @hide
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public interface OptimizeStrategy {

    /**
     * Determines whether {@link AppSearchImpl#optimize()} need to be triggered to release garbage
     * resources in AppSearch base on the given information.
     *
     * @param optimizeInfo The proto object indicates the number of garbage resources in AppSearch.
     * @return {@code true} if {@link AppSearchImpl#optimize()} need to be triggered.
     */
    boolean shouldOptimize(@NonNull GetOptimizeInfoResultProto optimizeInfo);
}
