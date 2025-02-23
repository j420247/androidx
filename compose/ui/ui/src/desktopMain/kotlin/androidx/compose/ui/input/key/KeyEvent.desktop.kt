/*
 * Copyright 2020 The Android Open Source Project
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

package androidx.compose.ui.input.key

import androidx.compose.ui.awt.awtEventOrNull
import java.awt.event.KeyEvent.KEY_LOCATION_STANDARD
import java.awt.event.KeyEvent.KEY_LOCATION_UNKNOWN
import java.awt.event.KeyEvent.KEY_PRESSED
import java.awt.event.KeyEvent.KEY_RELEASED
import java.awt.event.KeyEvent.VK_UNDEFINED

/**
 * The native desktop [KeyEvent][KeyEventAwt].
 */
actual typealias NativeKeyEvent = Any

/**
 * The key that was pressed.
 */
actual val KeyEvent.key: Key
    get() = Key(
        awtEventOrNull?.keyCode ?: VK_UNDEFINED,
        awtEventOrNull?.keyLocationForCompose ?: KEY_LOCATION_STANDARD
    )

private val java.awt.event.KeyEvent.keyLocationForCompose get() =
    if (keyLocation == KEY_LOCATION_UNKNOWN) KEY_LOCATION_STANDARD else keyLocation

/**
 * The UTF16 value corresponding to the key event that was pressed. The unicode character
 * takes into account any meta keys that are pressed (eg. Pressing shift results in capital
 * alphabets). The UTF16 value uses the
 * [U+n notation][http://www.unicode.org/reports/tr27/#notation] of the Unicode Standard.
 *
 * An [Int] is used instead of a [Char] so that we can support supplementary characters. The
 * Unicode Standard allows for characters whose representation requires more than 16 bits.
 * The range of legal code points is U+0000 to U+10FFFF, known as Unicode scalar value.
 *
 * The set of characters from U+0000 to U+FFFF is sometimes referred to as the Basic
 * Multilingual Plane (BMP). Characters whose code points are greater than U+FFFF are called
 * supplementary characters. In this representation, supplementary characters are represented
 * as a pair of char values, the first from the high-surrogates range, (\uD800-\uDBFF), the
 * second from the low-surrogates range (\uDC00-\uDFFF).
 */
actual val KeyEvent.utf16CodePoint: Int
    get() = awtEventOrNull?.keyChar?.code ?: 0

/**
 * The [type][KeyEventType] of key event.
 */
actual val KeyEvent.type: KeyEventType
    get() = when (awtEventOrNull?.id) {
        KEY_PRESSED -> KeyEventType.KeyDown
        KEY_RELEASED -> KeyEventType.KeyUp
        else -> KeyEventType.Unknown
    }

/**
 * Indicates whether the Alt key is pressed.
 */
actual val KeyEvent.isAltPressed: Boolean
    get() = awtEventOrNull?.isAltDown == true || awtEventOrNull?.isAltGraphDown == true

/**
 * Indicates whether the Ctrl key is pressed.
 */
actual val KeyEvent.isCtrlPressed: Boolean
    get() = awtEventOrNull?.isControlDown == true

/**
 * Indicates whether the Meta key is pressed.
 */
actual val KeyEvent.isMetaPressed: Boolean
    get() = awtEventOrNull?.isMetaDown == true

/**
 * Indicates whether the Shift key is pressed.
 */
actual val KeyEvent.isShiftPressed: Boolean
    get() = awtEventOrNull?.isShiftDown == true
