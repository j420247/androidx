/*
 * Copyright 2022 The Android Open Source Project
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

package androidx.car.app.hardware.common;

import static com.google.common.truth.Truth.assertThat;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.car.hardware.CarPropertyValue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.internal.DoNotInstrument;


@RunWith(RobolectricTestRunner.class)
@DoNotInstrument
public class PropertyUtilsTest {
    private static final int PROPERTY_ID = 123;
    private static final int GLOBAL_AREA_ID = 0;
    private static final Integer PROPERTY_VALUE = 5;
    private static final long TIMESTAMP_NANOS = 43_000_000;
    private static final int TIMESTAMP_MILLIS = 43;
    private static final CarPropertyResponse.Builder<Object> CAR_PROPERTY_RESPONSE_BUILDER =
            CarPropertyResponse.builder().setPropertyId(PROPERTY_ID).setTimestampMillis(
                    TIMESTAMP_MILLIS);

    @Rule
    public final MockitoRule mockito = MockitoJUnit.rule();

    @Mock
    private CarPropertyValue<Integer> mCarPropertyValue;

    @Before
    public void setUp() {
        when(mCarPropertyValue.getPropertyId()).thenReturn(PROPERTY_ID);
        when(mCarPropertyValue.getAreaId()).thenReturn(GLOBAL_AREA_ID);
        when(mCarPropertyValue.getValue()).thenReturn(PROPERTY_VALUE);
        when(mCarPropertyValue.getTimestamp()).thenReturn(TIMESTAMP_NANOS);
    }

    @Test
    public void convertPropertyValueToPropertyResponse_ifStatusAvailableThenAddsValue() {
        when(mCarPropertyValue.getStatus()).thenReturn(CarPropertyValue.STATUS_AVAILABLE);
        assertThat(
                PropertyUtils.convertPropertyValueToPropertyResponse(mCarPropertyValue)).isEqualTo(
                CAR_PROPERTY_RESPONSE_BUILDER.setStatus(CarValue.STATUS_SUCCESS).setValue(
                        PROPERTY_VALUE).build());
    }

    @Test
    public void convertPropertyValueToPropertyResponse_ifStatusIsUnavailableThenIgnoresValue() {
        when(mCarPropertyValue.getStatus()).thenReturn(CarPropertyValue.STATUS_UNAVAILABLE);
        assertThat(
                PropertyUtils.convertPropertyValueToPropertyResponse(mCarPropertyValue)).isEqualTo(
                CAR_PROPERTY_RESPONSE_BUILDER.setStatus(CarValue.STATUS_UNAVAILABLE).build());
        verify(mCarPropertyValue, never()).getValue();
    }

    @Test
    public void convertPropertyValueToPropertyResponse_ifStatusIsErrorThenIgnoresValue() {
        when(mCarPropertyValue.getStatus()).thenReturn(CarPropertyValue.STATUS_ERROR);
        assertThat(
                PropertyUtils.convertPropertyValueToPropertyResponse(mCarPropertyValue)).isEqualTo(
                CAR_PROPERTY_RESPONSE_BUILDER.setStatus(CarValue.STATUS_UNKNOWN).build());
        verify(mCarPropertyValue, never()).getValue();
    }
}
